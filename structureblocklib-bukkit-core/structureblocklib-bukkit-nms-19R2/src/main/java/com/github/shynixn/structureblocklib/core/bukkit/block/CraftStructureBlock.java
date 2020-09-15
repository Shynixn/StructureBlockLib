package com.github.shynixn.structureblocklib.core.bukkit.block;

import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockData;
import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockLoad;
import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockSave;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.block.CraftBlockState;

public class CraftStructureBlock extends CraftBlockState implements StructureBlockData, StructureBlockSave, StructureBlockLoad {
    public CraftStructureBlock(Block block) {
        super(block);
    }

    public CraftStructureBlock(Block block, int flag) {
        super(block, flag);
    }

    public CraftStructureBlock(Material material) {
        super(material);
    }

}
