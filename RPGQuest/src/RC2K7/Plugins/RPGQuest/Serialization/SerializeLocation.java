package RC2K7.Plugins.RPGQuest.Serialization;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SerializeLocation {
	
	Location Loc;
	
	public SerializeLocation(Location loc)
	{
		this.Loc = loc;
	}
	
	public SerializeLocation(String loc)
	{
		String[] tree1 = loc.split(":");
		Location tmp = new Location(Bukkit.getWorld("world"), 0D, 0D, 0D);
		for(String parent1 : tree1)
		{
			String[] tree2 = parent1.split("@");
			if(tree2[0].equals("WORLD")) tmp.setWorld(Bukkit.getWorld(tree2[1]));
			else if(tree2[0].equals("X")) tmp.setX(Double.valueOf(tree2[1]));
			else if(tree2[0].equals("Y")) tmp.setY(Double.valueOf(tree2[1]));
			else if(tree2[0].equals("Z")) tmp.setZ(Double.valueOf(tree2[1]));
			else if(tree2[0].equals("PITCH")) tmp.setPitch(Float.valueOf(tree2[1]));
			//else if(tree2[0].equals("YAW")) tmp.setYaw(0.0F);
			//else if(tree2[0].equals("YAW")) tmp.setYaw(Float.valueOf(tree2[1]));
		}
		this.Loc = tmp;
	}
	
	public String getString()
	{
		return "WORLD@" + this.Loc.getWorld().getName() + ":X@" + String.valueOf(this.Loc.getX()) + ":Y@" + String.valueOf(this.Loc.getY()) + ":Z@" + String.valueOf(this.Loc.getZ()) + ":PITCH@" + String.valueOf(this.Loc.getPitch()) + ":YAW@" + String.valueOf(this.Loc.getYaw());
	}
	
	public Location getLocation()
	{
		return this.Loc;
	}

}
