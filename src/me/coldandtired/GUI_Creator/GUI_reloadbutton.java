package me.coldandtired.GUI_Creator;

import org.bukkit.ChatColor;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI_reloadbutton extends GenericButton
{
	Main plugin;
	
	GUI_reloadbutton(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		SpoutPlayer p = event.getPlayer();
		p.getMainScreen().closePopup();
		plugin.get_screens();
		plugin.log.info("[GUI Creator] Screen files reloaded!");
		p.sendMessage(ChatColor.GREEN + "[GUI Creator] Screen files reloaded!");
		plugin.guis.put(p.getName(), new GUI(plugin, p));
	}
}
