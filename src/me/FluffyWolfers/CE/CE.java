package me.FluffyWolfers.CE;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class CE extends JavaPlugin{
	
	public static CE c;
	public static PluginDescriptionFile pdf;
	
	public void onEnable(){
		
		c = this;
		pdf = this.getDescription();
		
		Bukkit.getLogger().info("[CartEssentials v" + pdf.getVersion() + "] Starting up...");
		
		this.getCommand("cart").setExecutor(new CECommand());
		this.getCommand("essentials").setExecutor(new CECommand());
		this.getCommand("cartessentials").setExecutor(new CECommand());
		this.getCommand("ce").setExecutor(new CECommand());
		
		this.loadConfigs();
		
	}
	
	public void loadConfigs(){
		
		this.saveDefaultConfig();
		
	}
	
	public static String getPrefix(){
		return ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "ChestRegeneration" + ChatColor.AQUA + "] " + ChatColor.LIGHT_PURPLE;
	}
	
	public static String getLogPrefix(){
		return "[" + pdf.getName() + " v" + pdf.getVersion() + "]";
	}

}
