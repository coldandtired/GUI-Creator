package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericGradient;

public class GuiGradient extends GenericGradient
{
	String top_colour;
	String bottom_colour;
	
	GuiGradient(Map<String, Object> g)
	{
		top_colour = g.containsKey("top_color") ? GuiControl.get_string(g.get("top_color")) : Main.getString("gradient_top_color");
		bottom_colour = g.containsKey("bottom_color") ? GuiControl.get_string(g.get("bottom_color")) : Main.getString("gradient_bottom_color");
		
		setTopColor(GuiControl.get_colour(top_colour));
		setBottomColor(GuiControl.get_colour(bottom_colour));
	}
}