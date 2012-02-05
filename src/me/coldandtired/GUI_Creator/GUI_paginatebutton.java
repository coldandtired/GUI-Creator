package me.coldandtired.GUI_Creator;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GUI_paginatebutton extends GenericButton
{
	GUI gui;
	int index;
	
	public GUI_paginatebutton(String text, int index, GUI gui) 
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
