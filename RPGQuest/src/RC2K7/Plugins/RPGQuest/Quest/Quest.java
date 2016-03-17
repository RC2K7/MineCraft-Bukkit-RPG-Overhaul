package RC2K7.Plugins.RPGQuest.Quest;

import java.util.ArrayList;
import java.util.List;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Goal.GoalBase;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;

public class Quest {
	
	//Main Class
	private RPGQuest RPG;
	
	//Variables
	private boolean Enabled = false;
	private String QuestName = new String();
	private String QuestText = new String();
	private boolean Major = false;
	
	private List<String> PreReq = new ArrayList<>();
	private List<GoalBase> Goals = new ArrayList<>();
	
	private QuestNPC EntStart = null;
	private QuestNPC EntEnd = null;
	
	//Constructor
	public Quest(RPGQuest rpg, String name, boolean isMajor)
	{
		this.RPG = rpg;
		this.QuestName = name;
		this.Major = isMajor;
	}
	
	//Link Quest Starter QuestNPC To This Quest
	public void linkStart(QuestNPC qnpc)
	{
		this.EntStart = qnpc;
	}
	
	//Link Quest Turn In QuestNPC To This Quest
	public void linkEnd(QuestNPC qnpc)
	{
		this.EntStart = qnpc;
	}
	
	//Add A Goal To This Quest
	public void addGoal(GoalBase gb)
	{
		this.Goals.add(gb);
	}
	
	//Remove A Goal From This Quest
	public void remGoal(int i)
	{
		if(this.Goals.size() > i)
		{
			this.Goals.remove(i);
		}
	}
	
	//Sets Wether The Quest Is Enabled or Disabled
	public void setEnable(boolean enable)
	{
		this.Enabled = enable;
	}
        
        //Sets The Quest Text
        public void setQuestText(String txt)
        {
            this.QuestText = txt;
        }
        
        //Sets The PreReqs
        public void setPreReqs(List<String> list)
        {
            this.PreReq = list;
        }
        
        //Sets The Goals
        public void setGoals(List<GoalBase> list)
        {
            this.Goals = list;
        }
        
        //Sets The Major
        public void setMajor(boolean major)
        {
            this.Major = major;
        }
	
	//Getters
	public boolean isEnabled(){ return this.Enabled; }
	public String getQuestName(){ return this.QuestName; }
	public String getQuestText(){ return this.QuestText; }
	public List<String> getPreReq(){ return this.PreReq; }
	public List<GoalBase> getGoals(){ return this.Goals; }
	public QuestNPC getStartNPC(){ return this.EntStart; }
	public QuestNPC getEndNPC(){ return this.EntEnd; }
	public boolean getMajor(){ return this.Major; }

}
