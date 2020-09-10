package com.github.shynixn.structureblocklib.api.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureLoader;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Api Bukkit implementation.
 */
public class StructureBlockLib implements StructureBlockLibApi {
    private final Method structureSaveCreate;
    private final Method structureLoadCreate;

    /**
     * Init.
     */
    public StructureBlockLib() {
        try {
            structureSaveCreate = Class.forName("com.github.shynixn.structureblocklib.core.bukkit.Main")
                    .getDeclaredMethod("createStructureSaver", Plugin.class);
            structureLoadCreate = Class.forName("com.github.shynixn.structureblocklib.core.bukkit.Main")
                    .getDeclaredMethod("createStructureLoader", Plugin.class);
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new instance of the {@link StructureSaver} for saving
     * structures from a minecraft world into a target.
     *
     * @param plugin Plugin instance using this API.
     * @return A new instance of the {@link StructureSaver}.
     */
    @Override
    public StructureSaver saveStructure(Plugin plugin) {
        try {
            return (StructureSaver) structureSaveCreate.invoke(null, plugin);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a new instance of the {@link StructureLoader} for loading
     * structures from a source into the minecraft world.
     *
     * @param plugin Plugin instance using this API.
     * @return A new instance of the {@link StructureLoader}.
     */
    @Override
    public StructureLoader loadStructure(Plugin plugin) {
        try {
            return (StructureLoader) structureLoadCreate.invoke(null, plugin);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
