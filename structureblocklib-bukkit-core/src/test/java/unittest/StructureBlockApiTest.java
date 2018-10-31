package unittest;

import com.github.shynixn.structureblocklib.bukkit.api.StructureBlockApi;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.StructureBlockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
public class StructureBlockApiTest {

    /**
     * Given
     * a stateless api
     * When
     * getStructureBlockService is called
     * Then
     * a service should be returned.
     */
    @Test
    void getStructureBlockService_StatelessApi_ShouldReturnService() {
        // Arrange
        final StructureBlockApi classUnderTest = createWithDependencies();

        // Act
        final StructureBlockService structureBlockService = classUnderTest.getStructureBlockService();

        // Assert
        Assertions.assertNotNull(structureBlockService);
    }

    /**
     * Given
     * a stateless api
     * When
     * getStructurePersistenceService is called
     * Then
     * a service should be returned.
     */
    @Test
    void getStructurePersistenceService_StatelessApi_ShouldReturnService() {
        // Arrange
        final StructureBlockApi classUnderTest = createWithDependencies();

        // Act
        final PersistenceStructureService persistenceStructureService = classUnderTest.getStructurePersistenceService();

        // Assert
        Assertions.assertNotNull(persistenceStructureService);
    }

    private static StructureBlockApi createWithDependencies() {
        return StructureBlockApi.INSTANCE;
    }
}
