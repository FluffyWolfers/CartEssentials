package me.FluffyWolfers.CE;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CECommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if(sender instanceof Player){
			
			Player p = (Player) sender;
			//String name = p.getName();
			
			if(args.length == 0){
				
				p.sendMessage(CE.getPrefix() + "Version: " + ChatColor.GREEN + "v" + CE.pdf.getVersion());
				p.sendMessage(CE.getPrefix() + "Creator: " + ChatColor.GREEN + "FluffyWolfers");
				p.sendMessage(CE.getPrefix() + "Type /ce help");
				
			}else if(args.length > 0){
				
				String command = args[0];
				
				if(command.equalsIgnoreCase("help")){
					
					p.sendMessage(CE.getPrefix() + "/ce help - Displays help menu");
					p.sendMessage(CE.getPrefix() + "/ce reload - Reloads config file");
					
				}else if(command.equalsIgnoreCase("reload")){
					
					if(p.hasPermission("cartessentials.reload")){
						
						CE.c.reloadConfig();
						
						p.sendMessage(CE.getPrefix() + "Reloaded!");
						
					}
					
				}
				
			}
			
		}
		
		return false;
	}

}
