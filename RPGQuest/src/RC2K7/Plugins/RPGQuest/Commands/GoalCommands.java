package RC2K7.Plugins.RPGQuest.Commands;

import RC2K7.Plugins.RPGAPI.CommandAPI.MultiCmd;
import RC2K7.Plugins.RPGAPI.CommandAPI.SubInfo;
import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Goal.GoalBase;
import RC2K7.Plugins.RPGQuest.Goal.KillGoal;
import RC2K7.Plugins.RPGQuest.Goal.UseGoal;
import RC2K7.Plugins.RPGQuest.Quest.Quest;
import RC2K7.Plugins.RPGQuest.Util.GoalSelection;

public class GoalCommands {

	RPGQuest RPG;
	
	public GoalCommands(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	@MultiCmd(description="List All Goals For A Quest",
			permission="rpgq.admin.g.list",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=true)
	public void list(SubInfo info)
	{
		if(!this.RPG.QM.containsQuest(info.getIntArgs(0).replaceAll("_", " ")))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		Quest quest = this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " "));
		if(quest.getGoals().isEmpty())
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "No Goals Have Been Set!");
			return;
		}
		for(int x = 0; x < quest.getGoals().size(); x++)
		{
			MultiColorUtil.send(info.getSender(), "{gold}%d {reset}%s", x, quest.getGoals().get(x).getString());
		}
	}
	
	@MultiCmd(description="Creates And Selects A Goal To Modify",
			permission="rpgq.admin.g.create",
			minArgs=1,
			usage="<Goal_Type{Kill|Collect|Use|Talk|Travel}>",
			allowConsole=true)
	public void create(SubInfo info)
	{
		if(!this.RPG.GoalUtility.isGoalType(info.getIntArgs(0)))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "{red}Invalid Goal Type {gold} {Kill|Collect|Use|Talk|Travel}");
			return;
		}
		
		GoalBase goal = null;
		
		switch(this.RPG.GoalUtility.getGoalType(info.getIntArgs(0)))
		{
		case KILL:
			goal = new KillGoal(this.RPG, "", -1);
			break;
		case USE:
			goal = new UseGoal(this.RPG, "", "");
			break;
		case COLLECT:
			break;
		case TALK:
			break;
		case TRAVEL:
			break;
		default:
			break;
		}
		
		GoalEditCommands.GoalCreator.put(info.getSender(), new GoalSelection(null, -1, goal));
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "{green}Created And Selected A New Goal");
	}
	
	@MultiCmd(description="Gets Information For The Selected Goal",
			permission="rpgq.admin.g.info",
			minArgs=0,
			usage="",
			allowConsole = true)
	public void info(SubInfo info)
	{
		if(!GoalEditCommands.GoalCreator.containsKey(info.getSender()))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
			return;
		}
		GoalEditCommands.GoalCreator.get(info.getSender()).getGoal().showInfo(info.getSender());
	}
	
	@MultiCmd(description="Deletes A Goal From Quest At Index",
			permission="rpgq.admin.g.delete",
			minArgs=2,
			usage="<Quest_Name> <Index>",
			allowConsole=true)
	public void delete(SubInfo info)
	{
		if(!this.RPG.QM.containsQuest(info.getIntArgs(0).replaceAll("_", " ")))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		int index = Integer.parseInt(info.getIntArgs(1));
		Quest quest = this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " "));
		if(index >= quest.getGoals().size())
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Index Does Not Exist!");
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Type {green}/rpgq g list {gold}<QUEST_NAME> {reset}To Get Goal Index.");
			return;
		}
		quest.getGoals().remove(index);
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Goal At Index [{blue}%d{reset}] Has Been Removed.", index);
	}
	
	@MultiCmd(description="Selects A Goal To Modify",
			permission="rpgq.admin.q.select",
			minArgs=2,
			usage="<Quest_Name> <Index>",
			allowConsole=true)
	public void select(SubInfo info)
	{
		if(!this.RPG.QM.containsQuest(info.getIntArgs(0).replaceAll("_", " ")))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		int index = Integer.valueOf(info.getIntArgs(1));
		if(index >= this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " ")).getGoals().size())
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Index Does Not Exist!");
			return;
		}
		GoalEditCommands.GoalCreator.put(info.getSender(), new GoalSelection(info.getIntArgs(0).replaceAll("_", " "), index, this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " ")).getGoals().get(index)));
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Goal At Index [{green}%d{reset}] From Quest {blue}%s {reset}Has Been Selected.", index, info.getIntArgs(0).replaceAll("_", " "));
	}
	
	@MultiCmd(description="Clears Your Selection",
			permission="rpgq.admin.g.clear",
			minArgs=0,
			usage="",
			allowConsole=true)
	public void clear(SubInfo info)
	{
		if(!GoalEditCommands.GoalCreator.containsKey(info.getSender()))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Do Not Have A Goal Selected.");
			return;
		}
		GoalEditCommands.GoalCreator.remove(info.getSender());
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Your Selected Goal Has Been Cleared.");
	}
	
	@MultiCmd(description="Saves Goal",
			permission="rpgq.admin.g.save",
			minArgs=0,
			usage="<[Quest_Name]>",
			allowConsole=true)
	public void save(SubInfo info)
	{
		if(!GoalEditCommands.GoalCreator.containsKey(info.getSender()))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
			return;
		}
		GoalSelection sel = GoalEditCommands.GoalCreator.get(info.getSender());
		
		if(sel.getQuest() != null)
		{
			if(!this.RPG.QM.containsQuest(sel.getQuest()))
			{
				sel.setQuest(null);
				sel.setIndex(-1);
				GoalEditCommands.GoalCreator.put(info.getSender(), sel);
				MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Could Not Find Predefined Quest. Type /{green}rpgq g save {gold}<Quest_Name>");
				return;
			}
			if(sel.getIndex() != -1)
			{
				this.RPG.QM.getQuest(sel.getQuest()).getGoals().set(sel.getIndex(), sel.getGoal());
			}else
			{
				this.RPG.QM.getQuest(sel.getQuest()).addGoal(sel.getGoal());
			}
                        this.RPG.LoadConfig.SaveQuest(this.RPG.QM.getQuest(sel.getQuest()));
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Successfuly Saved Goal To {blue}%s{reset}.", sel.getQuest());
		}else
		{
			if(info.getNumArgs() < 1)
			{
				MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Could Not Find Predefined Quest. Type /{green}rpgq g save {gold}<Quest_Name>");
				return;
			}else
			{
				if(!this.RPG.QM.containsQuest(info.getIntArgs(0).replaceAll("_", " ")))
				{
					MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Could Not Find Quest.");
					return;
				}else
				{
					this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " ")).addGoal(sel.getGoal());
                                        this.RPG.LoadConfig.SaveQuest(this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " ")));
					MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Successfuly Saved Goal To {blue}%s{reset}.", info.getIntArgs(0).replaceAll("_", " "));
				}
			}
		}
		GoalEditCommands.GoalCreator.remove(info.getSender());
	}
	
}
