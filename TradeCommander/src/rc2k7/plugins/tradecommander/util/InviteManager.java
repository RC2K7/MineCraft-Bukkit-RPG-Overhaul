package rc2k7.plugins.tradecommander.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import rc2k7.plugins.tradecommander.base.Invite;

public class InviteManager {

	private static List<Invite> list = new ArrayList<>();
	
	public static void addInvite(String a, String b){
		list.add(new Invite(a, b));
	}
	
	public static void remInvite(int index){
		if(index >= list.size())
			return;
		list.remove(index);
	}
	
	public static void remInvite(Invite inv){
		if(!list.contains(inv))
			return;
		list.remove(inv);
	}
	
	public static Invite getInvite(int i){
		if(i >= list.size())
			return null;
		return list.get(i);
	}
	
	public static Invite getInvite(Player p){
		for(Invite inv : list)
			if(inv.containsPlayer(p.getDisplayName()))
				return inv;
		return null;
	}
	
	public static Invite getInvite(String name){
		for(Invite inv : list)
			if(inv.containsPlayer(name))
				return inv;
		return null;
	}
	
	public static List<Invite> getInviteList(){return list;}
}
