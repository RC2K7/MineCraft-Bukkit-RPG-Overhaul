package RC2K7.Plugins.RPGQuest;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import RC2K7.Plugins.RPGAPI.CommandAPI.MultiCommand;
import RC2K7.Plugins.RPGAPI.Config.CustomConfig;
import RC2K7.Plugins.RPGAPI.Config.ExtendedFileConfig;
import RC2K7.Plugins.RPGQuest.Actions.GoalActions;
import RC2K7.Plugins.RPGQuest.Commands.BasicCommand;
import RC2K7.Plugins.RPGQuest.Commands.GoalCommands;
import RC2K7.Plugins.RPGQuest.Commands.GoalEditCommands;
import RC2K7.Plugins.RPGQuest.Commands.QuestEditCommands;
import RC2K7.Plugins.RPGQuest.Listeners.GoalListener;
import RC2K7.Plugins.RPGQuest.Listeners.PlayerListener;
import RC2K7.Plugins.RPGQuest.Listeners.QuestNPCListeners;
import RC2K7.Plugins.RPGQuest.LoadConfig.LoadConfigs;
import RC2K7.Plugins.RPGQuest.Manager.PlayerQuestLogManager;
import RC2K7.Plugins.RPGQuest.Manager.QuestManager;
import RC2K7.Plugins.RPGQuest.Manager.QuestNPCManager;
import RC2K7.Plugins.RPGQuest.MySQL.MySQL;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;
import RC2K7.Plugins.RPGQuest.Util.GoalUtil;
import RC2K7.Plugins.RPGQuest.Util.ItemUtil;
import RC2K7.Plugins.RPGQuest.Util.Settings;
import java.io.File;

public class RPGQuest extends JavaPlugin {
    
    public MySQL mysql;
    
    //Actions
    public GoalActions GA;
    
    //Managers
    public QuestNPCManager QNPCM;
    public QuestManager QM;
    public PlayerQuestLogManager PQLM;
    
    //Listeners
    public QuestNPCListeners NPCListener;
    public PlayerListener PL;
    public GoalListener GL;
    
    //Configuration
    public CustomConfig NPCConfig;
    public ExtendedFileConfig QuestConfig;
    public CustomConfig PlayerLogConfig;
    public LoadConfigs LoadConfig;
    
    //Utility|Helpful
    public OverWrite overWrite;
    public ItemUtil ItemUtility;
    public GoalUtil GoalUtility;
    public Settings QuestSettings;
    
    @Override
    public void onEnable() {
        
        //Command Handling By RPGAPI
        
        MultiCommand base = new MultiCommand();
        base.autoRegisterFrom(new BasicCommand(this));
        getCommand("rpgq").setExecutor(base);
        
        MultiCommand qbase = new MultiCommand();
        qbase.autoRegisterFrom(new QuestEditCommands(this));
        base.addSub("q", "rpgq.admin.q").setDescription("Quest Edit Menu").setMinArgs(0).setUsage("").allowConsole().setHandler(qbase);
        
        MultiCommand gbase = new MultiCommand();
        gbase.autoRegisterFrom(new GoalCommands(this));
        base.addSub("g", "rpgq.admin.g").setDescription("Goal Menu").setMinArgs(0).setUsage("").allowConsole().setHandler(gbase);
        
        MultiCommand gebase = new MultiCommand();
        gebase.autoRegisterFrom(new GoalEditCommands(this));
        gbase.addSub("edit", "rpgq.admin.g.edit").setDescription("Goal Edit Menu").setMinArgs(0).setUsage("").allowConsole().setHandler(gebase);
        
        ////////////////////////////////////////////////////
        
        this.GA = new GoalActions(this);
        
        this.QNPCM = new QuestNPCManager(this);
        this.QM = new QuestManager(this);
        this.PQLM = new PlayerQuestLogManager(this);
        
        this.NPCListener = new QuestNPCListeners(this);
        this.PL = new PlayerListener(this);
        this.GL = new GoalListener(this);
        
        this.overWrite = new OverWrite();
        this.ItemUtility = new ItemUtil(this);
        this.GoalUtility = new GoalUtil(this);
        this.QuestSettings = new Settings();
        
        this.NPCConfig = new CustomConfig(this, "NPCs.yml");
        try
        {
            this.QuestConfig = new ExtendedFileConfig(this, new File(this.getDataFolder(), "Quests.yml"));
        } catch (Exception e){}
        
        this.PlayerLogConfig = new CustomConfig(this, "PlayerLogs.yml");
        this.NPCConfig.saveDefaultConfig();
        this.LoadConfig = new LoadConfigs(this);
        
        for(World world : Bukkit.getWorlds())
        {
            for(Chunk chunk : world.getLoadedChunks())
            {
                this.NPCListener.loadQuestNPCS(chunk);
            }
        }
        
        //Spawn NPCS 10 Second Repeatable Timer
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run()
            {
                for(QuestNPC npc : QNPCM.QuestNPCs)
                {
                    if(npc.getLocation().getLocation().getChunk().isLoaded())
                    {
                        if(npc.getLivingEntity() == null || npc.getLivingEntity().isDead() || !checkLocation(npc.getLivingEntity().getLocation(), npc.getLocation().getLocation()))
                        {
                            npc.deSpawn();
                            npc.spawn();
                        }
                    }
                }
            }
        }, 200, 200);
    }
    
    @Override
    public void onDisable() {
        this.QNPCM.deSpawnAll();
    }
    
    //Check To See If Positions Match
    public boolean checkLocation(Location a, Location b)
    {
        if((int)a.getX() == (int)b.getX() && (int)a.getY() == (int)b.getY() && (int)a.getZ() == (int)b.getZ())
            return true;
        return false;
    }
    
}
