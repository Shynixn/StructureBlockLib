package com.shynixn.structureblocklib.lib;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by Shynixn
 */
public final class LocationHelper
{//
    public static Location getDownCornerLocation(Location corner1, Location corner2)
    {
        int x,y,z;
        if (corner1.getBlockX() < corner2.getBlockX())
            x = corner1.getBlockX();
        else
            x = corner2.getBlockX();
        if (corner1.getBlockY() < corner2.getBlockY())
            y = corner1.getBlockY();
        else
            y = corner2.getBlockY();
        if (corner1.getBlockZ() < corner2.getBlockZ())
            z = corner1.getBlockZ();
        else
            z = corner2.getBlockZ();
        return new Location(corner1.getWorld(), x, y, z);
    }

    public static Vector toDimensions(Location downCorner, Location upCorner)
    {
        return new Vector(upCorner.getBlockX() - downCorner.getBlockX(),upCorner.getBlockY() - downCorner.getBlockY(),upCorner.getBlockZ() - downCorner.getBlockZ());
    }

    public static Location getUpCornerLocation(Location corner1, Location corner2)
    {
        int x,y,z;
        if (corner1.getBlockX() > corner2.getBlockX())
            x = corner1.getBlockX();
        else
            x = corner2.getBlockX();
        if (corner1.getBlockY() > corner2.getBlockY())
            y = corner1.getBlockY();
        else
            y = corner2.getBlockY();
        if (corner1.getBlockZ() > corner2.getBlockZ())
            z = corner1.getBlockZ();
        else
            z = corner2.getBlockZ();
        return new Location(corner1.getWorld(), x, y, z);
    }
}
