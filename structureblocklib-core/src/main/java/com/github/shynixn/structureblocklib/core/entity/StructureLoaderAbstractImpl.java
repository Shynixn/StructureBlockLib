package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.*;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.enumeration.Version;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class StructureLoaderAbstractImpl<L, V> implements StructureLoaderAbstract<L, V> {
    private final ProxyService proxyService;
    private final StructureSerializationService serializationService;
    private final StructureWorldService worldService;

    private Position location;
    private boolean includeEntities = false;
    private StructureRotation rotation = StructureRotation.NONE;
    private StructureMirror mirror = StructureMirror.NONE;
    private float integrity = 1.0F;
    private long seed = 0L;

    /**
     * Creates a new raw structure load instance.
     *
     * @param proxyService         dependency.
     * @param serializationService dependency.
     * @param worldService         dependency.
     */
    public StructureLoaderAbstractImpl(ProxyService proxyService, StructureSerializationService serializationService, StructureWorldService worldService) {
        this.proxyService = proxyService;
        this.serializationService = serializationService;
        this.worldService = worldService;
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
     * Should entities which may or may not be included in the
     * saved file be included in the loaded structure.
     * Default false.
     *
     * @return flag.
     */
    @Override
    public boolean isIncludeEntitiesEnabled() {
        return this.includeEntities;
    }

    /**
     * Gets the target mirror type.
     * Default StructureMirror.NONE.
     *
     * @return {@link StructureMirror}.
     */
    @Override
    public StructureMirror getMirrorType() {
        return mirror;
    }

    /**
     * Gets the target rotation type.
     * Default StructureRotation.NONE.
     *
     * @return {@link StructureRotation}.
     */
    @Override
    public StructureRotation getRotationType() {
        return rotation;
    }

    /**
     * Gets the target integrity.
     * Default 1.0.
     * 1.0 ->  Every block which is present in the structure file is placed in the world.
     * smnaller 1.0 -> Blocks get randomly removed by loading depending on the given {@code getSeed}.
     *
     * @return integrity.
     */
    @Override
    public float getIntegrity() {
        return integrity;
    }

    /**
     * Gets the target seed.
     * Default 0L.
     * The seed is used to randomly remove blocks if the integrity {@code getIntegrity} is less than 1.0.
     *
     * @return seed.
     */
    @Override
    public long getSeed() {
        return seed;
    }

    /**
     * Sets the target Location corner where the
     * blocks start to get placed.
     * Required parameter.
     *
     * @param location Location.
     * @return This instance.
     */
    @Override
    public @NotNull StructureLoaderAbstract<L, V> at(@Nullable L location) {
        this.location = this.proxyService.toPosition(location);
        return this;
    }

    /**
     * Should entities which may or may not be included in the
     * saved file be included in the loaded structure.
     * Default false.
     *
     * @param enabled Flag.
     * @return This instance.
     */
    @Override
    public StructureLoaderAbstract<L, V> includeEntities(boolean enabled) {
        this.includeEntities = enabled;
        return this;
    }

    /**
     * Sets the target mirror type.
     * Default StructureMirror.NONE.
     *
     * @param mirror Mirror.
     * @return This instance.
     */
    @Override
    public StructureLoaderAbstract<L, V> mirror(StructureMirror mirror) {
        this.mirror = mirror;
        return this;
    }

    /**
     * Sets the target rotation type.
     * Default StructureRotation.NONE.
     *
     * @param rotation Rotation.
     * @return This instance.
     */
    @Override
    public StructureLoaderAbstract<L, V> rotation(StructureRotation rotation) {
        this.rotation = rotation;
        return this;
    }

    /**
     * Sets the target integrity.
     * Default 1.0.
     * 1.0 ->  Every block which is present in the structure file is placed in the world.
     * smaller 1.0 -> Blocks get randomly removed by loading depending on the given {@code setSeed}.
     *
     * @param integrity Integrity.
     * @return This instance.
     */
    @Override
    public StructureLoaderAbstract<L, V> integrity(float integrity) {
        this.integrity = integrity;
        return this;
    }

    /**
     * Sets the target seed.
     * Default 0L.
     * The seed is used to randomly remove blocks if the integrity {@code setIntegrity} is less than 1.0.
     *
     * @param seed Seed.
     * @return This instance.
     */
    @Override
    public StructureLoaderAbstract<L, V> seed(long seed) {
        this.seed = seed;
        return this;
    }

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
    @Override
    public @NotNull ProgressToken<Void> loadFromSaver(@NotNull StructureSaverAbstract<L, V> source) {
        ProgressTokenImpl<Void> progressToken = new ProgressTokenImpl<>();
        progressToken.progress(0.0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CompletionStage<Void> completableFuture = source.saveToOutputStream(outputStream).getCompletionStage().thenComposeAsync(e_ -> {
            progressToken.progress(0.5);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            return loadFromInputStream(inputStream).getCompletionStage()
                    .thenAcceptAsync(e1_ -> {
                        progressToken.progress(1.0);
                        try {
                            inputStream.close();
                            outputStream.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, proxyService.getSyncExecutor());
        }, proxyService.getSyncExecutor());
        progressToken.setCompletionStage(completableFuture);
        return progressToken;
    }

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
    @Override
    public @NotNull ProgressToken<Void> loadFromWorld(@NotNull String worldName, @NotNull String author, @NotNull String name) {
        Version version = proxyService.getServerVersion();
        File file;
        if (version.isVersionSameOrGreaterThan(Version.VERSION_1_13_R2)) {
            file = new File(worldName + File.separator + "generated" + File.separator + author + File.separator + "structures" + File.separator + name + ".nbt");
        } else {
            file = new File(worldName + File.separator + "structures" + File.separator + name + ".nbt");
        }

        ProgressTokenImpl<Void> progressToken = new ProgressTokenImpl<>();
        CompletionStage<Void> completionStage = CompletableFuture.completedFuture(null).thenComposeAsync(e_ -> {
            try {
                Files.createDirectories(file.getParentFile().toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProgressToken<Void> childProgressToken = loadFromFile(file);
            childProgressToken.onProgress(progressToken::progress);
            return childProgressToken.getCompletionStage();
        }, proxyService.getAsyncExecutor());
        progressToken.setCompletionStage(completionStage);
        return loadFromFile(file);
    }

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
    @Override
    @NotNull
    public ProgressToken<Void> loadFromString(@NotNull String source) {
        ProgressTokenImpl<Void> progressToken = new ProgressTokenImpl<>();
        CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null).thenComposeAsync(e_ -> {
            byte[] content = Base64.getDecoder().decode(source);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
            ProgressToken<Void> childProgressToken = loadFromInputStream(inputStream);
            childProgressToken.onProgress(progressToken::progress);
            return childProgressToken.getCompletionStage().thenAcceptAsync(e1_ -> {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, proxyService.getAsyncExecutor());
        }, proxyService.getAsyncExecutor());
        progressToken.setCompletionStage(completableFuture);
        return progressToken;
    }

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
    @Override
    @NotNull
    public ProgressToken<Void> loadFromPath(@NotNull Path source) {
        return loadFromFile(source.toFile());
    }

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
    @Override
    @NotNull
    public ProgressToken<Void> loadFromFile(@NotNull File source) {
        ProgressTokenImpl<Void> progressToken = new ProgressTokenImpl<>();
        CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null).thenComposeAsync(e_ -> {
            try {
                FileInputStream inputStream = new FileInputStream(source);
                ProgressToken<Void> childProgressToken = loadFromInputStream(inputStream);
                childProgressToken.onProgress(progressToken::progress);
                return childProgressToken.getCompletionStage().thenAcceptAsync(e1_ -> {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, proxyService.getAsyncExecutor());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }, proxyService.getAsyncExecutor());
        progressToken.setCompletionStage(completableFuture);
        return progressToken;
    }

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
    @Override
    @NotNull
    public ProgressToken<Void> loadFromInputStream(@NotNull InputStream source) {
        ProgressTokenImpl<Void> progressToken = new ProgressTokenImpl<>();
        StructurePlaceMetaImpl meta = new StructurePlaceMetaImpl();
        meta.location = this.location;
        meta.includeEntities = this.includeEntities;
        meta.integrity = this.integrity;
        meta.seed = this.seed;
        meta.mirror = this.mirror;
        meta.rotation = this.rotation;

        progressToken.progress(0.0);
        CompletableFuture<Void> completableFuture = CompletableFuture.completedFuture(null).thenComposeAsync(e_ -> {
            try {
                Object definedStructure = serializationService.deSerialize(source);
                return CompletableFuture.runAsync(() -> {
                    try {
                        progressToken.progress(0.5);
                        worldService.placeStructureToWorld(meta, definedStructure);
                        progressToken.progress(1.0);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, proxyService.getSyncExecutor());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, proxyService.getAsyncExecutor());
        progressToken.setCompletionStage(completableFuture);

        return progressToken;
    }
}
