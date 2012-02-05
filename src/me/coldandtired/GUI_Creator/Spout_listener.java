package me.coldandtired.GUI_Creator;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.player.FileManager;

public class Spout_listener implements Listener//extends SpoutListener
{
	Main plugin;
	
	public Spout_listener(Main plugin) 
	{
		this.plugin = plugin;
	}

	@EventHandler
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) 
	{
		File f = plugin.create_skin(event.getPlayer());
		if (f != null)
		{
			FileManager fm = SpoutManager.getFileManager();
			if (!fm.getCache(plugin).contains(f)) fm.addToCache(plugin, f);
		}
    }
}
