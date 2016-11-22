package com.shynixn.structureblocklib.api;

import com.shynixn.structureblocklib.api.entity.StructureBlock;
import com.shynixn.structureblocklib.api.entity.StructureMirror;
import com.shynixn.structureblocklib.api.entity.StructureRotation;
import com.shynixn.structureblocklib.business.bukkit.nms.NMSRegistry;
import com.shynixn.structureblocklib.lib.LocationHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Created by Shynixn
 */
public final class StructureBlockApi {

    public static StructureBlock from(Block block) {
        if(block == null)
            throw new IllegalArgumentException("Block cannot be null!");
        if(block.getType() != Material.STRUCTURE_BLOCK)
            throw new IllegalArgumentException("Block has to be a structureblock!");
        return NMSRegistry.createStructureBlock(block);
    }

    public static void save(Player player, String saveName, Location corner1, Location corner2) {
        save(player, saveName, corner1, corner2, true);
    }

    public static void save(Player player, String saveName, Location corner1, Location corner2, boolean ignoreEntities) {
        if(player == null)
            throw new IllegalArgumentException("Player cannot be null!");
        save(String.valueOf(player.getUniqueId()), saveName, corner1, corner2, ignoreEntities);
    }

    public static void save(String author, String saveName, Location corner1, Location corner2) {
        save(author, saveName, corner1, corner2, true);
    }

    public static void save(String author, String saveName, Location corner1, Location corner2, boolean ignoreEntities) {
        if(corner1 == null)
            throw new IllegalArgumentException("Corner1 cannot be null!");
        if(corner2 == null)
            throw new IllegalArgumentException("Corner2 cannot be null!");
        Location tmp = LocationHelper.getDownCornerLocation(corner1, corner2);
        save(author, saveName, tmp, LocationHelper.toDimensions(tmp, LocationHelper.getUpCornerLocation(corner1, corner2)), ignoreEntities);
    }

    public static void save(Player player, String saveName, Location corner, Vector dimensions) {
        save(player, saveName, corner, dimensions, true);
    }

    public static void save(Player player, String saveName, Location corner, Vector dimensions, boolean ignoreEntities) {
        if(player == null)
            throw new IllegalArgumentException("Player cannot be null!");
        save(String.valueOf(player.getUniqueId()), saveName, corner, dimensions, ignoreEntities);
    }

    public static void save(String author, String saveName, Location corner, Vector dimensions) {
        save(author, saveName, corner, dimensions, true);
    }

    public static void save(String author, String saveName, Location corner, Vector dimensions, boolean ignoreEntities) {
        if(author == null)
            throw new IllegalArgumentException("Author cannot be null!");
        if(saveName== null)
            throw new IllegalArgumentException("Savename cannot be null!");
        if(corner == null)
            throw new IllegalArgumentException("Corner cannot be null!");
        if(dimensions == null)
            throw new IllegalArgumentException("Dimensions cannot be null!");
        NMSRegistry.save(author,saveName,corner,dimensions, ignoreEntities);
    }

    public static void load(Player player, String saveName, Location location) {
        load(player,saveName,location, true);
    }

    public static void load(Player player, String saveName, Location location, boolean ignoreEntities) {
        load(player,saveName,location,ignoreEntities, StructureRotation.NONE, StructureMirror.NONE);
    }

    public static void load(Player player, String saveName, Location location, boolean ignoreEntities, StructureRotation rotation, StructureMirror mirror) {
        if(player == null)
            throw new IllegalArgumentException("Player cannot be null!");
        load(String.valueOf(player.getUniqueId()), saveName, location, ignoreEntities, rotation, mirror);
    }

    public static void load(String author, String saveName, Location location) {
        load(author, saveName,location, true);
    }

    public static void load(String author, String saveName, Location location, boolean ignoreEntities) {
        load(author,saveName,location,ignoreEntities, StructureRotation.NONE, StructureMirror.NONE);
    }

    public static void load(String author, String saveName, Location location, boolean ignoreEntities, StructureRotation rotation, StructureMirror mirror) {
        if(author == null)
            throw new IllegalArgumentException("Author cannot be null!");
        if(saveName == null)
            throw new IllegalArgumentException("Savename cannot be null!");
        if(location == null)
            throw new IllegalArgumentException("Location cannot be null!");
        if(rotation == null)
            throw new IllegalArgumentException("Rotation cannot be null!");
        if(mirror == null)
            throw new IllegalArgumentException("Mirror cannot be null!");
        NMSRegistry.load(author,saveName,location, ignoreEntities, rotation, mirror);
    }
}
