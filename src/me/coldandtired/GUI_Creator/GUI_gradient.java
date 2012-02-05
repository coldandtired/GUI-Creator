package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericGradient;

public class GUI_gradient extends GenericGradient
{
	String top_colour;
	String bottom_colour;
	
	GUI_gradient(Map<String, Object> g)
	{
		top_colour = g.containsKey("top_color") ? GUI_control.get_string(g.get("top_color")) : Main.gradient_top_color;
		bottom_colour = g.containsKey("bottom_color") ? GUI_control.get_string(g.get("bottom_color")) : Main.gradient_bottom_color;
		
		setTopColor(GUI_control.get_colour(top_colour));
		setBottomColor(GUI_control.get_colour(bottom_colour));
	}
}
