package rc2k7.plugins.rollercore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import rc2k7.plugins.rollercore.util.RollManager;

public class InvEvents implements Listener {
	
	/*This Disables Removing Items
	 * From The Roll Inventory
	 * 
	 */
	@EventHandler
	public void onInvClick(InventoryClickEvent event){
		if(RollManager.getRollList() == null)
			return;
		if(event.getInventory().getName().equals("Rolling For:"))
			event.setCancelled(true);
	}

}
