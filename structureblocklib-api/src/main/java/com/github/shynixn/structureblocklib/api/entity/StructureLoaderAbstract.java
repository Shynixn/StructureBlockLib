package com.github.shynixn.structureblocklib.api.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Interface fluent API to load structures from sources into
 * the world
 */
public interface StructureLoaderAbstract<L, V> {
    /**
     * Gets the target Location.
     *
     * @return location.
     */
    @Nullable
    L getLocation();

    /**
     * Sets the target Location corner where the
     * blocks start to get placed.
     * Required parameter.
     *
     * @param location Location.
     * @return This instance.
     */
    @NotNull
    StructureLoaderAbstract<L, V> at(@Nullable L location);

    /**
     * Loads the structure blocks and entities from the given source and places
     * the blocks at the defined position.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param source Existing Structure in world defined by {@link StructureSaverAbstract}.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromSaver(@NotNull StructureSaverAbstract<L, V> source);

    /**
     * Loads the structure blocks and entities from the structure storage
     * inside each world folder of Minecraft and places
     * the blocks at the defined position.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param worldName World where the structure file is stored.
     * @param name      Name of the stored structure.
     * @param author    Name of the structure author.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromWorld(@NotNull String worldName, @NotNull String name, @NotNull String author);

    /**
     * Loads the structure blocks and entities from the given source and places
     * the blocks at the defined position.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param source Base64 encoded Structure binary.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromString(@NotNull String source);

    /**
     * Loads the structure blocks and entities from the given source and places
     * the blocks at the defined position.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param source Existing Path.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromPath(@NotNull Path source);

    /**
     * Loads the structure blocks and entities from the given source and places
     * the blocks at the defined position.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param source Existing File.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromFile(@NotNull File source);

    /**
     * Loads the structure blocks and entities from the given source and places
     * the blocks at the defined position.
     * <p>
     * This call does not block and finishes in the future. Use
     * {@link ProgressToken} ()} for cancellation or callbacks.
     *
     * @param source Open binary inputStream. Does not close the inputStream.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromInputStream(@NotNull InputStream source);
}
