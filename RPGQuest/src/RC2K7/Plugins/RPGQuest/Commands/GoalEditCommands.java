package RC2K7.Plugins.RPGQuest.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;

import RC2K7.Plugins.RPGAPI.CommandAPI.MultiCmd;
import RC2K7.Plugins.RPGAPI.CommandAPI.SubInfo;
import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Util.GoalSelection;

public class GoalEditCommands
{
	
	private RPGQuest RPG = null;
	
	public static Map<CommandSender, GoalSelection> GoalCreator = new HashMap<CommandSender, GoalSelection>();
	
	public GoalEditCommands(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	@MultiCmd(description="Adds A PreReq",
			permission="rpgq.admin.g.edit",
			minArgs=1,
			usage="<Goal_Index>",
			allowConsole = true)
	public void addprereq(SubInfo info)
	{
		if(!GoalCreator.containsKey(info.getSender()))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
			return;
		}
		int index = Integer.valueOf(info.getIntArgs(0));
		GoalCreator.get(info.getSender()).getGoal().addPreReq(index);
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Added PreReq [{green}%d{reset}] To Selected Goal.", index);
	}
	
	@MultiCmd(description="Removes A PreReq",
			permission="rpgq.admin.g.edit",
			minArgs=1,
			usage="<Index>",
			allowConsole=true)
	public void remprereq(SubInfo info)
	{
		if(!GoalCreator.containsKey(info.getSender()))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
			return;
		}
		int index = Integer.valueOf(info.getIntArgs(0));
		GoalCreator.get(info.getSender()).getGoal().remPreReq(index);
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Deleted PreReq [{green}%d{reset}] From Selected Goal.", index);
	}
	
	@MultiCmd(description="Clears All PreReqs",
			permission="rpgq.admin.g.edit",
			minArgs=0,
			usage="",
			allowConsole=true)
	public void clearprereq(SubInfo info)
	{
		if(!GoalCreator.containsKey(info.getSender()))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
			return;
		}
		GoalCreator.get(info.getSender()).getGoal().clearPreReq();
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Deleted {green}All {reset}PreReq From Selected Goal.");
	}
        
        @MultiCmd(description = "Add A Region",
                permission = "rpgq.admin.g.edit",
                minArgs = 1,
                usage = "<Region_Name>",
                allowConsole = false)
        public void addregion(SubInfo info)
        {
            if(!GoalCreator.containsKey(info.getSender()))
            {
                MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
                return;
            }
            GoalCreator.get(info.getSender()).getGoal().addRegion(info.getIntArgs(0));
            MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Added Region {green}%s{reset} To Selected Goal.", info.getIntArgs(0));
        }
        
        @MultiCmd(description = "Remove A Region",
                permission = "rpgq.admin.g.edit",
                minArgs = 1,
                usage = "<Region_Name>",
                allowConsole = false)
        public void remregion(SubInfo info)
        {
            if(!GoalCreator.containsKey(info.getSender()))
            {
                MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
                return;
            }
            GoalCreator.get(info.getSender()).getGoal().remRegion(info.getIntArgs(0));
            MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Removed Region {green}%s{reset} To Selected Goal.", info.getIntArgs(0));
        }       
        @MultiCmd(description = "Clears All Regions",
                permission = "rpgq.admin.g.edit",
                minArgs = 0,
                usage = "",
                allowConsole = false)
        public void clearregions(SubInfo info)
        {
            if(!GoalCreator.containsKey(info.getSender()))
            {
                MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "You Must Select Or Create A Goal First.");
                return;
            }
            GoalCreator.get(info.getSender()).getGoal().clearRegions();
            MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Deleted {green}All {reset}Regions From Selected Goal.");
        }

}
