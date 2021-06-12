package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import org.jetbrains.annotations.NotNull;

public class StructurePlaceMetaImpl implements StructurePlaceMeta {
    public Position location;
    public boolean includeEntities;
    public StructureRotation rotation = StructureRotation.NONE;
    public StructureMirror mirror = StructureMirror.NONE;
    public float integrity = 1.0F;
    public long seed = 0L;

    /**
     * Should entities be loaded.
     * Default false.
     *
     * @return flag.
     */
    @Override
    public boolean isIncludeEntitiesEnabled() {
        return includeEntities;
    }

    /**
     * Gets the target mirror type.
     * Default StructureMirror.NONE.
     *
     * @return {@link StructureMirror}.
     */
    @Override
    public StructureMirror getMirrorType() {
        return mirror;
    }

    /**
     * Gets the target rotation type.
     * Default StructureRotation.NONE.
     *
     * @return {@link StructureRotation}.
     */
    @Override
    public StructureRotation getRotationType() {
        return rotation;
    }

    /**
     * Gets the target integrity.
     * Default 1.0.
     * 1.0 ->  Every block which is present in the structure file is placed in the world.
     * smaller 1.0 -> Blocks get randomly removed by loading depending on the given {@code getSeed}.
     *
     * @return integrity.
     */
    @Override
    public float getIntegrity() {
        return integrity;
    }

    /**
     * Gets the target seed.
     * Default 0L.
     * The seed is used to randomly remove blocks if the integrity {@code getIntegrity} is less than 1.0.
     *
     * @return seed.
     */
    @Override
    public long getSeed() {
        return seed;
    }

    /**
     * Gets the source Location.
     *
     * @return location.
     */
    @Override
    public @NotNull Position getLocation() {
        return location;
    }
}
