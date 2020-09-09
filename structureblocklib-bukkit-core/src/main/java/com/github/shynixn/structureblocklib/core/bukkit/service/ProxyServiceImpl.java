package com.github.shynixn.structureblocklib.core.bukkit.service;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.enumeration.Version;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.core.entity.PositionImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class ProxyServiceImpl implements ProxyService {
    private Plugin plugin;

    /**
     * Creates a new instance of proxy service.
     *
     * @param plugin dependency.
     */
    public ProxyServiceImpl(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Converts the given position to a location.
     *
     * @param position position.
     * @return location.
     */
    @Override
    public <L> @Nullable L toLocation(@Nullable Position position) {
        if (position == null) {
            return null;
        }

        return (L) new Location(Bukkit.getWorld(position.getWorldName()), position.getX(), position.getY(), position.getZ());
    }

    /**
     * Converts the given position to a vector.
     *
     * @param position position.
     * @return vector.
     */
    @Override
    public <V> @Nullable V toVector(@Nullable Position position) {
        if (position == null) {
            return null;
        }

        return (V) new Vector(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Converts the given location to a position.
     *
     * @param location Location.
     * @return position.
     */
    @Override
    public @Nullable <L> Position toPosition(@Nullable L location) {
        if (location == null) {
            return null;
        }

        Position position = new PositionImpl();

        if (location instanceof Location) {
            Location l = (Location) location;
            position.setWorldName(l.getWorld().getName());
            position.setX(l.getX());
            position.setY(l.getY());
            position.setZ(l.getZ());
        }

        if (location instanceof Vector) {
            Vector l = (Vector) location;
            position.setX(l.getX());
            position.setY(l.getY());
            position.setZ(l.getZ());
        }

        return position;
    }

    /**
     * Runs an async task.
     *
     * @param runnable Runnable.
     */
    @Override
    public void runAsyncTask(@NotNull Runnable runnable) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Runs a sync task.
     *
     * @param runnable Runnable.
     */
    @Override
    public void runSyncTask(@NotNull Runnable runnable) {
        plugin.getServer().getScheduler().runTask(plugin, runnable);
    }

    /**
     * Gets the running minecraft version.
     *
     * @return version.
     */
    @Override
    public Version getServerVersion() {
        return Version.VERSION_1_13_R1;
    }
}
