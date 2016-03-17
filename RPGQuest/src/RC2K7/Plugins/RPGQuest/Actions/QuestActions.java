package RC2K7.Plugins.RPGQuest.Actions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Quest.PlayerQuestLog;
import RC2K7.Plugins.RPGQuest.Quest.Quest;

public class QuestActions {
	
	RPGQuest RPG;
	
	String Stub = ChatColor.GOLD + "RPGQuests: " + ChatColor.RESET;
	
	public QuestActions(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	//Action Call << Adds Quest To A Player
	public void AddQuest(Player player, Quest quest)
	{
		if(!this.RPG.PQLM.isPlayerQuestLogCreated(player.getName()))
		{
			this.RPG.PQLM.addPlayerQuestLog(player.getName());
		}
		PlayerQuestLog PQL = this.RPG.PQLM.getPlayerQuestLog(player.getName());
		if(PQL.containsQuest(quest))
		{
			player.sendMessage(this.Stub + "You Already Started This Quest!");
			return;
		}
		if(PQL.canTakeQuest(quest.getMajor()))
		{
			player.sendMessage(this.Stub + "You Cannot Take Anymore Of That Type Of Quests!");
			return;
		}
		PQL.addQuest(quest);
		player.sendMessage(this.Stub + "You Have Started Quest " + ChatColor.BLUE + quest.getQuestName() + ChatColor.RESET + ".");
	}

}
