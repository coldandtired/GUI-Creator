package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.screen.SliderDragEvent;
import org.getspout.spoutapi.gui.GenericSlider;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class GUI_slider extends GenericSlider
{
	String name;
	String text;
	int max;
	String mode;
	List<Player> online_players;
	List<OfflinePlayer> offline_players;
	String value = "";
	GUI gui;
	List<String> values;
	String info;
	String original_text;
	
	String get_text(int i)
	{
		//text = "";
		if (mode.equalsIgnoreCase("normal"))
		{
			if (values == null)
			{
				value = Integer.toString(i);
				//gui.replace_text();
				gui.update_tooltips(name, value);
				return original_text + value;
			}
			else
			{
				if (i == max) 
				{
					value = "";
					gui.update_tooltips(name, value);
					return original_text + "nothing";
				}
				else
				{	
					value = values.get(i);
					gui.update_tooltips(name, value);
					return original_text + value;
				}
			}
		}
		if (mode.equalsIgnoreCase("online_players"))
		{
			if (i == max) 
			{
				value = "";
				gui.update_tooltips(name, value);
				return original_text + "no player";
			}
			else
			{	
				value = online_players.get(i).getName();
				gui.update_tooltips(name, value);
				return original_text + value;
			}
		}
		if (mode.equalsIgnoreCase("offline_players"))
		{
			if (i == max)
			{
				value = "";
				gui.update_tooltips(name, value);
				return original_text + "no player";
			}
			else
			{
				value = offline_players.get(i).getName();
				gui.update_tooltips(name, value);
				return original_text + value;
			}
		}
		return null;
	}
	
	public GUI_slider(Map<String, Object> s, GUI gui) 
	{
		this.gui = gui;
		this.label.setAuto(true);
		this.label.setAnchor(WidgetAnchor.CENTER_CENTER);
		this.label.setWidth(50);
		max = s.containsKey("max") ? (Integer)s.get("max") : 100;
		name = s.containsKey("name") ? GUI_control.get_string(s.get("name")) : "";
		String align = s.containsKey("align") ? GUI_control.get_string(s.get("align")) : "center";
		original_text = s.containsKey("text") ? GUI_control.get_string(s.get("text")) + " " : "";
		if (align.equalsIgnoreCase("left")) setAlign(WidgetAnchor.CENTER_LEFT);
		else if (align.equalsIgnoreCase("center")) setAlign(WidgetAnchor.CENTER_CENTER);
		else if (align.equalsIgnoreCase("right")) setAlign(WidgetAnchor.CENTER_RIGHT);
		mode = s.containsKey("mode") ? GUI_control.get_string(s.get("mode")) : "normal";
		Object o = s.containsKey("default") ? s.get("default") : null;
		int def = o instanceof Integer ? (Integer)o : 50; 
		if (mode.equalsIgnoreCase("online_players"))
		{
			online_players = Arrays.asList(Bukkit.getOnlinePlayers());
			max = online_players.size();
			def = online_players.indexOf(gui.me);
		}
		if (mode.equalsIgnoreCase("offline_players"))
		{
			offline_players = Arrays.asList(Bukkit.getServer().getOfflinePlayers());
			max = offline_players.size();
			def = offline_players.indexOf(gui.me);
		}
		if (mode.equalsIgnoreCase("normal"))
		{
			String temp = s.containsKey("values") ? ((String)s.get("values")).replaceAll(" ", "") : "";			
			if (!temp.equalsIgnoreCase(""))
			{
				values = new ArrayList<String>();
				for (String s2 : temp.split(",")) values.add(s2);
				max = values.size();	
				if (o instanceof String) def = values.indexOf((String)o);
				if (o instanceof Integer) def--;
			}
		}
		if (def > max) def = max;
		if (def < 0) def = 0;
		String colour = s.containsKey("text_color") ? GUI_control.get_string(s.get("text_color")) : Main.slider_color;
		setTextColor(GUI_control.get_colour(colour));
		setSliderPosition((float)def / max);
		//setText(get_text(def));
		text = get_text(def);
		info = GUI_control.get_info(s.containsKey("info") ? GUI_control.get_string(s.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onSliderDrag(SliderDragEvent event)
	{		
		int i = (int) (event.getNewPosition() * max);
		//setText(get_text(i));
		text = get_text(i);
		gui.replace_text();
	}
}
