package unittest;

import com.github.shynixn.structureblocklib.api.entity.Position;
import com.github.shynixn.structureblocklib.api.enumeration.Version;
import com.github.shynixn.structureblocklib.api.service.ProxyService;
import com.github.shynixn.structureblocklib.bukkit.service.ProxyServiceImpl;
import com.github.shynixn.structureblocklib.core.entity.PositionImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.logging.Logger;

public class TProxyServiceImpl {
    /**
     * Given Position
     * when toLocation is called
     * then value should be correctly converted.
     */
    @Test
    public void toLocation_Position_ShouldCorrectlyConvert() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();
        Position position = new PositionImpl();
        position.setX(50);
        position.setY(20);
        position.setZ(-3);
        position.setWorldName("world");

        // Actual
        Location actual = classUnderTest.toLocation(position);

        // Assert
        Assertions.assertEquals(position.getX(), actual.getX());
        Assertions.assertEquals(position.getY(), actual.getY());
        Assertions.assertEquals(position.getZ(), actual.getZ());
        Assertions.assertNotNull(actual.getWorld());
    }

    /**
     * Given null
     * when toLocation is called
     * then null should be returned.
     */
    @Test
    public void toLocation_Null_ShouldReturnNull() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();

        // Actual
        Location actual = classUnderTest.toLocation(null);

        // Assert
        Assertions.assertNull(actual);
    }

    /**
     * Given Position
     * when toVector is called
     * then value should be correctly converted.
     */
    @Test
    public void toVector_Position_ShouldCorrectlyConvert() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();
        Position position = new PositionImpl();
        position.setX(50);
        position.setY(20);
        position.setZ(-3);

        // Actual
        Vector actual = classUnderTest.toVector(position);

        // Assert
        Assertions.assertEquals(position.getX(), actual.getX());
        Assertions.assertEquals(position.getY(), actual.getY());
        Assertions.assertEquals(position.getZ(), actual.getZ());
    }


    /**
     * Given null
     * when toVector is called
     * then returned value should be null.
     */
    @Test
    public void toVector_Null_ShouldReturnNull() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();

        // Actual
        Vector actual = classUnderTest.toVector(null);

        // Assert
        Assertions.assertNull(actual);
    }

    /**
     * Given Location
     * when toPosition is called
     * then value should be correctly converted.
     */
    @Test
    public void toPosition_Location_ShouldCorrectlyConvert() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();
        Location location = new Location(Bukkit.getWorld("world"), 20, -30, 12);

        // Actual
        Position actual = classUnderTest.toPosition(location);

        // Assert
        Assertions.assertEquals(location.getX(), actual.getX());
        Assertions.assertEquals(location.getY(), actual.getY());
        Assertions.assertEquals(location.getZ(), actual.getZ());
    }

    /**
     * Given Vector
     * when toPosition is called
     * then value should be correctly converted.
     */
    @Test
    public void toPosition_Vector_ShouldCorrectlyConvert() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();
        Vector vector = new Vector(20, -30, 12);

        // Actual
        Position actual = classUnderTest.toPosition(vector);

        // Assert
        Assertions.assertEquals(vector.getX(), actual.getX());
        Assertions.assertEquals(vector.getY(), actual.getY());
        Assertions.assertEquals(vector.getZ(), actual.getZ());
    }

    /**
     * Given null
     * when toPosition is called
     * then null should be returned.
     */
    @Test
    public void toPosition_Null_ShouldReturnNull() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();

        // Actual
        Position actual = classUnderTest.toPosition(null);

        // Assert
        Assertions.assertNull(actual);
    }

    /**
     * Given test environment
     * when getVersion is called
     * then Unknown should be returned.
     */
    @Test
    public void getServerVersion_TestEnvironment_ShouldUnknownVersion() {
        // Arrange
        ProxyService classUnderTest = createWithDependencies();

        // Actual
        Version version = classUnderTest.getServerVersion();

        // Assert
        Assertions.assertEquals(Version.VERSION_UNKNOWN, version);
    }

    private ProxyService createWithDependencies() {
        Plugin plugin = Mockito.mock(Plugin.class);
        Server server = Mockito.mock(Server.class);
        Mockito.when(server.getLogger()).thenReturn(Logger.getGlobal());
        Mockito.when(server.getWorld(Mockito.anyString())).thenReturn(Mockito.mock(World.class));

        if (Bukkit.getServer() == null) {
            Bukkit.setServer(server);
        }

        return new ProxyServiceImpl(plugin);
    }
}
