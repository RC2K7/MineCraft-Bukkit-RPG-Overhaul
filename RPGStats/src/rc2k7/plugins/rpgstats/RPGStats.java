package rc2k7.plugins.rpgstats;

import java.io.File;
import java.util.List;

import me.ThaH3lper.com.EpicBoss;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import rc2k7.plugins.rpgstats.api.PlayerStats;
import rc2k7.plugins.rpgstats.commands.Commands;
import rc2k7.plugins.rpgstats.listener.DamageListener;
import rc2k7.plugins.rpgstats.listener.InventoryListener;
import rc2k7.plugins.rpgstats.listener.PlayerListener;
import rc2k7.plugins.rpgstats.manager.PlayerStatsManager;
import rc2k7.plugins.rpgstats.util.Util;
import rc2k7.plugins.rpgstats.util.Variables;

public class RPGStats extends JavaPlugin {
	
	public static EpicBoss eb = null;
	public static RPGStats plugin = null;
	public static YamlConfiguration playerConfig = YamlConfiguration.loadConfiguration(new File("plugins/RPGStats/Players.yml"));
	private static boolean isDebugging = true;
	
	public void onEnable() {
		eb = (EpicBoss)Bukkit.getPluginManager().getPlugin("EpicBossRecoded");
		if(eb != null)
			Bukkit.getLogger().info("[RPGStats]Hooked Onto EPicBoss");
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
		saveDefaultConfig();
		reload();
		for(Player player : Bukkit.getOnlinePlayers())
		{
			PlayerStatsManager.addPlayerStats(player.getName());
			Util.setupStatList(player);
			Util.setStatsToList(player);
		}
	}
	
	public void onDisable() {
	}
	
	@SuppressWarnings("unchecked")
	public void reload(){
		Variables.levels = (List<Integer>) getConfig().getList("Level.Levels");
		Variables.expNeeded = (List<Integer>)getConfig().getList("Level.ExperienceNeeded");
		Variables.health = (List<Integer>)getConfig().getList("Level.Health");
		Variables.baseDamages = (List<Integer>)getConfig().getList("Level.BaseDamages");
		playerConfig = YamlConfiguration.loadConfiguration(new File("plugins/RPGStats/Players.yml"));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(label.equalsIgnoreCase("rpgstats"))
			return Commands.onCommand(sender, args);
		if(isDebugging && label.equalsIgnoreCase("test"))
			if(((Player)sender).isOp()){
				Player player = (Player)sender;
				PlayerStats ps = PlayerStatsManager.getPlayerStats(((Player)sender).getName());
				sender.sendMessage("Health:" + String.valueOf(player.getHealth()) + "/" + String.valueOf(ps.maxHealth));
				sender.sendMessage("Dmg:" + String.valueOf(ps.dmgMin) + "-" + String.valueOf(ps.dmgMax));
				sender.sendMessage("Armor:" + String.valueOf(ps.armor));
				sender.sendMessage("CritChance:" + String.valueOf(ps.critChance));
				sender.sendMessage("CritMult:" + String.valueOf(ps.critMult));
				sender.sendMessage("FireDmg:" + String.valueOf(ps.elemFire));
				sender.sendMessage("WaterDmg:" + String.valueOf(ps.elemWater));
				sender.sendMessage("ElectricityDmg:" + String.valueOf(ps.elemElectricity));
				sender.sendMessage("HolyDmg:" + String.valueOf(ps.elemHoly));
				sender.sendMessage("UnHolyDmg:" + String.valueOf(ps.elemUnHoly));
				sender.sendMessage("AcidDmg:" + String.valueOf(ps.elemAcid));
				sender.sendMessage("FirstResist:" + String.valueOf(ps.resistFire));
				sender.sendMessage("WaterResist:" + String.valueOf(ps.resistWater));
				sender.sendMessage("ElectricityResist:" + String.valueOf(ps.resistElectricity));
				sender.sendMessage("HolyResist:" + String.valueOf(ps.resistHoly));
				sender.sendMessage("UnHolyResist:" + String.valueOf(ps.resistUnHoly));
				sender.sendMessage("AcidResist:" + String.valueOf(ps.resistAcid));
			}
		return true;
	}

}
