package com.github.shynixn.structureblocklib.bukkit.core.business.service;

import com.github.shynixn.structureblocklib.bukkit.api.business.proxy.StructureBlock;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.StructureBlockService;
import com.github.shynixn.structureblocklib.bukkit.core.VersionSupport;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.lang.reflect.Constructor;
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
public class StructureBlockServiceImpl implements StructureBlockService {
    private final VersionSupport versionSupport = VersionSupport.getServerVersion();

    /**
     * Gets the structure block proxy at the given location and creates a structureBlock at the given
     * location if the block is not already a structureBlock.
     *
     * @param location location
     * @return structureBlock.
     */
    @Override
    public <S extends StructureBlock> S getOrCreateStructureBlockAt(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        }

        if (location.getBlock().getType() != Material.STRUCTURE_BLOCK) {
            location.getBlock().setType(Material.STRUCTURE_BLOCK);
        }

        try {
            final Constructor constructor = Class.forName("com.github.shynixn.structureblocklib.bukkit.core.nms.VERSION.CraftStructureBlock".replace("VERSION", this.versionSupport.getVersionText())).getDeclaredConstructor(Block.class);
            //noinspection unchecked
            return (S) constructor.newInstance(location.getBlock());
        } catch (final Exception ex) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to create structureBlock. Is your server version support?", ex);
            throw new RuntimeException(ex);
        }
    }
}
