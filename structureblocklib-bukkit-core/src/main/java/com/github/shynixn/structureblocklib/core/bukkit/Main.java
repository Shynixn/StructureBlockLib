package com.github.shynixn.structureblocklib.core.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.core.bukkit.entity.StructureSaverImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.ProxyServiceImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.StructureSerializationServiceImpl;
import com.github.shynixn.structureblocklib.core.bukkit.service.StructureWorldServiceImpl;
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
        StructureWorldService worldService = new StructureWorldServiceImpl();
        StructureSerializationService serializationService = new StructureSerializationServiceImpl(Bukkit.getWorlds().get(0));
        return new StructureSaverImpl(proxyService, serializationService, worldService);
    }
}
