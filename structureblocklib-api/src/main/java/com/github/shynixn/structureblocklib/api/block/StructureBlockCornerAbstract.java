package com.github.shynixn.structureblocklib.api.block;

import org.jetbrains.annotations.Nullable;

public interface StructureBlockCornerAbstract extends StructureBlockAbstract {
    /**
     * Sets the name of the save.
     *
     * @param name name.
     */
    void setSaveName(@Nullable String name);

    /**
     * Returns the name of the save.
     *
     * @return name.
     */
    @Nullable
    String getSaveName();
}
