package com.github.shynixn.structureblocklib.api.bukkit.block;

import com.github.shynixn.structureblocklib.api.block.StructureBlockLoadAbstract;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 * Bukkit Wrapper for the StructureBlock.
 */
public interface StructureBlockLoad extends StructureBlockLoadAbstract<Location, Vector, Block, Entity, World>, StructureBlockConstruction {
}
