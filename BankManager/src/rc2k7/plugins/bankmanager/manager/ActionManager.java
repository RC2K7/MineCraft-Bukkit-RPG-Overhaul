package rc2k7.plugins.bankmanager.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import rc2k7.plugins.bankmanager.BankManager;
import rc2k7.plugins.bankmanager.inventory.SerializedInventory;
import rc2k7.plugins.bankmanager.util.Util;

public class ActionManager {
	
	public static void openBank(String name){
		BankManager.plugin.reloadConfig();
		String bank = BankManager.plugin.getConfig().getString(name);
		Inventory inv = null;
		Player player = Util.getPlayer(name);
		if(BankManager.wgPlugin != null)
			if(!Util.doesRegionContainLoc(player.getLocation())){
				Util.sendMessage(player, "You Cannot Open Your Bank Here");
				return;
			}
		if(bank == null){
			if(player.hasPermission("rc2k7.bankmanager.5row"))
				inv = Bukkit.createInventory(null, 45, "Bank:");
			else if(player.hasPermission("rc2k7.bankmanager.4row"))
				inv = Bukkit.createInventory(null, 36, "Bank:");
			else if(player.hasPermission("rc2k7.bankmanager.3row"))
				inv = Bukkit.createInventory(null, 27, "Bank:");
			else if(player.hasPermission("rc2k7.bankmanager.2row"))
				inv = Bukkit.createInventory(null, 18, "Bank:");
			else if(player.hasPermission("rc2k7.bankmanager.1row"))
				inv = Bukkit.createInventory(null, 9, "Bank:");
		}
		else
			inv = SerializedInventory.deSerializeInventory(bank);
		
		if(player.hasPermission("rc2k7.bankmanager.5row"))
			inv = Util.upgradeInventory(inv, 45);
		else if(player.hasPermission("rc2k7.bankmanager.4row"))
			inv = Util.upgradeInventory(inv, 36);
		else if(player.hasPermission("rc2k7.bankmanager.3row"))
			inv = Util.upgradeInventory(inv, 27);
		else if(player.hasPermission("rc2k7.bankmanager.2row"))
			inv = Util.upgradeInventory(inv, 18);
		else if(player.hasPermission("rc2k7.bankmanager.1row"))
			inv = Util.upgradeInventory(inv, 9);
		
		if(inv != null)
			Util.getPlayer(name).openInventory(inv);
	}

}
