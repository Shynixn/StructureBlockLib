package com.shynixn.structureblocklib.business.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Shynixn//
 */
public final class StructureBlockLibPlugin extends JavaPlugin {
    public static final String PREFIX_CONSOLE = ChatColor.YELLOW + "[StBLi] ";

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Loading StructureBlockLib...");
        Bukkit.getConsoleSender().sendMessage(PREFIX_CONSOLE + ChatColor.GREEN + "Enabled StructureBlockLib " + getDescription().getVersion() + " by Shynixn");
    }
}
