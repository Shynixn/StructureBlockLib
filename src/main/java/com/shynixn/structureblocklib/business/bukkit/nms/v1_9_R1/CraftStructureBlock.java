package com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R1;

import com.shynixn.structureblocklib.api.entity.*;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.block.CraftBlockState;
import org.bukkit.util.Vector;

public class CraftStructureBlock extends CraftBlockState implements StructureBlockData, StructureBlockSave, StructureBlockLoad {
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
    private float integrity;
    private long seed;

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
        final NBTTagCompound compound = new NBTTagCompound();
        this.structure.save(compound);

        this.name = compound.getString("name");
        this.author = compound.getString("author");
        this.metadata = compound.getString("metadata");
        this.position = new BlockPosition(compound.getInt("posX"),compound.getInt("posY"),compound.getInt("posZ"));
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

    private NBTTagCompound convert() {
        final NBTTagCompound compound = new NBTTagCompound();
        this.structure.save(compound);
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
        this.mirrorType = mirrorType;
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
        load(this.author, this.name, new Location(this.getWorld(), this.position.getX(), this.position.getY(), this.position.getZ()), this.ignoreEntities, this.rotation, this.mirrorType);
    }

    @Override
    public void setRotation(StructureRotation rotation) {
        this.rotation = rotation;
    }

    @Override
    public StructureRotation getRotation() {
        return this.rotation;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
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
        save(this.author, this.name, new Location(this.getWorld(), this.position.getX(), this.position.getY(), this.position.getZ()), new Vector(this.sizeX, this.sizeY, this.sizeZ), this.ignoreEntities);
    }

    @Override
    public void setSaveName(String name) {
        this.name = name;
    }

    @Override
    public String getSaveName() {
        return this.name;
    }

    @Override
    public void setStructureLocation(Location location) {
        this.position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
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
        this.metadata = blockNameMetaData;
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

    public static void save(String author, String saveName, Location corner, Vector dimensions, boolean ignoreEntities) {
        final BlockPosition vPosition = new BlockPosition(0, 0, 0);
        final BlockPosition var1 = vPosition.a(new BlockPosition(corner.getBlockX(), corner.getBlockY(), corner.getBlockZ()));
        final WorldServer var2 = ((CraftWorld) corner.getWorld()).getHandle();
        final MinecraftServer var3 = ((CraftWorld) corner.getWorld()).getHandle().getMinecraftServer();
        final DefinedStructureManager var4 = var2.y();
        final DefinedStructure var5 = var4.a(var3, new MinecraftKey(saveName, author));
        var5.a(((CraftWorld)corner.getWorld()).getHandle(), var1, new BlockPosition(dimensions.getBlockX(),dimensions.getBlockY(), dimensions.getBlockZ()), !ignoreEntities, Blocks.BARRIER);
        var5.a(author);
        var4.c(var3, new MinecraftKey(saveName));
    }

    public static void load(String author, String saveName, Location corner, boolean ignoreEntities, StructureRotation rotation, StructureMirror mirror) {
        if(((CraftWorld)corner.getWorld()).getHandle().isClientSide)
            return;
        final BlockPosition vPosition = new BlockPosition(0, 0, 0);
        final BlockPosition var1 = vPosition.a(new BlockPosition(corner.getBlockX(), corner.getBlockY(), corner.getBlockZ()));
        final WorldServer var2 = ((CraftWorld) corner.getWorld()).getHandle();
        final MinecraftServer var3 = ((CraftWorld) corner.getWorld()).getHandle().getMinecraftServer();
        final DefinedStructureManager var4 = var2.y();
        final DefinedStructure var5 = var4.a(var3, new MinecraftKey(saveName, author));
        final DefinedStructureInfo var9 = (new DefinedStructureInfo()).a(getBlockMirror(mirror)).a(getBlockRotation(rotation)).a(ignoreEntities).a((ChunkCoordIntPair) null).a((net.minecraft.server.v1_9_R1.Block) null).b(false);
        var5.a(((CraftWorld)corner.getWorld()).getHandle(), var1, var9);
    }
}
