package me.coldandtired.GUI_Creator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.gui.GenericComboBox;

public class GUI_combobox extends GenericComboBox
{
	GUI gui;
	String info;
	String text;
	String name;
	Map<String, String> items;
	
	@SuppressWarnings("unchecked")
	public GUI_combobox(Map<String, Object> cb, GUI gui) 
	{
		this.gui = gui;
		text = cb.containsKey("text") ? GUI_control.get_string(cb.get("text")) : "";
		setText(text);
		
		name = cb.containsKey("name") ? GUI_control.get_string(cb.get("name")) : "";
		info = GUI_control.get_info(cb.containsKey("info") ? GUI_control.get_string(cb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
		if (cb.containsKey("items"))
		{
			List<String> temp = new ArrayList<String>();
			items = new HashMap<String, String>();
			for (Map<String, Object> i : (ArrayList<Map<String, Object>>)cb.get("items"))
			{
				String s = (String)i.get("text");
				temp.add(s);
				Object o = i.get("selected_value");
				String s2;
				if (o == null) s2 = s; else s2 = GUI_control.get_string(o);
				items.put(s, s2);
			}
			this.setItems(temp);
		}
	}
	
	public void onSelectionChanged(int i, String text) 
	{
		if (i > -1)
		{
			setText(text);
			gui.replace_text();
		}
	}
}
