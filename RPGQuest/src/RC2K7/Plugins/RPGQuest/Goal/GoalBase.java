package RC2K7.Plugins.RPGQuest.Goal;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.command.CommandSender;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil.GoalType;
import org.bukkit.configuration.ConfigurationSection;

public abstract class GoalBase
{
	
	//Main Class
	private RPGQuest RPG = null;
	private GoalType GT = null;
	
	//Individual Goal
	private boolean Completed = false;
	private boolean Hidden = false;
	private Set<Integer> PreReqs = new HashSet<>();
	private Set<String> Regions = new HashSet<>();
	
	//Quest Strings
	private String GoalStartText = null;
	private String GoalFinishText = null;
	
	public GoalBase(RPGQuest rpg, GoalType gt)
	{
		this.setRPG(rpg);
		this.setGT(gt);
	}
	
	public void setPreReqs(Set<Integer> set)
	{
		this.PreReqs = set;
	}
	
	public void addPreReq(int integer)
	{
		this.PreReqs.add(integer);
	}
	
	public void remPreReq(int integer)
	{
		this.PreReqs.remove(integer);
	}
	
	public void clearPreReq()
	{
		this.PreReqs.clear();
	}
	
	public void setRegions(Set<String> set)
	{
		this.Regions = set;
	}
	
	public void addRegion(String str)
	{
		this.Regions.add(str);
	}
	
	public void remRegion(String str)
	{
		this.Regions.remove(str);
	}
	
	public void clearRegions()
	{
		this.Regions.clear();
	}
	
	public void setCompleted(boolean toggle)
	{
		this.Completed = toggle;
	}
	
	public void setHidden(boolean toggle)
	{
		this.Hidden = toggle;
	}
	
	public void setRPG(RPGQuest rpg) {
		RPG = rpg;
	}
	
	public void setBeginText(String str)
	{
		this.GoalStartText = str;
	}
	
	public void setCompletedText(String str)
	{
		this.GoalFinishText = str;
	}
	
	private void setGT(GoalType gt)
	{
		this.GT = gt;
	}
	
	public void showInfo(CommandSender playername)
	{
		MultiColorUtil.send(playername, "{gold}--- Goal Info ---");
		MultiColorUtil.send(playername, "{green}GoalType: {reset}%s", String.valueOf(this.GT));
		
		if(this.PreReqs.isEmpty())
		{
			MultiColorUtil.send(playername, "{green}PreReqs: {reset}NONE");
		}else
		{
			StringBuilder sbPreReq = new StringBuilder();
			Iterator<Integer> itrPreReq = this.PreReqs.iterator();
			while(itrPreReq.hasNext())
			{
				sbPreReq.append(itrPreReq.next());
				if(itrPreReq.hasNext())
				{
					sbPreReq.append(", ");
				}
			}
			
			MultiColorUtil.send(playername, "{green}PreReqs: {reset}%s", sbPreReq.toString());
		}
		
		if(this.Regions.isEmpty())
		{
			MultiColorUtil.send(playername, "{green}Regions: {reset}ANYWHERE");
		}else
		{
			StringBuilder sbRegions = new StringBuilder();
			Iterator<String> itrRegions = this.Regions.iterator();
			while(itrRegions.hasNext())
			{
				sbRegions.append(itrRegions.next());
				if(itrRegions.hasNext())
				{
					sbRegions.append(", ");
				}
			}
			
			MultiColorUtil.send(playername, "{green}Regions: {reset}%s", sbRegions.toString());
		}
		
		MultiColorUtil.send(playername, "{green}GoalStartText: {reset}%s", this.GoalStartText);
		MultiColorUtil.send(playername, "{green}GoalFinishText: {reset}%s", this.GoalFinishText);
	}
	
	public Set<Integer> getPreReqs(){ return this.PreReqs; }
        public Set<String> getRegions(){ return this.Regions; }
	public boolean isCompleted(){ return this.Completed; }
	public boolean getHidden(){ return this.Hidden; }
	public RPGQuest getRPG() { return RPG; }
	public String getBeginText(){ return this.GoalStartText; }
	public String getCompletedText(){ return this.GoalFinishText; }
	public GoalType getGoalType(){ return this.GT; }
	
	//Abstract Methods Dependent On Goal
	public abstract String getGoalActionText();
	public abstract String getGoalActionCompletedText();
	public abstract boolean checkGoalDetails(String playername, String detail);
	public abstract void doAction();
	public abstract String getString();
        
        public ConfigurationSection getConfigSection(ConfigurationSection cs)
        {
            //Default Goal Properties
            cs.set("GoalType", String.valueOf(getGoalType()));
            cs.set("GoalStartText", getBeginText());
            cs.set("GoalEndText", getCompletedText());
            cs.set("PreReqs", getPreReqs());
            cs.set("Regions", getRegions());
            return cs;
        }
        
        public void loadConfigSection(ConfigurationSection cs)
        {
            //Default Goal Loading
            setGT(this.RPG.GoalUtility.getGoalType(cs.getString("GoalType")));
            setBeginText(cs.getString("GoalStartText"));
            setCompletedText(cs.getString("GoalEndText"));
            setPreReqs((Set<Integer>)cs.get("PreReqs"));
            setRegions((Set<String>)cs.get("Regions"));
        }
	
}
