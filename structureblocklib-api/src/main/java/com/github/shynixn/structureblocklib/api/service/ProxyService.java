package com.github.shynixn.structureblocklib.api.service;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.enumeration.Version;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.Executor;

/**
 * Proxies small framework specific actions.
 */
public interface ProxyService {
    /**
     * Converts the given position to a location.
     *
     * @param position position.
     * @param <L>      Location Type.
     * @return location.
     */
    @Nullable <L> L toLocation(@Nullable Position position);

    /**
     * Converts the given position to a vector.
     *
     * @param position position.
     * @param <V>      Vector Type.
     * @return vector.
     */
    @Nullable <V> V toVector(@Nullable Position position);

    /**
     * Converts the given location to a position.
     *
     * @param location Location.
     * @param <L>      Location Type.
     * @return position.
     */
    @Nullable <L> Position toPosition(@Nullable L location);

    /**
     * Deprecated. Use getAsyncExecutor().execute instead.
     */
    @Deprecated
    void runAsyncTask(@NotNull Runnable runnable);

    /**
     * Deprecated. Use getSyncExecutor().execute instead.
     */
    @Deprecated
    void runSyncTask(@NotNull Runnable runnable);

    /**
     * Gets an execute to schedule tasks on the synchronous bukkit thread.
     *
     * @return {@link Executor}.
     */
    Executor getSyncExecutor();

    /**
     * Gets an execute to schedule tasks on the asynchronous bukkit threadPool.
     *
     * @return {@link Executor}.
     */
    Executor getAsyncExecutor();

    /**
     * Gets the running minecraft version.
     *
     * @return version.
     */
    Version getServerVersion();
}
