package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GUI_closebutton extends GenericButton
{
	public void onButtonClick(ButtonClickEvent event) 
	{
		event.getPlayer().getMainScreen().closePopup();
	}
}
