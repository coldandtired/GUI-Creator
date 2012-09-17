package me.coldandtired.GUI_Creator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.yaml.snakeyaml.Yaml;

public class Main extends JavaPlugin
{
    public Logger log = Bukkit.getLogger();
    GUI_creator_listener listener = new GUI_creator_listener(this);
   // public Map<String, GUI> guis;
    static List<Map<?, ?>> screen_files;
    static FileConfiguration config;
    static String screen_button_color;
    static String selected_button_color;
    static String command_button_color;
    static String button_hover_color;
    static String background_color;
    static String gradient_top_color;
    static String gradient_bottom_color;
    static String spacer_color;
    static String label_color;
    static String check_box_color;
    static String radio_button_color;
    static String slider_color;
    static String text_box_inner_color;
    static String text_box_outer_color;
    static String link_button_color;
    static String combo_box_color;
    static String url_button_color;
    public static Economy economy = null;
    static boolean make_skins = true;

    boolean is_latest_version()
	{
		DocumentBuilder dbf;
		try 
		{
			dbf = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dbf.parse("http://dev.bukkit.org/server-mods/gui-creator/files.rss");
			XPath xpath = XPathFactory.newInstance().newXPath();
			String s = ((Element) xpath.evaluate("//item[1]/title", doc, XPathConstants.NODE)).getTextContent();
			return (s.equalsIgnoreCase(getDescription().getVersion()));
		} 
		catch (Exception e) {return true;}		
	}
    
    
    @Override
    public void onDisable() 
    {
    //	guis = null;
    	log = null;
    	listener = null;
    	screen_files = null;
        config = null;
        screen_button_color = null;
        selected_button_color = null;
        command_button_color = null;
        button_hover_color = null;
        background_color = null;
        gradient_top_color = null;
        gradient_bottom_color = null;
        spacer_color = null;
        label_color = null;
        check_box_color = null;
        radio_button_color = null;
        slider_color = null;
        text_box_inner_color = null;
        text_box_outer_color = null;
        link_button_color = null;
        combo_box_color = null;
        url_button_color = null;
        economy = null;
    }
    
