package RC2K7.Plugins.RPGQuest.Quest;

import java.util.ArrayList;
import java.util.List;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGAPI.Util.PlayerUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Goal.GoalBase;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil.GoalType;

public class PlayerQuestLog {
	
	//Variables
	private RPGQuest RPG;
	
	private String Player;
	
	private List<Quest> MajorQuests = new ArrayList<>();
	private List<Quest> MinorQuests = new ArrayList<>();
	
	//Constructor
	public PlayerQuestLog(RPGQuest rpg, String name)
	{
		this.RPG = rpg;
		this.Player = name;
	}
	
	//Add Quest To Quest Log
	public void addQuest(Quest quest)
	{
		if(quest.getMajor()) this.MajorQuests.add(quest);
		if(!quest.getMajor()) this.MinorQuests.add(quest);
	}
	
	//Delete Quest From Quest Log
	public void delQuest(Quest quest)
	{
		if(quest.getMajor()) this.MajorQuests.remove(quest);
		if(!quest.getMajor()) this.MinorQuests.remove(quest);
	}
	
	//Check If Player Can Obtain Another Quest
	public boolean canTakeQuest(boolean isMajor)
	{
		if(isMajor && this.MajorQuests.size() < this.RPG.QuestSettings.MaxMajorQuests) return true;
		if(!isMajor && this.MinorQuests.size() < this.RPG.QuestSettings.MaxMinorQuests) return true;
		return false;
	}
	
	//Check If Player Already Has Started A Quest
	public boolean containsQuest(Quest quest)
	{
		if(this.MajorQuests.contains(quest)) return true;
		if(this.MinorQuests.contains(quest)) return true;
		return false;
	}
	
	//Increment Count For Goals
	public void GoalActionDone(GoalType goaltype, String detail, String name)
	{
		for(Quest quest : this.MajorQuests)
		{
			for(GoalBase goal : quest.getGoals())
			{
				if(!goal.getHidden() && goal.getGoalType().equals(goaltype) && goal.checkGoalDetails(name, detail) && !goal.isCompleted())
				{
					goal.doAction();
					if(goal.isCompleted())
					{
						if(goal.getGoalActionCompletedText() != null)
						{
							MultiColorUtil.send(name, goal.getGoalActionCompletedText());
						}
						if(goal.getCompletedText() != null)
						{
							MultiColorUtil.send(name, goal.getCompletedText());
						}
					}else
					{
						PlayerUtil.getPlayer(name).sendMessage(goal.getGoalActionText());
					}
				}
			}
		}
		
		for(Quest quest : this.MinorQuests)
		{
			for(GoalBase goal : quest.getGoals())
			{
				if(!goal.getHidden() && goal.getGoalType().equals(goaltype) && goal.checkGoalDetails(name, detail) && !goal.isCompleted())
				{
					goal.doAction();
					if(goal.isCompleted())
					{
						if(goal.getGoalActionCompletedText() != null)
						{
							MultiColorUtil.send(name, goal.getGoalActionCompletedText());
						}
						if(goal.getCompletedText() != null)
						{
							MultiColorUtil.send(name, goal.getCompletedText());
						}
					}else
					{
						PlayerUtil.getPlayer(name).sendMessage(goal.getGoalActionText());
					}
				}
			}
		}
	}
	
	//Getter Functions
	public String getPlayer(){ return this.Player; }
	public List<Quest> getMajorQuests(){ return this.MajorQuests; }
	public List<Quest> getMinorQuests(){ return this.MinorQuests; }

}
