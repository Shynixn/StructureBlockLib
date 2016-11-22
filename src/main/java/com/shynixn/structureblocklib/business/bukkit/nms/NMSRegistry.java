package com.shynixn.structureblocklib.business.bukkit.nms;

import com.shynixn.structureblocklib.api.entity.StructureBlock;
import com.shynixn.structureblocklib.api.entity.StructureMirror;
import com.shynixn.structureblocklib.api.entity.StructureRotation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * Created by Shynixn
 */
public class NMSRegistry {
    public static <T extends StructureBlock> T createStructureBlock(Block block) {
        if (getServerVersion().equals("v1_11_R1"))
            return (T) new com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock(block);
        else if (getServerVersion().equals("v1_10_R1"))
            return (T) new com.shynixn.structureblocklib.business.bukkit.nms.v1_10_R1.CraftStructureBlock(block);
        else if(getServerVersion().equals("v1_9_R2"))
            return (T) new com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R2.CraftStructureBlock(block);
        else if(getServerVersion().equals("v1_9_R1"))
            return (T) new com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R1.CraftStructureBlock(block);
        throw new IllegalStateException("Server version is not supported for StructureLib.");
    }

    public static void save(String author, String saveName, Location corner, Vector dimensions, boolean ignoreEntities) {
        if (getServerVersion().equals("v1_11_R1"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock.save(author, saveName, corner, dimensions, ignoreEntities);
        else if (getServerVersion().equals("v1_10_R1"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_10_R1.CraftStructureBlock.save(author, saveName, corner, dimensions, ignoreEntities);
        else if(getServerVersion().equals("v1_9_R2"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R2.CraftStructureBlock.save(author, saveName, corner, dimensions, ignoreEntities);
        else if(getServerVersion().equals("v1_9_R1"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R1.CraftStructureBlock.save(author, saveName, corner, dimensions, ignoreEntities);
        else
            throw new IllegalStateException("Server version is not supported for StructureLib.");
    }

    public static void load(String author, String saveName, Location location, boolean ignoreEntities, StructureRotation rotation, StructureMirror mirror) {
        if (getServerVersion().equals("v1_11_R1"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_11_R1.CraftStructureBlock.load(author, saveName, location, ignoreEntities, rotation, mirror);
        else if (getServerVersion().equals("v1_10_R1"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_10_R1.CraftStructureBlock.load(author, saveName, location, ignoreEntities, rotation, mirror);
        else if(getServerVersion().equals("v1_9_R2"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R2.CraftStructureBlock.load(author, saveName, location, ignoreEntities, rotation, mirror);
        else if(getServerVersion().equals("v1_9_R1"))
            com.shynixn.structureblocklib.business.bukkit.nms.v1_9_R1.CraftStructureBlock.load(author, saveName, location, ignoreEntities, rotation, mirror);
        else
            throw new IllegalStateException("Server version is not supported for StructureLib.");
    }

    private static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
    }
}
