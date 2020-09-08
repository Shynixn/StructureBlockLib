package com.github.shynixn.structureblocklib.api.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import org.bukkit.plugin.Plugin;

/**
 * Api Bukkit implementation.
 */
class StructureBlockLib implements StructureBlockLibApi {
    /**
     * Creates a new instance of the {@link StructureSaver} for saving
     * structures from a minecraft world into a target.
     *
     * @param plugin Plugin instance using this API.
     * @return A new instance of the {@link StructureSaver}.
     */
    @Override
    public StructureSaver saveStructure(Plugin plugin) {
        return null;
    }
}
