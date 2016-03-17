package rc2k7.plugins.bankmanager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import rc2k7.plugins.bankmanager.commands.BankCommands;
import rc2k7.plugins.bankmanager.listeners.InventoryListener;

public class BankManager extends JavaPlugin {
	
	public static Plugin plugin;
	public static WorldGuardPlugin wgPlugin = null;

	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
		if(Bukkit.getPluginManager().isPluginEnabled("WorldGuard")){
			wgPlugin =  (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
			getLogger().info(ChatColor.RED + "[BankManager] Hooked Onto WorldGuard");
		}
	}
	
	public void onDisable() {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(label.equalsIgnoreCase("bank"))
			return BankCommands.bankCommands(sender, args);
		return false;
	}
	
}
