package com.shynixn.structureblocklib.api.entity;

import org.bukkit.block.BlockState;

/**
 * Created by Shynixn
 */
public interface StructureBlock extends BlockState {
    void setStructureMode(StructureMode structureMode);

    StructureMode getStructureMode();
}
