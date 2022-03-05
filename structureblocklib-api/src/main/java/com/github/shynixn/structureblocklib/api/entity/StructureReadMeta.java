package com.github.shynixn.structureblocklib.api.entity;

import org.jetbrains.annotations.NotNull;

public interface StructureReadMeta {
    /**
     * Gets the source Location.
     *
     * @return location.
     */
    @NotNull
    Position getLocation();

    /**
     * Gets the offset of the selection.
     *
     * @return offset.
     */
    @NotNull
    Position getOffset();

    /**
     * Gets the author.
     * The author is a optional meta data in the final
     * structure file.
     *
     * @return author.
     */
    @NotNull String getAuthor();

    /**
     * Should entities included in the saved file.
     * Default false.
     *
     * @return flag.
     */
    boolean isIncludeEntitiesEnabled();

    /**
     * Deprecated as this internal value is no longer exposed since 1.18.2.
     */
    @NotNull
    @Deprecated()
    String getStructureVoidTypeName();
}
