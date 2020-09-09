package com.github.shynixn.structureblocklib.core.bukkit.service;

import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StructureWorldServiceImpl implements StructureWorldService {

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    @Override
    public Object readStructureFromWorld(StructureReadMeta meta) {
        return null;
    }
}
