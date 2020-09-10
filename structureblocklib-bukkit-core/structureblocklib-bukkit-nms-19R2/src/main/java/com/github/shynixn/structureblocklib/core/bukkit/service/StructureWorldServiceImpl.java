package com.github.shynixn.structureblocklib.core.bukkit.service;

import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

/**
 * Implementation to interact with structures in the world.
 */
public class StructureWorldServiceImpl implements StructureWorldService {
    /**
     * Places the blocks in the world defined by the given structure.
     *
     * @param meta      Meta data to describe the placement.
     * @param structure NMS structure.
     */
    @Override
    public void placeStructureToWorld(StructurePlaceMeta meta, Object structure) throws Exception {
        if (!(structure instanceof DefinedStructure)) {
            throw new IllegalArgumentException("DefinedStructure has to be an NMS handle!");
        }

        DefinedStructure definedStructure = (DefinedStructure) structure;
        World world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPosition cornerBlock = new BlockPosition((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        DefinedStructureInfo info = new DefinedStructureInfo();
        // TODO: Settings
        definedStructure.a(world, cornerBlock, info);
    }

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    @Override
    public Object readStructureFromWorld(StructureReadMeta meta) throws Exception {
        World world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPosition cornerBlock = new BlockPosition((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        BlockPosition offsetBlock = new BlockPosition((int) meta.getOffset().getX(), (int) meta.getOffset().getY(), (int) meta.getOffset().getZ());
        Block structureVoid = (Block) Blocks.class.getDeclaredField(meta.getStructureVoidTypeName()).get(null);

        DefinedStructure definedStructure = new DefinedStructure();
        definedStructure.a(world, cornerBlock, offsetBlock, meta.isIncludeEntitiesEnabled(), structureVoid);
        definedStructure.a(meta.getAuthor());
        return definedStructure;
    }
}
