package me.coldandtired.GUI_Creator;

import java.io.File;
import java.util.Map;

import org.getspout.spoutapi.gui.GenericTexture;

public class GUI_texture extends GenericTexture
{	
	GUI_texture(Map<String, Object> t, GUI gui)
	{
		String mode = t.containsKey("mode") ? (String)t.get("mode") : "normal";
		if (mode.equalsIgnoreCase("skin")) setUrl(gui.plugin.getDataFolder() + File.separator + "Skins" + File.separator + gui.me.getName() + ".png");
	
		if (t.containsKey("url")) setUrl(GUI_control.get_string(t.get("url")));
	}
}
