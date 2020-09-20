package com.github.shynixn.structureblocklib.api.service;

import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;

public interface StructureWorldService {
    /**
     * Places the blocks in the world defined by the given structure.
     *
     * @param meta      Meta data to describe the placement.
     * @param structure NMS structure.
     */
    void placeStructureToWorld(StructurePlaceMeta meta, Object structure) throws Exception;

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    Object readStructureFromWorld(StructureReadMeta meta) throws Exception;
}
