package unittest;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.bukkit.v1_17_R1.TypeConversionServiceImpl;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;
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
        Assertions.assertEquals(EnumBlockMirror.b, conversionService.convertToMirrorHandle(StructureMirror.LEFT_RIGHT));
        Assertions.assertEquals(EnumBlockMirror.c, conversionService.convertToMirrorHandle(StructureMirror.FRONT_BACK));
        Assertions.assertEquals(EnumBlockMirror.a, conversionService.convertToMirrorHandle(StructureMirror.NONE));
        Assertions.assertEquals(StructureMirror.LEFT_RIGHT, conversionService.convertToStructureMirror(EnumBlockMirror.b));
        Assertions.assertEquals(StructureMirror.FRONT_BACK, conversionService.convertToStructureMirror(EnumBlockMirror.c));
        Assertions.assertEquals(StructureMirror.NONE, conversionService.convertToStructureMirror(EnumBlockMirror.a));
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
        Assertions.assertEquals(EnumBlockRotation.b, conversionService.convertToRotationHandle(StructureRotation.ROTATION_90));
        Assertions.assertEquals(EnumBlockRotation.c, conversionService.convertToRotationHandle(StructureRotation.ROTATION_180));
        Assertions.assertEquals(EnumBlockRotation.d, conversionService.convertToRotationHandle(StructureRotation.ROTATION_270));
        Assertions.assertEquals(EnumBlockRotation.a, conversionService.convertToRotationHandle(StructureRotation.NONE));
        Assertions.assertEquals(StructureRotation.ROTATION_90, conversionService.convertToStructureRotation(EnumBlockRotation.b));
        Assertions.assertEquals(StructureRotation.ROTATION_180, conversionService.convertToStructureRotation(EnumBlockRotation.c));
        Assertions.assertEquals(StructureRotation.ROTATION_270, conversionService.convertToStructureRotation(EnumBlockRotation.d));
        Assertions.assertEquals(StructureRotation.NONE, conversionService.convertToStructureRotation(EnumBlockRotation.a));
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
        Assertions.assertEquals(BlockPropertyStructureMode.c, conversionService.convertToStructureModeHandle(StructureMode.CORNER));
        Assertions.assertEquals(BlockPropertyStructureMode.a, conversionService.convertToStructureModeHandle(StructureMode.SAVE));
        Assertions.assertEquals(BlockPropertyStructureMode.d, conversionService.convertToStructureModeHandle(StructureMode.DATA));
        Assertions.assertEquals(BlockPropertyStructureMode.b, conversionService.convertToStructureModeHandle(StructureMode.LOAD));
        Assertions.assertEquals(StructureMode.CORNER, conversionService.convertToStructureMode(BlockPropertyStructureMode.c));
        Assertions.assertEquals(StructureMode.DATA, conversionService.convertToStructureMode(BlockPropertyStructureMode.d));
        Assertions.assertEquals(StructureMode.SAVE, conversionService.convertToStructureMode(BlockPropertyStructureMode.a));
        Assertions.assertEquals(StructureMode.LOAD, conversionService.convertToStructureMode(BlockPropertyStructureMode.b));
    }

    private TypeConversionService createWithDependencies() {
        return new TypeConversionServiceImpl();
    }
}
