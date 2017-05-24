package com.github.shynixn.structureblocklib.api;

import com.github.shynixn.structureblocklib.api.entity.StructureBlock;
import com.github.shynixn.structureblocklib.api.entity.StructureMirror;
import com.github.shynixn.structureblocklib.api.entity.StructureRotation;
import com.github.shynixn.structureblocklib.business.bukkit.nms.NMSRegistry;
import com.github.shynixn.structureblocklib.lib.LocationHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class StructureBlockApi {

    /**
     * Init
     */
    private StructureBlockApi() {
        super();
    }

    /**
     * Creates a structureBlock from the given bukkit block
     *
     * @param block block
     * @return structureBlock
     */
    public static StructureBlock from(Block block) {
        if (block == null)
            throw new IllegalArgumentException("Block cannot be null!");
        if (block.getType() != Material.STRUCTURE_BLOCK)
            throw new IllegalArgumentException("Block has to be a structureblock!");
        return NMSRegistry.createStructureBlock(block);
    }

    /**
     * Creates a structureBlock from the given location of a bukkit block
     *
     * @param location location
     * @return structureBlock
     */
    public static StructureBlock from(Location location) {
        if (location == null)
            throw new IllegalArgumentException("Location cannot be null!");
        return from(location.getBlock());
    }

    /**
     * Stores a structure for the given player with the given saveName between the corner1 and corner2
     *
     * @param player   player
     * @param saveName saveName
     * @param corner1  corner 1
     * @param corner2  corner 2
     */
    public static void save(Player player, String saveName, Location corner1, Location corner2) {
        save(player, saveName, corner1, corner2, true);
    }

    /**
     * Stores a structure for the given player with the given saveName between the corner1 and corner2 and option to ignoreEntities
     *
     * @param player         player
     * @param saveName       saveName
     * @param corner1        corner1
     * @param corner2        corner2
     * @param ignoreEntities store or not store entities
     */
    public static void save(Player player, String saveName, Location corner1, Location corner2, boolean ignoreEntities) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null!");
        save(String.valueOf(player.getUniqueId()), saveName, corner1, corner2, ignoreEntities);
    }

    /**
     * Stores a structure for the given author with the given saveName between the corner1 and corner2
     *
     * @param author   author
     * @param saveName saveName
     * @param corner1  corner1
     * @param corner2  corner2
     */
    public static void save(String author, String saveName, Location corner1, Location corner2) {
        save(author, saveName, corner1, corner2, true);
    }

    /**
     * Stores a structure for the given author with the given saveName between the corner1 and corner2 and option to ignoreEntities
     *
     * @param author         author
     * @param saveName       saveName
     * @param corner1        corner1
     * @param corner2        corner2
     * @param ignoreEntities store or not store entities
     */
    public static void save(String author, String saveName, Location corner1, Location corner2, boolean ignoreEntities) {
        if (corner1 == null)
            throw new IllegalArgumentException("Corner1 cannot be null!");
        if (corner2 == null)
            throw new IllegalArgumentException("Corner2 cannot be null!");
        final Location tmp = LocationHelper.getDownCornerLocation(corner1, corner2);
        save(author, saveName, tmp, LocationHelper.toDimensions(tmp, LocationHelper.getUpCornerLocation(corner1, corner2)), ignoreEntities);
    }

    /**
     * Stores a structure for the given player with the given saveName by a corner and his vectors
     *
     * @param player     player
     * @param saveName   saveName
     * @param corner     corner
     * @param dimensions vector
     */
    public static void save(Player player, String saveName, Location corner, Vector dimensions) {
        save(player, saveName, corner, dimensions, true);
    }

    /**
     * Stores a structure for the given player with the given saveName by a corner and his vectors
     * @param player player
     * @param saveName saveName
     * @param corner corner
     * @param dimensions vector
     * @param ignoreEntities store or not store entities
     */
    public static void save(Player player, String saveName, Location corner, Vector dimensions, boolean ignoreEntities) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null!");
        save(String.valueOf(player.getUniqueId()), saveName, corner, dimensions, ignoreEntities);
    }

    /**
     * Stores a structure for the given author with the given saveName by a corner and his vectors
     * @param author         author
     * @param saveName saveName
     * @param corner corner
     * @param dimensions vector
     */
    public static void save(String author, String saveName, Location corner, Vector dimensions) {
        save(author, saveName, corner, dimensions, true);
    }

    /**
     * Stores a structure for the given author with the given saveName by a corner and his vectors
     * @param author         author
     * @param saveName saveName
     * @param corner corner
     * @param dimensions vector
     * @param ignoreEntities store or not store entities
     */
    public static void save(String author, String saveName, Location corner, Vector dimensions, boolean ignoreEntities) {
        if (author == null)
            throw new IllegalArgumentException("Author cannot be null!");
        if (saveName == null)
            throw new IllegalArgumentException("Savename cannot be null!");
        if (corner == null)
            throw new IllegalArgumentException("Corner cannot be null!");
        if (dimensions == null)
            throw new IllegalArgumentException("Dimensions cannot be null!");
        NMSRegistry.save(author, saveName, corner, dimensions, ignoreEntities);
    }

    /**
     * Loads a structure of the given player and saveName to the given location
     * @param player player
     * @param saveName saveName
     * @param location location
     */
    public static void load(Player player, String saveName, Location location) {
        load(player, saveName, location, true);
    }

    /**
     * Loads a structure of the given player and saveName to the given location ignoring entities
     * @param player player
     * @param saveName saveName
     * @param location location
     * @param ignoreEntities load or not load entities
     */
    public static void load(Player player, String saveName, Location location, boolean ignoreEntities) {
        load(player, saveName, location, ignoreEntities, StructureRotation.NONE, StructureMirror.NONE);
    }

    /**
     * Loads a structure of the given player and saveName to the given location ignoring entities
     * @param player player
     * @param saveName saveName
     * @param location location
     * @param ignoreEntities ignoreEntities
     * @param rotation rotation
     * @param mirror mirror
     */
    public static void load(Player player, String saveName, Location location, boolean ignoreEntities, StructureRotation rotation, StructureMirror mirror) {
        if (player == null)
            throw new IllegalArgumentException("Player cannot be null!");
        load(String.valueOf(player.getUniqueId()), saveName, location, ignoreEntities, rotation, mirror);
    }

    /**
     * Loads a structure of the given author and saveName to the given location
     * @param author author
     * @param saveName saveName
     * @param location location
     */
    public static void load(String author, String saveName, Location location) {
        load(author, saveName, location, true);
    }

    /**
     * Loads a structure of the given author and saveName to the given location ignoring entities
     * @param author author
     * @param saveName saveName
     * @param location location
     * @param ignoreEntities load or not load entities
     */
    public static void load(String author, String saveName, Location location, boolean ignoreEntities) {
        load(author, saveName, location, ignoreEntities, StructureRotation.NONE, StructureMirror.NONE);
    }

    /**
     * Loads a structure of the given author and saveName to the given location ignoring entities
     * @param author author
     * @param saveName saveName
     * @param location location
     * @param ignoreEntities ignoreEntities
     * @param rotation rotation
     * @param mirror mirror
     */
    public static void load(String author, String saveName, Location location, boolean ignoreEntities, StructureRotation rotation, StructureMirror mirror) {
        if (author == null)
            throw new IllegalArgumentException("Author cannot be null!");
        if (saveName == null)
            throw new IllegalArgumentException("Savename cannot be null!");
        if (location == null)
            throw new IllegalArgumentException("Location cannot be null!");
        if (rotation == null)
            throw new IllegalArgumentException("Rotation cannot be null!");
        if (mirror == null)
            throw new IllegalArgumentException("Mirror cannot be null!");
        NMSRegistry.load(author, saveName, location, ignoreEntities, rotation, mirror);
    }
}
