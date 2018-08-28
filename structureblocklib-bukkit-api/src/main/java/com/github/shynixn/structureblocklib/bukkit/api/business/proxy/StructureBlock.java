package com.github.shynixn.structureblocklib.bukkit.api.business.proxy;

import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMode;
import org.bukkit.block.BlockState;

public interface StructureBlock extends BlockState {
    /**
     * Changes the type of the structureBlock.
     *
     * @param structureMode structureMode
     */
    void setStructureMode(StructureMode structureMode);

    /**
     * Returns the type of the structureBlock.
     *
     * @return structureMode
     */
    StructureMode getStructureMode();
}
