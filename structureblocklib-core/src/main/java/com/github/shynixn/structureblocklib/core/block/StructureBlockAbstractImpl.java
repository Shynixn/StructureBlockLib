package com.github.shynixn.structureblocklib.core.block;

import com.github.shynixn.structureblocklib.api.block.StructureBlockAbstract;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import org.jetbrains.annotations.NotNull;

public class StructureBlockAbstractImpl implements StructureBlockAbstract {
    private StructureMode mode = StructureMode.CORNER;

    /**
     * Changes the type of the structureBlock.
     *
     * @param structureMode structureMode.
     */
    @Override
    public void setStructureMode(@NotNull StructureMode structureMode) {
        this.mode = structureMode;
    }

    /**
     * Returns the type of the structureBlock.
     *
     * @return structureMode.
     */
    @Override
    public @NotNull StructureMode getStructureMode() {
        return mode;
    }
}
