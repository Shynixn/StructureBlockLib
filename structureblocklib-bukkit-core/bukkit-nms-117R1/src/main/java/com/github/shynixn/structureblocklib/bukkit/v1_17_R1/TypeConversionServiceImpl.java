package com.github.shynixn.structureblocklib.bukkit.v1_17_R1;

import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.api.service.TypeConversionService;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class TypeConversionServiceImpl implements TypeConversionService {
    /**
     * Converts the given handle to a {@link StructureMode}.
     *
     * @param handle NMS handle.
     * @return {@link StructureMode}.
     */
    @Override
    public StructureMode convertToStructureMode(Object handle) {
        net.minecraft.world.level.block.state.properties.StructureMode usageMode = (net.minecraft.world.level.block.state.properties.StructureMode) handle;
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
        Mirror mirror = (Mirror) handle;
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
        Rotation rotation = (Rotation) handle;
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
                return net.minecraft.world.level.block.state.properties.StructureMode.SAVE;
            case DATA:
                return net.minecraft.world.level.block.state.properties.StructureMode.DATA;
            case LOAD:
                return net.minecraft.world.level.block.state.properties.StructureMode.LOAD;
            default:
                return net.minecraft.world.level.block.state.properties.StructureMode.CORNER;
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
                return Mirror.FRONT_BACK;
            case LEFT_RIGHT:
                return Mirror.LEFT_RIGHT;
            default:
                return Mirror.NONE;
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
                return Rotation.CLOCKWISE_90;
            case ROTATION_180:
                return Rotation.CLOCKWISE_180;
            case ROTATION_270:
                return Rotation.COUNTERCLOCKWISE_90;
            default:
                return Rotation.NONE;
        }
    }
}
