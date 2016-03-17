package rc2k7.plugins.tradecommander.util;

import org.bukkit.entity.Player;

import rc2k7.plugins.tradecommander.base.Invite;
import rc2k7.plugins.tradecommander.base.Trade;
import rc2k7.plugins.tradecommander.variables.Variables;

public class ActionManager {
	
	/*
	 * 
	 */
	public static void doInvite(String a, String b){
		if(PlayerManager. isPlayerInTrade(a) || PlayerManager.isPlayerInvited(a))
			return;
		if(PlayerManager.isPlayerInTrade(b) || PlayerManager.isPlayerInvited(b))
			return;
		Player pA = PlayerManager.getExactPlayer(a);
		Player pB = PlayerManager.getExactPlayer(b);
		if(Variables.onlyLocalTrade && pA.getLocation().distance(pB.getLocation()) > Variables.localDistance){
			PlayerManager.sendMessage(a, b + " Is Too Far Away.");
			return;
		}
		InviteManager.addInvite(a, b);
	}
	
	/*
	 * 
	 */
	public static void doAccept(String name){
		Invite invite = null;
		if(!PlayerManager.isPlayerInTrade(name) && !PlayerManager.isPlayerInvited(name))
			return;
		if((invite = InviteManager.getInvite(name)) != null)
			invite.doAccept();
	}
	
	/*
	 * 
	 */
	public static void doDeny(String name){
		Invite invite = null;
		if((invite = InviteManager.getInvite(name)) != null)
			invite.doDeny();
	}
	
	public static void doClose(String name){
		Trade trade = null;
		if((trade = TradeManager.getTrade(name)) != null)
			trade.doClose(name);
	}
	
	/*
	 * 
	 */
	public static void doShowTrade(String name){
		if(!PlayerManager.isPlayerInTrade(name)){
			PlayerManager.sendStubMessage(name, "You Are Not In A Trade.");
			return;
		}
		TradeManager.getTrade(name).doShowTrade(name);
	}

}
