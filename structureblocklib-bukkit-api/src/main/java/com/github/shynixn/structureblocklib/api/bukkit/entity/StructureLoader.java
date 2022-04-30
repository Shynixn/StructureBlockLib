package com.github.shynixn.structureblocklib.api.bukkit.entity;

import com.github.shynixn.structureblocklib.api.entity.StructureLoaderAbstract;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

/**
 * Bukkit Wrapper for StructureLoader.
 */
public interface StructureLoader extends StructureLoaderAbstract<Location, Vector, Block, World> {
}
