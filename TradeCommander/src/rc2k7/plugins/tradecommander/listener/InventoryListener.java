package rc2k7.plugins.tradecommander.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import rc2k7.plugins.tradecommander.base.Trade;
import rc2k7.plugins.tradecommander.util.ActionManager;
import rc2k7.plugins.tradecommander.util.PlayerManager;
import rc2k7.plugins.tradecommander.util.TradeHolder.Slot;
import rc2k7.plugins.tradecommander.util.TradeManager;

public class InventoryListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInv(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		if(event.getInventory().getTitle() != "Trade:")
			return;
		if(!PlayerManager.isPlayerInTrade(player.getDisplayName()))
			return;
		if(event.getRawSlot() < 0)
			return;
		if(event.getCurrentItem().getTypeId() == 0 && event.getCursor().getTypeId() == 0){
			return;
		}
		Trade trade = TradeManager.getTrade(player.getDisplayName());
		Slot loc = trade.getHolder().getSlot(event.getRawSlot());
		Slot playerLoc = trade.getPlayerSlot(player.getDisplayName());
		if(playerLoc.equals(loc)){
			setItem(event);
			trade.setBothDeny();
			player.updateInventory();
			return;
		}
		if(loc == null)
			return;
		if(loc.equals(Slot.SLOTACCEPT))
			trade.doAccept(player.getDisplayName());
		if(loc.equals(Slot.SLOTDENY))
			trade.doDeny(player.getDisplayName());
		cancelClick(event);
	}
	
	private void setItem(InventoryClickEvent event){
		ItemStack tmp = event.getCurrentItem();
		event.setCurrentItem(event.getCursor());
		event.setCursor(tmp);
		event.setCancelled(true);
		event.setResult(Result.ALLOW);
	}
	
	@SuppressWarnings("deprecation")
	private void cancelClick(InventoryClickEvent event){
		event.setCursor(event.getCursor());
		event.setCurrentItem(event.getCurrentItem());
		event.setCancelled(true);
		event.setResult(Result.DENY);
		((Player)event.getWhoClicked()).updateInventory();
	}
	
	@EventHandler
	public void onInvClose(InventoryCloseEvent event){
		Player player = (Player)event.getPlayer();
		if(!event.getInventory().getTitle().equals("Trade:"))
			return;
		if(!PlayerManager.isPlayerInTrade(player.getDisplayName()))
			return;
		ActionManager.doClose(player.getDisplayName());
	}

}
