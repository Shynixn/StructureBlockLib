package com.shynixn.structureblocklib.business.bukkit;

import com.shynixn.structureblocklib.api.StructureBlockApi;
import com.shynixn.structureblocklib.lib.PluginHandler;
import com.shynixn.structureblocklib.lib.SimpleCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * Created by Shynixn
 */
public class StructureBlockLibPlugin extends JavaPlugin
{
    private CraftStructure craftStructure;

    @Override
    public void onEnable()
    {
        Location location = new Location(Bukkit.getWorld("world"), 0,0,0);
        PluginHandler.load(this, SimpleCommandExecutor.UnRegistred.class);
        new Command();
        super.onEnable();
    }

    private class Command extends SimpleCommandExecutor.UnRegistred
    {
        public Command()
        {
            super("gerd", "gerd", "gerd","gerd", "gerd");
        }

        @Override
        public void onPlayerExecuteCommand(Player player, String[] args)
        {
            if(args.length == 1 && args[0].equals("save"))
            {
                StructureBlockApi.save(player, "Gerdsave", player.getLocation(), new Vector(5,5,5));
            }
            else if(args.length == 1 && args[0].equals("load"))
            {
                StructureBlockApi.load("oha", "Gerdsave", player.getLocation(), new Vector(5,5,5));
            }
        }
    }
}
