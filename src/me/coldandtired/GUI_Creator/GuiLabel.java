package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.gui.GenericLabel;

public class GuiLabel extends GenericLabel
{
	String align;
	String text;
	
	GuiLabel(Map<String,  Object> t)
	{
		String colour = t.containsKey("text_color") ? GuiControl.get_string(t.get("text_color")) : Main.getString("label_color");
		setTextColor(GuiControl.get_colour(colour));
		text = t.containsKey("text") ? GuiControl.get_string(t.get("text")) : "text_label";
		setText(text);
		shadow = t.containsKey("has_shadow") ? (Boolean)t.get("has_shadow") : true;
		align = t.containsKey("align") ? GuiControl.get_string(t.get("align")) : "center";
	}
}