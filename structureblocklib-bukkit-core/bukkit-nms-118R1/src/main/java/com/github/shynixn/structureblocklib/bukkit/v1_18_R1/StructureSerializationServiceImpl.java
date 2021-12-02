package com.github.shynixn.structureblocklib.bukkit.v1_18_R1;

import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Serialization service.
 */
public class StructureSerializationServiceImpl implements StructureSerializationService {
    /**
     * Deserializes the {@link InputStream} to an NMS handle of StructureTemplate.
     * This call is blocking.
     *
     * @param inputStream Opened inputStream. Does not close the stream after processing.
     * @return A new NMS instance of StructureTemplate.
     */
    @Override
    public Object deSerialize(InputStream inputStream) throws IOException {
        CompoundTag compound = NbtIo.readCompressed(inputStream);
        StructureTemplate template = new StructureTemplate();
        template.load(compound);
        return template;
    }

    /**
     * Serializes the NMS handle of StructureTemplate to an {@link OutputStream}.
     * This call is blocking.
     *
     * @param structureTemplate NMS handle.
     * @param outputStream     Opened outputStream. Does not close the stream after processing.
     */
    @Override
    public void serialize(Object structureTemplate, OutputStream outputStream) throws IOException {
        if (!(structureTemplate instanceof StructureTemplate)) {
            throw new IllegalArgumentException("StructureTemplate has to be an NMS handle!");
        }

        CompoundTag compound = (((StructureTemplate) structureTemplate)).save(new CompoundTag());
        NbtIo.writeCompressed(compound, outputStream);
    }
}
