package com.github.shynixn.structureblocklib.bukkit;

import com.github.shynixn.structureblocklib.api.block.StructureBlockAbstract;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureLoader;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import com.github.shynixn.structureblocklib.api.enumeration.Version;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.core.block.StructureBlockAbstractImpl;
import com.github.shynixn.structureblocklib.bukkit.entity.StructureLoaderImpl;
import com.github.shynixn.structureblocklib.bukkit.entity.StructureSaverImpl;
import com.github.shynixn.structureblocklib.bukkit.service.ProxyServiceImpl;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

public class Main {
    /**
     * Creates a new structure block reference to the world for the given plugin.
     *
     * @param plugin plugin-
     * @return Instance.
     */
    public static Object createStructureBlock(Plugin plugin, Location location) {
        ProxyService proxyService = new ProxyServiceImpl(plugin);
        Version version = proxyService.getServerVersion();

        try {
            TypeConversionService typeConversionService = (TypeConversionService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.TypeConversionServiceImpl")
                    .getDeclaredConstructor().newInstance();
            StructureSaver saver = createStructureSaver(plugin);
            StructureLoader loader = createStructureLoader(plugin);
            StructureBlockAbstractImpl<Location, Vector, Block, World> abstractBlock = new StructureBlockAbstractImpl<>(proxyService, loader, saver);
            return findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.CraftStructureBlock")
                    .getDeclaredConstructor(StructureBlockAbstractImpl.class, TypeConversionService.class, Block.class)
                    .newInstance(abstractBlock, typeConversionService, location.getBlock());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new structure saver for the given plugin.
     *
     * @param plugin plugin.
     * @return saver.
     */
    public static StructureSaver createStructureSaver(Plugin plugin) {
        ProxyService proxyService = new ProxyServiceImpl(plugin);
        Version version = proxyService.getServerVersion();

        try {
            TypeConversionService typeConversionService = (TypeConversionService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.TypeConversionServiceImpl")
                    .getDeclaredConstructor().newInstance();
            StructureWorldService worldService = (StructureWorldService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.StructureWorldServiceImpl")
                    .getDeclaredConstructor(TypeConversionService.class)
                    .newInstance(typeConversionService);
            StructureSerializationService serializationService = (StructureSerializationService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.StructureSerializationServiceImpl")
                    .getDeclaredConstructor().newInstance();
            return new StructureSaverImpl(proxyService, serializationService, worldService);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new structure loader for the given plugin.
     *
     * @param plugin plugin.
     * @return loader.
     */
    public static StructureLoader createStructureLoader(Plugin plugin) {
        ProxyService proxyService = new ProxyServiceImpl(plugin);
        Version version = proxyService.getServerVersion();

        try {
            TypeConversionService typeConversionService = (TypeConversionService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.TypeConversionServiceImpl")
                    .getDeclaredConstructor().newInstance();
            StructureWorldService worldService = (StructureWorldService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.StructureWorldServiceImpl")
                    .getDeclaredConstructor(TypeConversionService.class)
                    .newInstance(typeConversionService);
            StructureSerializationService serializationService = (StructureSerializationService) findClazz(version, "com.github.shynixn.structureblocklib.bukkit.VERSION.StructureSerializationServiceImpl")
                    .getDeclaredConstructor().newInstance();
            return new StructureLoaderImpl(proxyService, serializationService, worldService);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a version dependent clazz.
     *
     * @param version version.
     * @param name    name.
     * @return clazz.
     */
    private static Class<?> findClazz(Version version, String name) throws ClassNotFoundException {
        return Class.forName(name.replace("VERSION", version.getBukkitId()));
    }
}
