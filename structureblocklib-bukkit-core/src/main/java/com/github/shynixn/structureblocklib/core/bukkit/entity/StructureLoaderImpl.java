package com.github.shynixn.structureblocklib.core.bukkit.entity;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureLoader;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.core.entity.StructureLoaderAbstractImpl;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StructureLoaderImpl extends StructureLoaderAbstractImpl<Location, Vector> implements StructureLoader {
    /**
     * Creates a new raw structure load instance.
     *
     * @param proxyService         dependency.
     * @param serializationService dependency.
     * @param worldService         dependency.
     */
    public StructureLoaderImpl(ProxyService proxyService, StructureSerializationService serializationService, StructureWorldService worldService) {
        super(proxyService, serializationService, worldService);
    }
}
