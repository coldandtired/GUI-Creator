package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericLabel;

public class GUI_label extends GenericLabel
{
	String align;
	String text;
	
	GUI_label(Map<String,  Object> t)
	{
		String colour = t.containsKey("text_color") ? GUI_control.get_string(t.get("text_color")) : Main.label_color;
		setTextColor(GUI_control.get_colour(colour));
		text = t.containsKey("text") ? GUI_control.get_string(t.get("text")) : "text_label";
		setText(text);
		shadow = t.containsKey("has_shadow") ? (Boolean)t.get("has_shadow") : true;
		align = t.containsKey("align") ? GUI_control.get_string(t.get("align")) : "center";
	}
}
