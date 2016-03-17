package RC2K7.Plugins.RPGQuest.Actions;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil.GoalType;

public class GoalActions {
	
	private RPGQuest RPG;
	
	public GoalActions(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	public void OnKillMonster(String name, String mob)
	{
		if(this.RPG.PQLM.PlayerContainActiveGoal(name, GoalType.KILL, mob.replaceAll("_", " ")))
		{
			this.RPG.PQLM.getPlayerQuestLog(name).GoalActionDone(GoalType.KILL, mob.replaceAll("_", " "), name);
		}
	}
	
	public boolean onItemPickup(String name, String itemname)
	{
		if(this.RPG.PQLM.PlayerContainActiveGoal(name, GoalType.COLLECT, itemname.replaceAll("_", " ")))
		{
			this.RPG.PQLM.getPlayerQuestLog(name).GoalActionDone(GoalType.COLLECT, itemname.replaceAll("_", " "), name);
			return true;
		}
		return false;
	}
	
	public void onRegionEnter(String name, String region)
	{
		if(this.RPG.PQLM.PlayerContainActiveGoal(name, GoalType.TRAVEL, region.replaceAll("_", " ")))
		{
			this.RPG.PQLM.getPlayerQuestLog(name).GoalActionDone(GoalType.TRAVEL, region.replaceAll("_", " "), name);
		}
	}
	
	public void onTalk(String name, String npcname)
	{
		if(this.RPG.PQLM.PlayerContainActiveGoal(name, GoalType.TALK, npcname.replaceAll("_", " ")))
		{
			this.RPG.PQLM.getPlayerQuestLog(name).GoalActionDone(GoalType.TALK, npcname.replaceAll("_", " "), name);
		}
	}

}
