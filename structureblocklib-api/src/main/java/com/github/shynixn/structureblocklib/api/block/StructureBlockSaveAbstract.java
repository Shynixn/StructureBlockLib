package com.github.shynixn.structureblocklib.api.block;

import com.github.shynixn.structureblocklib.api.entity.StructureSaverAbstract;
import org.jetbrains.annotations.NotNull;

public interface StructureBlockSaveAbstract<L, V> extends StructureBlockConstructionAbstract<L> {
    /**
     * Sets if invisibleBlocks should be visible.
     *
     * @param flag flag.
     */
    void setInvisibleBlocksEnabled(boolean flag);

    /**
     * Returns if invisibleBlocks are visible.
     *
     * @return visible
     */
    boolean isInvisibleBlocksEnabled();

    /**
     * Creates a new instance of {@link StructureSaverAbstract} which
     * contains the current block properties.
     *
     * @return New instance.
     */
    @NotNull
    StructureSaverAbstract<L, V> saveStructure();
}
