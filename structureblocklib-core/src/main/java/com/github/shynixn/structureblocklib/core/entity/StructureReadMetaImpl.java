package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import org.jetbrains.annotations.NotNull;

public class StructureReadMetaImpl implements StructureReadMeta {
    public Position location;
    public Position offset;
    public boolean includeEntities;
    public String structureVoid;
    public String author = "";

    /**
     * Gets the source Location.
     *
     * @return location.
     */
    @Override
    public @NotNull Position getLocation() {
        return location;
    }

    /**
     * Gets the offset of the selection.
     *
     * @return offset.
     */
    @Override
    public @NotNull Position getOffset() {
        return offset;
    }

    /**
     * Gets the author.
     * The author is a optional meta data in the final
     * structure file.
     *
     * @return author.
     */
    @Override
    public @NotNull String getAuthor() {
        return author;
    }

    /**
     * Should entities included in the saved file.
     * Default false.
     *
     * @return flag.
     */
    @Override
    public boolean isIncludeEntitiesEnabled() {
        return includeEntities;
    }

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
    @Override
    public @NotNull String getStructureVoidTypeName() {
        return structureVoid;
    }
}
