package com.github.shynixn.structureblocklib.bukkit.plugin.commandexecutor;

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by Shynixn 2019.
 * <p>
 * Version 1.2
 * <p>
 * MIT License
 * <p>
 * Copyright (c) 2019 by Shynixn
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
public class StructureNCommandExecutor implements CommandExecutor {
    private final String prefix = ChatColor.YELLOW + "[Structure] ";
    private final StructureBlockLibApi structureBlockLibApi;
    private final Plugin plugin;

    /**
     * Creates a new instance of the structureCommandExecutor with the persistenceStructureService
     * as dependency.
     *
     * @param structureBlockLibApi dependency.
     */
    public StructureNCommandExecutor(Plugin plugin, StructureBlockLibApi structureBlockLibApi) {
        this.structureBlockLibApi = structureBlockLibApi;
        this.plugin = plugin;
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

        if (args.length == 6 && args[0].equalsIgnoreCase("save") && this.toIntOrNull(args[2]) != null && this.toIntOrNull(args[3]) != null && this.toIntOrNull(args[4]) != null) {
            final String fileName = args[5];
            structureBlockLibApi
                    .saveStructure(plugin)
                    .at(player.getLocation())
                    .sizeX(this.toIntOrNull(args[2]))
                    .sizeY(this.toIntOrNull(args[3]))
                    .sizeZ(this.toIntOrNull(args[4]))
                    .author(player.getName())
                    .saveToPath(plugin.getDataFolder().toPath().resolve(fileName))
                    .onProgress(c -> System.out.println(String.format("Percentage %.2f", c)))
                    .onException(c -> c.printStackTrace())
                    .onResult(e -> player.sendMessage(this.prefix + ChatColor.GREEN + "Saved structure '" + args[1] + "'."));
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("load")) {
            final String fileName = args[2];
            structureBlockLibApi
                    .loadStructure(plugin)
                    .at(player.getLocation())
                    .loadFromPath(plugin.getDataFolder().toPath().resolve(fileName))
                    .onProgress(c -> System.out.println(String.format("Percentage %.2f", c)))
                    .onException(c -> c.printStackTrace())
                    .onResult(e -> player.sendMessage(this.prefix + ChatColor.GREEN + "Placed structure '" + args[1] + "'."));
            return true;
        }

        player.sendMessage(this.prefix + "/structure save <name> <x> <y> <z> <filename> - Saves a structure from the current player position and given offset.");
        player.sendMessage(this.prefix + "/structure load <name> <filename> - Loads a structure at the current player position.");

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
