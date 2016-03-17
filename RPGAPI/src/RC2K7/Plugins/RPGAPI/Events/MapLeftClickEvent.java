package RC2K7.Plugins.RPGAPI.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MapLeftClickEvent extends Event {
	
	private Player EventPlayer;
	private short EventMapID;
	private static final HandlerList handler = new HandlerList();
	
	public MapLeftClickEvent(Player player, short mapid)
	{
		this.EventPlayer = player;
		this.EventMapID = mapid;
	}
	
	public HandlerList getHandlers(){ return handler; }
	public static HandlerList getHandlerList(){ return handler; }
	public Player getPlayer(){ return this.EventPlayer; }
	public short getMapID(){ return this.EventMapID; }
	public boolean isPlayerSneaking(){ return this.EventPlayer.isSneaking(); }

}
