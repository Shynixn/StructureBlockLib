package com.github.shynixn.structureblocklib.bukkit.api.persistence.entity;

import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureRotation;

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
public interface StructureSaveConfiguration {

    /**
     * Returns the author.
     *
     * @return author.
     */
    String getAuthor();

    /**
     * Sets the author.
     *
     * @param author author.
     */
    void setAuthor(String author);

    /**
     * Returns the saveName.
     *
     * @return saveName.
     */
    String getSaveName();

    /**
     * Sets the saveName.
     *
     * @param saveName saveName.
     */
    void setSaveName(String saveName);

    /**
     * Returns the world folder where the structure gets stored.
     *
     * @return world.
     */
    String getWorld();

    /**
     * Sets the world folder where the structure gets stored.
     *
     * @param world world
     */
    void setWorld(String world);

    /**
     * Should entities be ignored.
     *
     * @return ignoreEntities
     */
    boolean isIgnoreEntities();

    /**
     * Set entities ignored.
     *
     * @param ignoreEntities entities
     */
    void setIgnoreEntities(boolean ignoreEntities);

    /**
     * Returns the rotation of the structure.
     *
     * @return rotation
     */
    StructureRotation getRotation();

    /**
     * Sets the rotation of the structure.
     *
     * @param rotation rotation
     */
    void setRotation(StructureRotation rotation);

    /**
     * Returns the mirror of the structure.
     *
     * @return mirror
     */
    StructureMirror getMirror();

    /**
     * Sets the mirror of the structure.
     *
     * @param mirror mirror
     */
    void setMirror(StructureMirror mirror);
}
