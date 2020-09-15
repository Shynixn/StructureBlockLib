package com.github.shynixn.structureblocklib.core.block;

import com.github.shynixn.structureblocklib.api.block.StructureBlockDataAbstract;
import com.github.shynixn.structureblocklib.api.block.StructureBlockLoadAbstract;
import com.github.shynixn.structureblocklib.api.block.StructureBlockSaveAbstract;
import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.entity.StructureLoaderAbstract;
import com.github.shynixn.structureblocklib.api.entity.StructureSaverAbstract;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StructureBlockAbstractImpl<L, V> implements StructureBlockDataAbstract, StructureBlockSaveAbstract<L, V>, StructureBlockLoadAbstract<L, V> {
    private final ProxyService proxyService;
    private final StructureLoaderAbstract<L, V> loader;
    private final StructureSaverAbstract<L, V> saver;

    private StructureMode mode = StructureMode.CORNER;
    private boolean boundingBox = true;
    private Position position;
    private String saveName;
    private boolean invisibleBlocks = false;
    private String metaData;

    /**
     * Creates a new instance with dependencies.
     */
    public StructureBlockAbstractImpl(ProxyService proxyService, StructureLoaderAbstract<L, V> loader, StructureSaverAbstract<L, V> saver) {
        this.proxyService = proxyService;
        this.loader = loader;
        this.saver = saver;
    }

    /**
     * Changes the type of the structureBlock.
     *
     * @param structureMode structureMode.
     */
    @Override
    public void setStructureMode(@NotNull StructureMode structureMode) {
        this.mode = structureMode;
    }

    /**
     * Returns the type of the structureBlock.
     *
     * @return structureMode.
     */
    @Override
    public @NotNull StructureMode getStructureMode() {
        return mode;
    }

    /**
     * Sets the mirrorType of the structure when getting load.
     *
     * @param mirrorType mirrorType.
     */
    @Override
    public void setMirrorType(@NotNull StructureMirror mirrorType) {
        this.loader.mirror(mirrorType);
    }

    /**
     * Sets the rotation of the structure when getting load.
     *
     * @param rotation rotation.
     */
    @Override
    public void setRotation(@NotNull StructureRotation rotation) {
        this.loader.rotation(rotation);
    }

    /**
     * Returns the rotation of the structure when getting load.
     *
     * @return rotation.
     */
    @Override
    public @NotNull StructureRotation getRotation() {
        return this.loader.getRotationType();
    }

    /**
     * Returns the mirrorType of the structure when getting load.
     *
     * @return mirrorType.
     */
    @Override
    public @NotNull StructureMirror getMirrorType() {
        return this.loader.getMirrorType();
    }

    /**
     * Sets the boundingBoxVisibility.
     *
     * @param visible visible.
     */
    @Override
    public void setBoundingBoxVisible(boolean visible) {
        this.boundingBox = visible;
    }

    /**
     * Returns if the boundingBox is visible.
     *
     * @return visible.
     */
    @Override
    public boolean isBoundingBoxVisible() {
        return boundingBox;
    }

    /**
     * Sets the integrity of the structure.
     *
     * @param integrity integrity.
     */
    @Override
    public void setIntegrity(float integrity) {
        this.loader.integrity(integrity);
    }

    /**
     * Returns the integrity of the structure.
     *
     * @return integrity.
     */
    @Override
    public float getIntegrity() {
        return this.loader.getIntegrity();
    }

    /**
     * Sets the seed of the structure.
     *
     * @param seed seed.
     */
    @Override
    public void setSeed(long seed) {
        this.loader.seed(seed);
    }

    /**
     * Returns the seed of the structure.
     *
     * @return seed.
     */
    @Override
    public long getSeed() {
        return this.loader.getSeed();
    }

    /**
     * Sets the author of the structure.
     *
     * @param author author.
     */
    @Override
    public void setAuthor(@Nullable String author) {
        this.saver.author(author);
    }

    /**
     * Returns the author of the structure.
     *
     * @return author.
     */
    @Override
    public @Nullable String getAuthor() {
        return this.saver.getAuthor();
    }

    /**
     * Changes the location of the structure.
     *
     * @param location location.
     */
    @Override
    public void setStructureLocation(@Nullable L location) {
        this.position = proxyService.toPosition(location);
    }

    /**
     * Returns the location of the structure.
     *
     * @return location.
     */
    @Nullable
    @Override
    public L getStructureLocation() {
        return proxyService.toLocation(this.position);
    }

    /**
     * Should entities which may or may not be included in the
     * saved file be included in the loaded/saved structure.
     * Default false.
     *
     * @param flag flag.
     */
    @Override
    public void setIncludeEntities(boolean flag) {
        saver.includeEntities(flag);
        loader.includeEntities(flag);
    }

    /**
     * Should entities which may or may not be included in the
     * saved file be included in the loaded/saved structure.
     * Default false.
     *
     * @return false.
     */
    @Override
    public boolean isIncludeEntitiesEnabled() {
        return saver.isIncludeEntitiesEnabled();
    }

    /**
     * Changes the size of the structure in X direction.
     *
     * @param sizeX sizeX.
     */
    @Override
    public void setSizeX(int sizeX) {
        this.saver.sizeX(sizeX);
    }

    /**
     * Changes the size of the structure in Y direction.
     *
     * @param sizeY sizeY.
     */
    @Override
    public void setSizeY(int sizeY) {
        this.saver.sizeY(sizeY);
    }

    /**
     * Changes the size of the structure in Z direction.
     *
     * @param sizeZ sizeZ.
     */
    @Override
    public void setSizeZ(int sizeZ) {
        this.saver.sizeZ(sizeZ);
    }

    /**
     * Returns the size of the structure in X direction.
     *
     * @return xSize.
     */
    @Override
    public int getSizeX() {
        Position vector = proxyService.toPosition(this.saver.getOffset());

        if (vector == null) {
            return 0;
        }

        return (int) vector.getX();
    }

    /**
     * Returns the size of the structure in Y direction.
     *
     * @return ySize.
     */
    @Override
    public int getSizeY() {
        Position vector = proxyService.toPosition(this.saver.getOffset());

        if (vector == null) {
            return 0;
        }

        return (int) vector.getY();
    }

    /**
     * Returns the size of the structure in Z direction.
     *
     * @return zSize.
     */
    @Override
    public int getSizeZ() {
        Position vector = proxyService.toPosition(this.saver.getOffset());

        if (vector == null) {
            return 0;
        }

        return (int) vector.getZ();
    }

    /**
     * Sets the name of the save.
     *
     * @param name name.
     */
    @Override
    public void setSaveName(@Nullable String name) {
        this.saveName = name;
    }

    /**
     * Returns the name of the save.
     *
     * @return name.
     */
    @Override
    public @Nullable String getSaveName() {
        return saveName;
    }

    /**
     * Sets custom meta data. Please use the minecraft documentation to find out more.
     *
     * @param blockNameMetaData customMeta.
     */
    @Override
    public void setBlockNameMetaData(@Nullable String blockNameMetaData) {
        this.metaData = blockNameMetaData;
    }

    /**
     * Returns the custom meta data.
     *
     * @return customMeta
     */
    @Override
    public @Nullable String getBlockNameMetaData() {
        return this.metaData;
    }

    /**
     * Sets if invisibleBlocks should be visible.
     *
     * @param flag flag.
     */
    @Override
    public void setInvisibleBlocksEnabled(boolean flag) {
        this.invisibleBlocks = flag;
    }

    /**
     * Returns if invisibleBlocks are visible.
     *
     * @return visible
     */
    @Override
    public boolean isInvisibleBlocksEnabled() {
        return invisibleBlocks;
    }

    /**
     * Gets the associated {@link StructureSaverAbstract} instance which
     * contains the current block properties.
     *
     * @return Saver.
     */
    @Override
    public @NotNull StructureSaverAbstract<L, V> saveStructure() {
        return saver;
    }

    /**
     * Gets the associated {@link StructureLoaderAbstract} instance which
     * contains the current block properties.
     *
     * @return Loader.
     */
    @Override
    public @NotNull StructureLoaderAbstract<L, V> loadStructure() {
        return loader;
    }
}
