package com.github.shynixn.structureblocklib.bukkit.v1_18_R1;

import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockData;
import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockLoad;
import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockSave;
import com.github.shynixn.structureblocklib.api.entity.StructureLoaderAbstract;
import com.github.shynixn.structureblocklib.api.entity.StructureSaverAbstract;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.core.block.StructureBlockAbstractImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlockState;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class CraftStructureBlock extends CraftBlockState implements StructureBlockData, StructureBlockSave, StructureBlockLoad {
    public StructureBlockAbstractImpl<Location, Vector, Block, World> internalBlock;
    public TypeConversionService conversionService;
    public StructureBlockEntity tileEntityStructure;

    /**
     * Creates a new instance with dependencies.
     *
     * @param structure dependency.
     * @param block     dependency.
     */
    public CraftStructureBlock(StructureBlockAbstractImpl<Location, Vector, Block, World> structure, TypeConversionService conversionService, Block block) {
        super(block);
        final CraftWorld world = (CraftWorld) block.getWorld();
        this.internalBlock = structure;
        this.conversionService = conversionService;
        this.tileEntityStructure = (StructureBlockEntity) world.getHandle().getBlockEntity(new BlockPos(this.getX(), this.getY(), this.getZ()), true);

        if (tileEntityStructure == null) {
            throw new IllegalArgumentException("The block at " + world.getName() + " " + this.getX() + " " + this.getY() + " " + this.getZ() + " is not a StructureBlock.");
        }

        CompoundTag compound = new CompoundTag();
        compound = saveCompoundTileEntityStructure(this.tileEntityStructure, compound);
        this.setSaveName(compound.getString("name"));
        this.setAuthor(compound.getString("author"));
        this.setBlockNameMetaData(compound.getString("metadata"));
        this.setStructureLocation(new Location(block.getWorld(), compound.getInt("posX"), compound.getInt("posY"), compound.getInt("posZ")));
        this.setSizeX(compound.getInt("sizeX"));
        this.setSizeY(compound.getInt("sizeY"));
        this.setSizeZ(compound.getInt("sizeZ"));
        this.setIncludeEntities(!compound.getBoolean("ignoreEntities"));
        this.setBoundingBoxVisible(compound.getBoolean("showboundingbox"));
        this.setInvisibleBlocksEnabled(compound.getBoolean("showair"));
        this.setIntegrity(compound.getFloat("integrity"));
        this.setSeed(compound.getLong("seed"));
        this.setMirrorType(conversionService.convertToStructureMirror(Mirror.valueOf(compound.getString("mirror"))));
        this.setRotationType(conversionService.convertToStructureRotation(Rotation.valueOf(compound.getString("rotation"))));
        this.setStructureMode(conversionService.convertToStructureMode(net.minecraft.world.level.block.state.properties.StructureMode.valueOf(compound.getString("mode"))));
    }

    /**
     * Updates the craft changes to the minecraft world.
     *
     * @param force        force update.
     * @param applyPhysics apply physics.
     * @return success.
     */
    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        CompoundTag compound = new CompoundTag();
        compound = saveCompoundTileEntityStructure(this.tileEntityStructure, compound);
        compound.putString("name", this.getSaveName());
        compound.putString("author", this.getAuthor());
        compound.putString("metadata", this.getBlockNameMetaData());
        compound.putInt("posX", this.getStructureLocation().getBlockX());
        compound.putInt("posY", this.getStructureLocation().getBlockY());
        compound.putInt("posZ", this.getStructureLocation().getBlockZ());
        compound.putInt("sizeX", this.getSizeX());
        compound.putInt("sizeY", this.getSizeY());
        compound.putInt("sizeZ", this.getSizeZ());
        compound.putBoolean("showboundingbox", this.isBoundingBoxVisible());
        compound.putBoolean("showair", this.isInvisibleBlocksEnabled());
        compound.putBoolean("ignoreEntities", !this.isIncludeEntitiesEnabled());
        compound.putFloat("integrity", this.getIntegrity());
        compound.putLong("seed", this.getSeed());
        compound.putString("rotation", conversionService.convertToRotationHandle(getRotationType()).toString());
        compound.putString("mirror", conversionService.convertToMirrorHandle(getMirrorType()).toString());
        compound.putString("mode", conversionService.convertToStructureModeHandle(getStructureMode()).toString());
        this.loadCompoundTileEntityStructure(this.tileEntityStructure, compound);
        this.setChangedTileEntityStructure(this.tileEntityStructure);

        return result;
    }


    /**
     * Sets the mirrorType of the structure when getting load.
     *
     * @param mirrorType mirrorType.
     */
    @Override
    public void setMirrorType(@NotNull StructureMirror mirrorType) {
        this.internalBlock.setMirrorType(mirrorType);
    }

    /**
     * Sets the rotation of the structure when getting load.
     *
     * @param rotation rotation.
     */
    @Override
    public void setRotationType(@NotNull StructureRotation rotation) {
        this.internalBlock.setRotationType(rotation);
    }

    /**
     * Returns the rotation of the structure when getting load.
     *
     * @return rotation.
     */
    @Override
    public @NotNull StructureRotation getRotationType() {
        return internalBlock.getRotationType();
    }

    /**
     * Returns the mirrorType of the structure when getting load.
     *
     * @return mirrorType.
     */
    @Override
    public @NotNull StructureMirror getMirrorType() {
        return internalBlock.getMirrorType();
    }

    /**
     * Sets the boundingBoxVisibility.
     *
     * @param visible visible.
     */
    @Override
    public void setBoundingBoxVisible(boolean visible) {
        this.internalBlock.setBoundingBoxVisible(visible);
    }

    /**
     * Returns if the boundingBox is visible.
     *
     * @return visible.
     */
    @Override
    public boolean isBoundingBoxVisible() {
        return this.internalBlock.isBoundingBoxVisible();
    }

    /**
     * Sets the integrity of the structure.
     *
     * @param integrity integrity.
     */
    @Override
    public void setIntegrity(float integrity) {
        this.internalBlock.setIntegrity(integrity);
    }

    /**
     * Returns the integrity of the structure.
     *
     * @return integrity.
     */
    @Override
    public float getIntegrity() {
        return this.internalBlock.getIntegrity();
    }

    /**
     * Sets the seed of the structure.
     *
     * @param seed seed.
     */
    @Override
    public void setSeed(long seed) {
        this.internalBlock.setSeed(seed);
    }

    /**
     * Returns the seed of the structure.
     *
     * @return seed.
     */
    @Override
    public long getSeed() {
        return internalBlock.getSeed();
    }

    /**
     * Sets the author of the structure.
     *
     * @param author author.
     */
    @Override
    public void setAuthor(@Nullable String author) {
        this.internalBlock.setAuthor(author);
    }

    /**
     * Returns the author of the structure.
     *
     * @return author.
     */
    @Override
    public @Nullable String getAuthor() {
        return this.internalBlock.getAuthor();
    }

    /**
     * Changes the location of the structure.
     *
     * @param location location.
     */
    @Override
    public void setStructureLocation(@Nullable Location location) {
        this.internalBlock.setStructureLocation(location);
    }

    /**
     * Returns the location of the structure.
     *
     * @return location.
     */
    @Nullable
    @Override
    public Location getStructureLocation() {
        return this.internalBlock.getStructureLocation();
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
        this.internalBlock.setIncludeEntities(flag);
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
        return internalBlock.isIncludeEntitiesEnabled();
    }

    /**
     * Changes the size of the structure in X direction.
     *
     * @param sizeX sizeX.
     */
    @Override
    public void setSizeX(int sizeX) {
        this.internalBlock.setSizeX(sizeX);
    }

    /**
     * Changes the size of the structure in Y direction.
     *
     * @param sizeY sizeY.
     */
    @Override
    public void setSizeY(int sizeY) {
        this.internalBlock.setSizeY(sizeY);
    }

    /**
     * Changes the size of the structure in Z direction.
     *
     * @param sizeZ sizeZ.
     */
    @Override
    public void setSizeZ(int sizeZ) {
        this.internalBlock.setSizeZ(sizeZ);
    }

    /**
     * Returns the size of the structure in X direction.
     *
     * @return xSize.
     */
    @Override
    public int getSizeX() {
        return this.internalBlock.getSizeX();
    }

    /**
     * Returns the size of the structure in Y direction.
     *
     * @return ySize.
     */
    @Override
    public int getSizeY() {
        return internalBlock.getSizeY();
    }

    /**
     * Returns the size of the structure in Z direction.
     *
     * @return zSize.
     */
    @Override
    public int getSizeZ() {
        return internalBlock.getSizeZ();
    }

    /**
     * Sets the name of the save.
     *
     * @param name name.
     */
    @Override
    public void setSaveName(@Nullable String name) {
        this.internalBlock.setSaveName(name);
    }

    /**
     * Returns the name of the save.
     *
     * @return name.
     */
    @Override
    public @Nullable String getSaveName() {
        return internalBlock.getSaveName();
    }

    /**
     * Sets custom meta data. Please use the minecraft documentation to find out more.
     *
     * @param blockNameMetaData customMeta.
     */
    @Override
    public void setBlockNameMetaData(@Nullable String blockNameMetaData) {
        this.internalBlock.setBlockNameMetaData(blockNameMetaData);
    }

    /**
     * Returns the custom meta data.
     *
     * @return customMeta
     */
    @Override
    public @Nullable String getBlockNameMetaData() {
        return internalBlock.getBlockNameMetaData();
    }

    /**
     * Sets if invisibleBlocks should be visible.
     *
     * @param flag flag.
     */
    @Override
    public void setInvisibleBlocksEnabled(boolean flag) {
        this.internalBlock.setInvisibleBlocksEnabled(flag);
    }

    /**
     * Returns if invisibleBlocks are visible.
     *
     * @return visible
     */
    @Override
    public boolean isInvisibleBlocksEnabled() {
        return internalBlock.isInvisibleBlocksEnabled();
    }

    /**
     * Changes the type of the structureBlock.
     *
     * @param structureMode structureMode.
     */
    @Override
    public void setStructureMode(@NotNull StructureMode structureMode) {
        this.internalBlock.setStructureMode(structureMode);
    }

    /**
     * Returns the type of the structureBlock.
     *
     * @return structureMode.
     */
    @Override
    public @NotNull StructureMode getStructureMode() {
        return internalBlock.getStructureMode();
    }

    /**
     * Creates a new instance of {@link StructureLoaderAbstract} which
     * contains the current block properties.
     *
     * @return New instance.
     */
    @Override
    public @NotNull StructureLoaderAbstract<Location, Vector, Block, World> loadStructure() {
        return internalBlock.loadStructure();
    }

    /**
     * Creates a new instance of {@link StructureSaverAbstract} which
     * contains the current block properties.
     *
     * @return New instance.
     */
    @Override
    public @NotNull StructureSaverAbstract<Location, Vector> saveStructure() {
        return internalBlock.saveStructure();
    }

    /**
     * The mapping for StructureBlockEntity save is broken in spigot. This is a workaround.
     */
    private CompoundTag saveCompoundTileEntityStructure(StructureBlockEntity tileEntityStructure, CompoundTag compoundTag) {
        try {
            Method method = StructureBlockEntity.class.getDeclaredMethod("b", CompoundTag.class);
            method.setAccessible(true);
            method.invoke(tileEntityStructure, compoundTag);
            return compoundTag;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The mapping for StructureBlockEntity load is broken in spigot. This is a workaround.
     */
    private void loadCompoundTileEntityStructure(StructureBlockEntity tileEntityStructure, CompoundTag compoundTag) {
        try {
            Method method = StructureBlockEntity.class.getDeclaredMethod("a", CompoundTag.class);
            method.invoke(tileEntityStructure, compoundTag);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The mapping for StructureBlockEntity setChanged is broken in spigot. This is a workaround.
     */
    private void setChangedTileEntityStructure(StructureBlockEntity tileEntityStructure) {
        try {
            Method method = BlockEntity.class.getDeclaredMethod("e");
            method.invoke(tileEntityStructure);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
