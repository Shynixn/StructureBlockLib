package com.shynixn.structureblocklib.api.entity;

import org.bukkit.Location;

/**
 * Created by Shynixn
 */
public interface StructureBlockConstruction extends StructureBlockCorner
{
    void setAuthor(String author);

    String getAuthor();

    void setStructureLocation(Location location);

    Location getStructureLocation();

    void setIgnoreEntities(boolean ignoreEntities);

    boolean isIgnoreEntities();

    void setSizeX(int sizeX);

    void setSizeY(int sizeY);

    void setSizeZ(int sizeZ);

    int getSizeX();

    int getSizeY();

    int getSizeZ();
}
