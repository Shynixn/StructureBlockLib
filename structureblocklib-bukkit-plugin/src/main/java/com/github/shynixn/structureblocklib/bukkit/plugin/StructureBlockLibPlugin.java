package com.github.shynixn.structureblocklib.bukkit.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Plugin main which initializes the plugin data.
 */
public final class StructureBlockLibPlugin extends JavaPlugin {
    private static final String PREFIX_CONSOLE = ChatColor.YELLOW + "[StBLi] ";

    /**
     * Initializes the plugin.
     */
    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Loading StructureBlockLib...");
        Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Enabled StructureBlockLib " + this.getDescription().getVersion() + " by Shynixn");
    }
}
