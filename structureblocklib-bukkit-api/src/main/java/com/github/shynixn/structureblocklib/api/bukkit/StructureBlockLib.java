package com.github.shynixn.structureblocklib.api.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Api Bukkit implementation.
 */
public class StructureBlockLib implements StructureBlockLibApi {
    private final Method structureSaveCreate;

    /**
     * Init.
     */
    public StructureBlockLib() {
        try {
            structureSaveCreate = Class.forName("com.github.shynixn.structureblocklib.core.bukkit.Main")
                    .getDeclaredMethod("createStructureSaver", Plugin.class);
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
}
