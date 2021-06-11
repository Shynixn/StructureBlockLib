package com.github.shynixn.structureblocklib.bukkit.v1_17_R1;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.properties.BlockPropertyStructureMode;

public class TypeConversionServiceImpl implements TypeConversionService {
    /**
     * Converts the given handle to a {@link StructureMode}.
     *
     * @param handle NMS handle.
     * @return {@link StructureMode}.
     */
    @Override
    public StructureMode convertToStructureMode(Object handle) {
        BlockPropertyStructureMode usageMode = (BlockPropertyStructureMode) handle;
        switch (usageMode) {
            case d:
                return StructureMode.DATA;
            case b:
                return StructureMode.LOAD;
            case a:
                return StructureMode.SAVE;
            default:
                return StructureMode.CORNER;
        }
    }

    /**
     * Converts the given handle to a {@link StructureMirror}.
     *
     * @param handle NMS handle.
     * @return {@link StructureMirror}.
     */
    @Override
    public StructureMirror convertToStructureMirror(Object handle) {
        EnumBlockMirror mirror = (EnumBlockMirror) handle;
        switch (mirror) {
            case c:
                return StructureMirror.FRONT_BACK;
            case b:
                return StructureMirror.LEFT_RIGHT;
            default:
                return StructureMirror.NONE;
        }
    }

    /**
     * Converts the given handle to a {@link StructureRotation}.
     *
     * @param handle NMS handle.
     * @return {@link StructureRotation}.
     */
    @Override
    public StructureRotation convertToStructureRotation(Object handle) {
        EnumBlockRotation rotation = (EnumBlockRotation) handle;
        switch (rotation) {
            case b:
                return StructureRotation.ROTATION_90;
            case c:
                return StructureRotation.ROTATION_180;
            case d:
                return StructureRotation.ROTATION_270;
            default:
                return StructureRotation.NONE;
        }
    }

    /**
     * Converts the given {@link StructureMode} to a handle.
     *
     * @param mode {@link StructureMode}.
     * @return NMS handle.
     */
    @Override
    public Object convertToStructureModeHandle(StructureMode mode) {
        switch (mode) {
            case SAVE:
                return BlockPropertyStructureMode.a;
            case DATA:
                return BlockPropertyStructureMode.d;
            case LOAD:
                return BlockPropertyStructureMode.b;
            default:
                return BlockPropertyStructureMode.c;
        }
    }

    /**
     * Converts the given {@link StructureMirror} to a handle.
     *
     * @param mirror {@link StructureMirror}.
     * @return NMS handle.
     */
    @Override
    public Object convertToMirrorHandle(StructureMirror mirror) {
        switch (mirror) {
            case FRONT_BACK:
                return EnumBlockMirror.c;
            case LEFT_RIGHT:
                return EnumBlockMirror.b;
            default:
                return EnumBlockMirror.a;
        }
    }

    /**
     * Converts the given {@link StructureRotation} to a handle.
     *
     * @param rotation {@link StructureRotation}.
     * @return NMS handle.
     */
    @Override
    public Object convertToRotationHandle(StructureRotation rotation) {
        switch (rotation) {
            case ROTATION_90:
                return EnumBlockRotation.b;
            case ROTATION_180:
                return EnumBlockRotation.c;
            case ROTATION_270:
                return EnumBlockRotation.d;
            default:
                return EnumBlockRotation.a;
        }
    }
}
