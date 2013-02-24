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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.FileManager;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.yaml.snakeyaml.Yaml;

public class Main extends JavaPlugin implements BindingExecutionDelegate, Listener
{
    private List<Map<?, ?>> screen_files;
    public static Economy economy = null;

    void checkVersion()
	{
    	if (!getConfig().getBoolean("check_for_newer_version", true)) return;
    	
		DocumentBuilder dbf;
		try 
		{
			dbf = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dbf.parse("http://dev.bukkit.org/server-mods/gui-creator/files.rss");
			XPath xpath = XPathFactory.newInstance().newXPath();
			String s = ((Element) xpath.evaluate("//item[1]/title", doc, XPathConstants.NODE)).getTextContent();
			if  (!s.equalsIgnoreCase(getDescription().getVersion())) getLogger().info("There's a more recent version available!");
		} 
		catch (Exception e) {}		
	}
       
    @Override
    public void onDisable() 
    {
    	screen_files = null;
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
    	getConfig().options().copyDefaults(true);		 
		saveConfig();
    	checkVersion();
    	
    	if (!reload()) return;
    	
    	try 
		{
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		} 
		catch (IOException e) 
		{
		    getLogger().warning("Something went wrong with Metrics - it will be disabled.");
		}  	
    	
    	File f = new File(getDataFolder() + File.separator + "Skins");
		if (!f.exists()) f.mkdir();		
    
		SpoutManager.getKeyBindingManager().registerBinding("gui_creator_show_gui", Keyboard.KEY_C, "Shows the GUI", this, this);
		
		PluginManager pm = Bukkit.getServer().getPluginManager();
		if (pm.getPlugin("Vault") != null) setup_economy();	
    	
		pm.registerEvents(this, this);
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
    	File f = new File(getDataFolder() + File.separator + "Screens" + File.separator + "example.yml");
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
    
    boolean reload()
    {    	
    	reloadConfig();
		
    	if (!getScreens())
		{
			getLogger().warning("No screen files found - plugin will stop");
			setEnabled(false);
			return false;
		}  
		
		return true;
    }
    
    private boolean getScreens()
    {
    	screen_files = null;
    	File f = new File(getDataFolder() + File.separator + "Screens");
		if (!f.exists())
		{
			f.mkdir();
			make_example();
			return false;
		}
		
		InputStream input;
    	String loc = getDataFolder() + File.separator + "Screens" + File.separator;			
		f = new File(loc);
		String[] children = f.list();
		if (children.length == 0) return false;
		
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
		return true;
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{      	
		if (cmd.getName().equalsIgnoreCase("reload_GUI_creator") && args.length == 0)
		{
			reload();
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
			
			SpoutPlayer p;
	    	if (sender instanceof SpoutPlayer) p = (SpoutPlayer)sender; else return true;
			if (!p.isSpoutCraftEnabled() || screen_files == null) return true;
			Gui gui = new Gui(this, p);	
			if (args.length == 0) return true;
			else
			{
				gui.jump_to_screen(Integer.parseInt(args[0]), args);
			}
		}
		return false;
	}
    
    List<Map<?, ?>> getScreenFiles()
    {
    	return screen_files;
    }
    
    public static String getString(String s)
    {
    	return Bukkit.getServer().getPluginManager().getPlugin("GUI Creator").getConfig().getString(s);
    }
    
    public static boolean getBool(String s)
    {
    	return Bukkit.getServer().getPluginManager().getPlugin("GUI Creator").getConfig().getBoolean(s);
    }

// Listener stuff
    
    @Override
	public void keyPressed(KeyBindingEvent event) 
	{
		SpoutPlayer p = event.getPlayer();
    	if (p.hasPermission("gui_creator.can_open_gui"))
		{
			if (p.getActiveScreen() == ScreenType.GAME_SCREEN || (p.getActiveScreen() == ScreenType.CUSTOM_SCREEN && p.getMainScreen().getActivePopup() == null))
			{
				Gui gui = new Gui(this, p);
				String[] params = Bukkit.getServer().getPluginManager().getPlugin("GUI Creator").getConfig().contains("open_screen") ? 
						GuiControl.get_string(Main.getString("open_screen")).split(" ") : null;
				int open = params != null ? Integer.parseInt(params[0]) : -2;
		
				p.getMainScreen().attachPopupScreen(gui);
				if (open > -1) gui.jump_to_screen(open, params);
			}
			else if (p.getMainScreen().getActivePopup() instanceof Gui)				
			{
				for (Widget w : p.getMainScreen().getActivePopup().getAttachedWidgets())
				{
					if (w instanceof GuiTextField && ((GuiTextField)w).isFocus()) return;
				}
				p.getMainScreen().closePopup();
			}				
    	}
	}

	@Override
	public void keyReleased(KeyBindingEvent event) {}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		if (!Main.getBool("make_player_skins")) return;
		
		File f = create_skin(event.getPlayer().getName());
		if (f != null)
		{
			FileManager fm = SpoutManager.getFileManager();
			if (!fm.getCache(this).contains(f)) fm.addToCache(this, f);
		}
	}

}