package helper;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.enumeration.Version;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.core.entity.PositionImpl;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockito.Mockito;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MockedProxyService implements ProxyService {
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    public Queue<Runnable> concurrentList = new ConcurrentLinkedQueue<Runnable>();

    /**
     * Converts the given position to a location.
     *
     * @param position position.
     * @return location.
     */
    @Override
    public <L> @Nullable L toLocation(@Nullable Position position) {
        return (L) new Location(Mockito.mock(World.class), position.getX(), position.getY(), position.getZ());
    }

    /**
     * Converts the given position to a vector.
     *
     * @param position position.
     * @return vector.
     */
    @Override
    public <V> @Nullable V toVector(@Nullable Position position) {
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
        executor.submit(runnable);
    }

    /**
     * Runs a sync task.
     *
     * @param runnable Runnable.
     */
    @Override
    public void runSyncTask(@NotNull Runnable runnable) {
        concurrentList.add(runnable);
    }

    /**
     * Gets an execute to schedule tasks on the synchronous bukkit thread.
     *
     * @return {@link Executor}.
     */
    @Override
    public Executor getSyncExecutor() {
        return c ->  concurrentList.add(c);
    }

    /**
     * Gets an execute to schedule tasks on the asynchronous bukkit threadPool.
     *
     * @return {@link Executor}.
     */
    @Override
    public Executor getAsyncExecutor() {
        return executor;
    }

    /**
     * Gets the running minecraft version.
     *
     * @return version.
     */
    @Override
    public Version getServerVersion() {
        return Version.VERSION_UNKNOWN;
    }
}
