package com.shynixn.structureblocklib.lib;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by Shynixn
 */
public interface SimpleCommandExecutor
{
    void onPlayerExecuteCommand(Player player, String[] args);

    void onCommandSenderExecuteCommand(CommandSender sender, String[] args);

    class Registered implements SimpleCommandExecutor, CommandExecutor
    {
        @PluginHandler.PluginLoad
        protected static JavaPlugin plugin;

        public Registered(String command)
        {
            this(command, plugin);
        }

        public Registered(String command, JavaPlugin plugin)
        {
            if(plugin == null)
                throw new IllegalArgumentException("Plugin cannot be null!");
            plugin.getCommand(command).setExecutor(this);
        }

        @Override
        public final boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
        {
            if(commandSender instanceof Player)
            {
                onPlayerExecuteCommand((Player) command, strings);
            }
            onCommandSenderExecuteCommand(commandSender, strings);
            return true;
        }

        @Override
        public void onPlayerExecuteCommand(Player player, String[] args)
        {

        }

        @Override
        public void onCommandSenderExecuteCommand(CommandSender sender, String[] args)
        {

        }
    }

    class UnRegistred extends BukkitCommand implements SimpleCommandExecutor
    {
        @PluginHandler.PluginLoad
        protected static JavaPlugin plugin;

        public UnRegistred(String command, String useage, String description, String permission, String permissionMessage)
        {
            super(command);
            this.description = description;
            this.usageMessage = useage;
            this.setPermission(permission);
            this.setPermissionMessage(permissionMessage);
            this.setAliases(new ArrayList<String>());
            registerDynamicCommand(command, this);
        }

        private static void registerDynamicCommand(String command, BukkitCommand clazz)
        {
            Object obj = ReflectionLib.createClass("org.bukkit.craftbukkit.VERSION.CraftServer").cast(Bukkit.getServer());
            obj = ReflectionLib.invokeMethodByObject("getCommandMap", obj);
            ReflectionLib.invokeMethodByObject("register",obj, command, clazz);
        }

        @Override
        public final boolean execute(CommandSender sender, String alias, String[] args)
        {
            if (!sender.hasPermission(this.getPermission()))
            {
                sender.sendMessage(getPermissionMessage());
                return true;
            }
            if (sender instanceof Player)
            {
               onPlayerExecuteCommand((Player) sender, args);
            }
            onCommandSenderExecuteCommand(sender, args);
            return true;
        }

        @Override
        public void onPlayerExecuteCommand(Player player, String[] args)
        {

        }

        @Override
        public void onCommandSenderExecuteCommand(CommandSender sender, String[] args)
        {

        }
    }
}
