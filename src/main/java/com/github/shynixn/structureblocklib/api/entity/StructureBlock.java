package com.github.shynixn.structureblocklib.api.entity;

import org.bukkit.block.BlockState;

public interface StructureBlock extends BlockState {
    /**
     * Changes the type of the structureBlock
     *
     * @param structureMode structureMode
     */
    void setStructureMode(StructureMode structureMode);

    /**
     * Returns the type of the structureBlock
     *
     * @return structureMode
     */
    StructureMode getStructureMode();
}
