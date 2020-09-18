package unittest;

import com.github.shynixn.structureblocklib.bukkit.v1_10_R1.StructureSerializationServiceImpl;
import net.minecraft.server.v1_10_R1.DefinedStructure;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
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
        String expected = "H4sIAAAAAAAAAONiYGAAAHg/+U4EAAAA";

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Act
            classUnderTest.serialize(definedStructure, outputStream);
            String actual = Base64.getEncoder().encodeToString(outputStream.toByteArray());

            // Assert.
            Assertions.assertEquals(expected, actual);
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
