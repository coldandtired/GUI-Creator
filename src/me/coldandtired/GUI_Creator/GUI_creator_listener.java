package me.coldandtired.GUI_Creator;

import java.io.File;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.gui.Widget;
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
    	boolean show = Main.config.getBoolean("ignore_open_permission", false) ? p.isOp() : p.hasPermission("gui_creator.can_open_gui");
		if (show)
		{
			if (p.getActiveScreen() == ScreenType.GAME_SCREEN || (p.getActiveScreen() == ScreenType.CUSTOM_SCREEN && p.getMainScreen().getActivePopup() == null))
			{
				String s = p.getName();
				GUI gui;
				if (plugin.guis.containsKey(s)) gui =  plugin.guis.get(s);
				else
				{
					gui = new GUI(plugin, p);
    				plugin.guis.put(s, gui);
				}
				String[] params = Main.config.contains("open_screen") ? 
						GUI_control.get_string(Main.config.get("open_screen")).split(" ") : null;
				int open = params != null ? Integer.parseInt(params[0]) : -2;
				
				p.getMainScreen().attachPopupScreen(gui);
				if (open > -1) gui.jump_to_screen(open, params);
			}
			else if (p.getMainScreen().getActivePopup() instanceof GUI)				
			{
				for (Widget w : p.getMainScreen().getActivePopup().getAttachedWidgets())
				{
					if (w instanceof GUI_textfield && ((GUI_textfield)w).isFocus()) return;
				}
				p.getMainScreen().closePopup();
			}				
    	}
	}

	@Override
	public void keyReleased(KeyBindingEvent event) {}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{		
		if (!Main.make_skins) return;
		
		File f = plugin.create_skin(event.getPlayer().getName());
		if (f != null)
		{
			FileManager fm = SpoutManager.getFileManager();
			if (!fm.getCache(plugin).contains(f)) fm.addToCache(plugin, f);
		}
	}
}
