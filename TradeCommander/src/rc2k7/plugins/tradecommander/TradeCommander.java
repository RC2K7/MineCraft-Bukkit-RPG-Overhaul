package rc2k7.plugins.tradecommander;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import rc2k7.plugins.tradecommander.command.TCCommands;
import rc2k7.plugins.tradecommander.listener.InventoryListener;

public class TradeCommander extends JavaPlugin {
	
	public static Plugin tradeCommander = null;
	
	@Override
	public void onEnable() {
		tradeCommander = this;
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
	}
	
	@Override
	public void onDisable() {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(label.equalsIgnoreCase("tc"))
			return TCCommands.onCommand(sender, args);
		return false;
	}

}
