package unittest;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import com.github.shynixn.structureblocklib.bukkit.v1_18_R2.TypeConversionServiceImpl;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
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
        Assertions.assertEquals(Mirror.LEFT_RIGHT, conversionService.convertToMirrorHandle(StructureMirror.LEFT_RIGHT));
        Assertions.assertEquals(Mirror.FRONT_BACK, conversionService.convertToMirrorHandle(StructureMirror.FRONT_BACK));
        Assertions.assertEquals(Mirror.NONE, conversionService.convertToMirrorHandle(StructureMirror.NONE));
        Assertions.assertEquals(StructureMirror.LEFT_RIGHT, conversionService.convertToStructureMirror(Mirror.LEFT_RIGHT));
        Assertions.assertEquals(StructureMirror.FRONT_BACK, conversionService.convertToStructureMirror(Mirror.FRONT_BACK));
        Assertions.assertEquals(StructureMirror.NONE, conversionService.convertToStructureMirror(Mirror.NONE));
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
        Assertions.assertEquals(Rotation.CLOCKWISE_90, conversionService.convertToRotationHandle(StructureRotation.ROTATION_90));
        Assertions.assertEquals(Rotation.CLOCKWISE_180, conversionService.convertToRotationHandle(StructureRotation.ROTATION_180));
        Assertions.assertEquals(Rotation.COUNTERCLOCKWISE_90, conversionService.convertToRotationHandle(StructureRotation.ROTATION_270));
        Assertions.assertEquals(Rotation.NONE, conversionService.convertToRotationHandle(StructureRotation.NONE));
        Assertions.assertEquals(StructureRotation.ROTATION_90, conversionService.convertToStructureRotation(Rotation.CLOCKWISE_90));
        Assertions.assertEquals(StructureRotation.ROTATION_180, conversionService.convertToStructureRotation(Rotation.CLOCKWISE_180));
        Assertions.assertEquals(StructureRotation.ROTATION_270, conversionService.convertToStructureRotation(Rotation.COUNTERCLOCKWISE_90));
        Assertions.assertEquals(StructureRotation.NONE, conversionService.convertToStructureRotation(Rotation.NONE));
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
        Assertions.assertEquals(net.minecraft.world.level.block.state.properties.StructureMode.CORNER, conversionService.convertToStructureModeHandle(StructureMode.CORNER));
        Assertions.assertEquals(net.minecraft.world.level.block.state.properties.StructureMode.SAVE, conversionService.convertToStructureModeHandle(StructureMode.SAVE));
        Assertions.assertEquals(net.minecraft.world.level.block.state.properties.StructureMode.DATA, conversionService.convertToStructureModeHandle(StructureMode.DATA));
        Assertions.assertEquals(net.minecraft.world.level.block.state.properties.StructureMode.LOAD, conversionService.convertToStructureModeHandle(StructureMode.LOAD));
        Assertions.assertEquals(StructureMode.CORNER, conversionService.convertToStructureMode(net.minecraft.world.level.block.state.properties.StructureMode.CORNER));
        Assertions.assertEquals(StructureMode.DATA, conversionService.convertToStructureMode(net.minecraft.world.level.block.state.properties.StructureMode.DATA));
        Assertions.assertEquals(StructureMode.SAVE, conversionService.convertToStructureMode(net.minecraft.world.level.block.state.properties.StructureMode.SAVE));
        Assertions.assertEquals(StructureMode.LOAD, conversionService.convertToStructureMode(net.minecraft.world.level.block.state.properties.StructureMode.LOAD));
    }

    private TypeConversionService createWithDependencies() {
        return new TypeConversionServiceImpl();
    }
}
