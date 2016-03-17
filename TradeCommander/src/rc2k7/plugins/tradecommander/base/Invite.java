package rc2k7.plugins.tradecommander.base;

import org.bukkit.scheduler.BukkitTask;

import rc2k7.plugins.tradecommander.TradeCommander;
import rc2k7.plugins.tradecommander.task.InviteTask;
import rc2k7.plugins.tradecommander.util.InviteManager;
import rc2k7.plugins.tradecommander.util.PlayerManager;
import rc2k7.plugins.tradecommander.util.TradeManager;
import rc2k7.plugins.tradecommander.variables.Variables;

public class Invite {
	
	private String a;
	private String b;
	private BukkitTask task = null;
	
	public Invite(String a, String b) {
		this.a = a;
		this.b = b;
		PlayerManager.sendStubMessage(a, b + " Has Been Invited To Trade.");
		PlayerManager.sendStubMessage(b, a + " Has Invited You To Trade.");
		task = new InviteTask(this).runTaskLater(TradeCommander.tradeCommander, Variables.inviteTimeOut * 20);
	}
	
	public void doAccept(){
		task.cancel();
		PlayerManager.sendStubMessage(a, b + " Accepted Trade.");
		PlayerManager.sendStubMessage(b, "Accepted Trade.");
		TradeManager.addTrade(a, b);
		InviteManager.remInvite(this);
	}
	
	public void doDeny(){
		task.cancel();
		PlayerManager.sendStubMessage(a, b + " Denied Trade.");
		PlayerManager.sendStubMessage(b, "Denied Trade.");
		InviteManager.remInvite(this);
	}
	
	public boolean containsPlayer(String name){
		if(a.equals(name) || b.equals(name))
			return true;
		return false;
	}

}
