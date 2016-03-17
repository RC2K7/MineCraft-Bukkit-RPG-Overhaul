package RC2K7.Plugins.RPGTeleport.Util;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;
import RC2K7.Plugins.RPGTeleport.Teleport.TeleportSet;

public class WarpUtil {
	
	RPGTeleport RPG;
	
	public WarpUtil(RPGTeleport rpg)
	{
		this.RPG = rpg;
	}
	
	public boolean containsSetName(String name)
	{
		for(TeleportSet ts : this.RPG.TeleportSets)
		{
			if(ts.getSetName().equals(name))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean containsWarpName(String name)
	{
		for(TeleportSet ts : this.RPG.TeleportSets)
		{
			if(ts.containsWarpName(name))
			{
				return true;
			}
		}
		return false;
	}
	
	public TeleportSet getTeleportSet(String name)
	{
		for(TeleportSet ts : this.RPG.TeleportSets)
		{
			if(ts.getSetName().equals(name))
			{
				return ts;
			}
		}
		return null;
	}
	
	public TeleportSet getTeleportSetByWarpName(String name)
	{
		for(TeleportSet ts : this.RPG.TeleportSets)
		{
			if(ts.containsWarpName(name))
			{
				return ts;
			}
		}
		return null;
	}

}
