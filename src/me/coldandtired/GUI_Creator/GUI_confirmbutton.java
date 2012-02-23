package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI_confirmbutton extends GenericButton 
{
	String command;
	
	GUI_confirmbutton(String command)
	{
		this.command = command;
	}
	
	public void onButtonClick(ButtonClickEvent event)
	{
		SpoutPlayer p = event.getPlayer();
		p.getMainScreen().closePopup();
		p.chat(command);
	}
}