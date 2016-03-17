package rc2k7.plugins.bankmanager.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import rc2k7.plugins.bankmanager.BankManager;
import rc2k7.plugins.bankmanager.inventory.SerializedInventory;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event){
		if(!event.getInventory().getTitle().equals("Bank:"))
			return;
		Player player = (Player)event.getPlayer();
		String tmpBank = SerializedInventory.serializeInventory(event.getInventory());
		BankManager.plugin.getConfig().set(player.getName(), tmpBank);
		BankManager.plugin.saveConfig();
	}

}
