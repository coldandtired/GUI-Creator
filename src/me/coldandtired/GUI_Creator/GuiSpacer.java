package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericGradient;

public class GuiSpacer extends GenericGradient
{
	String colour;
	
	GuiSpacer(Map<String, Object> s)
	{
		colour = s.containsKey("color") ? GuiControl.get_string(s.get("color")) : Main.getString("spacer_color");
		
		setTopColor(GuiControl.get_colour(colour));
		setBottomColor(GuiControl.get_colour(colour));
	}
}