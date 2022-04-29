package com.github.shynixn.structureblocklib.api.entity;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

public interface StructurePlaceMeta {
    /**
     * Gets the source Location.
     *
     * @return location.
     */
    @NotNull
    Position getLocation();

    /**
     * Gets the processors when placing this structure.
     *
     * @return processors.
     */
    @NotNull
    List<Function<?, Boolean>> getProcessors();

    /**
     * Should entities be loaded.
     * Default false.
     *
     * @return flag.
     */
    boolean isIncludeEntitiesEnabled();

    /**
     * Gets the target mirror type.
     * Default StructureMirror.NONE.
     *
     * @return {@link StructureMirror}.
     */
    StructureMirror getMirrorType();

    /**
     * Gets the target rotation type.
     * Default StructureRotation.NONE.
     *
     * @return {@link StructureRotation}.
     */
    StructureRotation getRotationType();

    /**
     * Gets the target integrity.
     * Default 1.0.
     * 1.0  Every block which is present in the structure file is placed in the world.
     * 1.0 Blocks get randomly removed by loading depending on the given {@code getSeed}.
     *
     * @return integrity.
     */
    float getIntegrity();

    /**
     * Gets the target seed.
     * Default 0L.
     * The seed is used to randomly remove blocks if the integrity {@code getIntegrity} is less than 1.0.
     *
     * @return seed.
     */
    long getSeed();
}
