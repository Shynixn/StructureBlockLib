package com.github.shynixn.structureblocklib.api.entity;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a part of the structure.
 */
public interface StructurePlacePart<Block, World> {
    /**
     * Gets currently processed block from the structure source.
     * The location of the block is relative to the position in the structure.
     *
     * @return {@link Block}.
     */
    @NotNull
    Block getSourceBlock();

    /**
     * Gets target block in the world where the source block is placed.
     *
     * @return {@link Block}.
     */
    @NotNull
    Block getTargetBlock();

    /**
     * Gets the world where the structure is placed.
     *
     * @return {@link World}.
     */
    @NotNull
    World getWorld();
}
