package com.github.shynixn.structureblocklib.bukkit.plugin;

import com.github.shynixn.structureblocklib.bukkit.api.StructureBlockApi;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import com.github.shynixn.structureblocklib.bukkit.core.VersionSupport;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * Plugin main which initializes the plugin data.
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
public final class StructureBlockLibPlugin extends JavaPlugin {
    private static final String PREFIX_CONSOLE = ChatColor.YELLOW + "[StBLi] ";
    private static final String PLUGIN_NAME = "StructureBlockLib";

    /**
     * Initializes the plugin.
     */
    @Override
    public void onEnable() {
        if (!VersionSupport.isServerVersionSupported(PLUGIN_NAME, PREFIX_CONSOLE)) {
            return;
        }

        Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Loading StructureBlockLib...");
        Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Enabled StructureBlockLib " + this.getDescription().getVersion() + " by Shynixn");

        Player player = Bukkit.getPlayer("Shynixn");

        PersistenceStructureService persistenceStructureService = StructureBlockApi.INSTANCE.getStructurePersistenceService();
        StructureSaveConfiguration saveConfiguration = persistenceStructureService.createSaveConfiguration("shynixn", "cool", "world");

        persistenceStructureService.save(saveConfiguration, player.getLocation(), new Vector(5, 5, 5));

    }
}
