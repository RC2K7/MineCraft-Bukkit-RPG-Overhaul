package rc2k7.plugins.rpgstats.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import rc2k7.plugins.rpgstats.api.ItemStats;
import rc2k7.plugins.rpgstats.api.PlayerStats;
import rc2k7.plugins.rpgstats.manager.PlayerStatsManager;
import rc2k7.plugins.rpgstats.util.Util;
import rc2k7.plugins.rpgstats.util.Variables.Armortypes;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onItemChange(PlayerItemHeldEvent event){
		Player player = event.getPlayer();
		PlayerStats ps = PlayerStatsManager.getPlayerStats(player.getName());
		if(ps != null){
			if(Util.getItemClass(player.getInventory().getItem(event.getNewSlot())) == Armortypes.MISC){
				ps.weapon.setItemStack(player.getInventory().getItem(event.getNewSlot()));
				ps.calculateVariables();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPutChangeArmor(InventoryClickEvent event){
		Player player = (Player)event.getWhoClicked();
		PlayerStats ps = PlayerStatsManager.getPlayerStats(player.getName());
		if(event.isShiftClick()){
			if(event.getCurrentItem() != null){
				ItemStats is = new ItemStats(event.getCurrentItem(), "Armor");
				if(ps.level < is.requiredLvl){
					cancelClick(event);
					player.updateInventory();
					return;
				}
				ItemStack tmp = null;
				switch(Util.getItemClass(event.getCurrentItem())){
				case HELMET:
					tmp = player.getInventory().getHelmet();
					player.getInventory().setHelmet(event.getCurrentItem());
					player.getInventory().setItem(event.getSlot(), tmp);
					break;
				case CHEST:
					tmp = player.getInventory().getChestplate();
					player.getInventory().setChestplate(event.getCurrentItem());
					player.getInventory().setItem(event.getSlot(), tmp);
					break;
				case LEGS:
					tmp = player.getInventory().getLeggings();
					player.getInventory().setLeggings(event.getCurrentItem());
					player.getInventory().setItem(event.getSlot(), tmp);
					break;
				case BOOTS:
					tmp = player.getInventory().getHelmet();
					player.getInventory().setBoots(event.getCurrentItem());
					player.getInventory().setItem(event.getSlot(), tmp);
					break;
				default:
					break; 
				}
				player.updateInventory();
				ps.updateEquipment();
				event.setCancelled(true);
				event.setResult(Result.ALLOW);
				return;
			}
		}
		if(event.getSlotType().equals(SlotType.ARMOR)){
			if(event.getCursor() != null){
				ItemStats is = new ItemStats(event.getCursor(), "Armor");
				if(ps.level < is.requiredLvl){
					cancelClick(event);
					player.updateInventory();
					return;
				}
				switch(Util.getItemClass(event.getCursor())){
				case HELMET:
					if(event.getSlot() != 39)
						return;
					break;
				case CHEST:
					if(event.getSlot() != 38)
						return;
					break;
				case LEGS:
					if(event.getSlot() != 37)
						return;
					break;
				case BOOTS:
					if(event.getSlot() != 36)
						return;
					break;
				default:
					break;
				}
				setItem(event);
				player.updateInventory();
				ps.updateEquipment();
				return;
			}
		}
	}
	
	private void setItem(InventoryClickEvent event){
		ItemStack tmp = event.getCurrentItem();
		event.setCurrentItem(event.getCursor());
		event.setCursor(tmp);
		event.setCancelled(true);
		event.setResult(Result.ALLOW);
	}
	
	private void cancelClick(InventoryClickEvent event){
		event.setCursor(event.getCursor());
		event.setCurrentItem(event.getCurrentItem());
		event.setCancelled(true);
		event.setResult(Result.DENY);
	}

}
