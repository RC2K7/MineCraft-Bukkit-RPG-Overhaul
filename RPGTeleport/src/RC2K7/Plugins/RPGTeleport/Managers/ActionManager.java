package RC2K7.Plugins.RPGTeleport.Managers;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;
import RC2K7.Plugins.RPGTeleport.Serialization.SerializeLocation;
import RC2K7.Plugins.RPGTeleport.Teleport.TeleportSet;

public class ActionManager {
	
	RPGTeleport RPG;
	String Stub = ChatColor.GOLD + "[RPGTP]: " + ChatColor.RESET;
	
	public ActionManager(RPGTeleport rpg)
	{
		this.RPG = rpg;
	}
	
	public void doCreate(CommandSender sender, String set)
	{
		if(this.RPG.WarpUtil.containsSetName(set))
		{
			sender.sendMessage(this.Stub + set + " Already Exists.");
			return;
		}
		this.RPG.TeleportSets.add(new TeleportSet(this.RPG, set, new ArrayList<String>(), new ArrayList<SerializeLocation>()));
		sender.sendMessage(this.Stub + "Set '" + set + "' Has Been Created.");
		this.RPG.LoadWarps.SaveWarps();
	}
	
	public void doAdd(Player sender, String warpname, String setname)
	{
		if(this.RPG.WarpUtil.containsWarpName(warpname))
		{
			sender.sendMessage(this.Stub + "Warp Already Exists.");
			return;
		}
		if(!this.RPG.WarpUtil.containsSetName(setname))
		{
			sender.sendMessage(this.Stub + "Cannot Find Set.");
			return;
		}
		TeleportSet ts = this.RPG.WarpUtil.getTeleportSet(setname);
		ts.addWarp(warpname, new SerializeLocation(sender.getLocation()));
		sender.sendMessage(this.Stub + warpname + " Has Been Added To " + setname + ".");
		this.RPG.LoadWarps.SaveWarps();
	}
	
	public void doRemove(CommandSender sender, String warpname)
	{
		if(!this.RPG.WarpUtil.containsWarpName(warpname))
		{
			sender.sendMessage(this.Stub + "Warp " + warpname + "Does Not Exist.");
			return;
		}
		TeleportSet ts = this.RPG.WarpUtil.getTeleportSetByWarpName(warpname);
		ts.removeWarp(warpname);
		sender.sendMessage(this.Stub + warpname + " Has Been Removed.");
		this.RPG.LoadWarps.SaveWarps();
	}
	
	public void doDelete(CommandSender sender, String setname)
	{
		if(!this.RPG.WarpUtil.containsSetName(setname))
		{
			sender.sendMessage(this.Stub + "Cannot Find Set " + setname + ".");
			return;
		}
		TeleportSet ts = this.RPG.WarpUtil.getTeleportSet(setname);
		this.RPG.TeleportSets.remove(ts);
		sender.sendMessage(this.Stub + "Set " + setname + " Has Been Deleted.");
		this.RPG.LoadWarps.SaveWarps();
	}
	
	public void doTeleportSpecific(CommandSender sender, Player player, String warpname)
	{
		if(!this.RPG.WarpUtil.containsWarpName(warpname))
		{
			sender.sendMessage(this.Stub + "Cannot Find Warp " + warpname);
			return;
		}
		TeleportSet ts = this.RPG.WarpUtil.getTeleportSetByWarpName(warpname);
		SerializeLocation sl = ts.getSerializeLocation(warpname);
		sl.getLocation().getChunk().load();
		player.teleport(sl.getLocation());
		sender.sendMessage(this.Stub + player.getName() + " Has Been Teleported To " + warpname + ".");
	}
	
	public void doTeleportRandom(CommandSender sender, Player player, String setname)
	{
		if(!this.RPG.WarpUtil.containsSetName(setname))
		{
			sender.sendMessage(this.Stub + "Cannon Find Set " + setname + ".");
			return;
		}
		TeleportSet ts = this.RPG.WarpUtil.getTeleportSet(setname);
		SerializeLocation sl = ts.getLocations().get(new Random().nextInt(ts.getLocations().size()));
		player.teleport(sl.getLocation());
		sender.sendMessage(this.Stub + player.getName() + " Has Randomly Teleported Within " + setname + ".");
	}

}
