package unittest;

import com.github.shynixn.structureblocklib.bukkit.v1_18_R2.StructureSerializationServiceImpl;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TStructureSerializationServiceImpl {
    /**
     * Given a valid defined Structure
     * when serialize is called
     * then the content should be written to the output stream.
     */
    @Test
    public void serialize_ValidDefinedStructure_ShouldCorrectlySerialize() {
        // Arrange
        StructureSerializationServiceImpl classUnderTest = createWithDependencies();
        StructureTemplate definedStructure = Mockito.mock(StructureTemplate.class);
        Mockito.when(definedStructure.save(Mockito.any(CompoundTag.class)))
                .thenReturn(new CompoundTag());
        byte[] expected = new byte[]{
                31, -117, 8, 0, 0, 0, 0, 0, 0, -1, -29, 98, 96, 96, 0, 0, 120, 63, -7, 78, 4, 0, 0, 0
        };

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Act
            classUnderTest.serialize(definedStructure, outputStream);
            byte[] actual = outputStream.toByteArray();

            // Assert.
            Assertions.assertArrayEquals(expected, actual);
        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    /**
     * Given a invalid defined Structure
     * when serialize is called
     * then an exception should be thrown.
     */
    @Test
    public void seSerialize_InvalidDefinedStructure_ShouldThrowException() {
        // Arrange
        StructureSerializationServiceImpl classUnderTest = createWithDependencies();
        StructureTemplate definedStructure = Mockito.mock(StructureTemplate.class);
        Mockito.when(definedStructure.save(Mockito.any(CompoundTag.class)))
                .thenReturn(new CompoundTag());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.serialize("NotReal", new ByteArrayOutputStream());
        });
    }

    private StructureSerializationServiceImpl createWithDependencies() {
        return new StructureSerializationServiceImpl();
    }
}
