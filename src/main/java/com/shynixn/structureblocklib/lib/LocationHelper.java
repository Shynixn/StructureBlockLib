package com.shynixn.structureblocklib.lib;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class LocationHelper {

    /**
     * Init
     */
    private LocationHelper() {
        super();
    }

    /**
     * Converts the down and upCorner into a vector
     *
     * @param downCorner downCorner
     * @param upCorner   upCorner
     * @return vector
     */
    public static Vector toDimensions(Location downCorner, Location upCorner) {
        return new Vector(upCorner.getBlockX() - downCorner.getBlockX(), upCorner.getBlockY() - downCorner.getBlockY(), upCorner.getBlockZ() - downCorner.getBlockZ());
    }

    /**
     * Returns the location of the lowest corner
     *
     * @param corner1 corner1
     * @param corner2 corner2
     * @return corner
     */
    public static Location getDownCornerLocation(Location corner1, Location corner2) {
        final int x;
        final int y;
        final int z;
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

    /**
     * Returns the location of the upper corner
     *
     * @param corner1 corner1
     * @param corner2 corner2
     * @return corner
     */
    public static Location getUpCornerLocation(Location corner1, Location corner2) {
        final int x;
        final int y;
        final int z;
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
