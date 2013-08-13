package me.FluffyWolfers.CE.Listeners;

import java.util.ArrayList;

import me.FluffyWolfers.CE.CE;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.util.Vector;

public class CEBlockListener implements Listener{
	
	public ArrayList<Player> flying = new ArrayList<Player>();
	
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
			//Fly
			if(type.getId() == CE.c.getConfig().getInt("speeds.fly-id")){
				
				Vector vec = p.getLocation().getDirection();
		        vec.setY(vec.getY() * Double.parseDouble(String.valueOf(getSpeed("speeds.fly-speed")) + "D"));
		        
		        flying.add(p);
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRightClick(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if(!p.isInsideVehicle()){
			
			if(flying.contains(p)){
				
				flying.remove(p);
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onVehicleUpdateJump(VehicleUpdateEvent e){
		
		if(CE.c.getConfig().getBoolean("options.better-jumps")){
			
			Vehicle v = e.getVehicle();
			
			if(v instanceof Minecart){
				
				if(v.getPassenger() != null){
					
					Player p = (Player) v.getPassenger();
					
					if(!v.isOnGround()&&!flying.contains(p)){
						
						Vector vec = v.getVelocity();
						vec.multiply(1.5);
					    ((Minecart) v).setFlyingVelocityMod(new Vector(0.67, 0.67, 0.67));
					    v.setVelocity(vec);
						
					}
					
				}else{
					
					if(!v.isOnGround()){
						
						Vector vec = v.getVelocity();
						vec.multiply(1.5);
					    ((Minecart) v).setFlyingVelocityMod(new Vector(0.67, 0.67, 0.67));
					    v.setVelocity(vec);
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onVehicleUpdate(VehicleUpdateEvent e){
		
		Vehicle v = e.getVehicle();
		
		if(v instanceof Minecart){
			
			if(v.getPassenger() != null){
				
				if(v.getPassenger().getType().equals(EntityType.PLAYER)){
					
					Player p = (Player) v.getPassenger();
					
					if(flying.contains(p)){
						
						if(p.hasPermission("cartessentials.fly")){
							
							Vector vec = p.getLocation().getDirection();
						    //vec.multiply(1);
						    //vec.setY(vec.getY() * 0.4D);
						    //if(vec.getY() < 0.0D){
						    	//vec.setY(vec.getY() + 0.3D);
						    //}
						    ((Minecart) v).setFlyingVelocityMod(new Vector(0, 0, 0));
						    v.setVelocity(vec);
							
						}
						
					}
					
				}
				
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
