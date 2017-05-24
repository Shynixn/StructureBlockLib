package com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1;

import com.shynixn.structureblocklib.api.entity.*;
import net.minecraft.server.v1_11_R1.EnumBlockMirror;
import net.minecraft.server.v1_11_R1.EnumBlockRotation;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.TileEntityStructure;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CraftSructureBlock111R1Test {

    @Test
    public void structureBlockBehaviourTest() {
        TileEntityStructure structure = mock(TileEntityStructure.class);
        CraftWorld world = mock(CraftWorld.class);
        Block block = mock(Block.class);
        when(block.getWorld()).thenReturn(world);
        when(world.getTileEntityAt(any(int.class), any(int.class), any(int.class))).thenReturn(structure);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                NBTTagCompound compound = invocationOnMock.getArgument(0);
                compound.setString("rotation", EnumBlockRotation.NONE.toString());
                compound.setString("mirror", EnumBlockMirror.NONE.toString());
                compound.setString("mode", TileEntityStructure.UsageMode.SAVE.toString());
                return compound;
            }
        }).when(structure).save(any(NBTTagCompound.class));

        final StructureBlock structureBlock = new com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock(block);
        assertEquals(StructureMode.SAVE, structureBlock.getStructureMode());

        structureBlock.setStructureMode(StructureMode.CORNER);
        assertEquals(StructureMode.CORNER, structureBlock.getStructureMode());

        Throwable exception = assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                structureBlock.setStructureMode(null);
            }
        });
        assertEquals("StructureMode cannot be null!", exception.getMessage());
        assertEquals(StructureMode.CORNER, structureBlock.getStructureMode());

        NBTTagCompound compound = ((com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock) structureBlock).convert();
        assertEquals(TileEntityStructure.UsageMode.CORNER.toString(), compound.getString("mode"));
    }

    @Test
    public void structureBlockConstructionBehaviourTest() {
        TileEntityStructure structure = mock(TileEntityStructure.class);
        CraftWorld world = mock(CraftWorld.class);
        Block block = mock(Block.class);
        when(block.getWorld()).thenReturn(world);
        when(world.getTileEntityAt(any(int.class), any(int.class), any(int.class))).thenReturn(structure);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                NBTTagCompound compound = invocationOnMock.getArgument(0);
                compound.setString("rotation", EnumBlockRotation.NONE.toString());
                compound.setString("mirror", EnumBlockMirror.NONE.toString());
                compound.setString("mode", TileEntityStructure.UsageMode.SAVE.toString());
                return compound;
            }
        }).when(structure).save(any(NBTTagCompound.class));

        final StructureBlockConstruction structureBlock = new com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock(block);

        structureBlock.setAuthor("SampleAuthor");
        assertEquals("SampleAuthor", structureBlock.getAuthor());
        structureBlock.setAuthor(null);
        assertEquals("SampleAuthor", structureBlock.getAuthor());

        structureBlock.setIgnoreEntities(true);
        assertEquals(true, structureBlock.isIgnoreEntities());

        structureBlock.setSizeX(5);
        structureBlock.setSizeY(10);
        structureBlock.setSizeZ(20);
        assertEquals(5, structureBlock.getSizeX());
        assertEquals(10, structureBlock.getSizeY());
        assertEquals(20, structureBlock.getSizeZ());

        structureBlock.setStructureLocation(new Location(world, 5.55, 2.25, 1.55));
        structureBlock.setStructureLocation(null);
        assertEquals(5, structureBlock.getStructureLocation().getX());
        assertEquals(2, structureBlock.getStructureLocation().getY());
        assertEquals(1, structureBlock.getStructureLocation().getZ());

        structureBlock.setSaveName("SampleSaveName");
        structureBlock.setSaveName(null);
        assertEquals("SampleSaveName", structureBlock.getSaveName());

        NBTTagCompound compound = ((com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock) structureBlock).convert();
        assertEquals("SampleAuthor", compound.getString("author"));
        assertEquals(true, compound.getBoolean("ignoreEntities"));
        assertEquals(5, compound.getInt("sizeX"));
        assertEquals(10, compound.getInt("sizeY"));
        assertEquals(20, compound.getInt("sizeZ"));
        assertEquals(5, compound.getDouble("posX"));
        assertEquals(2, compound.getDouble("posY"));
        assertEquals(1, compound.getDouble("posZ"));
        assertEquals("SampleSaveName", compound.getString("name"));
    }

    @Test
    public void structureBlockSaveLoadCornerDataBehaviourTest() {
        TileEntityStructure structure = mock(TileEntityStructure.class);
        CraftWorld world = mock(CraftWorld.class);
        Block block = mock(Block.class);
        when(block.getWorld()).thenReturn(world);
        when(world.getTileEntityAt(any(int.class), any(int.class), any(int.class))).thenReturn(structure);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                NBTTagCompound compound = invocationOnMock.getArgument(0);
                compound.setString("rotation", EnumBlockRotation.NONE.toString());
                compound.setString("mirror", EnumBlockMirror.NONE.toString());
                compound.setString("mode", TileEntityStructure.UsageMode.SAVE.toString());
                return compound;
            }
        }).when(structure).save(any(NBTTagCompound.class));

        StructureBlockSave structureBlockSave = new com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock(block);
        structureBlockSave.showInvisibleBlocks(true);
        assertEquals(true, structureBlockSave.isShowingInvisibleBlocks());

        StructureBlockLoad structureBlockLoad = (StructureBlockLoad) structureBlockSave;
        structureBlockLoad.setBoundingBoxVisible(true);
        assertEquals(true, structureBlockLoad.isBoundingBoxVisible());
        structureBlockLoad.setIntegrity(1.2F);
        assertEquals(1.2F, structureBlockLoad.getIntegrity());
        structureBlockLoad.setSeed(2L);
        assertEquals(2L, structureBlockLoad.getSeed());
        structureBlockLoad.setMirrorType(StructureMirror.LEFT_RIGHT);
        structureBlockLoad.setMirrorType(null);
        assertEquals(StructureMirror.LEFT_RIGHT, structureBlockLoad.getMirrorType());
        structureBlockLoad.setRotation(StructureRotation.ROTATION_90);
        structureBlockLoad.setRotation(null);
        assertEquals(StructureRotation.ROTATION_90, structureBlockLoad.getRotation());

        StructureBlockData structureBlockData = (StructureBlockData) structureBlockSave;
        structureBlockData.setBlockNameMetaData("SampleMeta");
        structureBlockData.setBlockNameMetaData(null);
        assertEquals("SampleMeta", structureBlockData.getBlockNameMetaData());

        NBTTagCompound compound = ((CraftStructureBlock) structureBlockSave).convert();
        assertEquals(true, compound.getBoolean("showair"));
        assertEquals(true, compound.getBoolean("showboundingbox"));
        assertEquals(1.2F, compound.getFloat("integrity"));
        assertEquals(2L, compound.getLong("seed"));
        assertEquals(EnumBlockMirror.LEFT_RIGHT.toString(), compound.getString("mirror"));
        assertEquals(EnumBlockRotation.CLOCKWISE_90.toString(), compound.getString("rotation"));
        assertEquals("SampleMeta", compound.getString("metadata"));
    }
}