    File create_skin(String name)
	{
    	String s = "http://s3.amazonaws.com/MinecraftSkins/" + name + ".png";
		BufferedImage old = null;	
		try 
		{
			URL image_url = new URL(s);
			InputStream in = image_url.openStream();
		    old = ImageIO.read(in);
		    BufferedImage new_image = new BufferedImage(16, 33, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = new_image.createGraphics();
		    //to x start, to y start, to x end, to y end,
		    //from x start, from y start, from x end, from y end
		    g.drawImage(old, 4, 1, 12, 9,
		    		8, 8, 16, 16, null);// head
		    g.drawImage(old, 4, 9, 12, 21,
		    		20, 20, 28, 32, null);//body
		    g.drawImage(old, 0, 9, 4, 21,
		    		44, 20, 48, 32, null);// left arm
		    g.drawImage(old, 12, 9, 16, 21,
		    		48, 20, 44, 32, null);// right arm
		    g.drawImage(old, 4, 21, 8, 33,
		    		4, 20, 8, 32, null);// left leg
		    g.drawImage(old, 8, 21, 12, 33,
		    		8, 20, 4, 32, null);// right leg
		    g.drawImage(old, 3, 0, 13, 10,
		    		40, 8, 48, 16, null);// head accessory
		    File f = new File(getDataFolder() + File.separator + "Skins" + File.separator + name.toLowerCase() + ".png");
		    ImageIO.write(new_image, "png", f);
			return f;			
		} 
		catch (IOException e) {}
		return null;
	}
    
    @Override
    public void onEnable() 
    { 	
    	if (!is_latest_version()) getLogger().info("There's a new version of GUI Creator available!");
    	
    	try 
		{
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} 
		catch (IOException e) 
		{
		    getLogger().warning("Something went wrong with Metrics - it will be disabled.");
		}
    	
    	File f = new File(getDataFolder() + File.separator + "config.yml");
		if (!f.exists())
		{
			try 
			{
				if (!getDataFolder().exists()) getDataFolder().mkdir();
				f.createNewFile();							
			} 
			catch (Exception e) {e.printStackTrace();}
		}
		f = new File(getDataFolder() + File.separator + "Screens");
		if (!f.exists()) f.mkdir();	
		f = new File(getDataFolder() + File.separator + "Skins");
		if (!f.exists()) f.mkdir();
		
		make_example();
		SpoutManager.getKeyBindingManager().registerBinding("gui_creator_show_gui", Keyboard.KEY_C, "Shows the GUI", listener, this);
		config = getConfig();
		config.addDefault("open_screen", -1);
		config.addDefault("ignore_open_permission", false);
		config.addDefault("ignore_reload_permission", false);
		config.addDefault("make_player_skins", true);
		config.addDefault("selected_button_color", "120,50,120");
		config.addDefault("screen_button_color", "150,150,150");
		config.addDefault("command_button_color", "255,255,255");
		config.addDefault("combo_box_color", "255,255,255");
		config.addDefault("url_button_color", "255,255,255");
		config.addDefault("button_hover_color", "150,120,50");
		config.addDefault("background_color", "0,0,0,130");
		config.addDefault("spacer_color", "255,255,255");
		config.addDefault("gradient_top_color", "255,255,255");
		config.addDefault("gradient_bottom_color", "255,255,255");
		config.addDefault("label_color", "255,255,255");
		config.addDefault("check_box_color", "255,255,255");
		config.addDefault("radio_button_color", "255,255,255");
		config.addDefault("slider_color", "255,255,255");
		config.addDefault("text_box_inner_color", "0,0,0,255");
		config.addDefault("text_box_outer_color", "159,159,159,255");
		config.addDefault("link_button_color", "255,255,255");
		config.addDefault("confirm_title", "Confirm command!");
		config.addDefault("confirm_text", "Are you sure?");
		config.addDefault("confirm_yes", "Yes");
		config.addDefault("confirm_no", "No");
		config.options().copyDefaults(true);
		saveConfig();
		selected_button_color = config.getString("selected_button_color");
		screen_button_color = config.getString("screen_button_color");
		combo_box_color = config.getString("combo_box_color");
		url_button_color = config.getString("url_button_color");
		command_button_color = config.getString("command_button_color");
		button_hover_color = config.getString("button_hover_color");
		background_color = config.getString("background_color");
		spacer_color = config.getString("spacer_color");
		gradient_top_color = config.getString("gradient_top_color");
		gradient_bottom_color = config.getString("gradient_bottom_color");
		label_color = config.getString("label_color");
		check_box_color = config.getString("check_box_color");
		radio_button_color = config.getString("radio_button_color");
		slider_color = config.getString("slider_color");
		text_box_inner_color = config.getString("text_box_inner_color");
		text_box_outer_color = config.getString("text_box_outer_color");
		link_button_color = config.getString("link_button_color");
		make_skins = config.getBoolean("make_player_skins");
		get_screens();
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(listener, this);
		if (pm.getPlugin("Vault") != null) setup_economy();
		log.info("[GUI Creator] Version " + getDescription().getVersion() + " running!");
	}
    
    private Boolean setup_economy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    private void make_example()
    {
    	File f = new File(getDataFolder() + File.separator + "example.yml");
		try 
		{
			f.createNewFile();
			FileWriter fw = new FileWriter(f);
			fw.write("screens:\n");
			fw.write("- text: Example\n");
			fw.write("  num_columns: 4\n");
			fw.write("  controls:\n");
			fw.write("  - type: button\n");
			fw.write("    row: 1\n");
			fw.write("    col: 1\n");
			fw.write("    text: Set Day\n");
			fw.write("    command: /time set ^cb^\n");
			fw.write("  - type: check_box\n");
			fw.write("    row: 1\n");
			fw.write("    col: 2\n");
			fw.write("    checked_value: 0\n");
			fw.write("    unchecked_value: 15000\n");
			fw.write("    info: checked - day, unchecked - night\n");
			fw.write("    name: cb");
			fw.close();
		} 
		catch (IOException e) {e.printStackTrace();}
    }
    
    void get_screens()
    {
    	screen_files = null;
		InputStream input;
    	String loc = getDataFolder() + File.separator + "Screens" + File.separator;			
		File dir = new File(loc);
		String[] children = dir.list();
		if (children.length == 0) return;
		
		screen_files = new ArrayList<Map<?, ?>>();
		
		for (int i = 0; i < children.length; i++)
		{	
			String s = children[i].substring(children[i].length() - 3);
			if (s.equalsIgnoreCase("yml") || s.equalsIgnoreCase("txt"))
			try 
			{
				input = new FileInputStream(new File(loc + children[i]));
				try 
				{
					Yaml yaml = new Yaml();
					Map<?, ?> a = (Map<?, ?>)yaml.load(input);
					screen_files.add(a);
				}
				finally
				{
					 try 
					 {
						 input.close();
					 }
					 catch (IOException e) {e.printStackTrace();}
				}
			}
			catch (FileNotFoundException e) {e.printStackTrace();}
		}		
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{    	
    	if (cmd.getName().equalsIgnoreCase("gctest"))
    	{
    		if (sender.isOp())
    		{
    			getLogger().info(Ansi.ansi().fg(Ansi.Color.GREEN).bold().toString() + "Test 1"
    					+ Ansi.ansi().fg(Ansi.Color.WHITE).bold().toString());
    			AnsiConsole.out.println(Ansi.ansi().fg(Ansi.Color.YELLOW).bold().toString() +"test 2"
    					+ Ansi.ansi().fg(Ansi.Color.WHITE).bold().toString());
    		}
    		return true;
    	}
    	
		if (cmd.getName().equalsIgnoreCase("reload_GUI_creator") && args.length == 0)
		{			
			if (sender instanceof Player)
			{
				boolean rl = config.getBoolean("ignore_reload_permission", false) ? sender.isOp() : sender.hasPermission("gui_creator.can_reload_screens");
				if (!rl)
				{
					sender.sendMessage(ChatColor.RED + "[GUI Creator] You don't have permission to reload the screens!");
					return true;
				}
			}
			get_screens();
			log.info("[GUI Creator] Screen files reloaded!");
			if (sender instanceof SpoutPlayer) sender.sendMessage(ChatColor.GREEN + "[GUI Creator] Screen files reloaded!");
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("open_screen"))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "[GUI Creator] This command can only be used by a player!");
				return true;
			}
			boolean show = config.getBoolean("ignore_open_permission", false) ? sender.isOp() : sender.hasPermission("gui_creator.can_open_gui");
			if (!show)
			{
				sender.sendMessage(ChatColor.RED + "[GUI Creator] You don't have permission to view the GUI!");
				return true;
			}
			
			SpoutPlayer p;
	    	if (sender instanceof SpoutPlayer) p = (SpoutPlayer)sender; else return true;
			if (!p.isSpoutCraftEnabled() || Main.screen_files == null) return true;
			GUI gui = new GUI(this, p);
	    	//guis.put(p.getName(), gui);	
			if (args.length == 0) return true;
			else
			{
				gui.jump_to_screen(Integer.parseInt(args[0]), args);
			}
		}
		return false;
	}
}
