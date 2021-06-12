package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.entity.ProgressToken;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.entity.StructureSaverAbstract;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRestriction;
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

/**
 * Interface fluent API to save structures from the world into
 * different targets.
 */
public class StructureSaverAbstractImpl<L, V> implements StructureSaverAbstract<L, V> {
    private final ProxyService proxyService;
    private final StructureSerializationService serializationService;
    private final StructureWorldService worldService;

    private Position location;
    private Position offset;
    private String author;
    private boolean includeEntities = false;
    private StructureRestriction structureRestriction = StructureRestriction.SINGLE_32;
    private String structureVoid;

    /**
     * Creates a new raw structure save instance.
     *
     * @param proxyService         dependency.
     * @param serializationService dependency.
     * @param worldService         dependency.
     */
    public StructureSaverAbstractImpl(ProxyService proxyService, StructureSerializationService serializationService, StructureWorldService worldService) {
        this.proxyService = proxyService;
        this.serializationService = serializationService;
        this.worldService = worldService;

        if (proxyService.getServerVersion().isVersionSameOrGreaterThan(Version.VERSION_1_17_R1)) {
            structureVoid = "jb";
        } else if (proxyService.getServerVersion().isVersionSameOrGreaterThan(Version.VERSION_1_13_R2)) {
            structureVoid = "STRUCTURE_VOID";
        } else if (proxyService.getServerVersion().isVersionSameOrGreaterThan(Version.VERSION_1_10_R1)) {
            structureVoid = "dj";
        } else {
            structureVoid = "BARRIER";
        }
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
    public @NotNull StructureSaverAbstract<L, V> at(@Nullable L location) {
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
    public @NotNull StructureSaverAbstract<L, V> offSet(@Nullable V vector) {
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
    public @NotNull StructureSaverAbstract<L, V> sizeX(int x) {
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
    public @NotNull StructureSaverAbstract<L, V> sizeY(int y) {
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
    public @NotNull StructureSaverAbstract<L, V> sizeZ(int
                                                               z) {
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
    public StructureSaverAbstract<L, V> author(@Nullable String author) {
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
    public StructureSaverAbstract<L, V> includeEntities(boolean enabled) {
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
    public @NotNull StructureSaverAbstract<L, V> restriction(@NotNull StructureRestriction structureRestriction) {
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
    @NotNull
    public StructureSaverAbstract<L, V> structureVoidTypeName(@NotNull String name) {
        this.structureVoid = name;
        return this;
    }

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
    @Override
    public @NotNull ProgressToken<Void> saveToWorld(@NotNull String worldName, @NotNull String author, @NotNull String name) {
        Version version = proxyService.getServerVersion();
        File file;

        if (version.isVersionSameOrGreaterThan(Version.VERSION_1_13_R2)) {
            file = new File(worldName + File.separator + "generated" + File.separator + author + File.separator + "structures" + File.separator + name + ".nbt");
        } else {
            file = new File(worldName + File.separator + "structures" + File.separator + name + ".nbt");
        }

        try {
            Files.createDirectories(file.getParentFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        author(author);

        return saveToFile(file);
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
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        ProgressTokenImpl<String> rootToken = new ProgressTokenImpl<>(completableFuture);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ProgressToken<Void> innerToken = saveToOutputStream(byteArrayOutputStream);
        innerToken.onProgress(rootToken::progress);
        innerToken.getCompletionStage().thenAccept(e -> {
            try {
                String data = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
                byteArrayOutputStream.close();
                completableFuture.complete(data);
            } catch (IOException ioException) {
                completableFuture.completeExceptionally(ioException);
            }
        });
        innerToken.getCompletionStage().exceptionally(e -> {
            completableFuture.completeExceptionally(e);
            return null;
        });

        return rootToken;
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
        return saveToFile(target.toFile());
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
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        ProgressTokenImpl<Void> rootToken = new ProgressTokenImpl<>(completableFuture);

        proxyService.runAsyncTask(() -> {
            try {
                FileOutputStream outputStream = new FileOutputStream(target);
                ProgressToken<Void> progressToken = saveToOutputStream(outputStream);
                progressToken.onProgress(rootToken::progress);
                progressToken.getCompletionStage().thenAccept(c -> {
                    try {
                        outputStream.close();
                        completableFuture.complete(c);
                    } catch (IOException e) {
                        completableFuture.completeExceptionally(e);
                    }
                });
                progressToken.getCompletionStage().exceptionally(throwable -> {
                    completableFuture.completeExceptionally(throwable);
                    return null;
                });
            } catch (IOException e) {
                proxyService.runSyncTask(() -> completableFuture.completeExceptionally(e));
            }
        });

        return rootToken;
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
        StructureReadMeta meta = validate();
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        ProgressTokenImpl<Void> progressToken = new ProgressTokenImpl<>(completableFuture);

        proxyService.runSyncTask(() -> {
            progressToken.progress(0.0);
            Object definedStructure;
            try {
                definedStructure = worldService.readStructureFromWorld(meta);
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
                return;
            }
            progressToken.progress(0.5);
            Object finalDefinedStructure = definedStructure;
            proxyService.runAsyncTask(() -> {
                try {
                    serializationService.serialize(finalDefinedStructure, target);
                    proxyService.runSyncTask(() -> {
                        completableFuture.complete(null);
                        progressToken.progress(1.0);
                    });
                } catch (IOException e) {
                    proxyService.runSyncTask(() -> completableFuture.completeExceptionally(e));
                }
            });
        });

        return progressToken;
    }

    /**
     * Validates if the given builder is correct.
     */
    private StructureReadMeta validate() {
        if (location == null) {
            throw new IllegalArgumentException("Location #at(Location) cannot be null!");
        }

        if (offset == null) {
            throw new IllegalArgumentException("Offset #offset(Location) cannot be null!");
        }

        StructureReadMeta structureReadMeta = createReadMeta();
        changeOffSetToPositivOffset(structureReadMeta.getLocation(), structureReadMeta.getOffset());
        validateDirection("x", structureReadMeta.getLocation().getX(), structureReadMeta.getOffset().getX());
        validateDirection("y", structureReadMeta.getLocation().getY(), structureReadMeta.getOffset().getY());
        validateDirection("z", structureReadMeta.getLocation().getZ(), structureReadMeta.getOffset().getZ());
        return structureReadMeta;
    }

    /**
     * Creates a read meta.
     *
     * @return New StructureReadMeta.
     */
    private StructureReadMeta createReadMeta() {
        StructureReadMetaImpl readMeta = new StructureReadMetaImpl();
        readMeta.location = new PositionImpl(this.location);
        readMeta.offset = new PositionImpl(this.offset);
        readMeta.includeEntities = this.includeEntities;
        readMeta.structureVoid = this.structureVoid;

        if (this.author != null) {
            readMeta.author = this.author;
        }

        return readMeta;
    }

    /**
     * Validates the direction (x,y,z) axe of the selection.
     *
     * @param direction direction.
     * @param source    source.
     * @param offset    offset.
     */
    private void validateDirection(String direction, double source, double offset) {
        double diff = (int) Math.abs(source - (source + offset));

        if (this.structureRestriction == StructureRestriction.SINGLE_32) {
            if (diff > 32) {
                throw new IllegalArgumentException("Axe " + direction + " exceeded StructureRestriction of 32x32x32! Change the restriction or reduce the size of your selection.");
            }
            return;
        }

        if (this.structureRestriction == StructureRestriction.SINGLE_48) {
            if (diff > 48) {
                throw new IllegalArgumentException("Axe " + direction + " exceeded StructureRestriction of 48x48x48! Change the restriction or reduce the size of your selection.");
            }
        }
    }

    /**
     * Modifies the offset so it points into positiv direction.
     *
     * @param source source.
     * @param offSet fofset.
     */
    private void changeOffSetToPositivOffset(Position source, Position offSet) {
        if (offSet.getX() < 0) {
            source.setX(source.getX() + offSet.getX() + 1);
            offSet.setX(offSet.getX() * -1);
        }

        if (offSet.getY() < 0) {
            source.setY(source.getY() + offSet.getY() + 1);
            offSet.setY(offSet.getY() * -1);
        }

        if (offSet.getZ() < 0) {
            source.setZ(source.getZ() + offSet.getZ() + 1);
            offSet.setZ(offSet.getZ() * -1);
        }
    }
}
