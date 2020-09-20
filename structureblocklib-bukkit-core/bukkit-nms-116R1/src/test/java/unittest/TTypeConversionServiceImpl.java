package unittest;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.bukkit.v1_16_R1.TypeConversionServiceImpl;
import net.minecraft.server.v1_16_R1.BlockPropertyStructureMode;
import net.minecraft.server.v1_16_R1.EnumBlockMirror;
import net.minecraft.server.v1_16_R1.EnumBlockRotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TTypeConversionServiceImpl {
    /**
     * Given StructureMirror
     * when convert is called
     * then value should be correctly converted.
     */
    @Test
    public void convert_StructureMirror_ShouldCorrectlyConvert() {
        // Arrange
        TypeConversionService conversionService = createWithDependencies();

        // Assert
        Assertions.assertEquals(EnumBlockMirror.LEFT_RIGHT, conversionService.convertToMirrorHandle(StructureMirror.LEFT_RIGHT));
        Assertions.assertEquals(EnumBlockMirror.FRONT_BACK, conversionService.convertToMirrorHandle(StructureMirror.FRONT_BACK));
        Assertions.assertEquals(EnumBlockMirror.NONE, conversionService.convertToMirrorHandle(StructureMirror.NONE));
        Assertions.assertEquals(StructureMirror.LEFT_RIGHT, conversionService.convertToStructureMirror(EnumBlockMirror.LEFT_RIGHT));
        Assertions.assertEquals(StructureMirror.FRONT_BACK, conversionService.convertToStructureMirror(EnumBlockMirror.FRONT_BACK));
        Assertions.assertEquals(StructureMirror.NONE, conversionService.convertToStructureMirror(EnumBlockMirror.NONE));
    }

    /**
     * Given StructureRotation
     * when convert is called
     * then value should be correctly converted.
     */
    @Test
    public void convert_StructureRotation_ShouldCorrectlyConvert() {
        // Arrange
        TypeConversionService conversionService = createWithDependencies();

        // Assert
        Assertions.assertEquals(EnumBlockRotation.CLOCKWISE_90, conversionService.convertToRotationHandle(StructureRotation.ROTATION_90));
        Assertions.assertEquals(EnumBlockRotation.CLOCKWISE_180, conversionService.convertToRotationHandle(StructureRotation.ROTATION_180));
        Assertions.assertEquals(EnumBlockRotation.COUNTERCLOCKWISE_90, conversionService.convertToRotationHandle(StructureRotation.ROTATION_270));
        Assertions.assertEquals(EnumBlockRotation.NONE, conversionService.convertToRotationHandle(StructureRotation.NONE));
        Assertions.assertEquals(StructureRotation.ROTATION_90, conversionService.convertToStructureRotation(EnumBlockRotation.CLOCKWISE_90));
        Assertions.assertEquals(StructureRotation.ROTATION_180, conversionService.convertToStructureRotation(EnumBlockRotation.CLOCKWISE_180));
        Assertions.assertEquals(StructureRotation.ROTATION_270, conversionService.convertToStructureRotation(EnumBlockRotation.COUNTERCLOCKWISE_90));
        Assertions.assertEquals(StructureRotation.NONE, conversionService.convertToStructureRotation(EnumBlockRotation.NONE));
    }

    /**
     * Given StructureMode
     * when convert is called
     * then value should be correctly converted.
     */
    @Test
    public void convert_StructureMode_ShouldCorrectlyConvert() {
        // Arrange
        TypeConversionService conversionService = createWithDependencies();

        // Assert
        Assertions.assertEquals(BlockPropertyStructureMode.CORNER, conversionService.convertToStructureModeHandle(StructureMode.CORNER));
        Assertions.assertEquals(BlockPropertyStructureMode.SAVE, conversionService.convertToStructureModeHandle(StructureMode.SAVE));
        Assertions.assertEquals(BlockPropertyStructureMode.DATA, conversionService.convertToStructureModeHandle(StructureMode.DATA));
        Assertions.assertEquals(BlockPropertyStructureMode.LOAD, conversionService.convertToStructureModeHandle(StructureMode.LOAD));
        Assertions.assertEquals(StructureMode.CORNER, conversionService.convertToStructureMode(BlockPropertyStructureMode.CORNER));
        Assertions.assertEquals(StructureMode.DATA, conversionService.convertToStructureMode(BlockPropertyStructureMode.DATA));
        Assertions.assertEquals(StructureMode.SAVE, conversionService.convertToStructureMode(BlockPropertyStructureMode.SAVE));
        Assertions.assertEquals(StructureMode.LOAD, conversionService.convertToStructureMode(BlockPropertyStructureMode.LOAD));
    }

    private TypeConversionService createWithDependencies() {
        return new TypeConversionServiceImpl();
    }
}
