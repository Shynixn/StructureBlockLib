package com.github.shynixn.structureblocklib.business.bukkit;

import com.github.shynixn.structureblocklib.business.bukkit.nms.NMSRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright 2017 Shynixn
 * <p>
 * Do not remove this header!
 * <p>
 * Version 1.0
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2017
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

    /**
     * Initializes the library
     */
    @Override
    public void onEnable() {
        if (!NMSRegistry.isVersionSupported()) {
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.RED + "================================================");
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.RED + "Petblocks does not support your server version");
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.RED + "Install v1.9.0 - v1.12.0");
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.RED + "Plugin gets now disabled!");
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.RED + "================================================");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Loading StructureBlockLib...");
            Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Enabled StructureBlockLib " + this.getDescription().getVersion() + " by Shynixn");
        }
    }
}
