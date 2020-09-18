package com.github.shynixn.structureblocklib.api.entity;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
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
     * Should entities which may or may not be included in the
     * saved file be included in the loaded structure.
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
     * 1.0 ->  Every block which is present in the structure file is placed in the world.
     * <1.0 -> Blocks get randomly removed by loading depending on the given {@code getSeed}.
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
     * Should entities which may or may not be included in the
     * saved file be included in the loaded structure.
     * Default false.
     *
     * @param enabled Flag.
     * @return This instance.
     */
    StructureLoaderAbstract<L, V> includeEntities(boolean enabled);

    /**
     * Sets the target mirror type.
     * Default StructureMirror.NONE.
     *
     * @param mirror Mirror.
     * @return This instance.
     */
    StructureLoaderAbstract<L, V> mirror(StructureMirror mirror);

    /**
     * Sets the target rotation type.
     * Default StructureRotation.NONE.
     *
     * @param rotation Rotation.
     * @return This instance.
     */
    StructureLoaderAbstract<L, V> rotation(StructureRotation rotation);

    /**
     * Sets the target integrity.
     * Default 1.0.
     * 1.0 ->  Every block which is present in the structure file is placed in the world.
     * <1.0 -> Blocks get randomly removed by loading depending on the given {@code setSeed}.
     *
     * @param integrity Integrity.
     * @return This instance.
     */
    StructureLoaderAbstract<L, V> integrity(float integrity);

    /**
     * Sets the target seed.
     * Default 0L.
     * The seed is used to randomly remove blocks if the integrity {@code setIntegrity} is less than 1.0.
     *
     * @param seed Seed.
     * @return This instance.
     */
    StructureLoaderAbstract<L, V> seed(long seed);

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
     * @param author    Name of the structure author.
     * @param name      Name of the stored structure.
     * @return NotNull instance of {@link ProgressToken}.
     */
    @NotNull
    ProgressToken<Void> loadFromWorld(@NotNull String worldName, @NotNull String author, @NotNull String name);

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
