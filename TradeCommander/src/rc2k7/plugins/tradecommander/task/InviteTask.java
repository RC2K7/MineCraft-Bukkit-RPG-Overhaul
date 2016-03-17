package rc2k7.plugins.tradecommander.task;

import org.bukkit.scheduler.BukkitRunnable;

import rc2k7.plugins.tradecommander.base.Invite;

public class InviteTask extends BukkitRunnable {
	
	private static Invite invite = null;
	
	public InviteTask(Invite inv) {
		invite = inv;
	}

	@Override
	public void run() {
		invite.doDeny();
	}

}
