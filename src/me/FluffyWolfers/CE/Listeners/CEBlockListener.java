package me.FluffyWolfers.CE.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.FluffyWolfers.CE.CE;
import me.FluffyWolfers.CE.CECommand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CEBlockListener implements Listener{
	
	public static ArrayList<Player> flying = new ArrayList<Player>();
	
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
				
				if(p != null){
					
					if(p.hasPermission("cartessentials.effects.speed")){
						
						v.setVelocity(v.getVelocity().multiply(getSpeed("speeds.speed-speed")));
						
					}
					
				}else{
					
					v.setVelocity(v.getVelocity().multiply(getSpeed("speeds.speed-speed")));
					
				}
				
			}
			//Break
			if(type.getId() == CE.c.getConfig().getInt("speeds.break-id")){
				
				if(p != null){
					
					if(p.hasPermission("cartessentials.effects.break")){
						
						v.setVelocity(v.getVelocity().multiply(getSpeed("speeds.break-speed")));
						
					}
					
				}else{
					
					v.setVelocity(v.getVelocity().multiply(getSpeed("speeds.break-speed")));
					
				}
				
			}
			//Stop
			if(type.getId() == CE.c.getConfig().getInt("speeds.stop-id")){
				
				if(p != null){
					
					if(p.hasPermission("cartessentials.effects.stop")){
						
						v.setVelocity(v.getVelocity().multiply(0.0));
						
						v.eject();
						String color = ChatColor.translateAlternateColorCodes('&', getMsg("messages.eject-msg"));
						p.sendMessage(CE.getPrefix() + color);
						
						((Minecart) v).setMaxSpeed(0D);
						
					}
					
				}else{
					
					v.setVelocity(v.getVelocity().multiply(0.0));
					
					((Minecart) v).setMaxSpeed(0D);
					
				}
				
			}
			//Reverser
			if(type.getId() == CE.c.getConfig().getInt("speeds.reverser-id")){
				
				if(p != null){
					
					if(p.hasPermission("cartessentials.effects.reverse")){
						
						v.setVelocity(v.getVelocity().multiply(-1.0));
						
					}
					
				}else{
					
					v.setVelocity(v.getVelocity().multiply(-1.0));
					
				}
				
			}
			//Fly
			if(type.getId() == CE.c.getConfig().getInt("speeds.fly-id")){
				
				Vector vec = p.getLocation().getDirection();
		        vec.setY(vec.getY() * Double.parseDouble(String.valueOf(getSpeed("speeds.fly-speed")) + "D"));
		        
		        flying.add(p);
				
			}
			
		}
		
	}
	
	ArrayList<Player> trainC = new ArrayList<Player>();
	HashMap<Minecart, Minecart> carts = new HashMap<Minecart, Minecart>();
	Minecart minecart = null;
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClickOnCart(PlayerInteractEntityEvent e){
		
		Player p = e.getPlayer();
		
		if(p.hasPermission("cartessentials.train")){

			Entity entity = e.getRightClicked();
			if(entity instanceof Minecart){
				
				Minecart cart = (Minecart) entity;
				minecart = cart;
				ItemStack hand = p.getItemInHand();
				Material type = hand.getType();
				
				if(type.equals(Material.MINECART)){
					
					e.setCancelled(true);
					
					trainC.add(p);
					
					p.sendMessage(CE.getPrefix() + "Now place a minecart behind this one!");
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onClickOnRail(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		
		if(trainC.contains(p)){
			
			p.sendMessage("1");
			
			if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){

				p.sendMessage("2");
				
				if(p.hasPermission("cartessentials.train")){

					p.sendMessage("3");
					
					Block clicked = e.getClickedBlock();

					p.sendMessage("4");
					
					if(clicked != null){

						p.sendMessage("5");
						
						Material type = clicked.getType();

						p.sendMessage("6");
						
						if(type.equals(Material.RAILS)||type.equals(Material.POWERED_RAIL)||type.equals(Material.DETECTOR_RAIL)){
							
							p.sendMessage("7");
							
							e.setCancelled(true);
							
							trainC.remove(p);
							
							Location loc = p.getTargetBlock(null, 10).getLocation();
							loc.setY(loc.getY() + 1);
							
							Minecart cart2 = (Minecart) loc.getWorld().spawnEntity(loc, EntityType.MINECART);
							
							carts.put(minecart, cart2);
							
							p.sendMessage(CE.getPrefix() + "Train Created!");
							
						}
						
					}
					
				}
				
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
			
			if(CECommand.stationary.contains(p)){
				
				CECommand.stationary.remove(p);
				
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
					
					if(p.hasPermission("cartessentials.jump")){
						
						if(!v.isOnGround()&&!flying.contains(p)){
							
							Vector vec = v.getVelocity();
							vec.multiply(1.5);
						    ((Minecart) v).setFlyingVelocityMod(new Vector(0.67, 0.67, 0.67));
						    v.setVelocity(vec);
							
						}
						
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
		
		Vehicle v = e.getVehicle();
		
		if(v instanceof Minecart){
			
			Minecart cart = (Minecart) v;
			
			if(carts.containsKey(cart)){
				
				Minecart cart2 = (Minecart) carts.get(cart);
				
				cart2.setVelocity(((Minecart)cart).getVelocity());
				
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
						
						if(!CECommand.stationary.contains(p)){
							
							if(p.hasPermission("cartessentials.fly")){
								
								Vector vec = p.getLocation().getDirection();
							    vec.multiply(1);
							    vec.setY(vec.getY() * 0.2D);
							    if(vec.getY() < 0.0D){
							    	vec.setY(vec.getY() + 0.1D);
							    }
							    ((Minecart) v).setFlyingVelocityMod(new Vector(0, 0, 0));
							    v.setVelocity(vec);
								
							}
							
						}else{
							
							((Minecart) v).setFlyingVelocityMod(new Vector(0, ((Minecart) v).getFlyingVelocityMod().getY(), 0));
							v.setVelocity(v.getVelocity().multiply(0));
							
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
