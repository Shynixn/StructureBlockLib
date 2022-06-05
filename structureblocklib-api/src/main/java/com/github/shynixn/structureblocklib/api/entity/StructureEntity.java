package com.github.shynixn.structureblocklib.api.entity;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents an entity stored in a structure.
 */
public interface StructureEntity<Entity, Location> {
    /**
     * Tries to spawn an entity from the stored nbt data at the given location.
     *
     * @param location Target {@link  Location}.
     * @return A valid entity or empty optional.
     */
    Optional<Entity> spawnEntity(Location location);

    /**
     * Generates a virtual entity, which is not added to the world but the data
     * can be extracted. This entity is destroyed after peeking.
     *
     * @return A valid entity or empty optional.
     */
    Optional<Entity> getEntity();

    /**
     * Gets the relative source location inside the structure.
     *
     * @return {@link Location}.
     */
    Location getSourceLocation();

    /**
     * Gets the nbt data of the stored entity in string format.
     *
     * @return NBT data.
     */
    String getNbtData();
}
