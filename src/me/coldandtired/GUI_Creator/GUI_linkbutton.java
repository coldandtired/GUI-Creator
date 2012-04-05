package me.coldandtired.GUI_Creator;

import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GUI_linkbutton extends GenericButton
{
	GUI gui;
	List<String> permissions;
	String info;
	int link;
	String text;
	String[] link_params;
	
 	GUI_linkbutton(Map<String, Object> lb, GUI gui)
	{
		this.gui = gui;
		String text_colour = lb.containsKey("text_color") ? GUI_control.get_string(lb.get("text_color")) : Main.link_button_color;
		setColor(GUI_control.get_colour(text_colour));
		String hover_colour = lb.containsKey("hover_color") ? GUI_control.get_string(lb.get("hover_color")) : Main.button_hover_color;
		setHoverColor(GUI_control.get_colour(hover_colour));
		text = lb.containsKey("text") ? GUI_control.get_string(lb.get("text")) : "button";
		setText(text);
		link_params = lb.containsKey("link_to") ? GUI_control.get_string(lb.get("link_to")).split(" ") : null;
		link = link_params != null ? Integer.parseInt(link_params[0]) : -2;
		info = GUI_control.get_info(lb.containsKey("info") ? GUI_control.get_string(lb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		gui.jump_to_screen(link, link_params);
	}
}
