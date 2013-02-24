package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericRadioButton;

public class GuiRadioButton extends GenericRadioButton
{
	String selected_value;
	String align;
	Gui gui;
	boolean selected;
	String info;
	String text;
	
	public GuiRadioButton(Map<String, Object> rb, Gui gui) 
	{
		this.gui = gui;
		text = rb.containsKey("text") ? GuiControl.get_string(rb.get("text")) : "";
		setText(text);
		if (rb.containsKey("group")) setGroup((Integer)rb.get("group")); else setGroup(0);
		selected = rb.containsKey("selected") ? (Boolean)rb.get("selected") : false;
		String colour = rb.containsKey("text_color") ? GuiControl.get_string(rb.get("text_color")) : Main.getString("radio_button_color");
		setTextColor(GuiControl.get_colour(colour));
		selected_value = rb.containsKey("selected_value") ? GuiControl.get_string(rb.get("selected_value")) : "";
		align = rb.containsKey("align") ? GuiControl.get_string(rb.get("align")) : "left";
		info = GuiControl.get_info(rb.containsKey("info") ? GuiControl.get_string(rb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		gui.replace_text();
		//gui.update_tooltips("", "");
	}
}