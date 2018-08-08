package com.github.shynixn.structureblocklib.bukkit.api;

import com.github.shynixn.structureblocklib.bukkit.api.business.service.LocationCalculationService;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.StructureBlockService;

import java.lang.reflect.InvocationTargetException;

public final class StructureBlockApi {
    public static final StructureBlockApi INSTANCE = new StructureBlockApi();
    private final StructureBlockService structureBlockService;
    private final PersistenceStructureService persistenceStructureService;
    private final LocationCalculationService locationCalculationService;

    /**
     * Initialize.
     */
    private StructureBlockApi() {
        super();
        try {
            this.structureBlockService = (StructureBlockService) Class.forName("com.github.shynixn.structureblocklib.bukkit.core.business.service.StructureBlockServiceImpl").newInstance();
            this.locationCalculationService = (LocationCalculationService) Class.forName("com.github.shynixn.structureblocklib.bukkit.core.business.service.LocationCalculationServiceImpl").newInstance();
            this.persistenceStructureService = (PersistenceStructureService) Class.forName("com.github.shynixn.structureblocklib.bukkit.core.business.service.PersistenceStructureServiceImpl")
                    .getDeclaredConstructor(LocationCalculationService.class).newInstance(this.locationCalculationService);
        } catch (final ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the proxy structureBlock service for handling structureBlocks in your world.
     *
     * @return structureBlockService.
     */
    public StructureBlockService getStructureBlockService() {
        return this.structureBlockService;
    }

    /**
     * Returns the persistence services for structures in minecraft.
     *
     * @return structureService.
     */
    public PersistenceStructureService getStructurePersistenceService() {
        return this.persistenceStructureService;
    }
}
