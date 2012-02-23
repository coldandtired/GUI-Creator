package me.coldandtired.GUI_Creator;

import java.util.List;
import java.util.Map;

import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.gui.GenericButton;

public class GUI_URLbutton extends GenericButton
{
	GUI gui;
	List<String> permissions;
	String info;
	String link;
	String text;
	
	GUI_URLbutton(Map<String, Object> lb, GUI gui)
	{
		this.gui = gui;
		String text_colour = lb.containsKey("text_color") ? GUI_control.get_string(lb.get("text_color")) : Main.url_button_color;
		setColor(GUI_control.get_colour(text_colour));
		String hover_colour = lb.containsKey("hover_color") ? GUI_control.get_string(lb.get("hover_color")) : Main.button_hover_color;
		setHoverColor(GUI_control.get_colour(hover_colour));
		text = lb.containsKey("text") ? GUI_control.get_string(lb.get("text")) : "button";
		setText(text);
		link = lb.containsKey("link_to") ? (String)lb.get("link_to") : "www.bing.com";
		info = GUI_control.get_info(lb.containsKey("info") ? GUI_control.get_string(lb.get("info")) : "");
		if (!info.equalsIgnoreCase("")) setTooltip(info); else setTooltip(link);
	}
	
	public void onButtonClick(ButtonClickEvent event) 
	{
		
		//try 
		//{
		//	Desktop.getDesktop().browse(java.net.URI.create(link));
		//} 
		//catch (IOException e) {}
	}
}
