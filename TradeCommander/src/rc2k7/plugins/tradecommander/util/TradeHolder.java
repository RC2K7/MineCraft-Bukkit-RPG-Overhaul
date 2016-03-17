package rc2k7.plugins.tradecommander.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TradeHolder implements InventoryHolder {
	
	public static enum Slot{
		SLOTA,
		SLOTB,
		SLOTACCEPT,
		SLOTDENY,
		SLOTCONFIRM
	}
	
	private final Inventory inventory = Bukkit.createInventory(this, 36, "Trade:");
	private final int slotA[] = {0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
	private final int slotB[] = {5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35};
	private final int slotAccept[] = {13};
	private final int slotDeny[] = {22};
	private final int slotAConfirm[] = {4};
	private final int slotBConfirm[] = {31};
	
	private ItemStack itmAccept = new ItemStack(Material.WOOL);
	private ItemStack itmDeny = new ItemStack(Material.WOOL);
	private ItemStack itmConfirm = new ItemStack(Material.WOOL);
	private ItemStack itmNConfirm = new ItemStack(Material.WOOL);
	
	public TradeHolder(){
		ItemMeta imAccept = itmAccept.getItemMeta();
		imAccept.setDisplayName("Accept");
		itmAccept.setItemMeta(imAccept);
		imAccept.setDisplayName("Deny");
		itmDeny.setItemMeta(imAccept);
		imAccept.setDisplayName("Waiting...");
		itmNConfirm.setItemMeta(imAccept);
		imAccept.setDisplayName("Confirmed");
		itmConfirm.setItemMeta(imAccept);
		itmAccept.setDurability((short)5);
		itmDeny.setDurability((short)14);
		itmConfirm.setDurability((short)11);
		for(int x : slotAccept)
			inventory.setItem(x, itmAccept);
		for(int x : slotDeny)
			inventory.setItem(x, itmDeny);
		for(int x : slotAConfirm)
			inventory.setItem(x, itmNConfirm);
		for(int x : slotBConfirm)
			inventory.setItem(x, itmNConfirm);
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public Slot getSlot(int x){
		if(arrayContains(slotA, x))
			return Slot.SLOTA;
		if(arrayContains(slotB, x))
			return Slot.SLOTB;
		if(arrayContains(slotAccept, x))
			return Slot.SLOTACCEPT;
		if(arrayContains(slotDeny, x))
			return Slot.SLOTDENY;
		if(arrayContains(slotAConfirm, x) || arrayContains(slotBConfirm, x))
			return Slot.SLOTCONFIRM;
		return null;
	}
	
	public boolean arrayContains(int[] x, int y){
		for(int z : x)
			if(z == y)
				return true;
		return false;
	}
	
	public void PlayerAConfirm(){
		for(int x : slotAConfirm)
			inventory.setItem(x, itmConfirm);
	}
	
	public void PlayerBConfirm(){
		for(int x : slotBConfirm)
			inventory.setItem(x, itmConfirm);
	}
	
	public void PlayerAWaiting(){
		for(int x : slotAConfirm){
			inventory.remove(x);
			inventory.setItem(x, itmNConfirm);
		}
	}
	
	public void PlayerBWaiting(){
		for(int x : slotBConfirm){
			inventory.remove(x);
			inventory.setItem(x, itmNConfirm);
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	public void givePlayerSlot(Player player, Slot slot){
		switch(slot){
		case SLOTA:
			for(int x : slotA)
				if(inventory.getItem(x) != null)
					player.getInventory().addItem(inventory.getItem(x));
			break;
		case SLOTB:
			for(int x : slotB)
				if(inventory.getItem(x) != null)
					player.getInventory().addItem(inventory.getItem(x));
			break;
		}
	}
	
	public void clearInv(){
		inventory.clear();
	}

}
