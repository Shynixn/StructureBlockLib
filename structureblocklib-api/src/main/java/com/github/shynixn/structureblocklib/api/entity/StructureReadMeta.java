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
     * Gets the name of the block type
     * which is being used as a Structure_Void.
     * <p>
     * Default STRUCTURE_VOID in 1.10 - Latest, BARRIER_BLOCK in 1.9.
     * <p>
     * If the selected structure contains blocks with this typename.
     * They are going to get ignored.
     *
     * @return Name of the block type.
     */
    @NotNull
    String getStructureVoidTypeName();
}
