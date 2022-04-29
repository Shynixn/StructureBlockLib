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
import com.github.shynixn.structureblocklib.bukkit.v1_17_R1.CraftStructureBlock;
import com.github.shynixn.structureblocklib.bukkit.v1_17_R1.StructureSerializationServiceImpl;
import com.github.shynixn.structureblocklib.bukkit.v1_17_R1.TypeConversionServiceImpl;
import com.github.shynixn.structureblocklib.core.block.StructureBlockAbstractImpl;
import com.github.shynixn.structureblocklib.core.entity.StructureLoaderAbstractImpl;
import com.github.shynixn.structureblocklib.core.entity.StructureSaverAbstractImpl;
import helper.MockedProxyService;
import helper.MockedStructureWorldService;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class CraftStructureBlockIT {
    /**
     * Given a structure block with a title entity structure including nbt tags
     * when creating a structure block
     * all nbt tags should be correctly retried.
     */
    // @Test Disabled because Jacoco patches the Mojang static methods to over sized methods
    // which lets Java crash. Run it without javacoco enabled
    public void create_NBTTags_ShouldCorrectlyRetrieve() {
        // Arrange
        MockedProxyService proxyService = new MockedProxyService();
        CompoundTag compound = new CompoundTag();
        compound.putString("author", "Mario");
        compound.putString("mirror", "LEFT_RIGHT");
        compound.putString("rotation", "CLOCKWISE_90");
        compound.putString("mode", "SAVE");
        compound.putString("name", "Thisismysavename");
        compound.putString("metadata", "Thisismetadata");
        compound.putInt("posX", 50);
        compound.putInt("posY", 550);
        compound.putInt("posZ", -30);
        compound.putInt("sizeX", 20);
        compound.putInt("sizeY", -40);
        compound.putInt("sizeZ", -70);
        compound.putBoolean("ignoreEntities", true);
        compound.putBoolean("showboundingbox", true);
        compound.putBoolean("showair", true);
        compound.putFloat("integrity", 0.4F);
        compound.putLong("seed", 50L);

        StructureBlockEntity structure = Mockito.mock(StructureBlockEntity.class);
        Mockito.when(structure.save(Mockito.any(CompoundTag.class))).thenReturn(compound);

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
        Assertions.assertEquals(0.4F, classUnderTest.getIntegrity());
        Assertions.assertEquals(50L, classUnderTest.getSeed());
    }

    /**
     * Given a structure block with changed properties
     * when updating the minecraft world
     * all properties should be transferred as nbt tags.
     */
    // @Test Disabled because Jacoco patches the Mojang static methods to over sized methods
    // which lets Java crash. Run it without javacoco enabled
    public void update_ChangedStructureBlock_ShouldCorrectlyGenerateNBT() {
        // Arrange
        MockedProxyService proxyService = new MockedProxyService();
        CompoundTag input = new CompoundTag();
        input.putString("mirror", "LEFT_RIGHT");
        input.putString("rotation", "CLOCKWISE_90");
        input.putString("mode", "SAVE");
        StructureBlockEntity structure = Mockito.mock(StructureBlockEntity.class);
        Mockito.when(structure.save(Mockito.any(CompoundTag.class))).thenReturn(input);
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
        classUnderTest.setIntegrity(0.4F);
        classUnderTest.setSeed(50L);

        // Act
        Wrap<CompoundTag> wrap = new Wrap<>();
        Mockito.doAnswer(invocation -> {
            wrap.item = invocation.getArgument(0);
            return null;
        }).when(structure).load(Mockito.any(CompoundTag.class));
        classUnderTest.update();
        CompoundTag actual = wrap.item;

        // Assert
        Assertions.assertEquals(Mirror.LEFT_RIGHT.toString(), actual.getString("mirror"));
        Assertions.assertEquals(Rotation.CLOCKWISE_90.toString(), actual.getString("rotation"));
        Assertions.assertEquals(StructureMode.SAVE.toString(), actual.getString("mode"));
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
        Assertions.assertEquals(0.4F, actual.getFloat("integrity"));
        Assertions.assertEquals(50L, actual.getLong("seed"));
    }

    private CraftStructureBlock createWithDependencies(ProxyService proxyService,StructureBlockEntity tileEntityStructure) {
        CraftWorld craftWorld = Mockito.mock(CraftWorld.class);
        CraftBlock block = Mockito.mock(CraftBlock.class);
        Mockito.when(block.getWorld()).thenReturn(craftWorld);
        net.minecraft.world.level.Level world = Mockito.mock(net.minecraft.world.level.Level.class);
        Mockito.when(block.getPosition()).thenReturn(new BlockPos(2, 2, 2));
        Mockito.when(world.getTileEntity(Mockito.any(BlockPos.class), Mockito.any(Boolean.class))).thenReturn(tileEntityStructure);

        StructureWorldService worldService = new MockedStructureWorldService();
        StructureSerializationService serializationService = new StructureSerializationServiceImpl();
        StructureLoaderAbstract<Location, Vector, Block, World> structureLoader = new StructureLoaderAbstractImpl<>(proxyService, serializationService, worldService);
        StructureSaverAbstract<Location, Vector> structureSaver = new StructureSaverAbstractImpl<>(proxyService, serializationService, worldService);
        StructureBlockAbstractImpl<Location, Vector, Block, World> vector = new StructureBlockAbstractImpl<>(proxyService, structureLoader, structureSaver);
        TypeConversionService conversionService = new TypeConversionServiceImpl();

        return new WrappedCraftStructureBlock(vector, conversionService, block);
    }

    private static class WrappedCraftStructureBlock extends CraftStructureBlock {
        public WrappedCraftStructureBlock(StructureBlockAbstractImpl<Location, Vector, Block, World> structure, TypeConversionService conversionService, Block block) {
            super(structure, conversionService, block);
        }

        @Override
        public CraftBlock getBlock() {
            return Mockito.mock(CraftBlock.class);
        }

        @Override
        public Material getType() {
            return Material.APPLE;
        }
    }

    private static class Wrap<T> {
        public T item;
    }
}
