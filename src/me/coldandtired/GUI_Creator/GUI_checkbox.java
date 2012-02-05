package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericCheckBox;

public class GUI_checkbox extends GenericCheckBox
{
	String checked_value;
	String unchecked_value;
	String name;
	String align;
	GUI gui;
	String info;
	String text;
	
	GUI_checkbox(Map<String, Object> cb, GUI gui)
	{
		text = cb.containsKey("text") ? GUI_control.get_string(cb.get("text")) : "";
		setText(text);
		
		this.gui = gui;
		boolean checked = cb.containsKey("checked") ? (Boolean)cb.get("checked") : false;
		setChecked(checked);
		String colour = cb.containsKey("text_color") ? GUI_control.get_string(cb.get("text_color")) : Main.check_box_color;
		setTextColor(GUI_control.get_colour(colour));
		align = cb.containsKey("align") ? GUI_control.get_string(cb.get("align")) : "left";
		name = cb.containsKey("name") ? GUI_control.get_string(cb.get("name")) : "";
		checked_value = cb.containsKey("checked_value") ? GUI_control.get_string(cb.get("checked_value")) : "";
		unchecked_value = cb.containsKey("unchecked_value") ? GUI_control.get_string(cb.get("unchecked_value")) : "";
		info = GUI_control.get_info(cb.containsKey("info") ? GUI_control.get_string(cb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		//gui.update_tooltips("", "");
		gui.replace_text();
	}
}
