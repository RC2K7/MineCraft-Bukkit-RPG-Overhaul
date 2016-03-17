package RC2K7.Plugins.RPGAPI.Listeners;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import RC2K7.Plugins.RPGAPI.RPGAPI;
import RC2K7.Plugins.RPGAPI.Enum.Movement;
import RC2K7.Plugins.RPGAPI.Events.RegionEnterEvent;
import RC2K7.Plugins.RPGAPI.Events.RegionLeaveEvent;

import com.sk89q.util.ReflectionUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RegionListener implements Listener
{
	
	private RPGAPI RPG;
	private WorldGuardPlugin WG;
	
	//Store Entered Regions
	private Map<String, Set<ProtectedRegion>> PlayerRegions = new HashMap<String, Set<ProtectedRegion>>();
	
	//Constructor
	public RegionListener(RPGAPI rpg, WorldGuardPlugin wg)
	{
		this.RPG = rpg;
		this.WG = wg;
		Bukkit.getPluginManager().registerEvents(this, this.RPG);
	}
	
	//Event When Player Joins The Game
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		checkRegions(event.getPlayer(), event.getPlayer().getLocation(), Movement.SPAWN);
	}
	
	//Event When Player Gets Kicked/Banned
	@EventHandler
	public void onKick(PlayerKickEvent event)
	{
		if(!this.PlayerRegions.containsKey(event.getPlayer().getName()))
		{
			return;
		}
		
		Set<ProtectedRegion> regions = this.PlayerRegions.get(event.getPlayer().getName());
		if(regions.size() != 0)
		{
			for(ProtectedRegion region : regions)
			{
				Event leaveEvent = new RegionLeaveEvent(region, event.getPlayer(), Movement.DISCONNECT);
				Bukkit.getPluginManager().callEvent(leaveEvent);
			}
		}
		this.PlayerRegions.remove(event.getPlayer().getName());
	}
	
	//Event When A Player Quits The Game
	@EventHandler
	public void onPlayerDC(PlayerQuitEvent event)
	{
		if(!this.PlayerRegions.containsKey(event.getPlayer().getName()))
		{
			return;
		}
		
		Set<ProtectedRegion> regions = this.PlayerRegions.get(event.getPlayer().getName());
		if(regions.size() != 0)
		{
			for(ProtectedRegion region : regions)
			{
				Event leaveEvent = new RegionLeaveEvent(region, event.getPlayer(), Movement.DISCONNECT);
				Bukkit.getPluginManager().callEvent(leaveEvent);
			}
		}
		this.PlayerRegions.remove(event.getPlayer().getName());
	}
	
	//Event When Player Moves
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event)
	{
		checkRegions(event.getPlayer(), event.getTo(), Movement.WALK);
	}
	
	//Event When Player Teleports
	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event)
	{
		checkRegions(event.getPlayer(), event.getTo(), Movement.TELEPORT);
	}
	
	//Event When Player Spawns
	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent event)
	{
		checkRegions(event.getPlayer(), event.getRespawnLocation(), Movement.SPAWN);
	}
	
	public synchronized void checkRegions(Player player, Location loc, Movement movement)
	{
		if(!this.PlayerRegions.containsKey(player.getName()))
		{
			this.PlayerRegions.put(player.getName(), new HashSet<ProtectedRegion>());
		}
		
		Set<ProtectedRegion> regions = new HashSet<>((Collection<ProtectedRegion>)this.PlayerRegions.get(player.getName()));
		
		RegionManager rm = this.WG.getRegionManager(loc.getWorld());
		ApplicableRegionSet set = rm.getApplicableRegions(loc);
		
		for(final ProtectedRegion region : set)
		{
			if(!regions.contains(region))
			{
				regions.add(region);
				Event enterEvent = new RegionEnterEvent(region, player, movement);
				Bukkit.getPluginManager().callEvent(enterEvent);
			}
		}
		
		Collection<?> app = (Collection<?>)ReflectionUtil.getField(set, "applicable");
		Iterator<?> itr = regions.iterator();
		
		while(itr.hasNext())
		{
			final ProtectedRegion region = (ProtectedRegion)itr.next();
			if(!app.contains(region))
			{
				if(rm.getRegionExact(region.getId()) != region)
				{
					itr.remove();
				}
				else
				{
					this.PlayerRegions.get(player.getName()).remove(region);
					Event eventLeave = new RegionLeaveEvent(region, player, movement);
					Bukkit.getPluginManager().callEvent(eventLeave);
					itr.remove();
				}
			}
		}
		this.PlayerRegions.put(player.getName(), regions);
	}

}
