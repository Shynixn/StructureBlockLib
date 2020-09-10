package com.github.shynixn.structureblocklib.api.entity;

import org.jetbrains.annotations.NotNull;

public interface StructurePlaceMeta {
    /**
     * Gets the source Location.
     *
     * @return location.
     */
    @NotNull
    Position getLocation();
}
