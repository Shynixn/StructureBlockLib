package com.github.shynixn.structureblocklib.api.service;

import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;

public interface StructureWorldService {
    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    Object readStructureFromWorld(StructureReadMeta meta);
}
