package me.coldandtired.GUI_Creator;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI_confirmbutton extends GenericButton 
{
	String command;
	boolean use_console;
	
	GUI_confirmbutton(String command, boolean use_console)
	{
		this.command = command;
		this.use_console = use_console;
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		SpoutPlayer p = event.getPlayer();
		p.getMainScreen().closePopup();
		if (use_console)
		{
			if (command.startsWith("/")) command = command.substring(1);
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
		}
		else p.chat(command);
	}
}