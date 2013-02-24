package me.coldandtired.GUI_Creator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GuiReloadButton extends GenericButton
{
	Main plugin;
	
	GuiReloadButton(Main plugin)
	{
		this.plugin = plugin;
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		SpoutPlayer p = event.getPlayer();
		p.getMainScreen().closePopup();
		plugin.reload();
		Bukkit.getLogger().info(p.getName() + "Reloaded the files!");
		p.sendMessage(ChatColor.GREEN + "[GUI Creator] Files reloaded!");
	}
}