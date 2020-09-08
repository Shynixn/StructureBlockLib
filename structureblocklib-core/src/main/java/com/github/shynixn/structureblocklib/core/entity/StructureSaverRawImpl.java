package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.entity.ProgressToken;
import com.github.shynixn.structureblocklib.api.entity.StructureSaverRaw;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRestriction;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Interface fluent API to save structures from the world into
 * different targets.
 */
public class StructureSaverRawImpl<L, V> implements StructureSaverRaw<L, V> {
    private final ProxyService proxyService;
    private Position location;
    private Position offset;
    private String author;
    private boolean includeEntities = false;
    private StructureRestriction structureRestriction = StructureRestriction.SINGLE_32;
    private String structureVoid = "STRUCTURE_VOID";

    /**
     * Creates a new raw structure save instance.
     *
     * @param proxyService dependency.
     */
    public StructureSaverRawImpl(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    /**
     * Gets the target Location.
     *
     * @return location.
     */
    @Nullable
    @Override
    public L getLocation() {
        if (location == null) {
            return null;
        }

        return proxyService.toLocation(location);
    }

    /**
     * Gets the offset of the selection.
     * Has to be smaller or equal to {@link StructureRestriction}.
     *
     * @return offset.
     */
    @Nullable
    @Override
    public V getOffset() {
        if (offset == null) {
            return null;
        }

        return proxyService.toVector(offset);
    }

    /**
     * Gets the author.
     * The author is a optional meta data in the final
     * structure file.
     *
     * @return author.
     */
    @Override
    public @Nullable String getAuthor() {
        return this.author;
    }

    /**
     * Should entities included in the saved file.
     * Default false.
     *
     * @return flag.
     */
    @Override
    public boolean isIncludeEntitiesEnabled() {
        return this.includeEntities;
    }

    /**
     * Gets the size restriction.
     * <p>
     * Default StructureRestriction.SINGLE_32.
     *
     * @return restriction.
     */
    @Override
    public @NotNull StructureRestriction getRestriction() {
        return this.structureRestriction;
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
        return this.structureVoid;
    }

    /**
     * Sets the source Location corner where the
     * blocks start to get saved.
     * Required parameter.
     *
     * @param location Location.
     * @return This instance.
     */
    @Override
    public @NotNull StructureSaverRaw<L, V> at(@Nullable L location) {
        this.location = this.proxyService.toPosition(location);
        return this;
    }

    /**
     * Sets the source Vector offset where the
     * blocks reach to get saved.
     * Required parameter.
     *
     * @param vector Vector.
     * @return This instance.
     */
    @Override
    public @NotNull StructureSaverRaw<L, V> offSet(@Nullable V vector) {
        this.offset = this.proxyService.toPosition(vector);
        return this;
    }

    /**
     * Sets the offset in x coordinate.
     *
     * @param x offset.
     * @return This instance.
     */
    @Override
    public @NotNull StructureSaverRaw<L, V> sizeX(double x) {
        if (offset == null) {
            offset = new PositionImpl();
        }

        offset.setX(x);
        return this;
    }

    /**
     * Sets the offset in y coordinate.
     *
     * @param y offset.
     * @return This instance.
     */
    @Override
    public @NotNull StructureSaverRaw<L, V> sizeY(double y) {
        if (offset == null) {
            offset = new PositionImpl();
        }

        offset.setY(y);
        return this;
    }

    /**
     * Sets the offset in z coordinate.
     *
     * @param z offset.
     * @return This instance.
     */
    @Override
    public @NotNull StructureSaverRaw<L, V> sizeZ(double z) {
        if (offset == null) {
            offset = new PositionImpl();
        }

        offset.setZ(z);
        return this;
    }

    /**
     * Sets the author.
     * The author is a optional meta data in the final
     * structure file.
     *
     * @param author name.
     * @return This instance.
     */
    @Override
    public StructureSaverRaw<L, V> author(@Nullable String author) {
        this.author = author;
        return this;
    }

    /**
     * Should entities be included in the save file.
     * Default false.
     *
     * @param enabled Flag.
     * @return This instance.
     */
    @Override
    public StructureSaverRaw<L, V> includeEntities(boolean enabled) {
        this.includeEntities = enabled;
        return this;
    }

    /**
     * Restricts the structure to a certain size if a larger
     * area is selected with offset.
     * Default StructureRestriction.SINGLE_32.
     *
     * @param structureRestriction Restriction.
     * @return This instance.
     */
    @Override
    public @NotNull StructureSaverRaw<L, V> restriction(@NotNull StructureRestriction structureRestriction) {
        this.structureRestriction = structureRestriction;
        return this;
    }

    /**
     * Sets the name of the block type
     * which is being used as a Structure_Void.
     * Default STRUCTURE_VOID in 1.10 - Latest, BARRIER_BLOCK in 1.9.
     *
     * @param name Name of the block type.
     * @return This instance.
     */
    @Override
    public StructureSaverRaw<L, V> structureVoidTypeName(String name) {
        this.structureVoid = name;
        return this;
    }

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt file located in the world folder,
     * author folder. Overrides existing files.
     *
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param worldName World where the structure file is stored.
     * @param name      Name of the stored structure.
     * @param author    Name of the structure author.
     * @return Instance of {@link ProgressToken}.
     */
    @Override
    public @NotNull ProgressToken<Void> saveToWorld(@NotNull String worldName, @NotNull String name, @NotNull String author) {
        return null;
    }

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt binary as Base64 encoded string.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @return Instance of {@link ProgressToken} with Base64EncodedString result.
     */
    @Override
    public @NotNull ProgressToken<String> saveToString() {
        return null;
    }

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt file.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param target Non existing or existing file. Overrides existing files.
     * @return Instance of {@link ProgressToken}.
     */
    @Override
    public @NotNull ProgressToken<Void> saveToPath(@NotNull Path target) {
        return null;
    }

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt file.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param target Non existing or existing file. Overrides existing files.
     * @return Instance of {@link ProgressToken}.
     */
    @Override
    public @NotNull ProgressToken<Void> saveToFile(@NotNull File target) {
        return null;
    }

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt binary stream.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param target Open binary outputStream. Does not close the outputStream.
     * @return Instance of {@link ProgressToken}.
     */
    @Override
    public @NotNull ProgressToken<Void> saveToOutputStream(@NotNull OutputStream target) {
        return null;
    }
}
