package integrationtest;

import com.github.shynixn.structureblocklib.api.entity.StructureLoaderAbstract;
import com.github.shynixn.structureblocklib.api.entity.StructureSaverAbstract;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.CraftStructureBlock;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.StructureSerializationServiceImpl;
import com.github.shynixn.structureblocklib.bukkit.v1_9_R2.TypeConversionServiceImpl;
import com.github.shynixn.structureblocklib.core.block.StructureBlockAbstractImpl;
import com.github.shynixn.structureblocklib.core.entity.StructureLoaderAbstractImpl;
import com.github.shynixn.structureblocklib.core.entity.StructureSaverAbstractImpl;
import helper.MockedProxyService;
import helper.MockedStructureWorldService;
import net.minecraft.server.v1_9_R2.EnumBlockMirror;
import net.minecraft.server.v1_9_R2.EnumBlockRotation;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import net.minecraft.server.v1_9_R2.TileEntityStructure;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CraftStructureBlockIT {
    /**
     * Given a structure block with a title entity structure including nbt tags
     * when creating a structure block
     * all nbt tags should be correctly retried.
     */
    @Test
    public void create_NBTTags_ShouldCorrectlyRetrieve() {
        // Arrange
        MockedProxyService proxyService = new MockedProxyService();
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("author", "Mario");
        compound.setString("mirror", "LEFT_RIGHT");
        compound.setString("rotation", "CLOCKWISE_90");
        compound.setString("mode", "SAVE");
        compound.setString("name", "Thisismysavename");
        compound.setString("metadata", "Thisismetadata");
        compound.setInt("posX", 50);
        compound.setInt("posY", 550);
        compound.setInt("posZ", -30);
        compound.setInt("sizeX", 20);
        compound.setInt("sizeY", -40);
        compound.setInt("sizeZ", -70);
        compound.setBoolean("ignoreEntities", true);
        compound.setBoolean("showboundingbox", true);
        compound.setBoolean("showair", true);

        TileEntityStructure structure = Mockito.mock(TileEntityStructure.class);
        Mockito.when(structure.save(Mockito.any(NBTTagCompound.class))).thenReturn(compound);

        // Act
        CraftStructureBlock classUnderTest = createWithDependencies(proxyService, structure);

        // Assert
        Assertions.assertEquals(StructureMirror.LEFT_RIGHT, classUnderTest.getMirrorType());
        Assertions.assertEquals(StructureRotation.ROTATION_90, classUnderTest.getRotationType());
        Assertions.assertEquals(StructureMode.SAVE, classUnderTest.getStructureMode());
        Assertions.assertEquals("Mario", classUnderTest.getAuthor());
        Assertions.assertEquals("Thisismysavename", classUnderTest.getSaveName());
        Assertions.assertEquals("Thisismetadata", classUnderTest.getBlockNameMetaData());
        Assertions.assertEquals(50, classUnderTest.getStructureLocation().getBlockX());
        Assertions.assertEquals(550, classUnderTest.getStructureLocation().getBlockY());
        Assertions.assertEquals(-30, classUnderTest.getStructureLocation().getBlockZ());
        Assertions.assertEquals(20, classUnderTest.getSizeX());
        Assertions.assertEquals(-40, classUnderTest.getSizeY());
        Assertions.assertEquals(-70, classUnderTest.getSizeZ());
        Assertions.assertFalse(classUnderTest.isIncludeEntitiesEnabled());
        Assertions.assertTrue(classUnderTest.isBoundingBoxVisible());
        Assertions.assertTrue(classUnderTest.isInvisibleBlocksEnabled());
    }

    /**
     * Given a structure block with changed properties
     * when updating the minecraft world
     * all properties should be transferred as nbt tags.
     */
    @Test
    public void update_ChangedStructureBlock_ShouldCorrectlyGenerateNBT() {
        // Arrange
        MockedProxyService proxyService = new MockedProxyService();
        NBTTagCompound input = new NBTTagCompound();
        input.setString("mirror", "LEFT_RIGHT");
        input.setString("rotation", "CLOCKWISE_90");
        input.setString("mode", "SAVE");
        TileEntityStructure structure = Mockito.mock(TileEntityStructure.class);
        Mockito.when(structure.save(Mockito.any(NBTTagCompound.class))).thenReturn(input);
        CraftStructureBlock classUnderTest = createWithDependencies(proxyService, structure);
        classUnderTest.setMirrorType(StructureMirror.LEFT_RIGHT);
        classUnderTest.setRotationType(StructureRotation.ROTATION_90);
        classUnderTest.setStructureMode(StructureMode.SAVE);
        classUnderTest.setAuthor("Mario");
        classUnderTest.setSaveName("Thisismysavename");
        classUnderTest.setBlockNameMetaData("Thisismetadata");
        classUnderTest.setStructureLocation(new Location(Mockito.mock(World.class), 50, 500, -30));
        classUnderTest.setSizeX(20);
        classUnderTest.setSizeY(-40);
        classUnderTest.setSizeZ(-70);
        classUnderTest.setIncludeEntities(true);
        classUnderTest.setBoundingBoxVisible(true);
        classUnderTest.setInvisibleBlocksEnabled(true);

        // Act
        Wrap<NBTTagCompound> wrap = new Wrap<>();
        Mockito.doAnswer(invocation -> {
            wrap.item = invocation.getArgument(0);
            return null;
        }).when(structure).a(Mockito.any(NBTTagCompound.class));
        classUnderTest.update();
        NBTTagCompound actual = wrap.item;

        // Assert
        Assertions.assertEquals(EnumBlockMirror.LEFT_RIGHT.toString(), actual.getString("mirror"));
        Assertions.assertEquals(EnumBlockRotation.CLOCKWISE_90.toString(), actual.getString("rotation"));
        Assertions.assertEquals(TileEntityStructure.UsageMode.SAVE.toString(), actual.getString("mode"));
        Assertions.assertEquals("Mario", actual.getString("author"));
        Assertions.assertEquals("Thisismysavename", actual.getString("name"));
        Assertions.assertEquals("Thisismetadata", actual.getString("metadata"));
        Assertions.assertEquals(50, actual.getInt("posX"));
        Assertions.assertEquals(500, actual.getInt("posY"));
        Assertions.assertEquals(-30, actual.getInt("posZ"));
        Assertions.assertEquals(20, actual.getInt("sizeX"));
        Assertions.assertEquals(-40, actual.getInt("sizeY"));
        Assertions.assertEquals(-70, actual.getInt("sizeZ"));
        Assertions.assertFalse(actual.getBoolean("ignoreEntities"));
        Assertions.assertTrue(actual.getBoolean("showboundingbox"));
        Assertions.assertTrue(actual.getBoolean("showair"));
    }

    private CraftStructureBlock createWithDependencies(ProxyService proxyService, TileEntityStructure tileEntityStructure) {
        CraftWorld craftWorld = Mockito.mock(CraftWorld.class);
        org.bukkit.block.Block block = Mockito.mock(org.bukkit.block.Block.class);
        Mockito.when(block.getWorld()).thenReturn(craftWorld);
        Mockito.when(craftWorld.getTileEntityAt(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(tileEntityStructure);

        StructureWorldService worldService = new MockedStructureWorldService();
        StructureSerializationService serializationService = new StructureSerializationServiceImpl();
        StructureLoaderAbstract<Location, Vector, Block,org.bukkit.entity.Entity, World> structureLoader = new StructureLoaderAbstractImpl<>(proxyService, serializationService, worldService);
        StructureSaverAbstract<Location, Vector> structureSaver = new StructureSaverAbstractImpl<>(proxyService, serializationService, worldService);
        StructureBlockAbstractImpl<Location, Vector, Block,org.bukkit.entity.Entity, World> vector = new StructureBlockAbstractImpl<>(proxyService, structureLoader, structureSaver);
        TypeConversionService conversionService = new TypeConversionServiceImpl();

        return new WrappedCraftStructureBlock(vector, conversionService, block);
    }

    private static class WrappedCraftStructureBlock extends CraftStructureBlock {
        public WrappedCraftStructureBlock(StructureBlockAbstractImpl<Location, Vector, Block,org.bukkit.entity.Entity, World> structure, TypeConversionService conversionService, Block block) {
            super(structure, conversionService, block);
        }

        @Override
        public Block getBlock() {
            return Mockito.mock(Block.class);
        }
    }

    private static class Wrap<T> {
        public volatile T item;
    }
}
