package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import org.jetbrains.annotations.NotNull;

public class StructurePlaceMetaImpl implements StructurePlaceMeta {
    public Position location;

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
