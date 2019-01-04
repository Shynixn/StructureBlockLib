package com.github.shynixn.structureblocklib.bukkit.core.business.service;

import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.LocationCalculationService;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import com.github.shynixn.structureblocklib.bukkit.core.VersionSupport;
import com.github.shynixn.structureblocklib.bukkit.core.persistence.entity.StructureSaveConfigurationEntity;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

/**
 * Created by Shynixn 2018.
 * <p>
 * Version 1.2
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2018 by Shynixn
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class PersistenceStructureServiceImpl implements PersistenceStructureService {
    private final VersionSupport versionSupport = VersionSupport.getServerVersion();
    private final LocationCalculationService locationCalculationService;

    /**
     * Creates a new persistencestructure service with Locationcalculation service dependency.
     *
     * @param locationCalculationService dependency
     */
    public PersistenceStructureServiceImpl(LocationCalculationService locationCalculationService) {
        this.locationCalculationService = locationCalculationService;
    }

    /**
     * Creates a saveConfiguration for the minimal required parameters in order to load and save structures from and to your world folder.
     * The structure will always be identified by the unique key from the combined author, saveName and world.
     *
     * @param author   author - key
     * @param saveName saveName - key
     * @param world    world - key. World Folder to store the Save, can be different from the actual location it is going to be used.
     * @return saveConfiguration.
     */
    @Override
    public StructureSaveConfiguration createSaveConfiguration(String author, String saveName, String world) {
        return new StructureSaveConfigurationEntity(world, author, saveName);
    }

    /**
     * Uses the saveConfiguration to store a structure by the source location and between the corner location.
     *
     * @param saveConfiguration saveConfiguration.
     * @param source            location.
     * @param corner            corner.
     */
    @Override
    public void save(StructureSaveConfiguration saveConfiguration, Location source, Location corner) {
        final Location lowerCorner = this.locationCalculationService.getDownCornerLocation(source, corner);
        final Location upperCorner = this.locationCalculationService.getUpCornerLocation(source, corner);
        final Vector offset = this.locationCalculationService.toDimensions(lowerCorner, upperCorner);

        this.save(saveConfiguration, lowerCorner, offset);
    }

    /**
     * Uses the saveConfiguration to store a structure by the source location and given offSet.
     *
     * @param saveConfiguration saveConfiguration.
     * @param source            location.
     * @param offSet            offset.
     */
    @Override
    public void save(StructureSaveConfiguration saveConfiguration, Location source, Vector offSet) {
        try {
            if (offSet.getBlockX() < 0) {
                source.setX(source.getX() + offSet.getX());
                offSet.setX(offSet.getBlockX() * -1);
            }
            if (offSet.getBlockY() < 0) {
                source.setY(source.getY() + offSet.getY());
                offSet.setY(offSet.getBlockY() * -1);
            }
            if (offSet.getBlockZ() < 0) {
                source.setZ(source.getZ() + offSet.getZ());
                offSet.setZ(offSet.getBlockZ() * -1);
            }

            final Method craftWorldGetHandle = this.findClazz("org.bukkit.craftbukkit.VERSION.CraftWorld").getDeclaredMethod("getHandle");
            final Object nmsWorld = craftWorldGetHandle.invoke(source.getWorld());
            final Class<?> blockPositionClazz = this.findClazz("net.minecraft.server.VERSION.BlockPosition");
            final Class<?> minecraftKeyClazz = this.findClazz("net.minecraft.server.VERSION.MinecraftKey");
            final Object vPosition = blockPositionClazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(0, 0, 0);
            final Object finalBlockPosition = blockPositionClazz.getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.BaseBlockPosition"))
                    .invoke(vPosition, blockPositionClazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(source.getBlockX(), source.getBlockY(), source.getBlockZ()));
            final Object finalSecondBlockPosition;
            finalSecondBlockPosition = blockPositionClazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(offSet.getBlockX() + 1, offSet.getBlockY() + 1, offSet.getBlockZ() + 1);

            final World saveWorldBukkit;
            if ((saveWorldBukkit = Bukkit.getWorld(saveConfiguration.getWorld())) == null) {
                throw new Exception("World " + saveConfiguration.getWorld() + " does not exist.");
            }

            final Object saveWorld = craftWorldGetHandle.invoke(saveWorldBukkit);
            final Object structureManager = this.findStructureManager(saveWorld);
            final Object mineCraftServer = this.findClazz("net.minecraft.server.VERSION.World").getDeclaredMethod("getMinecraftServer").invoke(saveWorld);
            final Object definedStructure;

            if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
                definedStructure = this.findClazz("net.minecraft.server.VERSION.DefinedStructureManager")
                        .getDeclaredMethod("a", minecraftKeyClazz)
                        .invoke(structureManager, minecraftKeyClazz.getDeclaredConstructor(String.class, String.class)
                                .newInstance(saveConfiguration.getAuthor(), saveConfiguration.getSaveName()));
            } else {
                definedStructure = this.findClazz("net.minecraft.server.VERSION.DefinedStructureManager")
                        .getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.MinecraftServer"), minecraftKeyClazz)
                        .invoke(structureManager, mineCraftServer, minecraftKeyClazz.getDeclaredConstructor(String.class, String.class)
                                .newInstance(saveConfiguration.getAuthor(), saveConfiguration.getSaveName()));
            }

            this.findClazz("net.minecraft.server.VERSION.DefinedStructure")
                    .getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.World"), blockPositionClazz, blockPositionClazz, boolean.class, this.findClazz("net.minecraft.server.VERSION.Block"))
                    .invoke(definedStructure, nmsWorld, finalBlockPosition, finalSecondBlockPosition, !saveConfiguration.isIgnoreEntities(), this.findClazz("net.minecraft.server.VERSION.Blocks").getDeclaredField("BARRIER").get(null));

            this.findClazz("net.minecraft.server.VERSION.DefinedStructure").getDeclaredMethod("a", String.class)
                    .invoke(definedStructure, saveConfiguration.getAuthor());

            if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
                this.findClazz("net.minecraft.server.VERSION.DefinedStructureManager").getDeclaredMethod("c", minecraftKeyClazz)
                        .invoke(structureManager, minecraftKeyClazz.getDeclaredConstructor(String.class, String.class).newInstance(saveConfiguration.getAuthor(), saveConfiguration.getSaveName()));
                Bukkit.getLogger().log(Level.INFO, "[StructureBlockLib] Stored structure to ../" + saveWorldBukkit.getName() + "/generated/" + saveConfiguration.getAuthor() + "/structures/" + saveConfiguration.getSaveName() + ".nbt");
            } else {
                this.findClazz("net.minecraft.server.VERSION.DefinedStructureManager").getDeclaredMethod("c", this.findClazz("net.minecraft.server.VERSION.MinecraftServer"), minecraftKeyClazz)
                        .invoke(structureManager, mineCraftServer, minecraftKeyClazz.getDeclaredConstructor(String.class, String.class).newInstance(saveConfiguration.getAuthor(), saveConfiguration.getSaveName()));

                Bukkit.getLogger().log(Level.INFO, "[StructureBlockLib] Stored structure to ../" + saveWorldBukkit.getName() + "/structures/" + saveConfiguration.getSaveName() + ".nbt");

                try {
                    final File sourceFile = new File(saveWorldBukkit.getName() + "/structures/" + saveConfiguration.getSaveName() + ".nbt");
                    final File targetFile = new File(saveWorldBukkit.getName() + "/generated/" + saveConfiguration.getAuthor() + "/structures/" + saveConfiguration.getSaveName() + ".nbt");
                    FileUtils.copyFile(sourceFile, targetFile);

                    Bukkit.getLogger().log(Level.INFO, "[StructureBlockLib] Stored 1.13 compatibility structure to ../" + saveWorldBukkit.getName() + "/generated/" + saveConfiguration.getAuthor() + "/structures/" + saveConfiguration.getSaveName() + ".nbt");
                } catch (final IOException ex) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to create compatibility structure copy.", ex);
                }
            }
        } catch (final Exception ex) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to load structure.", ex);
        }
    }

    /**
     * Uses the saveConfiguration to load a structure to the given target location.
     *
     * @param saveConfiguration saveConfiguration
     * @param target            location.
     * @return Returns true if the saveConfiguration could be successfully placed.
     */
    @Override
    public boolean load(StructureSaveConfiguration saveConfiguration, Location target) {
        try {
            if (!this.structureExists(saveConfiguration)) {
                return false;
            }

            final Method craftWorldGetHandle = this.findClazz("org.bukkit.craftbukkit.VERSION.CraftWorld").getDeclaredMethod("getHandle");
            final Class<?> minecraftKeyClazz = this.findClazz("net.minecraft.server.VERSION.MinecraftKey");
            final Object nmsWorld = craftWorldGetHandle.invoke(target.getWorld());
            final boolean isClientSideWorld = (boolean) this.findClazz("net.minecraft.server.VERSION.World").getDeclaredField("isClientSide").get(nmsWorld);

            if (isClientSideWorld) {
                return false;
            }

            final World saveWorldBukkit;
            if ((saveWorldBukkit = Bukkit.getWorld(saveConfiguration.getWorld())) == null) {
                throw new Exception("World " + saveConfiguration.getWorld() + " does not exist.");
            }

            final Object saveWorld = craftWorldGetHandle.invoke(saveWorldBukkit);
            final Class<?> blockPositionClazz = this.findClazz("net.minecraft.server.VERSION.BlockPosition");
            final Object vPosition = blockPositionClazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(0, 0, 0);
            final Object finalBlockPosition = blockPositionClazz.getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.BaseBlockPosition"))
                    .invoke(vPosition, blockPositionClazz.getDeclaredConstructor(int.class, int.class, int.class).newInstance(target.getBlockX(), target.getBlockY(), target.getBlockZ()));

            final Object structureManager = this.findStructureManager(saveWorld);
            final Object mineCraftServer = this.findClazz("net.minecraft.server.VERSION.World").getDeclaredMethod("getMinecraftServer").invoke(nmsWorld);

            final Object definedStructure;
            if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
                definedStructure = this.findClazz("net.minecraft.server.VERSION.DefinedStructureManager")
                        .getDeclaredMethod("a", minecraftKeyClazz)
                        .invoke(structureManager, minecraftKeyClazz.getDeclaredConstructor(String.class, String.class)
                                .newInstance(saveConfiguration.getAuthor(), saveConfiguration.getSaveName()));
            } else {
                definedStructure = this.findClazz("net.minecraft.server.VERSION.DefinedStructureManager")
                        .getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.MinecraftServer"), minecraftKeyClazz)
                        .invoke(structureManager, mineCraftServer, minecraftKeyClazz.getDeclaredConstructor(String.class, String.class)
                                .newInstance(saveConfiguration.getAuthor(), saveConfiguration.getSaveName()));
            }

            final Class<?> definedStructureInfoClazz = this.findClazz("net.minecraft.server.VERSION.DefinedStructureInfo");
            final Class mirrorClazz = this.findClazz("net.minecraft.server.VERSION.EnumBlockMirror");
            final Class rotationClazz = this.findClazz("net.minecraft.server.VERSION.EnumBlockRotation");

            final Object definedStructureInfo = definedStructureInfoClazz.newInstance();
            definedStructureInfoClazz.getDeclaredMethod("a", mirrorClazz).invoke(definedStructureInfo, this.getBlockMirror(mirrorClazz, saveConfiguration.getMirror()));
            definedStructureInfoClazz.getDeclaredMethod("a", rotationClazz).invoke(definedStructureInfo, this.getBlockRotation(rotationClazz, saveConfiguration.getRotation()));
            definedStructureInfoClazz.getDeclaredMethod("a", boolean.class).invoke(definedStructureInfo, saveConfiguration.isIgnoreEntities());
            definedStructureInfoClazz.getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.ChunkCoordIntPair")).invoke(definedStructureInfo, new Object[]{null});
            definedStructureInfoClazz.getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.Block")).invoke(definedStructureInfo, new Object[]{null});

            if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
                definedStructureInfoClazz.getDeclaredMethod("c", boolean.class).invoke(definedStructureInfo, false);
                this.findClazz("net.minecraft.server.VERSION.DefinedStructure")
                        .getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.GeneratorAccess"), blockPositionClazz, definedStructureInfoClazz)
                        .invoke(definedStructure, nmsWorld, finalBlockPosition, definedStructureInfo);
            } else {
                definedStructureInfoClazz.getDeclaredMethod("b", boolean.class).invoke(definedStructureInfo, false);
                this.findClazz("net.minecraft.server.VERSION.DefinedStructure")
                        .getDeclaredMethod("a", this.findClazz("net.minecraft.server.VERSION.World"), blockPositionClazz, definedStructureInfoClazz)
                        .invoke(definedStructure, nmsWorld, finalBlockPosition, definedStructureInfo);
            }

            return true;

        } catch (final Exception ex) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to load structure.", ex);
            return false;
        }
    }

    private Class<?> findClazz(String text) throws ClassNotFoundException {
        return Class.forName(text.replace("VERSION", this.versionSupport.getVersionText()));
    }

    @SuppressWarnings("unchecked")

    private Object getBlockRotation(Class rotationClazz, StructureRotation rotation) {
        switch (rotation) {
            case NONE:
                return Enum.valueOf(rotationClazz, "NONE");
            case ROTATION_90:
                return Enum.valueOf(rotationClazz, "CLOCKWISE_90");
            case ROTATION_180:
                return Enum.valueOf(rotationClazz, "CLOCKWISE_180");
            case ROTATION_270:
                return Enum.valueOf(rotationClazz, "COUNTERCLOCKWISE_90");
        }

        return null;
    }

    /**
     * Checks if the structure file specified in the given configuration actually exists.
     *
     * @param configuration configuration.
     * @return file exists.
     */
    private boolean structureExists(StructureSaveConfiguration configuration) {
        final File file;

        if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
            file = new File(configuration.getWorld() + File.separator + "generated" + File.separator + configuration.getAuthor() + File.separator + "structures" + File.separator + configuration.getSaveName() + ".nbt");
        } else {
            file = new File(configuration.getWorld() + File.separator + "structures" + File.separator + configuration.getSaveName() + ".nbt");
        }

        return file.isFile();
    }

    private Object findStructureManager(Object saveWorld) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R2)) {
            return this.findClazz("net.minecraft.server.VERSION.WorldServer").getDeclaredMethod("D").invoke(saveWorld);
        } else if (this.versionSupport.isVersionSameOrGreaterThan(VersionSupport.VERSION_1_13_R1)) {
            return this.findClazz("net.minecraft.server.VERSION.WorldServer").getDeclaredMethod("C").invoke(saveWorld);
        } else {
            return this.findClazz("net.minecraft.server.VERSION.WorldServer").getDeclaredMethod("y").invoke(saveWorld);
        }
    }

    @SuppressWarnings("unchecked")
    private Object getBlockMirror(Class mirrorClazz, StructureMirror mirror) {
        switch (mirror) {
            case NONE:
                return Enum.valueOf(mirrorClazz, "NONE");
            case FRONT_BACK:
                return Enum.valueOf(mirrorClazz, "FRONT_BACK");
            case LEFT_RIGHT:
                return Enum.valueOf(mirrorClazz, "LEFT_RIGHT");
        }

        return null;
    }
}
