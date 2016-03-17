package RC2K7.Plugins.RPGTeleport.Teleport;

import java.util.List;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;
import RC2K7.Plugins.RPGTeleport.Serialization.SerializeLocation;

public class TeleportSet {
	
	RPGTeleport RPG;
	String Name;
	List<String> WarpNames;
	List<SerializeLocation> Locations;
	
	public TeleportSet(RPGTeleport rpg, String name, List<String> warpnames, List<SerializeLocation> locations)
	{
		this.RPG = rpg;
		this.Name = name;
		this.WarpNames = warpnames;
		this.Locations = locations;
	}
	
	public void removeWarp(String name)
	{
		if(containsWarpName(name))
		{
			this.Locations.remove(this.WarpNames.indexOf(name));
			this.WarpNames.remove(name);
		}
	}
	
	public void addWarp(String name, SerializeLocation location)
	{
		this.WarpNames.add(name);
		this.Locations.add(location);
	}
	
	public boolean isSetName(String name) { return this.Name.equals(name); }
	
	public boolean containsWarpName(String warpname) { return this.WarpNames.contains(warpname); }
	
	public SerializeLocation getSerializeLocation(String warpname)
	{
		if(containsWarpName(warpname))
		{
			return (this.Locations.get(this.WarpNames.indexOf(warpname)));
		}
		return null;
	}
	
	public String getSetName(){ return this.Name; }
	
	public List<String> getWarpNames(){ return this.WarpNames; }
	
	public List<SerializeLocation> getLocations() { return this.Locations; }

}
