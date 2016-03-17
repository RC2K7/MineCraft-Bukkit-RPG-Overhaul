package rc2k7.plugins.rpgitemcreator.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rc2k7.plugins.rpgitemcreator.RPGItemCreator;
import rc2k7.plugins.rpgitemcreator.createitem.CreateLevelItem;

public class CommandPlayer {
	
	public RPGItemCreator rpg;
	
	public CommandPlayer(RPGItemCreator rpg){
		this.rpg = rpg;
	}
	
	public boolean Command(Player sender, String label, String[] args){
		if(!sender.isOp())
		{
			sender.sendMessage(ChatColor.RED + "You Are Not An Op And Have Been Flagged For Cheating");
			return true;
		}
		if(args.length >= 1){
			if(args[0].equalsIgnoreCase("reload")){
				this.rpg.itemConfig.reloadConfig();
				this.rpg.loadConfig.LoadLevels();
				this.rpg.loadConfig.LoadBosses();
				sender.sendMessage(ChatColor.GREEN + "RPGItemCreator Has Been Reloaded!");
				return true;
			}
			
			if(args.length == 2){
				if(args[0].equalsIgnoreCase("createlevel")){
					sender.getInventory().addItem(new CreateLevelItem(this.rpg, Integer.parseInt(args[1])).getItemStack());
					return true;
				}
			}
		}
		return false;
	}

}
