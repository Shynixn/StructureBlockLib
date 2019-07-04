package com.github.shynixn.structureblocklibsample.commandexecutor;

import com.github.shynixn.structureblocklib.bukkit.api.business.service.PersistenceStructureService;
import com.github.shynixn.structureblocklib.bukkit.api.persistence.entity.StructureSaveConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

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
public class StructureCommandExecutor implements CommandExecutor {
    private final String prefix = ChatColor.YELLOW + "[Structure] ";

    private final PersistenceStructureService persistenceStructureService;

    /**
     * Creates a new instance of the structureCommandExecutor with the persistenceStructureService
     * as dependency.
     *
     * @param persistenceStructureService dependency.
     */
    public StructureCommandExecutor(PersistenceStructureService persistenceStructureService) {
        this.persistenceStructureService = persistenceStructureService;
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

        if (args.length == 5 && args[0].equalsIgnoreCase("save") && this.toIntOrNull(args[2]) != null && this.toIntOrNull(args[3]) != null && this.toIntOrNull(args[4]) != null) {
            final Location location = player.getLocation();
            final Vector vector = new Vector(this.toIntOrNull(args[2]), this.toIntOrNull(args[3]), this.toIntOrNull(args[4]));

            final StructureSaveConfiguration structureSaveConfiguration = this.persistenceStructureService.createSaveConfiguration(player.getName().toLowerCase(), args[1], location.getWorld().getName());
            this.persistenceStructureService.save(structureSaveConfiguration, location, vector);

            player.sendMessage(this.prefix + ChatColor.GREEN + "Saved structure '" + args[1] + "'.");

            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("load")) {
            final Location location = player.getLocation();

            final StructureSaveConfiguration structureSaveConfiguration = this.persistenceStructureService.createSaveConfiguration(player.getName().toLowerCase(), args[1], location.getWorld().getName());
            final boolean hasBeenLoaded = this.persistenceStructureService.load(structureSaveConfiguration, location);

            if (hasBeenLoaded) {
                player.sendMessage(this.prefix + ChatColor.GREEN + "Placed structure '" + args[1] + "'.");

            } else {
                player.sendMessage(this.prefix + ChatColor.RED + "Cannot load structure '" + args[1] + "'.");
            }

            return true;
        }

        player.sendMessage(this.prefix + "/structure save <name> <x> <y> <z> - Saves a structure from the current player position and given offset.");
        player.sendMessage(this.prefix + "/structure load <name> - Loads a structure at the current player position.");

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
