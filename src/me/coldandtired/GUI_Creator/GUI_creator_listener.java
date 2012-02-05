package me.coldandtired.GUI_Creator;

import org.bukkit.permissions.Permission;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI_creator_listener implements BindingExecutionDelegate
{
	Main plugin;
	
	GUI_creator_listener(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public void keyPressed(KeyBindingEvent event) 
	{
		if (!event.getPlayer().isSpoutCraftEnabled() || Main.screen_files == null) return;
    	SpoutPlayer p = event.getPlayer();
    	Permission perm = new Permission("gui_creator.can_open_gui");
    	if (p.hasPermission(perm)) plugin.guis.put(p.getName(), new GUI(plugin, p));
	}

	@Override
	public void keyReleased(KeyBindingEvent event) {}

}
