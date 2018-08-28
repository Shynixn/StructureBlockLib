package com.github.shynixn.structureblocklib.bukkit.api.business.proxy;

import org.bukkit.Location;

public interface StructureBlockConstruction extends StructureBlockCorner {
    /**
     * Sets the author of the structure.
     *
     * @param author author
     */
    void setAuthor(String author);

    /**
     * Returns the author of the structure.
     *
     * @return author
     */
    String getAuthor();

    /**
     * Changes the location of the structure.
     *
     * @param location location
     */
    void setStructureLocation(Location location);

    /**
     * Returns the location of the structure.
     *
     * @return location
     */
    Location getStructureLocation();

    /**
     * Enables or disables ignoring entities on save or on load.
     *
     * @param ignoreEntities ignoreEntities
     */
    void setIgnoreEntities(boolean ignoreEntities);

    /**
     * Checks if the structure ignoring entities on save or on load.
     *
     * @return isIgnoringEntities
     */
    boolean isIgnoreEntities();

    /**
     * Changes the size of the structure in X direction.
     *
     * @param sizeX sizeX
     */
    void setSizeX(int sizeX);

    /**
     * Changes the size of the structure in Y direction.
     *
     * @param sizeY sizeY
     */
    void setSizeY(int sizeY);

    /**
     * Changes the size of the structure in Z direction.
     *
     * @param sizeZ sizeZ
     */
    void setSizeZ(int sizeZ);

    /**
     * Returns the size of the structure in X direction.
     *
     * @return xSize
     */
    int getSizeX();

    /**
     * Returns the size of the structure in Y direction.
     *
     * @return ySize
     */
    int getSizeY();

    /**
     * Returns the size of the structure in Z direction.
     *
     * @return zSize
     */
    int getSizeZ();
}
