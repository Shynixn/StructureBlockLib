package com.github.shynixn.structureblocklib.bukkit.core.nms.v1_9_R2;

import com.github.shynixn.structureblocklib.bukkit.api.StructureBlockApi;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.bukkit.api.business.proxy.StructureBlockData;
import com.github.shynixn.structureblocklib.bukkit.api.business.proxy.StructureBlockLoad;
import com.github.shynixn.structureblocklib.bukkit.api.business.proxy.StructureBlockSave;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.block.CraftBlockState;
import org.bukkit.util.Vector;

public class CraftStructureBlock extends CraftBlockState implements StructureBlockData, StructureBlockSave, StructureBlockLoad {
    private final PersistenceStructureService persistenceStructureService = StructureBlockApi.INSTANCE.getStructurePersistenceService();

    private final TileEntityStructure structure;

    private StructureMirror mirrorType;
    private StructureRotation rotation;
    private String author;
    private String name;
    private BlockPosition position;
    private int sizeX;
    private int sizeY;
    private int sizeZ;
    private String metadata;
    private StructureMode mode;
    private boolean ignoreEntities;
    private boolean boundingBox;
    private boolean invisibleBlocks;
    private float integrity = 1.0F;
    private long seed = 0L;

    public CraftStructureBlock(Block block) {
        super(block);
        final CraftWorld world = (CraftWorld) block.getWorld();
        this.structure = (TileEntityStructure) world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
        this.refresh();
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.structure.a(this.convert());
            this.structure.update();
        }
        return result;
    }

    private void refresh() {
        NBTTagCompound compound = new NBTTagCompound();
        compound = this.structure.save(compound);

        this.name = compound.getString("name");
        this.author = compound.getString("author");
        this.metadata = compound.getString("metadata");
        this.position = new BlockPosition(compound.getInt("posX"), compound.getInt("posY"), compound.getInt("posZ"));
        this.sizeX = compound.getInt("sizeX");
        this.sizeY = compound.getInt("sizeY");
        this.sizeZ = compound.getInt("sizeZ");
        this.ignoreEntities = compound.getBoolean("ignoreEntities");

        this.rotation = getBlockRotation(EnumBlockRotation.valueOf(compound.getString("rotation")));
        this.mirrorType = this.getBlockMirror(EnumBlockMirror.valueOf(compound.getString("mirror")));
        this.mode = getBlockUseage(TileEntityStructure.UsageMode.valueOf(compound.getString("mode")));

        this.boundingBox = compound.getBoolean("showboundingbox");
        this.invisibleBlocks = compound.getBoolean("showair");

        this.integrity = compound.getFloat("integrity");
        this.seed = compound.getLong("seed");
    }

    NBTTagCompound convert() {
        NBTTagCompound compound = new NBTTagCompound();
        compound = this.structure.save(compound);
        compound.setString("name", this.name);
        compound.setString("author", this.author);
        compound.setString("metadata", this.metadata);
        compound.setInt("posX", this.position.getX());
        compound.setInt("posY", this.position.getY());
        compound.setInt("posZ", this.position.getZ());

        compound.setInt("sizeX", this.sizeX);
        compound.setInt("sizeY", this.sizeY);
        compound.setInt("sizeZ", this.sizeZ);

        compound.setString("rotation", getBlockRotation(this.rotation).toString());
        compound.setString("mirror", getBlockMirror(this.mirrorType).toString());
        compound.setString("mode", getBlockUseage(this.mode).toString());

        compound.setBoolean("showboundingbox", this.boundingBox);
        compound.setBoolean("showair", this.invisibleBlocks);
        compound.setBoolean("ignoreEntities", this.ignoreEntities);

        compound.setFloat("integrity", this.integrity);
        compound.setLong("seed", this.seed);
        return compound;
    }

    @Override
    public TileEntity getTileEntity() {
        return this.structure;
    }

    @Override
    public void setMirrorType(StructureMirror mirrorType) {
        if (mirrorType != null) {
            this.mirrorType = mirrorType;
        }
    }

    @Override
    public StructureMirror getMirrorType() {
        return this.mirrorType;
    }

    @Override
    public void setBoundingBoxVisible(boolean visible) {
        this.boundingBox = visible;
    }

    @Override
    public boolean isBoundingBoxVisible() {
        return this.boundingBox;
    }

    @Override
    public void setIntegrity(float integrity) {
        this.integrity = integrity;
    }

    @Override
    public float getIntegrity() {
        return this.integrity;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public long getSeed() {
        return this.seed;
    }

    @Override
    public void load() {
        final StructureSaveConfiguration saveConfiguration = this.persistenceStructureService.createSaveConfiguration(this.author, this.name, this.getWorld().getName());
        saveConfiguration.setMirror(this.mirrorType);
        saveConfiguration.setRotation(this.rotation);
        saveConfiguration.setIgnoreEntities(this.ignoreEntities);
        saveConfiguration.setIntegrity(this.integrity);
        saveConfiguration.setSeed(this.seed);

        this.persistenceStructureService.load(saveConfiguration, new Location(this.getWorld(), this.position.getX(), this.position.getY(), this.position.getZ()));
    }

    @Override
    public void setRotation(StructureRotation rotation) {
        if (rotation != null) {
            this.rotation = rotation;
        }
    }

    @Override
    public StructureRotation getRotation() {
        return this.rotation;
    }

    @Override
    public void setAuthor(String author) {
        if (author != null) {
            this.author = author;
        }
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public void showInvisibleBlocks(boolean showInvisibleBlocks) {
        this.invisibleBlocks = showInvisibleBlocks;
    }

    @Override
    public boolean isShowingInvisibleBlocks() {
        return this.invisibleBlocks;
    }

    @Override
    public void save() {
        final StructureSaveConfiguration saveConfiguration = this.persistenceStructureService.createSaveConfiguration(this.author, this.name, this.getWorld().getName());
        saveConfiguration.setMirror(this.mirrorType);
        saveConfiguration.setRotation(this.rotation);
        saveConfiguration.setIgnoreEntities(this.ignoreEntities);

        this.persistenceStructureService.save(saveConfiguration, new Location(this.getWorld(), this.position.getX(), this.position.getY(), this.position.getZ()), new Vector(this.sizeX, this.sizeY, this.sizeZ));
    }

    @Override
    public void setSaveName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    @Override
    public String getSaveName() {
        return this.name;
    }

    @Override
    public void setStructureLocation(Location location) {
        if (location != null) {
            this.position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        }
    }

    @Override
    public Location getStructureLocation() {
        return new Location(this.getWorld(), this.position.getX(), this.position.getY(), this.position.getZ());
    }

    @Override
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    @Override
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    @Override
    public void setSizeZ(int sizeZ) {
        this.sizeZ = sizeZ;
    }

    @Override
    public int getSizeX() {
        return this.sizeX;
    }

    @Override
    public int getSizeY() {
        return this.sizeY;
    }

    @Override
    public int getSizeZ() {
        return this.sizeZ;
    }

    @Override
    public void setBlockNameMetaData(String blockNameMetaData) {
        if (blockNameMetaData != null) {
            this.metadata = blockNameMetaData;
        }
    }

    @Override
    public String getBlockNameMetaData() {
        return this.metadata;
    }

    @Override
    public void setIgnoreEntities(boolean ignoreEntities) {
        this.ignoreEntities = ignoreEntities;
    }

    @Override
    public boolean isIgnoreEntities() {
        return this.ignoreEntities;
    }

    @Override
    public void setStructureMode(StructureMode structureMode) {
        if (structureMode == null)
            throw new IllegalArgumentException("StructureMode cannot be null!");
        this.mode = structureMode;
    }

    @Override
    public StructureMode getStructureMode() {
        return this.mode;
    }

    private static StructureRotation getBlockRotation(EnumBlockRotation rotation) {
        switch (rotation) {
            case NONE:
                return StructureRotation.NONE;
            case CLOCKWISE_90:
                return StructureRotation.ROTATION_90;
            case CLOCKWISE_180:
                return StructureRotation.ROTATION_180;
            case COUNTERCLOCKWISE_90:
                return StructureRotation.ROTATION_270;
        }
        return null;
    }

    private static EnumBlockRotation getBlockRotation(StructureRotation rotation) {
        switch (rotation) {
            case NONE:
                return EnumBlockRotation.NONE;
            case ROTATION_90:
                return EnumBlockRotation.CLOCKWISE_90;
            case ROTATION_180:
                return EnumBlockRotation.CLOCKWISE_180;
            case ROTATION_270:
                return EnumBlockRotation.COUNTERCLOCKWISE_90;
        }
        return null;
    }

    private static StructureMode getBlockUseage(TileEntityStructure.UsageMode usageMod) {
        switch (usageMod) {
            case CORNER:
                return StructureMode.CORNER;
            case DATA:
                return StructureMode.DATA;
            case LOAD:
                return StructureMode.LOAD;
            case SAVE:
                return StructureMode.SAVE;
        }
        return null;
    }

    private static TileEntityStructure.UsageMode getBlockUseage(StructureMode structureMode) {
        switch (structureMode) {
            case CORNER:
                return TileEntityStructure.UsageMode.CORNER;
            case DATA:
                return TileEntityStructure.UsageMode.DATA;
            case LOAD:
                return TileEntityStructure.UsageMode.LOAD;
            case SAVE:
                return TileEntityStructure.UsageMode.SAVE;
        }
        return null;
    }

    private StructureMirror getBlockMirror(EnumBlockMirror mirror) {
        switch (mirror) {
            case NONE:
                return StructureMirror.NONE;
            case FRONT_BACK:
                return StructureMirror.FRONT_BACK;
            case LEFT_RIGHT:
                return StructureMirror.LEFT_RIGHT;
        }
        return null;
    }

    private static EnumBlockMirror getBlockMirror(StructureMirror mirror) {
        switch (mirror) {
            case NONE:
                return EnumBlockMirror.NONE;
            case FRONT_BACK:
                return EnumBlockMirror.FRONT_BACK;
            case LEFT_RIGHT:
                return EnumBlockMirror.LEFT_RIGHT;
        }
        return null;
    }
}
