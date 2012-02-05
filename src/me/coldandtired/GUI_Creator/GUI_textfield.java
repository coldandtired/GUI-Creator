package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class GUI_textfield extends GenericTextField
{
	String name;
	GUI gui;
	String info;
	String ph;
	
	GUI_textfield(Map<String, Object> t, GUI gui)
	{
		this.gui = gui;
		name = t.containsKey("name") ? GUI_control.get_string(t.get("name")) : "";
		setMaximumCharacters(100);
		ph = t.containsKey("text") ? GUI_control.get_string(t.get("text")) : "";
		placeholder = ph;
		String inner_color = t.containsKey("inner_color") ? GUI_control.get_string(t.get("inner_color")) : Main.text_box_inner_color;
		fieldColor = GUI_control.get_colour(inner_color);		
		String outer_color = t.containsKey("outer_color") ? GUI_control.get_string(t.get("outer_color")) : Main.text_box_outer_color;
		borderColor = GUI_control.get_colour(outer_color);
		info = GUI_control.get_info(t.containsKey("info") ? GUI_control.get_string(t.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	@Override
	public void onTextFieldChange(TextFieldChangeEvent event)
	{
		gui.replace_text();
		gui.update_tooltips(name, event.getNewText());
	}
}
