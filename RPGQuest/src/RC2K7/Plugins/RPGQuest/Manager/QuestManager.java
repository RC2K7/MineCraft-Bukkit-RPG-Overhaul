package RC2K7.Plugins.RPGQuest.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Quest.Quest;

public class QuestManager {
	
	RPGQuest RPG;
	List<Quest> QuestList = new ArrayList<>();
	
	public QuestManager(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	public void addQuest(String name, boolean major)
	{
		this.QuestList.add(new Quest(this.RPG, name, major));
	}
	
	public void delQuest(String name)
	{
		this.QuestList.remove(this.getQuest(name));
	}
	
	public Quest getQuest(String name)
	{
		for(Quest quest : this.QuestList)
		{
			if(quest.getQuestName().equals(name))
			{
				return quest;
			}
		}
		return null;
	}
	
	public Quest getQuest(LivingEntity le)
	{
		for(Quest quest : this.QuestList)
		{
			if(quest.getStartNPC().getLivingEntity().equals(le))
			{
				return quest;
			}
		}
		return null;
	}
	
	public boolean containsQuest(String name)
	{
		for(Quest quest : this.QuestList)
		{
			if(quest.getQuestName().equals(name))
			{
				return true;
			}
		}
		return false;
	}
	
	public List<Quest> getQuests(){ return this.QuestList; }

}
