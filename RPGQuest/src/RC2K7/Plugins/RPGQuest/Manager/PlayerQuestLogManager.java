package RC2K7.Plugins.RPGQuest.Manager;

import java.util.ArrayList;
import java.util.List;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Goal.GoalBase;
import RC2K7.Plugins.RPGQuest.Quest.PlayerQuestLog;
import RC2K7.Plugins.RPGQuest.Quest.Quest;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil.GoalType;

public class PlayerQuestLogManager {
	
	private RPGQuest RPG;
	
	private List<PlayerQuestLog> PlayerQuestLogs = new ArrayList<>();
	
	//Constructor
	public PlayerQuestLogManager(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	//Create PlayerQuestLog For Player
	public void addPlayerQuestLog(String name)
	{
		this.PlayerQuestLogs.add(new PlayerQuestLog(this.RPG, name));
	}
	
	//Delete PlayerQuestLog From List
	public void delPlayerQuestLog(String name)
	{
		for(PlayerQuestLog pql : this.PlayerQuestLogs)
		{
			if(pql.getPlayer().equals(name))
			{
				this.PlayerQuestLogs.remove(pql);
			}
		}
	}
	
	//Check If PlayerQuestLog Has Been Created For Player
	public boolean isPlayerQuestLogCreated(String name)
	{
		for(PlayerQuestLog pql : this.PlayerQuestLogs)
		{
			if(pql.getPlayer().equals(name))
			{
				return true;
			}
		}
		return false;
	}
	
	//Get PlayerQuestLog By Player
	public PlayerQuestLog getPlayerQuestLog(String name)
	{
		for(PlayerQuestLog pql : this.PlayerQuestLogs)
		{
			if(pql.getPlayer().equals(name))
			{
				return pql;
			}
		}
		PlayerQuestLog pql = new PlayerQuestLog(this.RPG, name);
		this.PlayerQuestLogs.add(pql);
		return pql;
	}
	
	//Check If Player Has Active Goal And If Goal Is Ready
	public boolean PlayerContainActiveGoal(String name, GoalType goaltype, String detail)
	{
		PlayerQuestLog pql = this.getPlayerQuestLog(name);
		for(Quest quest : pql.getMajorQuests())
		{
			for(GoalBase goal : quest.getGoals())
			{
				if(!goal.getHidden() && goal.getGoalType().equals(goaltype) && goal.checkGoalDetails(name, detail) && !goal.isCompleted())
				{
					return true;
				}
			}
		}
		for(Quest quest : pql.getMinorQuests())
		{
			for(GoalBase goal : quest.getGoals())
			{
				if(!goal.getHidden() && goal.getGoalType().equals(goaltype) && goal.checkGoalDetails(name, detail) && !goal.isCompleted())
				{
					return true;
				}
			}
		}
		return false;
	}

}
