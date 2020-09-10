package com.github.shynixn.structureblocklib.api.entity;

import com.github.shynixn.structureblocklib.api.enumeration.StructureRestriction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Interface fluent API to save structures from the world into
 * different targets.
 */
public interface StructureSaverAbstract<L, V> {
    /**
     * Gets the source Location.
     *
     * @return location.
     */
    @Nullable
    L getLocation();

    /**
     * Gets the offset of the selection.
     * Has to be smaller or equal to {@link StructureRestriction}.
     *
     * @return offset.
     */
    @Nullable
    V getOffset();

    /**
     * Gets the author.
     * The author is a optional meta data in the final
     * structure file.
     *
     * @return author.
     */
    @Nullable
    String getAuthor();

    /**
     * Should entities included in the saved file.
     * Default false.
     *
     * @return flag.
     */
    boolean isIncludeEntitiesEnabled();

    /**
     * Gets the size restriction.
     * <p>
     * Default StructureRestriction.SINGLE_32.
     *
     * @return restriction.
     */
    @NotNull
    StructureRestriction getRestriction();

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

    /**
     * Sets the source Location corner where the
     * blocks start to get saved.
     * Required parameter.
     *
     * @param location Location.
     * @return This instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> at(@Nullable L location);

    /**
     * Sets the source Vector offset where the
     * blocks reach to get saved.
     * Required parameter.
     *
     * @param vector Vector.
     * @return This instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> offSet(@Nullable V vector);

    /**
     * Sets the offset in x coordinate.
     *
     * @param x offset.
     * @return This instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> sizeX(double x);

    /**
     * Sets the offset in y coordinate.
     *
     * @param y offset.
     * @return This instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> sizeY(double y);

    /**
     * Sets the offset in z coordinate.
     *
     * @param z offset.
     * @return This instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> sizeZ(double z);

    /**
     * Sets the author.
     * The author is a optional meta data in the final
     * structure file.
     *
     * @param author name.
     * @return This instance.
     */
    StructureSaverAbstract<L, V> author(@Nullable String author);

    /**
     * Should entities be included in the save file.
     * Default false.
     *
     * @param enabled Flag.
     * @return This instance.
     */
    StructureSaverAbstract<L, V> includeEntities(boolean enabled);

    /**
     * Restricts the structure to a certain size if a larger
     * area is selected with offset.
     * Default StructureRestriction.SINGLE_32.
     *
     * @param structureRestriction Restriction.
     * @return This instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> restriction(@NotNull StructureRestriction structureRestriction);

    /**
     * Sets the name of the block type
     * which is being used as a Structure_Void.
     * Default STRUCTURE_VOID in 1.10 - Latest, BARRIER_BLOCK in 1.9.
     *
     * @param name Name of the block type.
     * @return This instance.
     */
    StructureSaverAbstract<L, V> structureVoidTypeName(String name);

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt file located in the world folder,
     * author folder. Overrides existing files.
     * This allows to use the structure in Vanilla Structure Blocks.
     *
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param worldName World where the structure file is stored.
     * @param author    Author of the structure.
     * @param name      Name of the stored structure.
     * @return Instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> saveToWorld(@NotNull String worldName, @NotNull String author, @NotNull String name);

    /**
     * Saves the blocks and entities from the world into
     * into a structure.nbt binary as Base64 encoded string.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @return Instance of {@link ProgressToken} with Base64EncodedString result.
     */
    @NotNull
    ProgressToken<String> saveToString();

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
    @NotNull
    ProgressToken<Void> saveToPath(@NotNull Path target);

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
    @NotNull
    ProgressToken<Void> saveToFile(@NotNull File target);

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
    @NotNull
    ProgressToken<Void> saveToOutputStream(@NotNull OutputStream target);
}
