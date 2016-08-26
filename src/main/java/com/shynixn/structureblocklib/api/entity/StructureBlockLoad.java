package com.shynixn.structureblocklib.api.entity;

/**
 * Created by Shynixn
 */
public interface StructureBlockLoad extends StructureBlockConstruction
{
    void setMirrorType(StructureMirror mirrorType);

    void setRotation(StructureRotation rotation);

    StructureRotation getRotation();

    StructureMirror getMirrorType();

    void setBoundingBoxVisible(boolean visible);

    boolean isBoundingBoxVisible();

    void setIntegrity(float integrity);

    float getIntegrity();

    void setSeed(long seed);

    long getSeed();

    void load();
}
