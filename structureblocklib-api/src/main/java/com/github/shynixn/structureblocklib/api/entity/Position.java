package com.github.shynixn.structureblocklib.api.entity;

import org.jetbrains.annotations.Nullable;

/**
 * Vector Util.
 */
public interface Position {
    /**
     * Gets the x coordinate.
     *
     * @return x.
     */
    double getX();

    /**
     * Gets the y coordinate.
     *
     * @return y.
     */
    double getY();

    /**
     * Gets the z coordinate.
     *
     * @return z.
     */
    double getZ();

    /**
     * Gets the world name.
     *
     * @return name.
     */
    @Nullable
    String getWorldName();

    /**
     * Sets the world name.
     *
     * @param world world.
     */
    void setWorldName(@Nullable String world);

    /**
     * Sets the x coordinate.
     *
     * @param x coordinate.
     */
    void setX(double x);

    /**
     * Sets the y coordinate.
     *
     * @param y coordinate.
     */
    void setY(double y);

    /**
     * Sets the z coordinate.
     *
     * @param z coordinate.
     */
    void setZ(double z);
}
