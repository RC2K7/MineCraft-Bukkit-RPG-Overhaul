package RC2K7.Plugins.RPGTeleport.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;

public class CommandPlayer {
	
	RPGTeleport RPG;
	
	public CommandPlayer(RPGTeleport rpg)
	{
		this.RPG = rpg;
	}
	
	public boolean onCommand(Player player, String[] args)
	{
		if(!player.isOp())
		{
			return true;
		}
		
		if(args.length >= 1)
		{
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))
			{
				player.sendMessage(ChatColor.RED + "---RPGTP Commands---");
				player.sendMessage(ChatColor.GREEN + "/rpgtp create <SETNAME>");
				player.sendMessage(ChatColor.GREEN + "/rpgtp add <WARPNAME> <SETNAME>");
				player.sendMessage(ChatColor.GREEN + "/rpgtp remove <WARPNAME>");
				player.sendMessage(ChatColor.GREEN + "/rpgtp delete <SETNAME>");
				player.sendMessage(ChatColor.GREEN + "/rpgtp tp <WARPNAME> <PLAYER>");
				player.sendMessage(ChatColor.GREEN + "/rpgtp specific <WARPNAME> <PLAYER>");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create"))
			{
				if(args.length != 2)
				{
					player.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp create <SETNAME>");
					return true;
				}
				this.RPG.AM.doCreate(player, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("add"))
			{
				if(args.length != 3)
				{
					player.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp add <WARPNAME> <SETNAME>");
					return true;
				}
				this.RPG.AM.doAdd(player, args[1], args[2]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("remove"))
			{
				if(args.length !=2)
				{
					player.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp remove <WARPNAME>");
					return true;
				}
				this.RPG.AM.doRemove(player, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("delete"))
			{
				if(args.length != 2)
				{
					player.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp delete <SETNAME>");
					return true;
				}
				this.RPG.AM.doDelete(player, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("tp"))
			{
				if(args.length != 3)
				{
					player.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp tp <SETNAME> <PLAYER>");
					return true;
				}
				Player b = Bukkit.getPlayer(args[2]);
				this.RPG.AM.doTeleportRandom(player, b, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("specific"))
			{
				if(args.length < 2 || args.length > 3)
				{
					player.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp specific <WARPNAME> <PLATER|OPTIONAL>");
					return true;
				}
				Player b = player;
				if(args.length == 3)
				{
					b = Bukkit.getPlayer(args[2]);
				}
				this.RPG.AM.doTeleportSpecific(player, b, args[1]);
				return true;
			}
		}
		return true;
	}

}
