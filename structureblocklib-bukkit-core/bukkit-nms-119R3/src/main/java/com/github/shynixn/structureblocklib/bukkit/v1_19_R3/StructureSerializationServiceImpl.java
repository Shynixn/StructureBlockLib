package com.github.shynixn.structureblocklib.bukkit.v1_19_R3;

import com.github.shynixn.structureblocklib.api.service.StructureSerializationService;
import net.minecraft.core.HolderGetter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Serialization service.
 */
public class StructureSerializationServiceImpl implements StructureSerializationService {
    private final HolderGetter<Block> holderGetter;

    public StructureSerializationServiceImpl() {
        MinecraftServer minecraftServer = ((CraftServer) Bukkit.getServer()).getServer();
        StructureTemplateManager structureManager = minecraftServer.getStructureManager();
        try {
            Field field = Arrays.stream(StructureTemplateManager.class.getDeclaredFields()).filter(e -> e.getType() == HolderGetter.class).findFirst().get();
            field.setAccessible(true);
            holderGetter = (HolderGetter<Block>) field.get(structureManager);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
        template.load(holderGetter, compound);
        return template;
    }

    /**
     * Serializes the NMS handle of StructureTemplate to an {@link OutputStream}.
     * This call is blocking.
     *
     * @param structureTemplate NMS handle.
     * @param outputStream      Opened outputStream. Does not close the stream after processing.
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
