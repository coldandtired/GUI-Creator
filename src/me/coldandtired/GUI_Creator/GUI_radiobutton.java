package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericRadioButton;

public class GUI_radiobutton extends GenericRadioButton
{
	String selected_value;
	String align;
	GUI gui;
	boolean selected;
	String info;
	String text;
	
	public GUI_radiobutton(Map<String, Object> rb, GUI gui) 
	{
		this.gui = gui;
		text = rb.containsKey("text") ? GUI_control.get_string(rb.get("text")) : "";
		setText(text);
		if (rb.containsKey("group")) setGroup((Integer)rb.get("group")); else setGroup(0);
		selected = rb.containsKey("selected") ? (Boolean)rb.get("selected") : false;
		String colour = rb.containsKey("text_color") ? GUI_control.get_string(rb.get("text_color")) : Main.radio_button_color;
		setTextColor(GUI_control.get_colour(colour));
		selected_value = rb.containsKey("selected_value") ? GUI_control.get_string(rb.get("selected_value")) : "";
		align = rb.containsKey("align") ? GUI_control.get_string(rb.get("align")) : "left";
		info = GUI_control.get_info(rb.containsKey("info") ? GUI_control.get_string(rb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		gui.replace_text();
		//gui.update_tooltips("", "");
	}
}
