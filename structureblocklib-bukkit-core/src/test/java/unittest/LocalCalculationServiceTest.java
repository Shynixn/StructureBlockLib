package unittest;

import com.github.shynixn.structureblocklib.bukkit.api.business.service.LocationCalculationService;
import com.github.shynixn.structureblocklib.bukkit.core.business.service.LocationCalculationServiceImpl;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
class LocalCalculationServiceTest {
    /**
     * Given
     * a downCorner and upCorner validated.
     * When
     * toDimensions is called
     * Then
     * the correct dimension should be returned.
     */
    @Test
    void toDimension_ValidDownUpperCorner_ShouldReturnDimension() {
        // Arrange
        final World world = Mockito.mock(World.class);
        final Location downCorner = new Location(world, 50, 50, 50);
        final Location upCorner = new Location(world, 120, 50, 130);
        final LocationCalculationService classUnderTest = createWithDependencies();

        // Act
        final Vector actualVector = classUnderTest.toDimensions(downCorner, upCorner);

        // Assert
        Assertions.assertEquals(70, actualVector.getBlockX());
        Assertions.assertEquals(0, actualVector.getBlockY());
        Assertions.assertEquals(80, actualVector.getBlockZ());
    }

    /**
     * Given
     * two corners in the world
     * When
     * getDownCorner location is called
     * Then
     * the correct downCorner should be returned.
     */
    @Test
    void getDownCornerLocation_ValidMixedLocations_ShouldReturnDownCorner() {
        // Arrange
        final World world = Mockito.mock(World.class);
        final Location corner1 = new Location(world, 130, 50, 20);
        final Location corner2 = new Location(world, 120, 20, 130);
        final LocationCalculationService classUnderTest = createWithDependencies();

        // Act
        final Location downCorner = classUnderTest.getDownCornerLocation(corner1, corner2);

        // Assert
        Assertions.assertEquals(120, downCorner.getBlockX());
        Assertions.assertEquals(20, downCorner.getBlockY());
        Assertions.assertEquals(20, downCorner.getBlockZ());
    }

    /**
     * Given
     * two corners in the world
     * When
     * getDownCorner location with different parameter order
     * Then
     * the same downCorner should be returned.
     */
    @Test
    void getDownCornerLocation_MixedParameters_ShouldReturnDownCorner() {
        // Arrange
        final World world = Mockito.mock(World.class);
        final Location corner1 = new Location(world, 130, 50, 20);
        final Location corner2 = new Location(world, 120, 20, 130);
        final LocationCalculationService classUnderTest = createWithDependencies();

        // Act
        final Location downCorner1 = classUnderTest.getDownCornerLocation(corner1, corner2);
        final Location downCorner2 = classUnderTest.getDownCornerLocation(corner2, corner1);

        // Assert
        Assertions.assertEquals(downCorner2.getBlockX(), downCorner1.getBlockX());
        Assertions.assertEquals(downCorner2.getBlockY(), downCorner1.getBlockY());
        Assertions.assertEquals(downCorner2.getBlockZ(), downCorner1.getBlockZ());
    }

    /**
     * Given
     * two corners in the world
     * When
     * getUpCorner location is called
     * Then
     * the correct upCorner should be returned.
     */
    @Test
    void getUpCornerLocation_ValidMixedLocations_ShouldReturnUpCorner() {
        // Arrange
        final World world = Mockito.mock(World.class);
        final Location corner1 = new Location(world, 130, 50, 20);
        final Location corner2 = new Location(world, 120, 20, 130);
        final LocationCalculationService classUnderTest = createWithDependencies();

        // Act
        final Location upCorner = classUnderTest.getUpCornerLocation(corner1, corner2);

        // Assert
        Assertions.assertEquals(130, upCorner.getBlockX());
        Assertions.assertEquals(50, upCorner.getBlockY());
        Assertions.assertEquals(130, upCorner.getBlockZ());
    }

    /**
     * Given
     * two corners in the world
     * When
     * getUpCorner location with different parameter order
     * Then
     * the same upCorner should be returned.
     */
    @Test
    void getUpCornerLocation_MixedParameters_ShouldReturnUpCorner() {
        // Arrange
        final World world = Mockito.mock(World.class);
        final Location corner1 = new Location(world, 130, 50, 20);
        final Location corner2 = new Location(world, 120, 20, 130);
        final LocationCalculationService classUnderTest = createWithDependencies();

        // Act
        final Location upCorner1 = classUnderTest.getUpCornerLocation(corner1, corner2);
        final Location upCorner2 = classUnderTest.getUpCornerLocation(corner2, corner1);

        // Assert
        Assertions.assertEquals(upCorner2.getBlockX(), upCorner1.getBlockX());
        Assertions.assertEquals(upCorner2.getBlockY(), upCorner1.getBlockY());
        Assertions.assertEquals(upCorner2.getBlockZ(), upCorner1.getBlockZ());
    }

    private static LocationCalculationService createWithDependencies() {
        return new LocationCalculationServiceImpl();
    }
}
