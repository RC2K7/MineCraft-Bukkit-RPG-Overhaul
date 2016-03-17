package rc2k7.plugins.rpgitemcreator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import me.ThaH3lper.com.EpicBoss;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.me.tft_02.soulbound.Soulbound;
import rc2k7.plugins.rpgitemcreator.commands.CommandHandler;
import rc2k7.plugins.rpgitemcreator.listener.DeathListener;
import rc2k7.plugins.rpgitemcreator.load.LoadBoss;
import rc2k7.plugins.rpgitemcreator.load.LoadConfigs;
import rc2k7.plugins.rpgitemcreator.load.LoadLevel;

public class RPGItemCreator extends JavaPlugin {
	
	RPGItemCreator plugin;
	public EpicBoss EB;
	public Soulbound SB;
	public final Logger log = Bukkit.getLogger();
	
	public SaveLoad itemConfig;
	public LoadConfigs loadConfig;
	public DeathListener deathListener;
	
	public List<LoadLevel> LevelList = new ArrayList<>();
	public List<LoadBoss> BossList = new ArrayList<>();
	
	@Override
	public void onEnable() {
		this.plugin = this;
		if((this.EB = (EpicBoss)Bukkit.getPluginManager().getPlugin("EpicBossRecoded")) != null)
			log.log(Level.INFO, "[RPGItemCreator] Hooked Onto EpicBossRecoded.");
		if((this.SB = (Soulbound)Bukkit.getPluginManager().getPlugin("Soulbound")) != null)
			log.log(Level.INFO, "[RPGItemCreator] Hooked Onto SoulBound.");
		
		this.getCommand("rpgitemcreator").setExecutor(new CommandHandler(this));
		
		this.itemConfig = new SaveLoad(this, "Items.yml");
		this.loadConfig = new LoadConfigs(this);
		
		this.deathListener = new DeathListener(this);
	}

}
