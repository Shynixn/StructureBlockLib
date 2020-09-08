package unittest;

import com.github.shynixn.structureblocklib.core.bukkit.service.StructureSerializationServiceImpl;
import net.minecraft.server.v1_9_R2.DefinedStructure;
import net.minecraft.server.v1_9_R2.DefinedStructureManager;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
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
     * Given a valid defined Structure
     * when deSerialize is called
     * then the content should be read from the input stream.
     */
    @Test
    public void deSerialize_ValidDefinedStructure_ShouldCorrectlyDeSerialize() {
        // Arrange
        StructureSerializationServiceImpl classUnderTest = createWithDependencies();
        byte[] input = Base64.getDecoder().decode("H4sIAAAAAAAAAONiYGAAAHg/+U4EAAAA");

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(input)) {
            // Act
            DefinedStructure definedStructure = (DefinedStructure) classUnderTest.deSerialize(inputStream);
            // Assert.
            Assertions.assertNotNull(definedStructure);
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
        DefinedStructureManager structureManager = Mockito.mock(DefinedStructureManager.class);
        return new StructureSerializationServiceImpl(structureManager);
    }
}
