package RC2K7.Plugins.RPGQuest.Goal;

import org.bukkit.command.CommandSender;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGAPI.Util.PlayerUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil.GoalType;
import org.bukkit.configuration.ConfigurationSection;

public class UseGoal extends GoalBase
{

	//Variables
	String ItemName = null;
	String RegionName = null;
	
	public UseGoal(RPGQuest rpg, String itemname, String regionname)
	{
		super(rpg, GoalType.USE);
		this.ItemName = itemname.replaceAll("_", " ");
		this.RegionName = regionname.replaceAll("_", " ");
	}
	
	@Override
	public String getGoalActionText() 
	{
		return MultiColorUtil.colorText(this.getRPG().QuestSettings.Stub + "You Have Sucessfully Used The Item {blue}%s{reset}.", this.ItemName);
	}

	@Override
	public boolean checkGoalDetails(String playername, String detail)
	{
		if(this.ItemName.equals(detail))
		{
			if(this.RegionName == null)
			{
				return true;
			}
			
			if(PlayerUtil.isPlayerInRegion(playername, this.RegionName))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void doAction()
	{
		this.setCompleted(true);
	}

	@Override
	public String getString()
	{
		return MultiColorUtil.colorText("USE %s %s", this.ItemName, this.RegionName == null ? "ANYWHERE" : this.RegionName);
	}

	@Override
	public String getGoalActionCompletedText()
	{
		return null;
	}

	@Override
	public void showInfo(CommandSender playername)
	{
		super.showInfo(playername);
                return;
	}
        
        @Override
        public ConfigurationSection getConfigSection(ConfigurationSection cs)
        {
            cs = super.getConfigSection(cs);
            return cs;
        }
        
        @Override
        public void loadConfigSection(ConfigurationSection cs)
        {
            super.loadConfigSection(cs);
        }
	
}
