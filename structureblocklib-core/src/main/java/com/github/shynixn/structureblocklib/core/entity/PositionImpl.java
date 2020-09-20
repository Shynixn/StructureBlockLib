package com.github.shynixn.structureblocklib.core.entity;

import com.github.shynixn.structureblocklib.api.entity.Position;
import org.jetbrains.annotations.Nullable;

/**
 * Vector Util.
 */
public class PositionImpl implements Position {
    private String world;
    private double x;
    private double y;
    private double z;

    public PositionImpl() {
    }

    public PositionImpl(Position position) {
        this.world = position.getWorldName();
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
    }

    /**
     * Gets the x coordinate.
     *
     * @return x.
     */
    @Override
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return y.
     */
    @Override
    public double getY() {
        return this.y;
    }

    /**
     * Gets the z coordinate.
     *
     * @return z.
     */
    @Override
    public double getZ() {
        return this.z;
    }

    /**
     * Gets the world name.
     *
     * @return name.
     */
    @Override
    public @Nullable String getWorldName() {
        return this.world;
    }

    /**
     * Sets the world name.
     *
     * @param world world.
     */
    @Override
    public void setWorldName(@Nullable String world) {
        this.world = world;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x coordinate.
     */
    @Override
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y coordinate.
     */
    @Override
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Sets the z coordinate.
     *
     * @param z coordinate.
     */
    @Override
    public void setZ(double z) {
        this.z = z;
    }
}
