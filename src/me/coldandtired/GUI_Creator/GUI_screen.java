package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.permissions.Permission;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;

public class GUI_screen extends GenericButton
{
	ArrayList<GUI_control> controls;	
	GUI gui;
	int row_height;
	int num_columns;
	Color text_colour;
	Color background_colour;
	int screen_height;
	int screen_width;
	int index;
	int id;
	boolean hidden;
	String show_permission;
	String hide_permission;
	int show_funds;
	String text;
	String info;
	
	@SuppressWarnings("unchecked")
	public GUI_screen(Map<String, Object> sb, GUI gui, int index) 
	{
		this.gui = gui;		
		this.index = index;
		show_permission = sb.containsKey("show_permission") ? ((String)sb.get("show_permission")).toLowerCase() : "";
		hide_permission = sb.containsKey("hide_permission") ? ((String)sb.get("hide_permission")).toLowerCase() : "";
		show_funds = sb.containsKey("show_funds") ? (Integer)sb.get("show_funds") : 0;
		row_height = sb.containsKey("row_height") ? (Integer)sb.get("row_height") : 18;
		hidden = sb.containsKey("hidden") ? (Boolean)sb.get("hidden") : false;
		id = sb.containsKey("id") ? (Integer)sb.get("id") : -1;
		num_columns = sb.containsKey("num_columns") ? (Integer)sb.get("num_columns") : 4;
		screen_height = sb.containsKey("screen_height") ? (Integer)sb.get("screen_height") : 98;
		screen_width = sb.containsKey("screen_width") ? (Integer)sb.get("screen_width") : 98;
		text_colour = GUI_control.get_colour(sb.containsKey("text_color") ? GUI_control.get_string(sb.get("text_color")) : Main.screen_button_color);
		String hover_colour = sb.containsKey("button_hover_color") ? GUI_control.get_string(sb.get("button_hover_color")) : Main.button_hover_color;
		setHoverColor(GUI_control.get_colour(hover_colour));
		background_colour = GUI_control.get_colour(sb.containsKey("background_color") ? GUI_control.get_string(sb.get("background_color")) : Main.background_color);
		setColor(text_colour);
		
		text = sb.containsKey("text") ? GUI_control.get_string(sb.get("text")) : "";
		setText(GUI_control.get_string(text));
	//	if (sb.containsKey("text")) setText(GUI_control.get_string(sb.get("text")));
		
		info = GUI_control.get_info(sb.containsKey("info") ? GUI_control.get_string(sb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
		
		if (sb.containsKey("controls"))
		{
			controls = new ArrayList<GUI_control>();
			for (Map<String, Object> g : (ArrayList<Map<String, Object>>)sb.get("controls")) 
			{
				boolean show = true;
				if (g.containsKey("show_funds"))
				{
					if (Main.economy.getBalance(gui.me.getName()) >= (Integer)g.get("show_funds")) show = true;
					else show = false;
				}
				if (g.containsKey("show_permission"))
				{
					Permission p = new Permission(((String)g.get("show_permission")).toLowerCase());
					if (gui.me.hasPermission(p)) show = true; else show = false;
				}
				if (g.containsKey("hide_permission"))
				{
					Permission p = new Permission(((String)g.get("hide_permission")).toLowerCase());
					if (gui.me.hasPermission(p)) show = false; else show = true;
				}
				if (show) controls.add(new GUI_control(g, gui)); 
			}
		}
	}
	        
	public void onButtonClick(ButtonClickEvent event) 
	{						
		gui.fill_command_area(gui.screens.indexOf(this), false);
	}
}
