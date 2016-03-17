package RC2K7.Plugins.RPGAPI;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import RC2K7.Plugins.RPGAPI.Events.MapRightClickEvent;
import RC2K7.Plugins.RPGAPI.Listeners.MapListener;
import RC2K7.Plugins.RPGAPI.Listeners.RegionListener;
import RC2K7.Plugins.RPGAPI.Maps.ScrollMap;
import RC2K7.Plugins.RPGAPI.Util.PlayerUtil;

public class RPGAPI extends JavaPlugin implements Listener {
		
	//Plugins
	public WorldGuardPlugin WG;
	
	//Listeners
	public MapListener MapListen;
	public RegionListener RegionListen;
	
	//Utilities
	PlayerUtil PlayerUtility = new PlayerUtil(this);
	
	
	@Override
	public void onEnable() 
	{
		Bukkit.getPluginManager().registerEvents(this, this);
		if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard"))
		{
			this.WG = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
		}
		this.RegionListen = new RegionListener(this, this.WG);
		this.MapListen = new MapListener(this);
	}
	
	@EventHandler
	public void onRightClickMap(MapRightClickEvent event)
	{
		if(event.isPlayerSneaking())
		{
			ScrollMap.getScrollMap(event.getMapID()).getMapRender().decrementIndex();
			ScrollMap.getScrollMap(event.getMapID()).update(event.getPlayer().getName());
		}
		else
		{
			ScrollMap.getScrollMap(event.getMapID()).getMapRender().incrementIndex();
			ScrollMap.getScrollMap(event.getMapID()).update(event.getPlayer().getName());
		}
	}

}
