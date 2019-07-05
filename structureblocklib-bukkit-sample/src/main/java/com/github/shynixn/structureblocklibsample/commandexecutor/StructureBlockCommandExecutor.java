package com.github.shynixn.structureblocklibsample.commandexecutor;

import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.bukkit.api.business.enumeration.StructureMode;
import com.github.shynixn.structureblocklib.bukkit.api.business.proxy.StructureBlockLoad;
import com.github.shynixn.structureblocklib.bukkit.api.business.proxy.StructureBlockSave;
import com.github.shynixn.structureblocklib.bukkit.api.business.service.StructureBlockService;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
public class StructureBlockCommandExecutor implements CommandExecutor {
    private final String prefix = ChatColor.YELLOW + "[Structure] ";

    private final StructureBlockService structureBlockService;

    /**
     * Creates a new instance of the StructureBlockCommandExecutor with a structureBlockService as dependency.
     *
     * @param structureBlockService structureBlockService.
     */
    public StructureBlockCommandExecutor(StructureBlockService structureBlockService) {
        this.structureBlockService = structureBlockService;
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

        if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
            final Location location = player.getLocation();

            final StructureBlockSave structureBlock = this.structureBlockService.getOrCreateStructureBlockAt(location);
            structureBlock.setStructureMode(StructureMode.SAVE);
            structureBlock.setSizeX(32);
            structureBlock.setSizeY(15);
            structureBlock.setSizeZ(12);
            structureBlock.update();

            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("load")) {
            final Location location = player.getLocation();

            final StructureBlockLoad structureBlock = this.structureBlockService.getOrCreateStructureBlockAt(location);
            structureBlock.setStructureMode(StructureMode.LOAD);
            structureBlock.setMirrorType(StructureMirror.FRONT_BACK);
            structureBlock.update();

            return true;
        }

        player.sendMessage(this.prefix + "/structureblock save - Places a save structureblock.");
        player.sendMessage(this.prefix + "/structureblock load - Places a load structureblock.");

        if (args.length != 0) {
            player.sendMessage(this.prefix + ChatColor.RED + "Cannot parse arguments.");
        }

        return true;
    }
}
