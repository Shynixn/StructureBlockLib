package com.github.shynixn.structureblocklib.bukkit.core.persistence.entity;

import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureRotation;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;

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
public class StructureSaveConfigurationEntity implements StructureSaveConfiguration {
    private String world;
    private boolean isIgnoreEntities = true;
    private StructureRotation rotation = StructureRotation.NONE;
    private StructureMirror mirror = StructureMirror.NONE;
    private String author;
    private String saveName;

    /**
     * Creates a new instance of the StructureSaveConfiguration.
     *
     * @param world    world
     * @param author   author
     * @param saveName saveName
     */
    public StructureSaveConfigurationEntity(String world, String author, String saveName) {
        this.world = world;
        this.author = author;
        this.saveName = saveName;
    }

    /**
     * Should entities be ignored.
     *
     * @return ignoreEntities
     */
    public boolean isIgnoreEntities() {
        return this.isIgnoreEntities;
    }

    /**
     * Set entities ignored.
     *
     * @param ignoreEntities entities
     */
    public void setIgnoreEntities(boolean ignoreEntities) {
        this.isIgnoreEntities = ignoreEntities;
    }

    /**
     * Returns the rotation of the structure.
     *
     * @return rotation
     */
    public StructureRotation getRotation() {
        return this.rotation;
    }

    /**
     * Sets the rotation of the structure.
     *
     * @param rotation rotation.
     */
    public void setRotation(StructureRotation rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns the mirror of the structure.
     *
     * @return mirro
     */
    public StructureMirror getMirror() {
        return this.mirror;
    }

    /**
     * Sets the mirror of the structure.
     *
     * @param mirror mirror
     */
    public void setMirror(StructureMirror mirror) {
        this.mirror = mirror;
    }

    /**
     * Returns the author.
     *
     * @return author.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Sets the author.
     *
     * @param author author.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Returns the saveName.
     *
     * @return saveName.
     */
    public String getSaveName() {
        return this.saveName;
    }

    /**
     * Sets the saveName.
     *
     * @param saveName saveName.
     */
    public void setSaveName(String saveName) {
        if (saveName == null) {
            throw new IllegalArgumentException("SaveName cannot be null!");
        }

        this.saveName = saveName;
    }

    /**
     * Returns the world folder where the structure gets stored.
     *
     * @return world.
     */
    @Override
    public String getWorld() {
        return this.world;
    }

    /**
     * Sets the world folder where the structure gets stored.
     *
     * @param world world
     */
    @Override
    public void setWorld(String world) {
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null!");
        }

        this.world = world;
    }
}
