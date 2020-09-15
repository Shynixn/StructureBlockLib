package com.github.shynixn.structureblocklib.api.block;

import com.github.shynixn.structureblocklib.api.entity.StructureLoaderAbstract;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureRotation;
import org.jetbrains.annotations.NotNull;

public interface StructureBlockLoadAbstract<L, V> extends StructureBlockConstructionAbstract<L> {
    /**
     * Sets the mirrorType of the structure when getting load.
     *
     * @param mirrorType mirrorType.
     */
    void setMirrorType(@NotNull StructureMirror mirrorType);

    /**
     * Sets the rotation of the structure when getting load.
     *
     * @param rotation rotation.
     */
    void setRotation(@NotNull StructureRotation rotation);

    /**
     * Returns the rotation of the structure when getting load.
     *
     * @return rotation.
     */
    @NotNull
    StructureRotation getRotation();

    /**
     * Returns the mirrorType of the structure when getting load.
     *
     * @return mirrorType.
     */
    @NotNull
    StructureMirror getMirrorType();

    /**
     * Sets the boundingBoxVisibility.
     *
     * @param visible visible.
     */
    void setBoundingBoxVisible(boolean visible);

    /**
     * Returns if the boundingBox is visible.
     *
     * @return visible.
     */
    boolean isBoundingBoxVisible();

    /**
     * Sets the integrity of the structure.
     *
     * @param integrity integrity.
     */
    void setIntegrity(float integrity);

    /**
     * Returns the integrity of the structure.
     *
     * @return integrity.
     */
    float getIntegrity();

    /**
     * Sets the seed of the structure.
     *
     * @param seed seed.
     */
    void setSeed(long seed);

    /**
     * Returns the seed of the structure.
     *
     * @return seed.
     */
    long getSeed();

    /**
     * Creates a new instance of {@link StructureLoaderAbstract} which
     * contains the current block properties.
     *
     * @return New instance.
     */
    @NotNull
    StructureLoaderAbstract<L, V> loadStructure();
}
