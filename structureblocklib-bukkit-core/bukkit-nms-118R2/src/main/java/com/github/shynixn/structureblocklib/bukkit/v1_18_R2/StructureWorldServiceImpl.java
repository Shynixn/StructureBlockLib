package com.github.shynixn.structureblocklib.bukkit.v1_18_R2;

import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;

import java.util.Random;

/**
 * Implementation to interact with structures in the world.
 */
public class StructureWorldServiceImpl implements StructureWorldService {
    private final TypeConversionService conversionService;

    /**
     * Creates a new service with dependencies.
     *
     * @param conversionService dependency.
     */
    public StructureWorldServiceImpl(TypeConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Places the blocks in the world defined by the given structure.
     *
     * @param meta      Meta data to describe the placement.
     * @param structure NMS structure.
     */
    @Override
    public void placeStructureToWorld(StructurePlaceMeta meta, Object structure) throws Exception {
        if (!(structure instanceof StructureTemplate)) {
            throw new IllegalArgumentException("StructureTemplate has to be an NMS handle!");
        }

        StructureTemplate template = (StructureTemplate) structure;
        ServerLevel world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPos cornerBlock = new BlockPos((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        StructurePlaceSettings info = new StructurePlaceSettings();
        info.setIgnoreEntities(!meta.isIncludeEntitiesEnabled());
        info.setMirror((Mirror) conversionService.convertToMirrorHandle(meta.getMirrorType()));
        info.setRotation((Rotation) conversionService.convertToRotationHandle(meta.getRotationType()));

        if (meta.getIntegrity() < 1.0F) {
            info.clearProcessors();
            float rotation = Mth.clamp(meta.getIntegrity(), 0.0F, 1.0F);
            BlockRotProcessor rotationProcessor = new BlockRotProcessor(rotation);
            Random random = new Random();

            if (meta.getSeed() != 0L) {
                random = new Random(meta.getSeed());
            }

            info.addProcessor(rotationProcessor);
            info.setRandom(random);
        }

        template.placeInWorld(world, cornerBlock, cornerBlock, info, new Random(), 2);
    }

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    @Override
    public Object readStructureFromWorld(StructureReadMeta meta) {
        ServerLevel world = ((CraftWorld) Bukkit.getWorld(meta.getLocation().getWorldName())).getHandle();
        BlockPos cornerBlock = new BlockPos((int) meta.getLocation().getX(), (int) meta.getLocation().getY(), (int) meta.getLocation().getZ());
        BlockPos offsetBlock = new BlockPos((int) meta.getOffset().getX(), (int) meta.getOffset().getY(), (int) meta.getOffset().getZ());
        Block structureVoid = Blocks.STRUCTURE_VOID;

        StructureTemplate template = new StructureTemplate();
        template.fillFromWorld(world, cornerBlock, offsetBlock, meta.isIncludeEntitiesEnabled(), structureVoid);
        template.setAuthor(meta.getAuthor());
        return template;
    }
}
