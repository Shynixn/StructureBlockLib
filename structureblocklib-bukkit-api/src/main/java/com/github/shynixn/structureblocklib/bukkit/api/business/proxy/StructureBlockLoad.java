package com.github.shynixn.structureblocklib.bukkit.api.business.proxy;

import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureRotation;

public interface StructureBlockLoad extends StructureBlockConstruction {
    /**
     * Sets the mirrorType of the structure when getting load.
     *
     * @param mirrorType mirrorType
     */
    void setMirrorType(StructureMirror mirrorType);

    /**
     * Sets the rotation of the structure when getting load.
     *
     * @param rotation rotation
     */
    void setRotation(StructureRotation rotation);

    /**
     * Returns the rotation of the structure when getting load.
     *
     * @return rotation
     */
    StructureRotation getRotation();

    /**
     * Returns the mirrorType of the structure when getting load.
     *
     * @return mirrorType
     */
    StructureMirror getMirrorType();

    /**
     * Sets the boundingBoxVisibility.
     *
     * @param visible visible
     */
    void setBoundingBoxVisible(boolean visible);

    /**
     * Returns if the boundingBox is visible.
     *
     * @return visible
     */
    boolean isBoundingBoxVisible();

    /**
     * Sets the integrity of the structure.
     *
     * @param integrity integrity
     */
    void setIntegrity(float integrity);

    /**
     * Returns the integrity of the structure.
     *
     * @return integrity
     */
    float getIntegrity();

    /**
     * Sets the seed of the structure.
     *
     * @param seed seed
     */
    void setSeed(long seed);

    /**
     * Returns the seed of the structure.
     *
     * @return seed
     */
    long getSeed();

    /**
     * Loads the structure from the worldSave into place.
     */
    void load();
}
