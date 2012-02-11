package me.coldandtired.GUI_Creator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.RenderPriority;

public class GUI_control
{
	int row_span;
	int col;
	int row;
	int col_span;
	int x;
	int y;
	int width;
	int height;
	GUI_button cb;
	GUI_gradient gradient;
	GUI_label label;
	GUI_textfield tf;
	GUI_spacer spacer;
	GUI_texture texture;
	GUI_checkbox check_box;
	GUI_radiobutton radio_button;
	GUI_slider slider;	
	GUI_linkbutton link_button;
	GUI_combobox combo_box;
	
	List<String> control_names = Arrays.asList("BUTTON", "TEXT_BOX", "GRADIENT", "TEXT_LABEL", "SPACER", "TEXTURE", "CHECK_BOX",
			"RADIO_BUTTON", "SLIDER", "LINK_BUTTON", "COMBO_BOX");
	
	static String get_info(String info)
	{
		if (!info.equalsIgnoreCase(""))
		{
			String[] lines = info.split("&&");
			String temp = "";
			for (String s: lines)
			{
				temp =  temp + ChatColor.YELLOW + s + "\n";
			}
			return temp;
		}
		else return info;		
	}
	
	static String get_string(Object o)
	{
		String s = "";
		if (o instanceof Boolean) s = Boolean.toString((Boolean)o);
		else if (o instanceof Integer) s = Integer.toString((Integer)o);
		else if (o instanceof Float) s = Float.toString((Float)o);
		else if (o instanceof Double) s = Double.toString((Double)o);
		else if (o instanceof String) s = (String)o;
		return s;
	}
	
	static Color get_colour(String s)
	{
		String[] c = (s).split(",");
		switch (c.length)
		{
			case 3:
				return new Color(Short.parseShort(c[0].trim()), Short.parseShort(c[1].trim()), Short.parseShort(c[2].trim()));
			case 4:
				return new Color(Short.parseShort(c[0].trim()), Short.parseShort(c[1].trim()), Short.parseShort(c[2].trim()),
						Short.parseShort(c[3].trim()));
		}
		return null;
	}
	
	GUI_control(Map<String, Object> c, GUI gui)
	{
		col_span = c.containsKey("col_span") ? (Integer)c.get("col_span") : 1;
		row_span = c.containsKey("row_span") ? (Integer)c.get("row_span") : 1;
		col = c.containsKey("col") ? (Integer)c.get("col") : 1;
		row = c.containsKey("row") ? (Integer)c.get("row") : 1;
		x = c.containsKey("x") ? (Integer)c.get("x") : -1;
		y = c.containsKey("y") ? (Integer)c.get("y") : -1;
		width = c.containsKey("width") ? (Integer)c.get("width") : -1;
		height = c.containsKey("height") ? (Integer)c.get("height") : -1;
		RenderPriority rp = c.containsKey("priority") ? RenderPriority.getRenderPriorityFromId(((Integer)c.get("priority")) - 1) : RenderPriority.Normal;
		
		switch (control_names.indexOf(((String)c.get("type")).toUpperCase()))
		{
			case 0: // button
				cb = new GUI_button((Map<String, Object>)c, gui);
				cb.setPriority(rp);
				break;
			case 1: // textfield
				tf = new GUI_textfield((Map<String, Object>)c, gui);	
				tf.setPriority(rp);
				break;
			case 2: // gradient
				gradient = new GUI_gradient((Map<String, Object>)c);
				gradient.setPriority(rp);
				break;
			case 3: // label
				label = new GUI_label((Map<String, Object>)c);	
				label.setPriority(rp);
				break;
			case 4: // spacer
				spacer = new GUI_spacer((Map<String, Object>)c);
				spacer.setPriority(rp);
				break;
			case 5: // texture
				texture = new GUI_texture((Map<String, Object>)c, gui);
				//File f = new File(gui.plugin.getDataFolder() + File.separator + "test" + File.separator +  "test.png");
				//if (f.exists())
				//{
				//poutManager.getFileManager().addToCache(gui.plugin, f);
				//	Bukkit.getLogger().info("yes");
				//texture = new GenericTexture(f.getName());
				texture.setPriority(rp);
			//	}
				break;
			case 6: // checkbox
				check_box = new GUI_checkbox((Map<String, Object>)c, gui);
				check_box.setPriority(rp);
				break;
			case 7: // radiobutton
				radio_button = new GUI_radiobutton((Map<String, Object>)c, gui);
				radio_button.setPriority(rp);
				break;
			case 8: // slider
				slider = new GUI_slider((Map<String, Object>)c, gui);
				slider.setPriority(rp);
				break;		
			case 9: // link_button
				link_button = new GUI_linkbutton((Map<String, Object>)c, gui);
				link_button.setPriority(rp);
				break;	
			case 10: // combo_box
				combo_box = new GUI_combobox((Map<String, Object>)c, gui);
				combo_box.setPriority(rp);
				break;
			default: break;
		}
	}
}
