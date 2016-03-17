package rc2k7.plugins.tradecommander.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rc2k7.plugins.tradecommander.util.ActionManager;
import rc2k7.plugins.tradecommander.util.PlayerManager;

public class TCCommands {
	
	public static boolean onCommand(CommandSender sender, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("You Cannot Use The Console For These Commands");
			return true;
		}
		
		Player player = (Player)sender;
		if(args.length != 1){
			PlayerManager.sendStubMessage(player.getDisplayName(), "Syntax Error: /tc <PLAYER|INVITE|ACCEPT|DENY|INV>");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("accept")){
			ActionManager.doAccept(player.getDisplayName());
			return true;
		}
		
		if(args[0].equalsIgnoreCase("deny")){
			ActionManager.doDeny(player.getDisplayName());
			return true;
		}
		
		if(args[0].equalsIgnoreCase("inv")){
			ActionManager.doShowTrade(player.getDisplayName());
			return true;
		}
		
		Player b = PlayerManager.getPlayer(args[0]);
		if(b == null || player.getDisplayName().equals(b.getDisplayName())){
			PlayerManager.sendStubMessage(player.getDisplayName(), "Cannot Find Player.");
			return true;
		}
		ActionManager.doInvite(player.getDisplayName(), b.getDisplayName());
		return false;
	}

}
