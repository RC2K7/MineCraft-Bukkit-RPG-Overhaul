package rc2k7.plugins.rpgstats.manager;

import java.util.ArrayList;
import java.util.List;

import rc2k7.plugins.rpgstats.api.PlayerStats;

public class PlayerStatsManager {
	
	public static List<PlayerStats> playerStats = new ArrayList<>();
	
	public static PlayerStats getPlayerStats(String name){
		for(PlayerStats ps : playerStats)
			if(ps.getPlayerName().equals(name))
				return ps;
		return null;
	}
	
	public static void addPlayerStats(String name){
		if(getPlayerStats(name) != null)
			return;
		playerStats.add(new PlayerStats(name));
	}
	
	public static void removePlayerStats(String name){
		if(getPlayerStats(name) == null)
			return;
		playerStats.remove(getPlayerStats(name));
	}

}
