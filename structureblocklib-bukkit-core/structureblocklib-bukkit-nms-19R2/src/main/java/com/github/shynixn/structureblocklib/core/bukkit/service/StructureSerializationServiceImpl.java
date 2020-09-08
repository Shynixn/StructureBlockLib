package com.github.shynixn.structureblocklib.core.bukkit.service;

import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import net.minecraft.server.v1_9_R2.DefinedStructure;
import net.minecraft.server.v1_9_R2.DefinedStructureManager;
import net.minecraft.server.v1_9_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_9_R2.NBTTagCompound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Serialization service.
 */
public class StructureSerializationServiceImpl implements StructureSerializationService {
    private final DefinedStructureManager structureManager;

    /**
     * Creates a new instance of the StructureSerializationServiceImpl.
     *
     * @param world world.
     */
    public StructureSerializationServiceImpl(World world) {
        this(((CraftWorld) world).getHandle().y());
    }

    /**
     * Creates a new instance of the StructureSerializationServiceImpl.
     *
     * @param structureManager manager.
     */
    public StructureSerializationServiceImpl(DefinedStructureManager structureManager) {
        this.structureManager = structureManager;
    }

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
