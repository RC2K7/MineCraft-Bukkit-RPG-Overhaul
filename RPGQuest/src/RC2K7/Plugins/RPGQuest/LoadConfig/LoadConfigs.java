package RC2K7.Plugins.RPGQuest.LoadConfig;

import RC2K7.Plugins.RPGQuest.Goal.GoalBase;
import RC2K7.Plugins.RPGQuest.Goal.KillGoal;
import RC2K7.Plugins.RPGQuest.Goal.UseGoal;
import org.bukkit.inventory.Inventory;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;
import RC2K7.Plugins.RPGQuest.Quest.Quest;
import RC2K7.Plugins.RPGQuest.Serialization.SerializeInventory;
import RC2K7.Plugins.RPGQuest.Serialization.SerializeLocation;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;

public final class LoadConfigs {
    
    RPGQuest RPG;
    
    public LoadConfigs(RPGQuest rpg)
    {
        this.RPG = rpg;
        LoadNPCs();
        LoadQuests();
    }
    
    public void LoadNPCs()
    {
        if(this.RPG.NPCConfig.getCustomConfig() != null)
        {
            this.RPG.QNPCM.QuestNPCs.clear();
            String path = "NPCS.Names";
            if(this.RPG.NPCConfig.getCustomConfig().contains(path))
            {
                if(this.RPG.NPCConfig.getCustomConfig().getConfigurationSection(path) == null){
                    this.RPG.NPCConfig.getCustomConfig().createSection(path);
                }
                for(String name : this.RPG.NPCConfig.getCustomConfig().getConfigurationSection(path).getKeys(false))
                {
                    int ID = 0;
                    SerializeLocation Loc = null;
                    Inventory Inv = null;
                    path = "NPCS.Names." + name;
                    if(this.RPG.NPCConfig.getCustomConfig().contains(path + ".EntityType"))
                    {
                        ID = this.RPG.NPCConfig.getCustomConfig().getInt(path + ".EntityType");
                    }
                    if(this.RPG.NPCConfig.getCustomConfig().contains(path + ".Location"))
                    {
                        Loc = new SerializeLocation(this.RPG.NPCConfig.getCustomConfig().getString(path + ".Location"));
                    }
                    if(this.RPG.NPCConfig.getCustomConfig().contains(path + ".Equipment"))
                    {
                        Inv = SerializeInventory.deSerializeInventory(this.RPG.NPCConfig.getCustomConfig().getString(path + ".Equipment"));
                    }
                    this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, name, ID, Loc, Inv));
                }
            }
        }
    }
    
    public void SaveNPCs()
    {
        if(this.RPG.NPCConfig.getCustomConfig() != null)
        {
            this.RPG.NPCConfig.reloadCustomConfig();
            this.RPG.NPCConfig.getCustomConfig().createSection("NPCS.Names");
            for(QuestNPC npc : this.RPG.QNPCM.QuestNPCs)
            {
                this.RPG.NPCConfig.getCustomConfig().set("NPCS.Names." + npc.getName() + ".EntityType", npc.getEntityType().getTypeId());
                this.RPG.NPCConfig.getCustomConfig().set("NPCS.Names." + npc.getName() + ".Location", npc.getLocation().getString());
                this.RPG.NPCConfig.getCustomConfig().set("NPCS.Names." + npc.getName() + ".Equipment", SerializeInventory.serializeInventory(npc.getInventory()));
            }
            this.RPG.NPCConfig.saveCustomConfig();
        }
    }
    
    public void LoadQuests()
    {
        if(this.RPG.QuestConfig.getConfig() != null)
        {
            this.RPG.QM.getQuests().clear();
            if(this.RPG.QuestConfig.hasPath("Quest"))
            {
                for(String questname : this.RPG.QuestConfig.getSection("Quest").getKeys(false))
                {
                    boolean Enabled = this.RPG.QuestConfig.getBoolean(String.format("Quest.%s.Enabled", questname));
                    String QuestText = this.RPG.QuestConfig.getString(String.format("Quest.%s.QuestText", questname));
                    boolean Major = this.RPG.QuestConfig.getBoolean(String.format("Quest.%s.Major", questname));
                    List<String> PreReq = this.RPG.QuestConfig.getList(String.format("Quest.%s.PreReq", questname));
                    SerializeLocation StartLoc = (this.RPG.QuestConfig.getString(String.format("Quest.%s.StartNPC", questname)).equals("NULL") ? null : new SerializeLocation(this.RPG.QuestConfig.getString(String.format("Quest.%s.StartNPC", questname))));
                    SerializeLocation EndLoc = (this.RPG.QuestConfig.getString(String.format("Quest.%s.EndNPC", questname)).equals("NULL") ? null : new SerializeLocation(this.RPG.QuestConfig.getString(String.format("Quest.%s.EndNPC", questname))));
                    List<GoalBase> Goals = new ArrayList<>();
                    if(this.RPG.QuestConfig.hasPath(String.format("Quest.%s.Goals", questname)))
                    {
                        for(String index : this.RPG.QuestConfig.getSection(String.format("Quest.%s.Goals", questname)).getKeys(false))
                        {
                            ConfigurationSection cs = this.RPG.QuestConfig.getSection(String.format("Quest.%s.Goals.%s", questname, String.valueOf(index)));
                            GoalBase goal = null;
                            switch(this.RPG.GoalUtility.getGoalType(cs.getString("GoalType")))
                            {
                                case KILL:
                                    goal = new KillGoal(this.RPG, "", -1);
                                    break;
                                case USE:
                                    goal = new UseGoal(this.RPG, "", "");
                                    break;
                            }
                            goal.loadConfigSection(cs);
                            Goals.add(goal);
                        }
                    }
                    Quest quest = new Quest(this.RPG, questname, Major);
                    quest.setQuestText(QuestText);
                    quest.setPreReqs(PreReq);
                    quest.linkStart(StartLoc == null ? null : this.RPG.QNPCM.getNPCAtLocation(StartLoc.getLocation()));
                    quest.linkEnd(EndLoc == null ? null : this.RPG.QNPCM.getNPCAtLocation(EndLoc.getLocation()));
                    quest.setGoals(Goals);
                    this.RPG.QM.getQuests().add(quest);
                }
            }
        }
    }
    
    public void SaveQuests()
    {
        if(this.RPG.QuestConfig.getConfig()!= null)
        {
            this.RPG.QuestConfig.createSection("Quests");
            for(Quest quest : this.RPG.QM.getQuests())
            {
                this.RPG.QuestConfig.set(String.format("Quest.%s.Enabled", quest.getQuestName()), quest.isEnabled());
                this.RPG.QuestConfig.set(String.format("Quest.%s.QuestText", quest.getQuestName()), quest.getQuestText());
                this.RPG.QuestConfig.set(String.format("Quest.%s.Major", quest.getQuestName()), quest.getMajor());
                this.RPG.QuestConfig.set(String.format("Quest.%s.PreReq", quest.getQuestName()), quest.getPreReq());
                this.RPG.QuestConfig.set(String.format("Quest.%s.StartNPC", quest.getQuestName()), quest.getStartNPC().getLocation().getString());
                this.RPG.QuestConfig.set(String.format("Quest.%s.EndNPC", quest.getQuestName()), quest.getEndNPC().getLocation().getString());
                for(int x = 0; x < quest.getGoals().size(); x++)
                {
                    ConfigurationSection cs = this.RPG.QuestConfig.createSection(String.format("Quest.%s.Goals.%d", quest.getQuestName(), x));
                    cs = quest.getGoals().get(x).getConfigSection(cs);
                }
            }
            this.RPG.QuestConfig.saveConfig();
        }
    }
    
    public void SaveQuest(Quest quest)
    {
        if(this.RPG.QuestConfig.getConfig() != null)
        {
            this.RPG.QuestConfig.createSection(String.format("Quest.%s", quest.getQuestName()));
            
            this.RPG.QuestConfig.set(String.format("Quest.%s.Enabled", quest.getQuestName()), quest.isEnabled());
            this.RPG.QuestConfig.set(String.format("Quest.%s.QuestText", quest.getQuestName()), quest.getQuestText());
            this.RPG.QuestConfig.set(String.format("Quest.%s.Major", quest.getQuestName()), quest.getMajor());
            this.RPG.QuestConfig.set(String.format("Quest.%s.PreReq", quest.getQuestName()), quest.getPreReq());
            if(quest.getStartNPC() != null)
                this.RPG.QuestConfig.set(String.format("Quest.%s.StartNPC", quest.getQuestName()), quest.getStartNPC().getLocation().getString());
            else
                this.RPG.QuestConfig.set(String.format("Quest.%s.StartNPC", quest.getQuestName()), "NULL");
            if(quest.getEndNPC() != null)
                this.RPG.QuestConfig.set(String.format("Quest.%s.EndNPC", quest.getQuestName()), quest.getEndNPC().getLocation().getString());
            else
                this.RPG.QuestConfig.set(String.format("Quest.%s.EndNPC", quest.getQuestName()), "NULL");
            for(int x = 0; x < quest.getGoals().size(); x++)
            {
                ConfigurationSection cs = this.RPG.QuestConfig.createSection(String.format("Quest.%s.Goals.%d", quest.getQuestName(), x));
                cs = quest.getGoals().get(x).getConfigSection(cs);
            }
            this.RPG.QuestConfig.saveConfig();
        }
    }
    
}
