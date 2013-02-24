package me.coldandtired.GUI_Creator;

import java.io.File;
import java.util.Map;

import org.getspout.spoutapi.gui.GenericTexture;

public class GuiTexture extends GenericTexture
{	
	String name;
	String link;
	
	GuiTexture(Map<String, Object> t, Gui gui)
	{
		name = t.containsKey("name") ? (String)t.get("name") : "";
		String mode = t.containsKey("mode") ? (String)t.get("mode") : "normal";

		if (mode.equalsIgnoreCase("skin")) setUrl(gui.plugin.getDataFolder() + File.separator + "Skins" + File.separator + gui.me.getName() + ".png");
	
		else if (t.containsKey("url"))
		{
			link = GuiControl.get_string(t.get("url"));
			setUrl(link);
		}
	}
	
	String getLink()
	{
		return link;
	}
}
