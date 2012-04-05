package me.coldandtired.GUI_Creator;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import org.bukkit.permissions.Permission;
import org.getspout.spoutapi.gui.ContainerType;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.Gradient;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class GUI  extends GenericPopup
{
	String label;
	String info;
	ArrayList<GUI_screen> screens = new ArrayList<GUI_screen>();
	ArrayList<GUI_screen> hidden_screens = new ArrayList<GUI_screen>();
	int col_width;
	Main plugin;
	SpoutPlayer me;
	GUI_screen sb;
	String[] params;
	
	String replace_params(String s, String n, String text)
	{
		int start = s.indexOf("^");
		int end = s.indexOf("^", start + 1);
		String sub = s.substring(start, end + 1);
		String name = s.substring(start + 1, end);
		int group = -1;
		try
		{
			group = Integer.parseInt(name);
		}
		catch (Exception e){}
		
		String replacement = "";
		if (name.equalsIgnoreCase(n)) replacement = text;
		else
		for (GUI_control g : sb.controls)
		{
			if (g.check_box != null && g.check_box.name.equalsIgnoreCase(name))
			{
				replacement = g.check_box.isChecked() ? g.check_box.checked_value : g.check_box.unchecked_value;
				break;
			}
			else if (g.tf != null && g.tf.name.equalsIgnoreCase(name))
			{
				replacement = g.tf.getText();
				break;
			}
			else if (g.radio_button != null && g.radio_button.getGroup() == group && g.radio_button.isSelected())
			{
				replacement = g.radio_button.selected_value;
				break;
			}
			else if (g.slider != null && g.slider.name.equalsIgnoreCase(name))
			{
				replacement = g.slider.value;
				break;
			}
			else if (g.combo_box != null && g.combo_box.name.equalsIgnoreCase(name))
			{
				if (g.combo_box.items != null && g.combo_box.getSelectedRow() > -1)
				replacement = g.combo_box.items.get(g.combo_box.getSelectedItem());
				break;
			}
		}
		if (name.equalsIgnoreCase("gc_player_name")) replacement = me.getName();
		if (name.equalsIgnoreCase("gc_server_name")) replacement = me.getServer().getName();
		if (name.equalsIgnoreCase("gc_world_name")) replacement = me.getWorld().getName();
		if (name.equalsIgnoreCase("gc_online_player_count")) replacement = Integer.toString(me.getServer().getOnlinePlayers().length);
		if (name.equalsIgnoreCase("gc_offline_player_count")) replacement = Integer.toString(me.getServer().getOfflinePlayers().length);
		if (name.equalsIgnoreCase("gc_world_player_count")) replacement = Integer.toString(me.getWorld().getPlayers().size());

		if (Main.economy != null && name.equalsIgnoreCase("gc_player_money"))
		{
			DecimalFormat f = new DecimalFormat("#,###.##");
			replacement = f.format(Main.economy.getBalance(me.getName()));
		}
		
		if (params != null && params.length > 0)
		{
			if (name.startsWith("gc_param_"))
			{
				String s2 = name.substring(name.length() -1);
				int i = Integer.parseInt(s2);
				if (params.length >= i) replacement = params[i - 1];
			}
		}
		
		s = s.replace(sub, replacement);
		s = s.trim();
		s = s.replaceAll("  ", " ");
		return s;
	}
	
	int get_align(String align, int x, int w, int size)
	{
		if (align.equalsIgnoreCase("left")) return x;
		else if (align.equalsIgnoreCase("center")) return (int) (x + (( w - size) / 2));
		else if (align.equalsIgnoreCase("right")) return (int) (x + (w - size));
		return x;
	}
	
	void update_tooltips(String name, String text)
	{
		for (Widget w : this.getAttachedWidgets())
		{
			if (w instanceof GUI_button)
			{
				GUI_button bt = (GUI_button)w; 
				String s = "";
				
				if (!bt.hide_command)
				{
					s = bt.command;
					if (s.contains("^")) while (s.contains("^"))  s = replace_params(s, name, text);
				}
				
				if (!bt.info.equalsIgnoreCase(""))
				{
					String s2 = bt.info;
					while (s2.contains("^")) s2 = replace_params(s2, "", "");
					s = s2 + "\n" + s;
				}
				
				if (s.contains(";"))
				{					
					String[] lines = s.split(";");
					s = "";
					for (String s2 : lines)	s = s + s2 + "\n";
				}
				if (!s.equalsIgnoreCase("")) bt.setTooltip(s);
			}
		}
	}	
	
	void replace_text()
	{
		for (Widget w : this.getAttachedWidgets())
		{
			if (w instanceof GUI_screen)
			{
				GUI_screen c = (GUI_screen)w; 
				String s = c.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setText(s);
				s = c.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setTooltip(s);
			}
			if (w instanceof GUI_button)
			{
				GUI_button bt = (GUI_button)w; 
				String s = bt.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				bt.setText(s);
				s = bt.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				bt.setTooltip(s);
			}
			if (w instanceof GUI_label)
			{
				GUI_label lb = (GUI_label)w; 
				String s = lb.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				lb.setText(s);
			}
			if (w instanceof GUI_checkbox)
			{
				GUI_checkbox c = (GUI_checkbox)w; 
				String s = c.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setText(s);
				s = c.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setTooltip(s);
			}
			if (w instanceof GUI_linkbutton)
			{
				GUI_linkbutton c = (GUI_linkbutton)w; 
				String s = c.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setText(s);
				s = c.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setTooltip(s);
			}
			if (w instanceof GUI_radiobutton)
			{
				GUI_radiobutton c = (GUI_radiobutton)w; 
				String s = c.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setText(s);
				s = c.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setTooltip(s);
			}
			if (w instanceof GUI_slider)
			{
				GUI_slider c = (GUI_slider)w; 
				String s = c.text;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setText(s);
				s = c.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setTooltip(s);
			}
			if (w instanceof GUI_textfield)
			{
				GUI_textfield c = (GUI_textfield)w; 
				String s = c.ph;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setPlaceholder(s);
				s = c.info;
				while (s.contains("^")) s = replace_params(s, "", "");
				c.setTooltip(s);
			}
		}
		update_tooltips("", "");
	}
	
	void fill_command_area(int screen, boolean hidden)
	{	
		if (!hidden)
		{
			for (GUI_screen s : screens)
				if (screens.indexOf(s) == screen) s.setColor(GUI_control.get_colour(Main.selected_button_color));
					else s.setColor(s.text_colour);
					sb = screens.get(screen);
		} else sb = hidden_screens.get(screen);
		
		for (Widget w : this.getAttachedWidgets())
		{
			if (w instanceof GUI_paginatebutton || w instanceof GUI_screen || w instanceof GUI_closebutton || w instanceof GUI_reloadbutton)
				continue; else removeWidget(w);
		}
		Gradient gr = new GenericGradient();
		gr.setX(3).setY(25).setWidth(421).setHeight(189);
		gr.setTopColor(sb.background_colour);
		gr.setBottomColor(sb.background_colour);
		gr.setPriority(RenderPriority.Highest);		
		attachWidget(plugin, gr);
		
		int gap = 2;
		col_width = (int) ((gr.getWidth() - 4 - ((sb.num_columns * gap) + gap)) / sb.num_columns);
		for (GUI_control g : sb.controls)
		{
			int w = g.width > -1 ? g.width : (col_width * g.col_span) + ((g.col_span - 1) * gap);
			int h = g.height > -1 ? g.height : (sb.row_height * g.row_span) + ((g.row_span - 1) * gap);
			int y = g.y > -1 ? g.y : ((g.row - 1) * sb.row_height) + 30;
			int x = g.x > -1 ? g.x : (int) (((g.col - 1) * col_width)) + (gap * (g.col)) + 5;
			if (g.cb != null)
			{
				//g.cb.sb = sb;
				g.cb.setX(x).setY(y);
				g.cb.setWidth(w).setHeight(h);
				g.cb.setAlign(WidgetAnchor.CENTER_CENTER);
				g.cb.setFixed(true);
				attachWidget(plugin, g.cb);
			}
				
			else if (g.link_button != null)
			{
				g.link_button.setX(x).setY(y);
				g.link_button.setWidth(w).setHeight(h);
				g.link_button.setAlign(WidgetAnchor.CENTER_CENTER);
				g.link_button.setFixed(true);
				attachWidget(plugin, g.link_button);
			}
			
			else if (g.tf != null)
			{
				g.tf.setWidth(w - 3).setHeight(13);
				g.tf.setX((int) (x + (w / 2) - (g.tf.getWidth() / 2))).setY((int) (y + (h / 2) - (g.tf.getHeight() / 2)));
				g.tf.setFixed(true);
				attachWidget(plugin, g.tf);	
			}
			
			else if (g.gradient != null)
			{
				g.gradient.setWidth(w).setHeight(h);
				g.gradient.setX(x).setY(y);
				g.gradient.setFixed(true);
				attachWidget(plugin, g.gradient);
			}
				
			else if (g.label != null)
			{		
				//int temph = GUI_label.getStringHeight(g.label.getText());				
				//int tempw = GUI_label.getStringWidth(g.label.getText());
				//g.label.setWidth(tempw).setHeight(temph);
				//g.label.setX(get_align(g.label.align, x, w, tempw));
				
				//g.label.setY((int) (y + (h / 2) - (temph / 2)));
				g.label.setY(y + 5).setX(x).setWidth(g.label.getWidth()).setHeight(g.label.getHeight());
				g.label.setFixed(true);
				attachWidget(plugin, g.label);
			}
			
			else if (g.spacer != null)
			{
				g.spacer.setWidth(w).setHeight(h / 3);				
				g.spacer.setX(x).setY((int) (y + (h / 2) - (g.spacer.getHeight() / 2)));
				g.spacer.setFixed(true);
				attachWidget(plugin, g.spacer);
			}			
			
			else if (g.texture != null)
			{
				g.texture.setX(x).setY(y);
				g.texture.setWidth(w).setHeight(h);
				attachWidget(plugin, g.texture);
			}
			
			else if (g.check_box != null)
			{
				//int tempw = GUI_label.getStringWidth(g.check_box.getText(), g.check_box.getScale()) + 19;
				g.check_box.setWidth(18).setHeight(18);
				g.check_box.setX(x);//get_align(g.check_box.align, x, w, tempw));
				g.check_box.setY(y);		
				g.check_box.setFixed(true);
				attachWidget(plugin, g.check_box);
			}
			
			else if (g.radio_button != null)
			{
				g.radio_button.setWidth(18).setHeight(18);
				//int tempw = GUI_label.getStringWidth(g.radio_button.getText(), g.radio_button.getScale()) + 18;				
				g.radio_button.setX(x);//get_align(g.radio_button.align, x, w, tempw));		
				g.radio_button.setY(y);				
				g.radio_button.setFixed(true);
				attachWidget(plugin, g.radio_button);	
			}
			
			else if (g.slider != null)
			{
				GenericContainer gc = new GenericContainer();
				gc.setX(x).setY(y);
				gc.setWidth(w).setHeight(h);
				gc.setLayout(ContainerType.HORIZONTAL);
				GUI_sliderminus sm = new GUI_sliderminus("<", this);
				sm.setX(x).setY(y);
				sm.setWidth(10).setHeight(h);
				g.slider.setWidth(w - 18).setHeight(h);
				g.slider.setX(x + 8).setY(y);
				g.slider.setFixed(true);
				GUI_sliderplus sp = new GUI_sliderplus(">", this);
				sm.setX(gc.getWidth() - 10).setY(y);
				sm.setWidth(10).setHeight(h);
				gc.addChildren(sm, g.slider, sp);
				attachWidgets(plugin, gc);	
			}
			
			else if (g.combo_box != null)
			{
				g.combo_box.setWidth(w).setHeight(h);
				g.combo_box.setX(x).setY(y);
				g.combo_box.setFixed(true);
				attachWidget(plugin, g.combo_box);	
			}
			
			else if (g.url_button != null)
			{
				g.url_button.setX(x).setY(y);
				g.url_button.setWidth(w).setHeight(h);
				g.url_button.setAlign(WidgetAnchor.CENTER_CENTER);
				g.url_button.setFixed(true);
				attachWidget(plugin, g.url_button);
			}
		}		
		for (Widget w : this.getAttachedWidgets())
		{
			if (w instanceof GUI_radiobutton)
			{
				GUI_radiobutton rb = (GUI_radiobutton)w;
				if (rb.selected) rb.setSelected(true);
			}
			else if (w instanceof GUI_slider)
			{
				GUI_slider slider = (GUI_slider)w;
				if (!slider.skin_texture.equalsIgnoreCase("") && !slider.mode.equalsIgnoreCase("normal")) 
					update_texture(slider.skin_texture, slider.get_value((int) (slider.getSliderPosition()) * slider.max));
			}
		}	
		replace_text();		
	}
	
	void update_texture(String name, String player)
	{
		for (GUI_control g : sb.controls)
		{
			if (g.texture != null && g.texture.name.equalsIgnoreCase(name))
			{
				File f = new File(plugin.getDataFolder() + File.separator + "Skins" + File.separator + player.toLowerCase() + ".png");
				if (f.exists()) g.texture.setUrl(f.getPath()); else g.texture.setUrl("");
			}
		}
	}
	
	void fill_screen_area(int index)
	{							
		for (Widget w : getAttachedWidgets()) if (w instanceof GUI_screen || w instanceof GUI_paginatebutton) removeWidget(w);
		int start;
		int count = screens.size() - index;
		int end = index + 5 < count ? index + 5 : screens.size();		
		if (count > 5) count = 5;
		start = 213 - ((count * 81) / 2);
    	for (int i = index; i < end; i++)
    	{
    		GUI_screen sb = screens.get(i);
    		sb.setWidth(80).setHeight(15);
    		sb.setX(start + i % 5 * 81);
    		sb.setY(3);
    		if (sb.hidden) sb.setVisible(false);
    		attachWidget(plugin, sb);
    	}
    	if (index > 0)
    	{
    		GUI_paginatebutton previous = new GUI_paginatebutton("<", Math.max(0, index - 5), this);
    		previous.setHeight(15).setWidth(10).setY(3).setX(0);
    		attachWidget(plugin, previous);
    	}
    	if (end < screens.size())
    	{
    		GUI_paginatebutton next = new GUI_paginatebutton(">", end, this);
    		next.setHeight(15).setWidth(10).setY(3).setX(415);
    		attachWidget(plugin, next);
    	}
	}
	
	void jump_to_screen(int id, String[] params)
	{
		if (params != null && params.length > 1)
		{
			this.params = new String[params.length -1];
			for (int i = 1; i < params.length; i++) this.params[i -1] = params[i];
		} else this.params = null;
				
		for (GUI_screen g : screens)
		{
			if (g.id == id) 
			{
				boolean jump_to = true;
				if (g.show_funds != 0 && Main.economy != null)
				{
					if (Main.economy.getBalance(me.getName()) >= g.show_funds) jump_to = true;
					else jump_to = false;
				}
				if (!g.show_permission.equalsIgnoreCase(""))
				{
					Permission p = new Permission(g.show_permission);
					if (me.hasPermission(p)) jump_to = true; else jump_to = false;
				}
				if (jump_to)
				{
					fill_screen_area((screens.indexOf(g) / 5) * 5);
					fill_command_area(screens.indexOf(g), false);
					return;
				}
			}
		}
		
		for (GUI_screen g : hidden_screens)
		{
			if (g.id == id) 
			{
				boolean jump_to = true;
				if (g.show_funds != 0 && Main.economy != null)
				{
					if (Main.economy.getBalance(me.getName()) >= g.show_funds) jump_to = true;
					else jump_to = false;
				}
				if (!g.show_permission.equalsIgnoreCase(""))
				{
					Permission p = new Permission(g.show_permission);
					if (me.hasPermission(p)) jump_to = true; else jump_to = false;
				}
				if (jump_to)
				{
					fill_command_area(hidden_screens.indexOf(g), true);
					return;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	GUI(Main plugin, SpoutPlayer me)
	{		
		fixed = true;
		setAnchor(WidgetAnchor.SCALE);
		this.me = me;
		setTransparent(true);
		this.plugin = plugin;
		for (Map<?, ?> map : Main.screen_files)
		{
			ArrayList<Map<String, Object>> b = (ArrayList<Map<String, Object>>) map.get("screens");
			for (int i = 0; i < b.size(); i++)
			{
				boolean show = true;							
				if (b.get(i).containsKey("show_funds") && Main.economy != null)
				{
					if (Main.economy.getBalance(me.getName()) >= (Integer)b.get(i).get("show_funds")) show = true;
					else show = false;
				}
				if (b.get(i).containsKey("show_permission"))
				{
					Permission p = new Permission(b.get(i).get("show_permission").toString().toLowerCase());
					if (me.hasPermission(p)) show = true;
					else show = false;
				}	
				if (b.get(i).containsKey("hidden")) show = false;
				if (show) screens.add(new GUI_screen(b.get(i), this, i)); else hidden_screens.add(new GUI_screen(b.get(i), this, i));
			}
		}		
		fill_screen_area(0);
		GUI_closebutton cb = new GUI_closebutton();
		cb.setText("X").setWidth(30).setHeight(15).setX(395).setY(223);
		cb.setTooltip("Closes the GUI");
		attachWidget(plugin, cb);
		
		Permission perm = new Permission("gui_creator.can_reload_screens");
    	if (me.hasPermission(perm))
    	{
    		GUI_reloadbutton reload_button = new GUI_reloadbutton(plugin);
			reload_button.setTooltip("Reloads the screens");
			reload_button.setText("/rgc").setWidth(30).setHeight(15).setX(3).setY(223);
			attachWidget(plugin, reload_button);
    	}
		
		int open = Main.config.getInt("open_screen", -1);
		String[] params = Main.config.getString("open_params", "").split(" ");
		
    	me.getMainScreen().attachPopupScreen(this);
    	if (open > -1) jump_to_screen(open, params);
	}
}
