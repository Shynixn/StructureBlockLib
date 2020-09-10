package com.github.shynixn.structureblocklib.core.bukkit.entity;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.core.entity.StructureSaverAbstractImpl;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StructureSaverImpl extends StructureSaverAbstractImpl<Location, Vector> implements StructureSaver {
    /**
     * Creates a new raw structure save instance.
     *
     * @param proxyService         dependency.
     * @param serializationService dependency.
     * @param worldService         dependency.
     */
    public StructureSaverImpl(ProxyService proxyService, StructureSerializationService serializationService, StructureWorldService worldService) {
        super(proxyService, serializationService, worldService);
    }
}
