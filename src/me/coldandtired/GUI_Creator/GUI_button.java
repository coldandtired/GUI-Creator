package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class GUI_button extends GenericButton
{
	String command;
	GUI gui;
	String info;
	boolean stay_open;
	boolean use_console;
	List<String> permissions;
	int cost;
	String text;
	boolean confirm_command;
	boolean hide_command;
	
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

		use_console = b.containsKey("use_console") ? (Boolean)b.get("use_console") : false;
		cost = b.containsKey("cost") ? (Integer)b.get("cost") : 0;
		String text_colour = b.containsKey("text_color") ? GUI_control.get_string(b.get("text_color")) : Main.command_button_color;
		setColor(GUI_control.get_colour(text_colour));
		String hover_colour = b.containsKey("hover_color") ? GUI_control.get_string(b.get("hover_color")) : Main.button_hover_color;
		setHoverColor(GUI_control.get_colour(hover_colour));
		text = b.containsKey("text") ? GUI_control.get_string(b.get("text")) : "button";
		setText(text);
		stay_open = b.containsKey("stay_open") ? (Boolean)b.get("stay_open") : false;
		confirm_command = b.containsKey("confirm_command") ? (Boolean)b.get("confirm_command") : false;
		hide_command = b.containsKey("hide_command") ? (Boolean)b.get("hide_command") : false;
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
		for (String s2 : commands)
		{
			if (!confirm_command)
			{
				if (use_console)
				{
					if (s2.startsWith("/")) s2 = s2.substring(1);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s2);
				}
				else gui.me.chat(s2);
			}
			else
			{
				GUI_confirmationbox cb = new GUI_confirmationbox(gui.plugin, s2, hide_command, use_console);
				cb.setHeight(80).setWidth(227).setX(100).setY(80);
				cb.setFixed(true);
				cb.setAnchor(WidgetAnchor.SCALE);
				cb.setBgVisible(false);
				gui.me.getMainScreen().attachPopupScreen(cb);
			 }
		}
	}
}