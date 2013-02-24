package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.GenericComboBox;

public class GuiComboBox extends GenericComboBox
{
	Gui gui;
	String info;
	String text;
	String name;
	String skin_texture;
	Map<String, String> items;
	
	@SuppressWarnings("unchecked")
	public GuiComboBox(Map<String, Object> cb, Gui gui) 
	{
		this.gui = gui;
		text = cb.containsKey("text") ? GuiControl.get_string(cb.get("text")) : "";
		setText(text);
		String text_colour = cb.containsKey("text_color") ? GuiControl.get_string(cb.get("text_color")) : Main.getString("combo_box_color");
		setColor(GuiControl.get_colour(text_colour));
		String hover_colour = cb.containsKey("hover_color") ? GuiControl.get_string(cb.get("hover_color")) : Main.getString("button_hover_color");
		setHoverColor(GuiControl.get_colour(hover_colour));
		name = cb.containsKey("name") ? GuiControl.get_string(cb.get("name")) : "";
		skin_texture = cb.containsKey("skin_texture") ? GuiControl.get_string(cb.get("skin_texture")) : "";
		info = GuiControl.get_info(cb.containsKey("info") ? GuiControl.get_string(cb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
		String mode = cb.containsKey("mode") ? GuiControl.get_string(cb.get("mode")) : "normal";
		
		if (mode.equalsIgnoreCase("normal") && cb.containsKey("items"))
		{
			List<String> temp = new ArrayList<String>();
			items = new HashMap<String, String>();
			for (Map<String, Object> i : (ArrayList<Map<String, Object>>)cb.get("items"))
			{
				String s = "" + i.get("text");
				temp.add(s);
				Object o = i.get("selected_value");
				String s2;
				if (o == null) s2 = s; else s2 = GuiControl.get_string(o);
				items.put(s, s2);
			}
			this.setItems(temp);
		}
		
		else if (mode.equalsIgnoreCase("online_players"))
		{
			items = new HashMap<String, String>();
			List<String> temp = new ArrayList<String>();
			for (Player p : Arrays.asList(Bukkit.getOnlinePlayers()))
			{
				String s = p.getName();
				temp.add(s);
				items.put(s, s);
			}
			temp.add("No player");
			items.put("No player", "");
			this.setItems(temp);
		}
		else if (mode.equalsIgnoreCase("offline_players"))
		{
			items = new HashMap<String, String>();
			List<String> temp = new ArrayList<String>();
			for (OfflinePlayer p : Arrays.asList(Bukkit.getServer().getOfflinePlayers()))
			{
				String s = p.getName();
				temp.add(s);
				items.put(s, s);
			}
			temp.add("No player");
			items.put("No player", "");
			this.setItems(temp);
		}	
		else if (mode.equalsIgnoreCase("banned_players"))
		{
			items = new HashMap<String, String>();
			List<String> temp = new ArrayList<String>();
			for (OfflinePlayer p : Bukkit.getServer().getBannedPlayers())
			{
				String s = p.getName();
				temp.add(s);
				items.put(s, s);
			}
			temp.add("No player");
			items.put("No player", "");
			this.setItems(temp);
		}
		else if (mode.equalsIgnoreCase("all_items"))
		{
			items = new HashMap<String, String>();
			List<String> temp = new ArrayList<String>();
			for (Material m : Material.values())
			{
				String s = m.name().toLowerCase();
				temp.add(s);
				items.put(s, s);
			}
			Collections.sort(temp);
			temp.add("No item");
			items.put("No item", "");
			this.setItems(temp);
		}
	}
	
	public void onSelectionChanged(int i, String text) 
	{
		if (i > -1)
		{
			setText(text);
			if (!skin_texture.equalsIgnoreCase("")) gui.update_texture(skin_texture, items.get(text));
			gui.replace_text();
		}
	}
}