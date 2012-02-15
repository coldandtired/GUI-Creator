package me.coldandtired.GUI_Creator;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.player.FileManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI_creator_listener implements BindingExecutionDelegate, Listener
{
	Main plugin;
	
	GUI_creator_listener(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void keyPressed(KeyBindingEvent event) 
	{
		if (!event.getPlayer().isSpoutCraftEnabled() || Main.screen_files == null) return;
    	SpoutPlayer p = event.getPlayer();
    	Permission perm = new Permission("gui_creator.can_open_gui");
    	if (p.hasPermission(perm))
    	{
    		String s = p.getName();
    		GUI gui;
    		if (plugin.guis.containsKey(s)) gui =  plugin.guis.get(s);
    		else
    		{
    			gui = new GUI(plugin, p);
    			plugin.guis.put(s, gui);
    		}
    		int open = plugin.config.getInt("open_screen", -1);
    		
        	p.getMainScreen().attachPopupScreen(gui);
        	if (open > -1) gui.jump_to_screen(open);
    		//plugin.guis.put(p.getName(), new GUI(plugin, p));
    	}
	}

	@Override
	public void keyReleased(KeyBindingEvent event) {}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{		
		File f = plugin.create_skin(event.getPlayer().getName());
		if (f != null)
		{
			FileManager fm = SpoutManager.getFileManager();
			if (!fm.getCache(plugin).contains(f)) fm.addToCache(plugin, f);
		}
	}
}
