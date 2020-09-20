package com.github.shynixn.structureblocklib.bukkit.plugin.commandexecutor;

import com.github.shynixn.structureblocklib.api.bukkit.StructureBlockLibApi;
import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockLoad;
import com.github.shynixn.structureblocklib.api.bukkit.block.StructureBlockSave;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMirror;
import com.github.shynixn.structureblocklib.api.enumeration.StructureMode;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class StructureBlockCommandExecutor implements CommandExecutor {
    private final String prefix = ChatColor.YELLOW + "[Structure] ";
    private final StructureBlockLibApi structureBlockLibApi;
    private final Plugin plugin;

    /**
     * Creates a new instance of the StructureBlockCommandExecutor with dependencies.
     *
     * @param plugin               dependency.
     * @param structureBlockLibApi dependency.
     */
    public StructureBlockCommandExecutor(Plugin plugin, StructureBlockLibApi structureBlockLibApi) {
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

        if (args.length == 1 && args[0].equalsIgnoreCase("save")) {
            final Location location = player.getLocation();
            location.getBlock().setType(Material.STRUCTURE_BLOCK);

            final StructureBlockSave structureBlock = structureBlockLibApi.getStructureBlockAt(location, plugin);
            structureBlock.setStructureMode(StructureMode.SAVE);
            structureBlock.setSaveName("SampleSave");
            structureBlock.setSizeX(31);
            structureBlock.setSizeY(15);
            structureBlock.setSizeZ(12);
            structureBlock.update();

            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("load")) {
            final Location location = player.getLocation();
            location.getBlock().setType(Material.STRUCTURE_BLOCK);

            final StructureBlockLoad structureBlock = structureBlockLibApi.getStructureBlockAt(location, plugin);
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
