package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GuiPaginateButton extends GenericButton
{
	Gui gui;
	int index;
	
	public GuiPaginateButton(String text, int index, Gui gui) 
	{
		super(text);
		this.gui = gui;
		this.index = index;
	}

	public void onButtonClick(ButtonClickEvent event) 
	{
		gui.fill_screen_area(index);
	}
}
