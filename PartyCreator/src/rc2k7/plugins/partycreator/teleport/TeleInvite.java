package rc2k7.plugins.partycreator.teleport;

import org.bukkit.entity.Player;

import rc2k7.plugins.partycreator.PartyCreator;
import rc2k7.plugins.partycreator.Util;

public class TeleInvite implements Runnable {
	
	private Player a;
	private Player b;
	
	private Thread thread = null;
	private boolean isRunning = false;
	
	public TeleInvite(Player a, Player b){
		isRunning = true;
		Util.sendMessage(a, "Teleport Request Sent To " + b.getDisplayName());
		Util.sendMessage(b, a.getDisplayName() + " Wants To Teleport You.");
		this.a = a;
		this.b = b;
		thread = new Thread(this);
		thread.start();
	}
	
	public void accept(){
		isRunning = false;
		Util.sendMessage(a, b.getDisplayName() + " Has Accepted Teleportation");
		Util.sendMessage(a, "Do Not Move For 5 Seconds");
		Util.sendMessage(b, "Do Not Move For 5 Seconds");
		TeleTimer tt = new TeleTimer(a, b);
		new Thread(tt).start();
		PartyCreator.tiList.remove(this);
	}
	
	public void deny(){
		isRunning = false;
		Util.sendMessage(a, b.getDisplayName() + " Has Denied Teleportation.");
		PartyCreator.tiList.remove(this);
	}
	
	public String getTargetPlayer(){ return b.getDisplayName(); }
	
	@Override
	public void run() {
		try {
			int cd = 30;
			while(cd > 0){
				Thread.sleep(1000);
				cd--;
				if(!isRunning)
					return;
			}
			deny();
			Util.sendMessage(b, "Teleport Invitation Timed Out.");
		} catch (Exception e) {
		}
	}

}
