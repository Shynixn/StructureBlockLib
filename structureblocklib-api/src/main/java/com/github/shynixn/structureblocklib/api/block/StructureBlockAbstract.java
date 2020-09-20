package com.github.shynixn.structureblocklib.api.block;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import org.jetbrains.annotations.NotNull;

public interface StructureBlockAbstract {
    /**
     * Changes the type of the structureBlock.
     *
     * @param structureMode structureMode.
     */
    void setStructureMode(@NotNull StructureMode structureMode);

    /**
     * Returns the type of the structureBlock.
     *
     * @return structureMode.
     */
    @NotNull
    StructureMode getStructureMode();
}
