package com.github.shynixn.structureblocklib.api.enumeration;

/**
 * Size restriction of the target structure.
 */
public enum StructureRestriction {
    /**
     * (Default in all operations)
     * Traditional size from 1.9.0 - latest.
     * Restricts the structure to single structure of a max size of 32x32x32.
     */
    SINGLE_32(32),

    /**
     * Larger size from 1.16 - latest.
     * Restricts the structure to single structure of a max size of 48x48x48.
     */
    SINGLE_48(48),

    /**
     * No size restrictions. This may cause
     * incompatibility to other programs.
     * Restricts the structure to single structure of unlimited size..
     */
    UNLIMITED(Integer.MAX_VALUE);

    private final int maxSize;

    StructureRestriction(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Gets the max size value.
     *
     * @return maxSize.
     */
    public int getMaxSize() {
        return maxSize;
    }
}
