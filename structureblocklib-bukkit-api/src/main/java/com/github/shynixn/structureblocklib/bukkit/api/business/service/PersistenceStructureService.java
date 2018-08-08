package com.github.shynixn.structureblocklib.bukkit.api.business.service;

import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import org.bukkit.Location;
import org.bukkit.util.Vector;

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
public interface PersistenceStructureService {

    /**
     * Creates a saveConfiguration for the minimal required parameters in order to load and save structures from and to your world folder.
     * The structure will always be identified by the unique key from the combined author, saveName and world.
     *
     * @param author   author - key
     * @param saveName saveName - key
     * @param world    world - key. World Folder to store the Save, can be different from the actual location it is going to be used.
     * @return saveConfiguration.
     */
    StructureSaveConfiguration createSaveConfiguration(String author, String saveName, String world);

    /**
     * Uses the saveConfiguration to store a structure by the source location and given offSet.
     *
     * @param saveConfiguration saveConfiguration.
     * @param source            location.
     * @param offSet            offset.
     */
    void save(StructureSaveConfiguration saveConfiguration, Location source, Vector offSet);

    /**
     * Uses the saveConfiguration to store a structure by the source location and between the corner location.
     *
     * @param saveConfiguration saveConfiguration.
     * @param source            location.
     * @param corner            corner.
     */
    void save(StructureSaveConfiguration saveConfiguration, Location source, Location corner);

    /**
     * Uses the saveConfiguration to load a structure to the given target location.
     *
     * @param saveConfiguration saveConfiguration
     * @param target            location.
     * @return Returns true if the saveConfiguration could be successfully placed.
     */
    boolean load(StructureSaveConfiguration saveConfiguration, Location target);
}
