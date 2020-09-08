package com.github.shynixn.structureblocklib.api.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import org.bukkit.plugin.Plugin;

/**
 * Root Api entry.
 */
public interface StructureBlockLibApi {
    /**
     * Accesses the {@link StructureBlockLibApi}.
     */
    StructureBlockLib INSTANCE = new StructureBlockLib();

    /**
     * Creates a new instance of the {@link StructureSaver} for saving
     * structures from a minecraft world into a target.
     *
     * @param plugin Plugin instance using this API.
     * @return A new instance of the {@link StructureSaver}.
     */
    StructureSaver saveStructure(Plugin plugin);
}
