package RC2K7.Plugins.RPGBossBars.Load;

import java.util.ArrayList;

import RC2K7.Plugins.RPGBossBars.RPGBossBars;

public class LoadConfig {
	
	RPGBossBars RPG;
	
	public LoadConfig(RPGBossBars rpg)
	{
		this.RPG = rpg;
		LoadConfigs();
	}
	
	public void LoadConfigs()
	{
		if(this.RPG.BossesConfig.getConfig().contains("ShowBossHealthBars"))
		{
			this.RPG.Setting.ShowBossHealthBars = this.RPG.BossesConfig.getConfig().getBoolean("ShowBossHealthBars");
		}
		if(this.RPG.BossesConfig.getConfig().contains("ShowOnAllBosses"))
		{
			this.RPG.Setting.ShowOnAllBosses = this.RPG.BossesConfig.getConfig().getBoolean("ShowOnAllBosses");
		}
		if(this.RPG.BossesConfig.getConfig().contains("Bosses"))
		{
			this.RPG.Setting.BossList = new ArrayList<>(this.RPG.BossesConfig.getConfig().getStringList("Bosses"));
		}
	}
	
	public void SaveConfigs()
	{
		this.RPG.BossesConfig.getConfig().set("ShowBossHealthBars", this.RPG.Setting.ShowBossHealthBars);
		this.RPG.BossesConfig.getConfig().set("ShowOnAllBosses", this.RPG.Setting.ShowOnAllBosses);
		this.RPG.BossesConfig.getConfig().set("Bosses", this.RPG.Setting.BossList);
		this.RPG.BossesConfig.saveConfig();
	}

}
