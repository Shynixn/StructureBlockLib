package helper;

import com.github.shynixn.structureblocklib.api.entity.StructurePlaceMeta;
import com.github.shynixn.structureblocklib.api.entity.StructureReadMeta;
import com.github.shynixn.structureblocklib.api.service.StructureWorldService;
import net.minecraft.server.v1_9_R2.DefinedStructure;

public class MockedStructureWorldService implements StructureWorldService {
    /**
     * Places the blocks in the world defined by the given structure.
     *
     * @param meta      Meta data to describe the placement.
     * @param structure NMS structure.
     */
    @Override
    public void placeStructureToWorld(StructurePlaceMeta meta, Object structure) throws Exception {
    }

    /**
     * Reads the blocks in the world into an NMS Structure definition.
     *
     * @param meta Meta data to describe the block selection.
     * @return A new NMS Structure definition.
     */
    @Override
    public Object readStructureFromWorld(StructureReadMeta meta) throws Exception {
        return new DefinedStructure();
    }
}
