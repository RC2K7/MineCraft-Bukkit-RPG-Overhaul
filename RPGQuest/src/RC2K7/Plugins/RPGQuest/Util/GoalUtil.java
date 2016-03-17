package RC2K7.Plugins.RPGQuest.Util;

import RC2K7.Plugins.RPGQuest.RPGQuest;

public class GoalUtil {
	
	RPGQuest RPG;
	
	//Constructor
	public GoalUtil(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	//Goal Type Enumerator
	public enum GoalType
	{
		COLLECT,
		KILL,
		TRAVEL,
		TALK,
		USE
	}
	
	//Check To See If Input Goal Is A Goal Type
	public boolean isGoalType(String goal)
	{
		if(goal.equals("COLLECT")) return true;
		if(goal.equals("KILL")) return true;
		if(goal.equals("TRAVEL")) return true;
		if(goal.equals("TALK")) return true;
		if(goal.equals("USE")) return true;
		return false;
	}
	
	//Get GoalType From String
	public GoalType getGoalType(String goal)
	{
		if(goal.equals("COLLECT")) return GoalType.COLLECT;
		if(goal.equals("KILL")) return GoalType.KILL;
		if(goal.equals("TRAVEL")) return GoalType.TRAVEL;
		if(goal.equals("TALK")) return GoalType.TALK;
		if(goal.equals("USE")) return GoalType.USE;
		return null;
	}

}
