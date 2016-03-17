package RC2K7.Plugins.RPGAPI.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ScrollMap {
	
	//Local
	private MapView MV = null;
	private MapMenu SMR = null;;
	
	//Global
	private static Map<Short, ScrollMap> AllScrollMaps = new HashMap<>();
	private static List<String> DirtyPlayers = new ArrayList<>();
	private static List<Short> DirtyMaps = new ArrayList<>();
	
	public ScrollMap(short id)
	{
		this.MV = Bukkit.getMap(id);
		this.SMR = new MapMenu();
		this.removeAllRenders();
	}
	
	public void removeAllRenders()
	{
		for(MapRenderer mr : this.MV.getRenderers())
		{
			this.MV.removeRenderer(mr);
		}
	}
	
	public static ScrollMap addScrollMap(short id)
	{
		if(containsScrollMap(id))
			return null;
		ScrollMap SM = new ScrollMap(id);
		AllScrollMaps.put(id, SM);
		DirtyMaps.add(id);
		return SM;
	}
	
	public static ScrollMap getScrollMap(short id)
	{
		if(containsScrollMap(id))
		{
			return AllScrollMaps.get(id);
		}
		return null;
	}
	
	public static boolean containsScrollMap(short key){ return AllScrollMaps.containsKey(key); }
	public static boolean isPlayerDirty(String name){ return DirtyPlayers.contains(name); }
	public static boolean isMapDirty(short id){ return DirtyMaps.contains(id); }
	public static void removeDirtyPlayer(String name){ DirtyPlayers.remove(name); }
	public static void removeDirtyMap(short id){ DirtyMaps.remove(new Short(id)); }

	public static List<String> getDirtyPlayers(){ return DirtyPlayers; }
	public static List<Short> getDirtyMaps(){ return DirtyMaps; }
	
	public MapMenu getMapRender(){ return this.SMR; }

	public void update(String name) {
		if(this.MV.getRenderers().contains(this.SMR))
		{
			this.MV.removeRenderer(this.getMapRender());
		}
		this.MV.addRenderer(this.getMapRender());
		DirtyPlayers.add(name);
	}

}
