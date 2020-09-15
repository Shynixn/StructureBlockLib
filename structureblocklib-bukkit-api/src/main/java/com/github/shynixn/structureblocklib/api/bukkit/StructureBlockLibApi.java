package com.github.shynixn.structureblocklib.api.bukkit;

import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlock;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureLoader;
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver;
import org.bukkit.Location;
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
     * Gets or creates a {@link StructureBlock} at the given location.
     * This instance hides all structure modes and can be access via simple type casting.
     * StructureBlockSave save = (StructureBlockSave) structureBlock;
     * etc.
     *
     * @param location Location of the block in the world.
     * @param plugin   Plugin instance using this API.
     * @param <T>      Type extending structure block.
     * @return A new instance of the {@link StructureBlock}.
     */
    <T extends StructureBlock> T getStructureBlockAt(Location location, Plugin plugin);

    /**
     * Creates a new instance of the {@link StructureSaver} for saving
     * structures from a minecraft world into a target.
     *
     * @param plugin Plugin instance using this API.
     * @return A new instance of the {@link StructureSaver}.
     */
    StructureSaver saveStructure(Plugin plugin);

    /**
     * Creates a new instance of the {@link StructureLoader} for loading
     * structures from a source into the minecraft world.
     *
     * @param plugin Plugin instance using this API.
     * @return A new instance of the {@link StructureLoader}.
     */
    StructureLoader loadStructure(Plugin plugin);
}
