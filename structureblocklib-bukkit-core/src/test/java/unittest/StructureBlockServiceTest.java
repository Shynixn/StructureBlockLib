package unittest;

import com.github.shynixn.structureblocklib.bukkit.api.business.service.StructureBlockService;
import com.github.shynixn.structureblocklib.bukkit.core.VersionSupport;
import com.github.shynixn.structureblocklib.bukkit.core.business.service.StructureBlockServiceImpl;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.logging.Logger;

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
public class StructureBlockServiceTest {
    /**
     * Given
     * a null location
     * When
     * getOrCreateStructureBlockAt is called
     * Then
     * a exception should be thrown.
     */
    @Test
    void getOrCreateStructureBlockAt_NullLocation_ShouldThrowException() {
        // Arrange
        final StructureBlockService classUnderTest = createWithDependencies();

        // Act
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            classUnderTest.getOrCreateStructureBlockAt(null);
        });
    }

    /**
     * Given
     * a valid location and valid server version
     * When
     * getOrCreateStructureBlockAt is called
     * Then
     * the constructor should be called but an exception should be expected.
     */
    @Test
    void getOrCreateStructureBlockAt_ValidLocation_ShouldReturnStructureBlock() {
        // Arrange
        final StructureBlockService classUnderTest = createWithDependencies();
        final Server server = Mockito.mock(Server.class);
        final World world = Mockito.mock(World.class);

        Mockito.when(server.getLogger()).thenReturn(Logger.getGlobal());

        try {
            final Field field = StructureBlockServiceImpl.class.getDeclaredField("versionSupport");
            field.setAccessible(true);
            field.set(classUnderTest, VersionSupport.VERSION_1_12_R1);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (Bukkit.getServer() == null) {
            Bukkit.setServer(server);
        }

        final Location location = Mockito.mock(Location.class);
        final Block block = Mockito.mock(Block.class);

        Mockito.when(block.getWorld()).thenReturn(world);
        Mockito.when(location.getBlock()).thenReturn(block);
        Mockito.when(block.getType()).thenReturn(Material.TNT);

        // Act
        Assertions.assertThrows(RuntimeException.class, () -> {
            classUnderTest.getOrCreateStructureBlockAt(location);
        });
    }

    private static StructureBlockService createWithDependencies() {
        return new StructureBlockServiceImpl();
    }
}
