package rc2k7.plugins.bankmanager.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rc2k7.plugins.bankmanager.manager.ActionManager;

public class BankCommands {
	
	public static boolean bankCommands(CommandSender sender, String[] args){
		if(!(sender instanceof Player))
			return true;
		ActionManager.openBank(((Player)sender).getName());
		return true;
	}

}
