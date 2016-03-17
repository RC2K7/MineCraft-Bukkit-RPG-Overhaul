package RC2K7.Plugins.RPGTeleport.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;

public class CommandConsole {
	
	RPGTeleport RPG;
	
	public CommandConsole(RPGTeleport rpg)
	{
		this.RPG = rpg;
	}
	
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if(args.length >= 1)
		{
			if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))
			{
				sender.sendMessage(ChatColor.RED + "---RPGTP Commands---");
				sender.sendMessage(ChatColor.GREEN + "/rpgtp create <SETNAME>");
				sender.sendMessage(ChatColor.GREEN + "/rpgtp add <WARPNAME> <SETNAME>");
				sender.sendMessage(ChatColor.GREEN + "/rpgtp remove <WARPNAME>");
				sender.sendMessage(ChatColor.GREEN + "/rpgtp delete <SETNAME>");
				sender.sendMessage(ChatColor.GREEN + "/rpgtp tp <WARPNAME> <PLAYER>");
				sender.sendMessage(ChatColor.GREEN + "/rpgtp specific <WARPNAME> <PLAYER>");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("create"))
			{
				if(args.length != 2)
				{
					sender.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp create <SETNAME>");
					return true;
				}
				this.RPG.AM.doCreate(sender, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("add"))
			{
				sender.sendMessage(ChatColor.RED + "This Command Cannot Be Used From Console");
				return true;
			}
			
			if(args[0].equalsIgnoreCase("remove"))
			{
				if(args.length !=2)
				{
					sender.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp remove <WARPNAME>");
					return true;
				}
				this.RPG.AM.doRemove(sender, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("delete"))
			{
				if(args.length != 2)
				{
					sender.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp delete <SETNAME>");
					return true;
				}
				this.RPG.AM.doDelete(sender, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("tp"))
			{
				if(args.length != 3)
				{
					sender.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp tp <SETNAME> <PLAYER>");
					return true;
				}
				Player b = Bukkit.getPlayer(args[2]);
				this.RPG.AM.doTeleportRandom(sender, b, args[1]);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("specific"))
			{
				if(args.length !=3)
				{
					sender.sendMessage(ChatColor.RED + "Syntax: " + ChatColor.RESET + "/rpgtp specific <WARPNAME> <PLATER>");
					return true;
				}
				Player b = Bukkit.getPlayer(args[2]);
				this.RPG.AM.doTeleportSpecific(sender, b, args[1]);
				return true;
			}
		}
		return true;
	}

}
