package me.FluffyWolfers.CE;

import me.FluffyWolfers.CE.Listeners.CEBlockListener;

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
		
		this.registerListeners();
		
	}
	
	public void registerListeners(){
		
		Bukkit.getServer().getPluginManager().registerEvents(new CEBlockListener(), this);
		
	}
	
	public void loadConfigs(){
		
		this.saveDefaultConfig();
		
	}
	
	public static String getPrefix(){
		return ChatColor.AQUA + "[" + ChatColor.DARK_AQUA + "ChestEssentials" + ChatColor.AQUA + "] " + ChatColor.LIGHT_PURPLE;
	}
	
	public static String getLogPrefix(){
		return "[" + pdf.getName() + " v" + pdf.getVersion() + "]";
	}

}
