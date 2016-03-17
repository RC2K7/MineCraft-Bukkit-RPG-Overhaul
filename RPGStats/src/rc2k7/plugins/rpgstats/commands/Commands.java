package rc2k7.plugins.rpgstats.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rc2k7.plugins.rpgstats.RPGStats;
import rc2k7.plugins.rpgstats.api.PlayerStats;
import rc2k7.plugins.rpgstats.manager.PlayerStatsManager;
import rc2k7.plugins.rpgstats.util.Util;

public class Commands {
	
	public static boolean onCommand(CommandSender sender, String[] args){
		if(sender instanceof Player)
			if(!((Player)sender).isOp())
				return true;
		if(args.length == 1){
			if(args[0].equalsIgnoreCase("reload")){
				RPGStats.plugin.reload();
				sender.sendMessage("Reloaded Configs");
				return true;
			}
		}
		
		if(args.length >= 2){
			if(args[0].equalsIgnoreCase("addlore")){
				if(!(sender instanceof Player))
					return true;
				Player player = (Player)sender;
				if(!player.isOp())
					return true;
				String tmp = new String();
				for(int x = 1; x < args.length; x++)
					tmp += args[x] + " ";
				tmp = tmp.substring(0, tmp.length() - 1);
				player.setItemInHand(Util.addLore(player.getItemInHand(), tmp));
			}
			
			if(args[0].equalsIgnoreCase("dellore")){
				if(!(sender instanceof Player))
					return true;
				Player player = (Player)sender;
				if(!player.isOp())
					return true;
				String tmp = new String();
				for(int x = 1; x < args.length; x++)
					tmp += args[x] + " ";
				tmp = tmp.substring(0, tmp.length() - 1);
				player.setItemInHand(Util.delLore(player.getItemInHand(), tmp));
			}
		}
			
		if(args.length == 3){
			if(args[0].equalsIgnoreCase("addexp")){
				Player player = Bukkit.getPlayer(args[1]);
				PlayerStats ps = PlayerStatsManager.getPlayerStats(player.getName());
				if(ps != null)ps.addExp(Integer.parseInt(args[2]));
				else sender.sendMessage("Could Not Find PlayerStats.");
				return true;
			}
			if(args[0].equalsIgnoreCase("setexp")){
				Player player = Bukkit.getPlayer(args[1]);
				PlayerStats ps = PlayerStatsManager.getPlayerStats(player.getName());
				if(ps != null)ps.setExp(Integer.parseInt(args[2]));
				else sender.sendMessage("Could Not Find PlayerStats");
				return true;
			}
		}
		return true;
	}
	
}
