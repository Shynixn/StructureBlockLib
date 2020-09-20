package com.github.shynixn.structureblocklib.bukkit.v1_13_R2;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import net.minecraft.server.v1_13_R2.BlockPropertyStructureMode;
import net.minecraft.server.v1_13_R2.EnumBlockMirror;
import net.minecraft.server.v1_13_R2.EnumBlockRotation;

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
            case DATA:
                return StructureMode.DATA;
            case LOAD:
                return StructureMode.LOAD;
            case SAVE:
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
            case FRONT_BACK:
                return StructureMirror.FRONT_BACK;
            case LEFT_RIGHT:
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
            case CLOCKWISE_90:
                return StructureRotation.ROTATION_90;
            case CLOCKWISE_180:
                return StructureRotation.ROTATION_180;
            case COUNTERCLOCKWISE_90:
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
                return BlockPropertyStructureMode.SAVE;
            case DATA:
                return BlockPropertyStructureMode.DATA;
            case LOAD:
                return BlockPropertyStructureMode.LOAD;
            default:
                return BlockPropertyStructureMode.CORNER;
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
                return EnumBlockMirror.FRONT_BACK;
            case LEFT_RIGHT:
                return EnumBlockMirror.LEFT_RIGHT;
            default:
                return EnumBlockMirror.NONE;
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
                return EnumBlockRotation.CLOCKWISE_90;
            case ROTATION_180:
                return EnumBlockRotation.CLOCKWISE_180;
            case ROTATION_270:
                return EnumBlockRotation.COUNTERCLOCKWISE_90;
            default:
                return EnumBlockRotation.NONE;
        }
    }
}
