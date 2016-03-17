package RC2K7.Plugins.RPGTeleport;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import RC2K7.Plugins.RPGTeleport.Commands.CommandHandler;
import RC2K7.Plugins.RPGTeleport.Managers.ActionManager;
import RC2K7.Plugins.RPGTeleport.RCConfig.LoadWarp;
import RC2K7.Plugins.RPGTeleport.RCConfig.SaveLoad;
import RC2K7.Plugins.RPGTeleport.Teleport.TeleportSet;
import RC2K7.Plugins.RPGTeleport.Util.WarpUtil;

public class RPGTeleport extends JavaPlugin{
	
	public SaveLoad WarpConfig;
	public LoadWarp LoadWarps;
	public ActionManager AM;
	public WarpUtil WarpUtil;
	
	public List<TeleportSet> TeleportSets = new ArrayList<>();
	
	public void onEnable()
	{
		this.WarpConfig = new SaveLoad(this, "Warps.yml");
		this.LoadWarps = new LoadWarp(this);
		this.AM = new ActionManager(this);
		this.WarpUtil = new WarpUtil(this);
		
		this.getCommand("rpgtp").setExecutor(new CommandHandler(this));
	}

	public void onDisable()
	{
		
	}
	
}
