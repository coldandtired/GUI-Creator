package me.coldandtired.GUI_Creator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.RenderPriority;

public class GuiControl
{
	int row_span;
	int col;
	int row;
	int col_span;
	int x;
	int y;
	int width;
	int height;
	GuiButton cb;
	GuiGradient gradient;
	GuiLabel label;
	GuiTextField tf;
	GuiSpacer spacer;
	GuiTexture texture;
	GuiCheckBox check_box;
	GuiRadioButton radio_button;
	GuiSlider slider;	
	GuiLinkButton link_button;
	GuiComboBox combo_box;
	
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
	
	GuiControl(Map<String, Object> c, Gui gui)
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
				cb = new GuiButton((Map<String, Object>)c, gui);
				cb.setPriority(rp);
				break;
			case 1: // textfield
				tf = new GuiTextField((Map<String, Object>)c, gui);	
				tf.setPriority(rp);
				break;
			case 2: // gradient
				gradient = new GuiGradient((Map<String, Object>)c);
				gradient.setPriority(rp);
				break;
			case 3: // label
				label = new GuiLabel((Map<String, Object>)c);	
				label.setPriority(rp);
				break;
			case 4: // spacer
				spacer = new GuiSpacer((Map<String, Object>)c);
				spacer.setPriority(rp);
				break;
			case 5: // texture
				texture = new GuiTexture((Map<String, Object>)c, gui);
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
				check_box = new GuiCheckBox((Map<String, Object>)c, gui);
				check_box.setPriority(rp);
				break;
			case 7: // radiobutton
				radio_button = new GuiRadioButton((Map<String, Object>)c, gui);
				radio_button.setPriority(rp);
				break;
			case 8: // slider
				slider = new GuiSlider((Map<String, Object>)c, gui);
				slider.setPriority(rp);
				break;		
			case 9: // link_button
				link_button = new GuiLinkButton((Map<String, Object>)c, gui);
				link_button.setPriority(rp);
				break;	
			case 10: // combo_box
				combo_box = new GuiComboBox((Map<String, Object>)c, gui);
				combo_box.setPriority(rp);
				break;
			default: break;
		}
	}
}
