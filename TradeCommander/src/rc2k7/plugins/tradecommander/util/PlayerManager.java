package rc2k7.plugins.tradecommander.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import rc2k7.plugins.tradecommander.base.Invite;
import rc2k7.plugins.tradecommander.base.Trade;

public class PlayerManager {
	
	private static String stub = ChatColor.GOLD + "TradeCommander: " + ChatColor.RESET;

	public static boolean isPlayerInTrade(String name){
		for(Trade trade : TradeManager.getTradeList())
			if(trade.containsPlayer(name))
				return true;
		return false;
	}
	
	public static boolean isPlayerInvited(String name){
		for(Invite invite : InviteManager.getInviteList())
			if(invite.containsPlayer(name))
				return true;
		return false;
	}
	
	public static void sendStubMessage(String p, String m){
		sendMessage(p, stub + m);
	}
	
	public static void sendMessage(String name, String m){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getDisplayName().equals(name)){
				player.sendMessage(m);
				return;
			}
	}
	
	public static Player getPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getDisplayName().contains(name))
				return player;
		return null;
	}
	
	public static Player getExactPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getDisplayName().equals(name))
				return player;
		return null;
	}
	
}
