package RC2K7.Plugins.RPGTeleport.RCConfig;

import java.util.ArrayList;
import java.util.List;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;
import RC2K7.Plugins.RPGTeleport.Serialization.SerializeLocation;
import RC2K7.Plugins.RPGTeleport.Teleport.TeleportSet;

public class LoadWarp {
	
	RPGTeleport RPG;
	
	public LoadWarp(RPGTeleport rpg)
	{
		this.RPG = rpg;
		LoadWarps();
	}
	
	public void LoadWarps()
	{
		if(this.RPG.WarpConfig.getConfig() != null)
		{
			this.RPG.TeleportSets.clear();
			if(this.RPG.WarpConfig.getConfig().contains("Warp.Sets"))
			{
				for(String name : this.RPG.WarpConfig.getConfig().getConfigurationSection("Warp.Sets").getKeys(false))
				{
					List<String> warpnames = new ArrayList<>();
					List<SerializeLocation> locations = new ArrayList<>();
					for(String warp : this.RPG.WarpConfig.getConfig().getStringList("Warp.Sets." + name))
					{
						String[] block = warp.split("#");
						warpnames.add(block[0]);
						locations.add(new SerializeLocation(block[1]));
					}
					this.RPG.TeleportSets.add(new TeleportSet(this.RPG, name, warpnames, locations));
				}
			}
		}
	}
	
	public void SaveWarps()
	{
		if(this.RPG.WarpConfig.getConfig() != null)
		{
			for(TeleportSet ts : this.RPG.TeleportSets)
			{
				List<String> WarpLocations = new ArrayList<>();
				for(int x = 0; x < ts.getWarpNames().size(); x++)
				{
					WarpLocations.add(ts.getWarpNames().get(x) + "#" + ts.getLocations().get(x).getString());
				}
				this.RPG.WarpConfig.getConfig().set("Warp.Sets." + ts.getSetName(), WarpLocations);
			}
			this.RPG.WarpConfig.saveConfig();
		}
	}

}
