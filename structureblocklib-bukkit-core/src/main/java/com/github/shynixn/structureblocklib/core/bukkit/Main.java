package com.github.shynixn.structureblocklib.core.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureLoader;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.core.bukkit.entity.StructureLoaderImpl;
import com.github.shynixn.structureblocklib.core.bukkit.entity.StructureSaverImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.ProxyServiceImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.StructureSerializationServiceImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.StructureWorldServiceImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.TypeConversionServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Main {
    /**
     * Creates a new structure saver for the given plugin.
     *
     * @param plugin plugin.
     * @return saver.
     */
    public static StructureSaver createStructureSaver(Plugin plugin) {
        ProxyService proxyService = new ProxyServiceImpl(plugin);
        TypeConversionService typeConversionService = new TypeConversionServiceImpl();
        StructureWorldService worldService = new StructureWorldServiceImpl(typeConversionService);
        StructureSerializationService serializationService = new StructureSerializationServiceImpl(Bukkit.getWorlds().get(0));
        return new StructureSaverImpl(proxyService, serializationService, worldService);
    }

    /**
     * Creates a new structure loader for the given plugin.
     *
     * @param plugin plugin.
     * @return loader.
     */
    public static StructureLoader createStructureLoader(Plugin plugin) {
        ProxyService proxyService = new ProxyServiceImpl(plugin);
        TypeConversionService typeConversionService = new TypeConversionServiceImpl();
        StructureWorldService worldService = new StructureWorldServiceImpl(typeConversionService);
        StructureSerializationService serializationService = new StructureSerializationServiceImpl(Bukkit.getWorlds().get(0));
        return new StructureLoaderImpl(proxyService, serializationService, worldService);
    }
}
