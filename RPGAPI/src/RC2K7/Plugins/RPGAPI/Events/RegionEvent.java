package RC2K7.Plugins.RPGAPI.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import RC2K7.Plugins.RPGAPI.Enum.Movement;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public abstract class RegionEvent extends Event
{
	
	private static final HandlerList Handler = new HandlerList();
	private ProtectedRegion Region;
	private Player Player;
	private Movement Movement;
	
	public RegionEvent(ProtectedRegion region, Player player, Movement movement)
	{
		this.Region = region;
		this.Player = player;
		this.Movement = movement;
	}
	
	
	//Getters
	public HandlerList getHandlers(){ return Handler; }
	public ProtectedRegion getProtectedRegion(){ return this.Region; }
	public Player getPlayer(){ return this.Player; }
	public Movement getMovement(){ return this.Movement; }
	public static HandlerList getHandlerList(){ return Handler; }
}
