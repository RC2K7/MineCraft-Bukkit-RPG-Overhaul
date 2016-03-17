package RC2K7.Plugins.RPGQuest.Goal;

import org.bukkit.command.CommandSender;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil.GoalType;
import org.bukkit.configuration.ConfigurationSection;

public class KillGoal extends GoalBase
{
	
	//Goal Details
	private String MobName = null;

	//Variables
	private int Required = 1;
	private int Amount = 0;
	
	public KillGoal(RPGQuest rpg, String mobname, int required)
	{
		super(rpg, GoalType.KILL);
		this.MobName = mobname.replaceAll("_", " ");
		this.Required = required;
	}

	@Override
	public String getGoalActionText()
	{
		return MultiColorUtil.colorText(this.getRPG().QuestSettings.Stub + "You Have Killed {blue}%d {reset}Of {green}%d {gold}%s{reset}.", this.Amount, this.Required, this.MobName);
	}

	@Override
	public boolean checkGoalDetails(String playername, String detail)
	{
		if(this.MobName.equals(detail))
		{
			return true;
		}
		return false;
	}

	@Override
	public void doAction()
	{
		this.Amount++;
		if(this.Amount >= this.Required)
		{
			this.setCompleted(true);
		}
	}

	@Override
	public String getString()
	{
		return MultiColorUtil.colorText("KILL %s %d", this.MobName, this.Required);
	}
	
	@Override
	public String getGoalActionCompletedText()
	{
		return MultiColorUtil.colorText(this.getRPG().QuestSettings.Stub + "You Have Killed All {gold}%s{reset}.", this.MobName);
	}

	@Override
	public void showInfo(CommandSender playername)
	{
		super.showInfo(playername);
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
