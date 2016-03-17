package rc2k7.plugins.partycreator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rc2k7.plugins.rollercore.api.RollerAPI;
import rc2k7.plugins.rollercore.base.Roll;

public class Party {
	
	private List<Player> members = new ArrayList<>();

	public Party(Player leader) {
		this.members.add(leader);
		if(PartyCreator.hasHighRoller){
			List<String> temp = new ArrayList<>();
			for(Player player : this.members)
				temp.add(player.getDisplayName());
			RollerAPI.addRoll(new Roll(temp));
		}
	}
	
	public void addPlayer(Player name){
		if(Util.getPlayerFromList(this.members, name.getDisplayName()) != null)
			return;
		Util.sendMessage(this.members, name.getDisplayName() + " Has Joined The Party.");
		this.members.add(name);
		if(PartyCreator.hasHighRoller){
			List<String> temp = new ArrayList<>();
			for(Player player : this.members)
				temp.add(player.getDisplayName());
			RollerAPI.setPlayers(temp, PartyCreator.pList.indexOf(this));
		}
	}
	
	public void addItem(ItemStack itm){
		RollerAPI.addItem(itm, PartyCreator.pList.indexOf(this));
	}
	
	public void removePlayer(Player name){
		Player tmp = null;
		if((tmp = Util.getPlayerFromList(this.members, name.getDisplayName())) == null)
			return;
		this.members.remove(tmp);
		if(PartyCreator.hasHighRoller){
			List<String> temp = new ArrayList<>();
			for(Player player : this.members)
				temp.add(player.getDisplayName());
			RollerAPI.setPlayers(temp, PartyCreator.pList.indexOf(this));
		}
		Util.sendMessage(this.members, name.getDisplayName() + " Has Left The Party.");
		if(this.members.size() == 1){
			if(PartyCreator.pcList.contains(this.members.get(0).getDisplayName()))
				PartyCreator.pcList.remove(this.members.get(0).getDisplayName());
			Util.sendMessage(this.members.get(0), "Party Has Been Disbanded.");
			RollerAPI.remRoll(PartyCreator.pList.indexOf(this));
			PartyCreator.pList.remove(this);
		}
	}
	
	public void promotePlayer(Player name){
		Player tmp = null;
		if((tmp = Util.getPlayerFromList(this.members, name.getDisplayName())) == null)
			return;
		this.members.remove(tmp);
		this.members.add(0, name);
		Util.sendMessage(this.members, name.getDisplayName() + " Has Been Promoted To Leader.");
	}
	
	public String getLeader(){return this.members.get(0).getDisplayName();}
	
	public List<Player> getMembers(){return this.members;}
	
}
