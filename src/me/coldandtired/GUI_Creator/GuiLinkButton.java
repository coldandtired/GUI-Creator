package me.coldandtired.GUI_Creator;

import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GuiLinkButton extends GenericButton
{
	Gui gui;
	List<String> permissions;
	String info;
	int link;
	String text;
	String[] link_params;

 	GuiLinkButton(Map<String, Object> lb, Gui gui)
	{
		this.gui = gui;
		String text_colour = lb.containsKey("text_color") ? GuiControl.get_string(lb.get("text_color")) : Main.getString("link_button_color");
		setColor(GuiControl.get_colour(text_colour));
		String hover_colour = lb.containsKey("hover_color") ? GuiControl.get_string(lb.get("hover_color")) : Main.getString("button_hover_color");
		setHoverColor(GuiControl.get_colour(hover_colour));
		text = lb.containsKey("text") ? GuiControl.get_string(lb.get("text")) : "button";
		setText(text);
		link_params = lb.containsKey("link_to") ? GuiControl.get_string(lb.get("link_to")).split(" ") : null;
		link = link_params != null ? Integer.parseInt(link_params[0]) : -2;
		info = GuiControl.get_info(lb.containsKey("info") ? GuiControl.get_string(lb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info);
	}

	public void onButtonClick(ButtonClickEvent event) 
	{
		gui.jump_to_screen(link, link_params);
	}
}