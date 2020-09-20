package com.github.shynixn.structureblocklib.api.block;

import org.jetbrains.annotations.Nullable;

public interface StructureBlockConstructionAbstract<L> extends StructureBlockCornerAbstract {
    /**
     * Sets the author of the structure.
     *
     * @param author author.
     */
    void setAuthor(@Nullable String author);

    /**
     * Returns the author of the structure.
     *
     * @return author.
     */
    @Nullable
    String getAuthor();

    /**
     * Changes the location of the structure.
     *
     * @param location location.
     */
    void setStructureLocation(@Nullable L location);

    /**
     * Returns the location of the structure.
     *
     * @return location.
     */
    @Nullable L getStructureLocation();

    /**
     * Should entities which may or may not be included in the
     * saved file be included in the loaded/saved structure.
     * Default false.
     *
     * @param flag flag.
     */
    void setIncludeEntities(boolean flag);

    /**
     * Should entities which may or may not be included in the
     * saved file be included in the loaded/saved structure.
     * Default false.
     *
     * @return false.
     */
    boolean isIncludeEntitiesEnabled();

    /**
     * Changes the size of the structure in X direction.
     *
     * @param sizeX sizeX.
     */
    void setSizeX(int sizeX);

    /**
     * Changes the size of the structure in Y direction.
     *
     * @param sizeY sizeY.
     */
    void setSizeY(int sizeY);

    /**
     * Changes the size of the structure in Z direction.
     *
     * @param sizeZ sizeZ.
     */
    void setSizeZ(int sizeZ);

    /**
     * Returns the size of the structure in X direction.
     *
     * @return xSize.
     */
    int getSizeX();

    /**
     * Returns the size of the structure in Y direction.
     *
     * @return ySize.
     */
    int getSizeY();

    /**
     * Returns the size of the structure in Z direction.
     *
     * @return zSize.
     */
    int getSizeZ();
}
