package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GUI_button extends GenericButton
{
	String command;
	GUI gui;
	String info;
	boolean stay_open;
	List<String> permissions;
	int cost;
	String text;
	
	@SuppressWarnings("unchecked")
	GUI_button(Map<String, Object> b, GUI gui)
	{
		this.gui = gui;		
		if (b.containsKey("temp_permissions"))
		{
			permissions = new ArrayList<String>();
			for (String s : (ArrayList<String>)b.get("temp_permissions"))
			{
				permissions.add(s.toLowerCase());
			}					
		}
		cost = b.containsKey("cost") ? (Integer)b.get("cost") : 0;
		String text_colour = b.containsKey("text_color") ? GUI_control.get_string(b.get("text_color")) : Main.command_button_color;
		setColor(GUI_control.get_colour(text_colour));
		String hover_colour = b.containsKey("hover_color") ? GUI_control.get_string(b.get("hover_color")) : Main.button_hover_color;
		setHoverColor(GUI_control.get_colour(hover_colour));
		text = b.containsKey("text") ? GUI_control.get_string(b.get("text")) : "button";
		setText(text);
		stay_open = b.containsKey("stay_open") ? (Boolean)b.get("stay_open") : false;
		command = b.containsKey("command") ? GUI_control.get_string(b.get("command")) : "";
		info = GUI_control.get_info(b.containsKey("info") ? GUI_control.get_string(b.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		if (this.permissions != null) for (String s : permissions) gui.me.addAttachment(gui.plugin, s, true, 50);
		String s = command;
		while (s.contains("^")) s = gui.replace_params(s, "", "");
		if (!stay_open) gui.close();
		if (cost != 0 && Main.economy != null) Main.economy.withdrawPlayer(gui.me.getName(), cost);
		String[] commands = s.split(";");
		for (String s2 : commands) gui.me.chat(s2);
	}
}