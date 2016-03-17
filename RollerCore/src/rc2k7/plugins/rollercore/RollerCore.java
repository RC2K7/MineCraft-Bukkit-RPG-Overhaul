package rc2k7.plugins.rollercore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import rc2k7.plugins.rollercore.listener.InvEvents;
import rc2k7.plugins.rollercore.variables.Variable;

public class RollerCore extends JavaPlugin{
	
	public static JavaPlugin plugin = null;
	public static RollerCore rc = null;
	
	public void onEnable() {
		plugin = this;
		rc = this;
		Bukkit.getPluginManager().registerEvents(new InvEvents(), this);
		saveDefaultConfig();
		loadConfig();
	}
	
	public void onDisable() {
	}
	
	public void loadConfig(){
		Variable.rollDuration = getConfig().getInt("RollDuration");
	}

}
