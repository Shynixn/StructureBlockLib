package unittest;

import com.github.shynixn.structureblocklib.bukkit.v1_11_R1.StructureSerializationServiceImpl;
import net.minecraft.server.v1_11_R1.DefinedStructure;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

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
        DefinedStructure definedStructure = Mockito.mock(DefinedStructure.class);
        Mockito.when(definedStructure.a(Mockito.any(NBTTagCompound.class)))
                .thenReturn(new NBTTagCompound());
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
        DefinedStructure definedStructure = Mockito.mock(DefinedStructure.class);
        Mockito.when(definedStructure.a(Mockito.any(NBTTagCompound.class)))
                .thenReturn(new NBTTagCompound());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.serialize("NotReal", new ByteArrayOutputStream());
        });
    }

    private StructureSerializationServiceImpl createWithDependencies() {
        return new StructureSerializationServiceImpl();
    }
}
