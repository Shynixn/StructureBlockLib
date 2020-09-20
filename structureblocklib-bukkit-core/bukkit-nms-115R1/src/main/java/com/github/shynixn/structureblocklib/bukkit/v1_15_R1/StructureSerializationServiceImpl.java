package com.github.shynixn.structureblocklib.bukkit.v1_15_R1;

import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import net.minecraft.server.v1_15_R1.DefinedStructure;
import net.minecraft.server.v1_15_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_15_R1.NBTTagCompound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Serialization service.
 */
public class StructureSerializationServiceImpl implements StructureSerializationService {
    /**
     * Deserializes the {@link InputStream} to an NMS handle of DefinedStructure.
     * This call is blocking.
     *
     * @param inputStream Opened inputStream. Does not close the stream after processing.
     * @return A new NMS instance of DefinedStructure.
     */
    @Override
    public Object deSerialize(InputStream inputStream) throws IOException {
        NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(inputStream);
        DefinedStructure var4 = new DefinedStructure();
        var4.b(nbttagcompound);
        return var4;
    }

    /**
     * Serializes the NMS handle of DefinedStructure to an {@link OutputStream}.
     * This call is blocking.
     *
     * @param definedStructure NMS handle.
     * @param outputStream     Opened outputStream. Does not close the stream after processing.
     */
    @Override
    public void serialize(Object definedStructure, OutputStream outputStream) throws IOException {
        if (!(definedStructure instanceof DefinedStructure)) {
            throw new IllegalArgumentException("DefinedStructure has to be an NMS handle!");
        }

        NBTTagCompound nbttagcompound = (((DefinedStructure) definedStructure)).a(new NBTTagCompound());
        NBTCompressedStreamTools.a(nbttagcompound, outputStream);
    }
}
