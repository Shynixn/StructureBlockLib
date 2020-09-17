package com.github.shynixn.structureblocklib.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureLoader;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.core.block.StructureBlockAbstractImpl;
import com.github.shynixn.structureblocklib.bukkit.entity.StructureLoaderImpl;
import com.github.shynixn.structureblocklib.bukkit.entity.StructureSaverImpl;
import com.github.shynixn.structureblocklib.bukkit.service.ProxyServiceImpl;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.CraftStructureBlock;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.StructureSerializationServiceImpl;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.StructureWorldServiceImpl;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.TypeConversionServiceImpl;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class Main {
    /**
     * Creates a new structure block reference to the world for the given plugin.
     *
     * @param plugin plugin-
     * @return Instance.
     */
    public static Object createStructureBlock(Plugin plugin, Location location) {
        ProxyService proxyService = new ProxyServiceImpl(plugin);
        TypeConversionService typeConversionService = new TypeConversionServiceImpl();
        StructureSaver saver = createStructureSaver(plugin);
        StructureLoader loader = createStructureLoader(plugin);
        StructureBlockAbstractImpl<Location, Vector> abstractBlock = new StructureBlockAbstractImpl<>(proxyService, loader, saver);
        return new CraftStructureBlock(abstractBlock, typeConversionService, location.getBlock());
    }

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
        StructureSerializationService serializationService = new StructureSerializationServiceImpl();
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
        StructureSerializationService serializationService = new StructureSerializationServiceImpl();
        return new StructureLoaderImpl(proxyService, serializationService, worldService);
    }
}
