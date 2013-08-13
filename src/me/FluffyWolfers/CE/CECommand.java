package me.FluffyWolfers.CE;

import java.util.ArrayList;

import me.FluffyWolfers.CE.Listeners.CEBlockListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.util.Vector;

public class CECommand implements CommandExecutor{
	
	public static ArrayList<Player> stationary = new ArrayList<Player>();
	
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
					p.sendMessage(CE.getPrefix() + "/ce stationary - Makes you fall very slowly while flying");
					p.sendMessage(CE.getPrefix() + "/ce fly - Makes you fly without a sponge");
					
				}else if(command.equalsIgnoreCase("reload")){
					
					if(p.hasPermission("cartessentials.command.reload")){
						
						CE.c.reloadConfig();
						
						p.sendMessage(CE.getPrefix() + "Reloaded!");
						
					}
					
				}else if(command.equalsIgnoreCase("stationary")){
					
					if(p.hasPermission("cartessentials.command.stationary")){
						
						if(p.isInsideVehicle()){
							
							Vehicle v = (Vehicle) p.getVehicle();
							
							if(v instanceof Minecart){
								
								Minecart mc = (Minecart) v;
								
								if(!mc.isOnGround()||CEBlockListener.flying.contains(p)){
									
									if(stationary.contains(p)){
										
										stationary.remove(p);
										
										p.sendMessage(CE.getPrefix() + "You are no longer stationary");
										
									}else{
										
										mc.setVelocity(mc.getVelocity().multiply(0));
										mc.setFlyingVelocityMod(new Vector(0, mc.getFlyingVelocityMod().getY(), 0));
										
										stationary.add(p);
										
										p.sendMessage(CE.getPrefix() + "You are now stationary");
										
									}
									
								}else{
									
									p.sendMessage(CE.getPrefix() + ChatColor.DARK_RED + "You must be flying");
									
								}
								
							}
							
						}
						
					}
					
				}else if(command.equalsIgnoreCase("fly")){
					
					if(p.hasPermission("cartessentials.command.fly")){
						
						if(p.hasPermission("cartessentials.fly")){
							
							if(p.isInsideVehicle()){
								
								Vehicle v = (Vehicle) p.getVehicle();
								
								if(v instanceof Minecart){
									
									if(!CEBlockListener.flying.contains(p)){
										
										CEBlockListener.flying.add(p);
										
										p.sendMessage(CE.getPrefix() + "You are now flying!");
										
									}else{
										p.sendMessage(CE.getPrefix() + ChatColor.DARK_RED + "You are already flying!");
									}
									
								}else{
									p.sendMessage(CE.getPrefix() + ChatColor.DARK_RED + "You do not in a minecart!");
								}
								
							}else{
								p.sendMessage(CE.getPrefix() + ChatColor.DARK_RED + "You do not in a minecart!");
							}
							
						}else{
							p.sendMessage(CE.getPrefix() + ChatColor.DARK_RED + "You do not have permission to fly!");
						}
						
					}else{
						p.sendMessage(CE.getPrefix() + ChatColor.DARK_RED + "You do not have permission to use this command!");
					}
					
				}
				
			}
			
		}
		
		return false;
	}

}
