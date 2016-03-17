package RC2K7.Plugins.RPGAPI.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.MapInitializeEvent;

import RC2K7.Plugins.RPGAPI.RPGAPI;
import RC2K7.Plugins.RPGAPI.Events.MapLeftClickEvent;
import RC2K7.Plugins.RPGAPI.Events.MapRightClickEvent;
import RC2K7.Plugins.RPGAPI.Maps.ScrollMap;

public class MapListener implements Listener
{
	
	private RPGAPI RPG;
	
	public MapListener(RPGAPI rpg)
	{
		this.RPG = rpg;
		Bukkit.getPluginManager().registerEvents(this, this.RPG);
	}
	
	@EventHandler
	public void onMap(MapInitializeEvent event)
	{
		if(!ScrollMap.containsScrollMap(event.getMap().getId()))
		{
			ScrollMap.addScrollMap(event.getMap().getId());
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event)
	{
		if((event.getAction().equals(Action.RIGHT_CLICK_AIR) ||
				event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) &&
				event.getPlayer().getItemInHand().getType().equals(Material.MAP))
		{
			Bukkit.getPluginManager().callEvent(new MapRightClickEvent(event.getPlayer(), event.getPlayer().getItemInHand().getDurability()));
		}
		
		if((event.getAction().equals(Action.LEFT_CLICK_AIR) ||
				event.getAction().equals(Action.LEFT_CLICK_BLOCK)) &&
				event.getPlayer().getItemInHand().getType().equals(Material.MAP))
		{
			Bukkit.getPluginManager().callEvent(new MapLeftClickEvent(event.getPlayer(), event.getPlayer().getItemInHand().getDurability()));
		}
	}

}
