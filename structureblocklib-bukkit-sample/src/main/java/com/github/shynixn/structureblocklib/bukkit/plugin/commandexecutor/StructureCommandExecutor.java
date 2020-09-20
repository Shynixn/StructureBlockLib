package com.github.shynixn.structureblocklib.bukkit.plugin.commandexecutor;

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class StructureCommandExecutor implements CommandExecutor {
    private final String prefix = ChatColor.YELLOW + "[Structure] ";
    private final StructureBlockLibApi structureBlockLibApi;
    private final Plugin plugin;

    /**
     * Creates a new instance of the structureCommandExecutor with dependencies.
     *
     * @param plugin               dependency.
     * @param structureBlockLibApi dependency.
     */
    public StructureCommandExecutor(Plugin plugin, StructureBlockLibApi structureBlockLibApi) {
        this.plugin = plugin;
        this.structureBlockLibApi = structureBlockLibApi;
    }

    /**
     * Gets called when the user enters a command.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String arg, String[] args) {
        if (!(commandSender instanceof Player)) {
            return false;
        }

        final Player player = (Player) commandSender;

        if (args.length == 5 && args[0].equalsIgnoreCase("saveworld") && this.toIntOrNull(args[2]) != null && this.toIntOrNull(args[3]) != null && this.toIntOrNull(args[4]) != null) {
            structureBlockLibApi
                    .saveStructure(plugin)
                    .at(player.getLocation())
                    .sizeX(this.toIntOrNull(args[2]))
                    .sizeY(this.toIntOrNull(args[3]))
                    .sizeZ(this.toIntOrNull(args[4]))
                    .saveToWorld(player.getLocation().getWorld().getName(), player.getName(), args[1])
                    .onProgress(c -> System.out.println(String.format("Percentage %.2f", c)))
                    .onException(c -> c.printStackTrace())
                    .onResult(e -> player.sendMessage(this.prefix + ChatColor.GREEN + "Saved structure '" + args[1] + "'."));
            return true;
        }

        if (args.length == 5 && args[0].equalsIgnoreCase("savefile") && this.toIntOrNull(args[2]) != null && this.toIntOrNull(args[3]) != null && this.toIntOrNull(args[4]) != null) {
            structureBlockLibApi
                    .saveStructure(plugin)
                    .at(player.getLocation())
                    .sizeX(this.toIntOrNull(args[2]))
                    .sizeY(this.toIntOrNull(args[3]))
                    .sizeZ(this.toIntOrNull(args[4]))
                    .saveToPath(plugin.getDataFolder().toPath().resolve(args[1]))
                    .onProgress(c -> System.out.println(String.format("Percentage %.2f", c)))
                    .onException(c -> c.printStackTrace())
                    .onResult(e -> player.sendMessage(this.prefix + ChatColor.GREEN + "Saved structure '" + args[1] + "'."));
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("loadworld")) {
            structureBlockLibApi
                    .loadStructure(plugin)
                    .at(player.getLocation())
                    .loadFromWorld(player.getLocation().getWorld().getName(), player.getName(), args[1])
                    .onProgress(c -> System.out.println(String.format("Percentage %.2f", c)))
                    .onException(c -> c.printStackTrace())
                    .onResult(e -> player.sendMessage(this.prefix + ChatColor.GREEN + "Placed structure '" + args[1] + "'."));
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("loadfile")) {
            structureBlockLibApi
                    .loadStructure(plugin)
                    .at(player.getLocation())
                    .loadFromPath(plugin.getDataFolder().toPath().resolve(args[1]))
                    .onProgress(c -> System.out.println(String.format("Percentage %.2f", c)))
                    .onException(c -> c.printStackTrace())
                    .onResult(e -> player.sendMessage(this.prefix + ChatColor.GREEN + "Placed structure '" + args[1] + "'."));
            return true;
        }

        player.sendMessage(this.prefix + "/structure saveworld <name> <x> <y> <z> - Saves a structure to the world folder.");
        player.sendMessage(this.prefix + "/structure savefile <filename> <x> <y> <z> - Saves a structure to the target file.");
        player.sendMessage(this.prefix + "/structure loadworld <name> - Loads a structure from the world folder.");
        player.sendMessage(this.prefix + "/structure loadfile <filename> - Loads a structure from the source file.");

        if (args.length != 0) {
            player.sendMessage(this.prefix + ChatColor.RED + "Cannot parse arguments.");
        }

        return true;
    }

    /**
     * Converts the given value to an integer. Returns null if it is not formatted properly.
     *
     * @param value value.
     * @return number.
     */
    private Integer toIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
