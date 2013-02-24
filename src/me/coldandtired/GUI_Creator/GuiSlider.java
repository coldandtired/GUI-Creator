package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.screen.SliderDragEvent;
import org.getspout.spoutapi.gui.GenericSlider;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class GuiSlider extends GenericSlider
{
	String name;
	String text;
	int max;
	String mode;
	List<Player> online_players;
	List<OfflinePlayer> offline_players;
	List<String> items;
	String value = "";
	Gui gui;
	List<String> values;
	String info;
	String original_text;
	String skin_texture;
	
	String get_value(int i)
	{
		if (mode.equalsIgnoreCase("normal"))
		{
			if (values == null)
			{
				value = Integer.toString(i);
				//gui.replace_text();
				gui.update_tooltips(name, value);
				return value;
			}
			else
			{
				if (i == max) 
				{
					value = "";
					gui.update_tooltips(name, value);
					return "nothing";
				}
				else
				{	
					value = values.get(i);
					gui.update_tooltips(name, value);
					return value;
				}
			}
		}
		else if (mode.equalsIgnoreCase("online_players"))
		{
			if (i == max) 
			{
				value = "";
				gui.update_tooltips(name, value);
				return "no player";
			}
			else
			{	
				value = online_players.get(i).getName();
				gui.update_tooltips(name, value);
				return value;
			}
		}
		else if (mode.equalsIgnoreCase("offline_players") || mode.equalsIgnoreCase("banned_players"))
		{
			if (i == max)
			{
				value = "";
				gui.update_tooltips(name, value);
				return "no player";
			}
			else
			{
				value = offline_players.get(i).getName();
				gui.update_tooltips(name, value);
				return value;
			}
		}
		else if (mode.equalsIgnoreCase("all_items"))
		{
			if (i == max) 
			{
				value = "";
				gui.update_tooltips(name, value);
				return "no item";
			}
			else
			{	
				value = items.get(i);
				gui.update_tooltips(name, value);
				return value;
			}
		}
		return "";
	}
	
	String get_full_text(int i)
	{
		return original_text + get_value(i);
	}
	
	public GuiSlider(Map<String, Object> s, Gui gui) 
	{
		this.gui = gui;
		this.label.setAuto(true);
		this.label.setAnchor(WidgetAnchor.CENTER_CENTER);
		this.label.setWidth(50);
		max = s.containsKey("max") ? (Integer)s.get("max") : 100;
		name = s.containsKey("name") ? GuiControl.get_string(s.get("name")) : "";
		String align = s.containsKey("align") ? GuiControl.get_string(s.get("align")) : "center";
		original_text = s.containsKey("text") ? GuiControl.get_string(s.get("text")) + " " : "";
		skin_texture = s.containsKey("skin_texture") ? GuiControl.get_string(s.get("skin_texture")) : "";
		if (align.equalsIgnoreCase("left")) setAlign(WidgetAnchor.CENTER_LEFT);		
		else if (align.equalsIgnoreCase("center")) setAlign(WidgetAnchor.CENTER_CENTER);
		else if (align.equalsIgnoreCase("right")) setAlign(WidgetAnchor.CENTER_RIGHT);
		mode = s.containsKey("mode") ? GuiControl.get_string(s.get("mode")) : "normal";
		Object o = s.containsKey("default") ? s.get("default") : null;
		int def = o instanceof Integer ? (Integer)o : 50;
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
		else if (mode.equalsIgnoreCase("online_players"))
		{
			online_players = Arrays.asList(Bukkit.getOnlinePlayers());
			max = online_players.size();
			def = online_players.indexOf(gui.me);
		}
		else if (mode.equalsIgnoreCase("offline_players"))
		{
			offline_players = Arrays.asList(Bukkit.getServer().getOfflinePlayers());
			max = offline_players.size();
			def = 0;
		}
		else if (mode.equalsIgnoreCase("banned_players"))
		{
			offline_players = new ArrayList<OfflinePlayer>(Bukkit.getServer().getBannedPlayers());
			max = offline_players.size();
			def = 0;
		}
		else if (mode.equalsIgnoreCase("all_items"))
		{
			items = new ArrayList<String>();
			for (Material m : Material.values()) items.add(m.name().toLowerCase());
			Collections.sort(items);
			max = items.size();
			def = 0;
		}
		
		if (def > max) def = max;
		if (def < 0) def = 0;
		String colour = s.containsKey("text_color") ? GuiControl.get_string(s.get("text_color")) : Main.getString("slider_color");
		setTextColor(GuiControl.get_colour(colour));
		setSliderPosition((float)def / max);
		text = get_full_text(def);		
		info = GuiControl.get_info(s.containsKey("info") ? GuiControl.get_string(s.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}
	
	public void onSliderDrag(SliderDragEvent event)
	{		
		int i = (int) (event.getNewPosition() * max);
		//setText(get_text(i));		
		text = get_full_text(i);
		if (!skin_texture.equalsIgnoreCase("") && !mode.equalsIgnoreCase("normal")) gui.update_texture(skin_texture, get_value(i));
		gui.replace_text();
	}
}