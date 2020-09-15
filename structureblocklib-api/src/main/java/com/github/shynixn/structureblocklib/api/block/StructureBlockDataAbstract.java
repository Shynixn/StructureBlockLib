package com.github.shynixn.structureblocklib.api.block;

import org.jetbrains.annotations.Nullable;

public interface StructureBlockDataAbstract extends StructureBlockAbstract {
    /**
     * Sets custom meta data. Please use the minecraft documentation to find out more.
     *
     * @param blockNameMetaData customMeta.
     */
    void setBlockNameMetaData(@Nullable String blockNameMetaData);

    /**
     * Returns the custom meta data.
     *
     * @return customMeta
     */
    @Nullable
    String getBlockNameMetaData();
}
