package me.coldandtired.GUI_Creator;

import java.util.Map;

import org.getspout.spoutapi.event.screen.TextFieldChangeEvent;
import org.getspout.spoutapi.gui.GenericTextField;

public class GuiTextField extends GenericTextField
{
	String name;
	Gui gui;
	String info;
	String ph;
	String skin_texture;
	
	GuiTextField(Map<String, Object> t, Gui gui)
	{
		this.gui = gui;
		skin_texture = t.containsKey("skin_texture") ? GuiControl.get_string(t.get("skin_texture")) : "";
		name = t.containsKey("name") ? GuiControl.get_string(t.get("name")) : "";
		setMaximumCharacters(100);
		ph = t.containsKey("text") ? GuiControl.get_string(t.get("text")) : "";
		placeholder = ph;
		String inner_color = t.containsKey("inner_color") ? GuiControl.get_string(t.get("inner_color")) :Main.getString("text_box_inner_color");
		fieldColor = GuiControl.get_colour(inner_color);		
		String outer_color = t.containsKey("outer_color") ? GuiControl.get_string(t.get("outer_color")) : Main.getString("text_box_outer_color");
		borderColor = GuiControl.get_colour(outer_color);
		info = GuiControl.get_info(t.containsKey("info") ? GuiControl.get_string(t.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
		if (t.containsKey("password_box")) setPasswordField((Boolean)t.get("password_box"));
	}
	
	@Override
	public void onTextFieldChange(TextFieldChangeEvent event)
	{
		gui.replace_text();
		String s = event.getNewText();
		gui.update_tooltips(name, s);
		if (!skin_texture.equalsIgnoreCase("")) gui.update_texture(skin_texture, s);
	}
}