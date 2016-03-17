package RC2K7.Plugins.RPGBossBars;

import java.util.logging.Level;

import me.ThaH3lper.com.EpicBoss;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import RC2K7.Plugins.RPGBossBars.Commands.CommandHandler;
import RC2K7.Plugins.RPGBossBars.Listener.BossListener;
import RC2K7.Plugins.RPGBossBars.Load.LoadConfig;
import RC2K7.Plugins.RPGBossBars.Settings.Settings;

public class RPGBossBars extends JavaPlugin {
	
	public EpicBoss EB;
	public Settings Setting = new Settings();
	public SAL BossesConfig;
	public LoadConfig LC;
	public BossListener BossEvents;
	
	public void onEnable() {
		if(Bukkit.getPluginManager().isPluginEnabled("EpicBossRecoded"))
		{
			this.EB = (EpicBoss)Bukkit.getPluginManager().getPlugin("EpicBossRecoded");
			this.BossesConfig = new SAL(this, "Bosses.yml");
			this.LC = new LoadConfig(this);
			this.BossEvents = new BossListener(this);
			this.getCommand("bossbar").setExecutor(new CommandHandler(this));
		}
		else
		{
			Bukkit.getLogger().log(Level.SEVERE, "[ROGBossBars] Disabled, Missing Required EpicBossRecoded Plugin!!");
			Bukkit.getPluginManager().disablePlugin(this);
		}
	}

}
