package me.FluffyWolfers.CE.Listeners;

import me.FluffyWolfers.CE.CE;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;

public class CEBlockListener implements Listener{
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPassOverBlock(VehicleMoveEvent e){
		
		Vehicle v = e.getVehicle();
		
		Player p = null;
		if(v.getPassenger() != null){
			
			p = (Player) v.getPassenger();
			
		}
		
		if(v instanceof Minecart){
			
			Location loc = v.getLocation();
			World w = loc.getWorld();
			
			loc.setY(loc.getY() - 1);
			
			Block b = w.getBlockAt(loc);
			
			Material type = b.getType();
			
			//Speed
			if(type.getId() == CE.c.getConfig().getInt("speeds.speed-id")){
				
				v.setVelocity(v.getVelocity().multiply(getSpeed("speeds.speed-speed")));
				
			}
			//Break
			if(type.getId() == CE.c.getConfig().getInt("speeds.break-id")){
				
				v.setVelocity(v.getVelocity().multiply(getSpeed("speeds.break-speed")));
				
			}
			//Stop
			if(type.getId() == CE.c.getConfig().getInt("speeds.stop-id")){
				
				v.setVelocity(v.getVelocity().multiply(0.0));
				
				if(p != null){
					v.eject();
					String color = ChatColor.translateAlternateColorCodes('&', getMsg("messages.eject-msg"));
					p.sendMessage(CE.getPrefix() + color);
				}
				
				((Minecart) v).setMaxSpeed(0D);
				
			}
			//Reverser
			if(type.getId() == CE.c.getConfig().getInt("speeds.reverser-id")){
				
				v.setVelocity(v.getVelocity().multiply(-1.0));
				
			}
			
		}
		
	}
	
	public double getSpeed(String configPath){
		if(CE.c.getConfig().isInt(configPath))
			return Double.parseDouble(String.valueOf(CE.c.getConfig().getInt(configPath)) + ".0");
		return CE.c.getConfig().getDouble(configPath);
	}
	
	public String getMsg(String configPath){
		return CE.c.getConfig().getString(configPath);
	}
	
	public int getF(Player player){
	    double d = (player.getLocation().getYaw() * 4.0F / 360.0F) + 0.5D;
	    int i = (int) d;
	    return d < i ? i - 1 : i;
	}
	
}
