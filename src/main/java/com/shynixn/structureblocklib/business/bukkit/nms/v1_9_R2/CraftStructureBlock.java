package com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R2;

import com.shynixn.structureblocklib.api.entity.*;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.block.CraftBlockState;

/**
 * Created by Shynixn
 */
public class CraftStructureBlock extends CraftBlockState implements StructureBlockData, StructureBlockSave, StructureBlockLoad
{
    private TileEntityStructure structure;

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

    public CraftStructureBlock(Block block)
    {
        super(block);
        CraftWorld world = (CraftWorld)block.getWorld();
        this.structure = (TileEntityStructure) world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
        refresh();
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics)
    {
        boolean result = super.update(force, applyPhysics);
        if(result)
        {
            structure.a(convert());
            structure.update();
        }
        return result;
    }

    private void refresh()
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound = structure.save(compound);

        this.name = compound.getString("name");
        this.author = compound.getString("author");
        this.metadata = compound.getString("metadata");
        this.position = new BlockPosition(compound.getInt("posX"),compound.getInt("posY"),compound.getInt("posZ"));
        this.sizeX = compound.getInt("sizeX");
        this.sizeY = compound.getInt("sizeY");
        this.sizeZ = compound.getInt("sizeZ");
        this.ignoreEntities = compound.getBoolean("ignoreEntities");

        this.rotation = getBlockRotation(EnumBlockRotation.valueOf(compound.getString("rotation")));
        this.mirrorType = getBlockMirror(EnumBlockMirror.valueOf(compound.getString("mirror")));
        this.mode = getBlockUseage(TileEntityStructure.UsageMode.valueOf(compound.getString("mode")));

        this.boundingBox = compound.getBoolean("showboundingbox");
        this.invisibleBlocks = compound.getBoolean("showair");

        this.integrity = compound.getFloat("integrity");
        this.seed = compound.getLong("seed");
    }

    private NBTTagCompound convert()
    {
        NBTTagCompound compound = new NBTTagCompound();
        compound = structure.save(compound);
        compound.setString("name", name);
        compound.setString("author", author);
        compound.setString("metadata", metadata);
        compound.setInt("posX",position.getX());
        compound.setInt("posY",position.getY());
        compound.setInt("posZ",position.getZ());

        compound.setInt("sizeX", sizeX);
        compound.setInt("sizeY", sizeY);
        compound.setInt("sizeZ", sizeZ);

        compound.setString("rotation", getBlockRotation().toString());
        compound.setString("mirror", getBlockMirror().toString());
        compound.setString("mode", getBlockUseage().toString());

        compound.setBoolean("showboundingbox", boundingBox);
        compound.setBoolean("showair", invisibleBlocks);
        compound.setBoolean("ignoreEntities", ignoreEntities);


        compound.setFloat("integrity", integrity);
        compound.setLong("seed",seed);
        return compound;
    }

    private StructureRotation getBlockRotation(EnumBlockRotation rotation)
    {
        switch (rotation)
        {
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

    private EnumBlockRotation getBlockRotation()
    {
        switch (getRotation())
        {
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

    private StructureMode getBlockUseage(TileEntityStructure.UsageMode usageMod)
    {
        switch (usageMod)
        {
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

    private TileEntityStructure.UsageMode getBlockUseage()
    {
        switch (getStructureMode())
        {
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

    private StructureMirror getBlockMirror(EnumBlockMirror mirror)
    {
        switch (mirror)
        {
            case NONE:
                return StructureMirror.NONE;
            case FRONT_BACK:
                return StructureMirror.FRONT_BACK;
            case LEFT_RIGHT:
                return StructureMirror.LEFT_RIGHT;
        }
        return null;
    }

    private EnumBlockMirror getBlockMirror()
    {
        switch (getMirrorType())
        {
            case NONE:
                return EnumBlockMirror.NONE;
            case FRONT_BACK:
                return EnumBlockMirror.FRONT_BACK;
            case LEFT_RIGHT:
                return EnumBlockMirror.LEFT_RIGHT;
        }
        return null;
    }

    @Override
    public TileEntity getTileEntity()
    {
        return structure;
    }

    @Override
    public void setMirrorType(StructureMirror mirrorType)
    {
        this.mirrorType = mirrorType;
    }

    @Override
    public StructureMirror getMirrorType()
    {
        return mirrorType;
    }

    @Override
    public void setBoundingBoxVisible(boolean visible)
    {
        this.boundingBox = visible;
    }

    @Override
    public boolean isBoundingBoxVisible()
    {
        return boundingBox;
    }

    @Override
    public void setIntegrity(float integrity)
    {
        this.integrity = integrity;
    }

    @Override
    public float getIntegrity()
    {
        return integrity;
    }

    @Override
    public void setSeed(long seed)
    {
        this.seed = seed;
    }

    @Override
    public long getSeed()
    {
        return seed;
    }

    @Override
    public void setRotation(StructureRotation rotation)
    {
        this.rotation = rotation;
    }

    @Override
    public StructureRotation getRotation()
    {
        return rotation;
    }

    @Override
    public void setAuthor(String author)
    {
        this.author = author;
    }

    @Override
    public String getAuthor()
    {
        return author;
    }

    @Override
    public void showInvisibleBlocks(boolean showInvisibleBlocks)
    {
        this.invisibleBlocks = showInvisibleBlocks;
    }

    @Override
    public boolean isShowingInvisibleBlocks()
    {
        return this.invisibleBlocks;
    }

    @Override
    public void setSaveName(String name)
    {
        this.name = name;
    }

    @Override
    public String getSaveName()
    {
        return name;
    }

    @Override
    public void setStructureLocation(Location location)
    {
        position = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    @Override
    public Location getStructureLocation()
    {
        return new Location(getWorld(), position.getX(), position.getY(), position.getZ());
    }

    @Override
    public void setSizeX(int sizeX)
    {
        this.sizeX = sizeX;
    }

    @Override
    public void setSizeY(int sizeY)
    {
        this.sizeY = sizeY;
    }

    @Override
    public void setSizeZ(int sizeZ)
    {
        this.sizeZ = sizeZ;
    }

    @Override
    public int getSizeX()
    {
        return sizeX;
    }

    @Override
    public int getSizeY()
    {
        return sizeY;
    }

    @Override
    public int getSizeZ()
    {
        return sizeZ;
    }

    @Override
    public void setBlockNameMetaData(String blockNameMetaData)
    {
        this.metadata = blockNameMetaData;
    }

    @Override
    public String getBlockNameMetaData()
    {
        return metadata;
    }

    @Override
    public void setIgnoreEntities(boolean ignoreEntities)
    {
        this.ignoreEntities = ignoreEntities;
    }

    @Override
    public boolean isIgnoreEntities()
    {
        return ignoreEntities;
    }

    @Override
    public void setStructureMode(StructureMode structureMode)
    {
        this.mode = structureMode;
    }

    @Override
    public StructureMode getStructureMode()
    {
        return mode;
    }
}
