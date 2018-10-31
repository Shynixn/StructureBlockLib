package com.github.shynixn.structureblocklib.bukkit.core.business.service;

import com.github.shynixn.structureblocklib.bukkit.api.business.service.LocationCalculationService;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Created by Shynixn 2018.
 * <p>
 * Version 1.2
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2018 by Shynixn
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class LocationCalculationServiceImpl implements LocationCalculationService {
    /**
     * Converts the down and upCorner into a vector.
     *
     * @param downCorner downCorner
     * @param upCorner   upCorner
     * @return vector
     */
    @Override
    public Vector toDimensions(Location downCorner, Location upCorner) {
        return new Vector(upCorner.getBlockX() - downCorner.getBlockX(), upCorner.getBlockY() - downCorner.getBlockY(), upCorner.getBlockZ() - downCorner.getBlockZ());
    }

    /**
     * Returns the location of the lowest corner.
     *
     * @param corner1 corner1
     * @param corner2 corner2
     * @return corner
     */
    @Override
    public Location getDownCornerLocation(Location corner1, Location corner2) {
        final int x;
        final int y;
        final int z;

        if (corner1.getBlockX() < corner2.getBlockX()) {
            x = corner1.getBlockX();
        } else {
            x = corner2.getBlockX();
        }

        if (corner1.getBlockY() < corner2.getBlockY()) {
            y = corner1.getBlockY();
        } else {
            y = corner2.getBlockY();
        }

        if (corner1.getBlockZ() < corner2.getBlockZ()) {
            z = corner1.getBlockZ();
        } else {
            z = corner2.getBlockZ();
        }

        return new Location(corner1.getWorld(), x, y, z);
    }

    /**
     * Returns the location of the upper corner.
     *
     * @param corner1 corner1
     * @param corner2 corner2
     * @return corner
     */
    @Override
    public Location getUpCornerLocation(Location corner1, Location corner2) {
        final int x;
        final int y;
        final int z;

        if (corner1.getBlockX() > corner2.getBlockX()) {
            x = corner1.getBlockX();
        } else {
            x = corner2.getBlockX();
        }

        if (corner1.getBlockY() > corner2.getBlockY()) {
            y = corner1.getBlockY();
        } else {
            y = corner2.getBlockY();
        }

        if (corner1.getBlockZ() > corner2.getBlockZ()) {
            z = corner1.getBlockZ();
        } else {
            z = corner2.getBlockZ();
        }

        return new Location(corner1.getWorld(), x, y, z);
    }
}
