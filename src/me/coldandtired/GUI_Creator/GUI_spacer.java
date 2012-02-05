package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericGradient;

public class GUI_spacer extends GenericGradient
{
	String colour;
	
	GUI_spacer(Map<String, Object> s)
	{
		colour = s.containsKey("color") ? GUI_control.get_string(s.get("color")) : Main.spacer_color;
		
		setTopColor(GUI_control.get_colour(colour));
		setBottomColor(GUI_control.get_colour(colour));
	}
}
