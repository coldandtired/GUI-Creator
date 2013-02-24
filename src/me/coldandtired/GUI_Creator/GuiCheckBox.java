package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericCheckBox;

public class GuiCheckBox extends GenericCheckBox
{
	String checked_value;
	String unchecked_value;
	String name;
	String align;
	Gui gui;
	String info;
	String text;
	
	GuiCheckBox(Map<String, Object> cb, Gui gui)
	{
		text = cb.containsKey("text") ? GuiControl.get_string(cb.get("text")) : "";
		setText(text);
		
		this.gui = gui;
		boolean checked = cb.containsKey("checked") ? (Boolean)cb.get("checked") : false;
		setChecked(checked);
		String colour = cb.containsKey("text_color") ? GuiControl.get_string(cb.get("text_color")) : Main.getString("check_box_color");
		setTextColor(GuiControl.get_colour(colour));
		align = cb.containsKey("align") ? GuiControl.get_string(cb.get("align")) : "left";
		name = cb.containsKey("name") ? GuiControl.get_string(cb.get("name")) : "";
		checked_value = cb.containsKey("checked_value") ? GuiControl.get_string(cb.get("checked_value")) : "";
		unchecked_value = cb.containsKey("unchecked_value") ? GuiControl.get_string(cb.get("unchecked_value")) : "";
		info = GuiControl.get_info(cb.containsKey("info") ? GuiControl.get_string(cb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		//gui.update_tooltips("", "");
		gui.replace_text();
	}
}
