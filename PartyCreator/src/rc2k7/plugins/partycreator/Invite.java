package rc2k7.plugins.partycreator;

import org.bukkit.entity.Player;

public class Invite implements Runnable {
	
	private Player leader;
	private Player user;
	
	private Thread thread = null;
	private boolean isRunning = false;
	
	public Invite(Player leader, Player user) {
		this.leader = leader;
		this.user = user;
		Util.sendMessage(user, this.leader.getDisplayName() + " Has Invited You To A Party.");
		this.isRunning = true;
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void acceptInvite(){
		isRunning = false;
		Party party = Util.getParty(leader.getDisplayName());
		if(party == null ){
			Util.sendMessage(user, "Party No Longer Exists");
			PartyCreator.iList.remove(this);
			return;
		}
		party.addPlayer(user);
		Util.sendMessage(user, "Accepted Invitation.");
		PartyCreator.iList.remove(this);
	}
	
	public void denyInvite(){
		isRunning = false;
		Party party = Util.getParty(leader.getDisplayName());
		if(party != null){
			Util.sendMessage(party.getMembers(), user.getDisplayName() + " Denied Invitation.");
			if(party.getMembers().size() == 1 && !Util.hasPlayerInvited(this.leader.getDisplayName()))
				party.removePlayer(this.leader);
		}
		Util.sendMessage(user, "Denied Invivitation.");
		
		PartyCreator.iList.remove(this);
	}
	
	public String getUser(){return this.user.getDisplayName();}
	
	public String getLeader(){return this.leader.getDisplayName();}

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
			denyInvite();
			Util.sendMessage(user, "Invitation Timed Out.");
		} catch (Exception e) {
		}
	}

}
