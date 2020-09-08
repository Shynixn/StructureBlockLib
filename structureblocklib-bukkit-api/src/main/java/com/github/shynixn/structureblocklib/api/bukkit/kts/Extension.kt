package com.github.shynixn.structureblocklib.api.bukkit.kts

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi
import com.github.shynixn.structureblocklib.api.bukkit.entity.StructureSaver
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.plugin.Plugin
import org.bukkit.util.Vector
import java.io.File

/**
 * Wrapper to save a structure to a target file.
 */
fun Plugin.saveStructureToFile(file: File, f: StructureSaver.() -> Unit) {
    val saver = StructureBlockLibApi.INSTANCE
        .saveStructure(this)
    f.invoke(saver)
    saver.saveToFile(file)
}

/**
 * Wrapper to create a location.
 */
fun location(f: Location.() -> Unit): Location {
    val location = Location(null, 0.0, 0.0, 0.0)
    f.invoke(location)
    return location
}

/**
 * Wrapper to create a vector.
 */
fun vector(f: Vector.() -> Unit): Vector {
    val vector = Vector(0.0, 0.0, 0.0)
    f.invoke(vector)
    return vector
}

/**
 * Wrapper to access a world.
 */
fun world(f: () -> String): World {
    return Bukkit.getWorld(f.invoke())!!
}
