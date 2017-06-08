package com.github.shynixn.structureblocklib.api.entity;

public interface StructureBlockData extends StructureBlock {
    /**
     * Sets custom meta data. Please use the minecraft documentation to find out more
     *
     * @param blockNameMetaData customMeta
     */
    void setBlockNameMetaData(String blockNameMetaData);

    /**
     * Returns the custom meta data
     *
     * @return customMeta
     */
    String getBlockNameMetaData();
}
