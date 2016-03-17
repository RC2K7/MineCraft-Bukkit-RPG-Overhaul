package rc2k7.plugins.rpgstats.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import rc2k7.plugins.rpgstats.api.ItemStats;
import rc2k7.plugins.rpgstats.api.PlayerStats;
import rc2k7.plugins.rpgstats.manager.PlayerStatsManager;
import rc2k7.plugins.rpgstats.util.Util;
import rc2k7.plugins.rpgstats.util.Variables.Armortypes;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		PlayerStatsManager.addPlayerStats(event.getPlayer().getName());
		Util.setupStatList(event.getPlayer());
		Util.setStatsToList(event.getPlayer());
		PlayerStatsManager.getPlayerStats(event.getPlayer().getName()).updateEquipment();
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		PlayerStatsManager.removePlayerStats(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event){
		PlayerStatsManager.removePlayerStats(event.getPlayer().getName());
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event){
		if(event.getPlayer().isDead())
			return;
		PlayerStats ps = PlayerStatsManager.getPlayerStats(event.getPlayer().getName());
		if(ps != null)
			ps.setLevel();
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event){
		if(event.getCause().equals(DamageCause.VOID)){
			if(event.getEntity() instanceof Player){
				Player player = ((Player)event.getEntity());
				if(!player.isDead())
					player.setHealth(0);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemUse(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			Player player = event.getPlayer();
			if(player.getItemInHand() == null)
				return;
			if(Util.getItemClass(player.getItemInHand()) == Armortypes.MISC)
				return;
			PlayerStats ps = PlayerStatsManager.getPlayerStats(player.getName());
			if(ps == null)
				return;
			ItemStats is = new ItemStats(player.getItemInHand(), "Armor");
			if(ps.level < is.requiredLvl){
				event.setCancelled(true);
				event.setUseInteractedBlock(Result.DENY);
				event.setUseItemInHand(Result.DENY);
				player.updateInventory();
				return;
			}
			setClickedItem(player);
			ps.updateEquipment();
			event.setCancelled(true);
			event.setUseInteractedBlock(Result.DENY);
			event.setUseItemInHand(Result.DENY);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void setClickedItem(Player player){
		ItemStack tmp = null;
		switch(Util.getItemClass(player.getItemInHand())){
		case HELMET:
			tmp = player.getInventory().getHelmet();
			player.getInventory().setHelmet(player.getItemInHand());
			player.setItemInHand(tmp);
			break;
		case CHEST:
			tmp = player.getInventory().getChestplate();
			player.getInventory().setChestplate(player.getItemInHand());
			player.setItemInHand(tmp);
			break;
		case LEGS:
			tmp = player.getInventory().getLeggings();
			player.getInventory().setLeggings(player.getItemInHand());
			player.setItemInHand(tmp);
			break;
		case BOOTS:
			tmp = player.getInventory().getBoots();
			player.getInventory().setBoots(player.getItemInHand());
			player.setItemInHand(tmp);
			break;
		default:
			break;
		}
		player.updateInventory();
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent event){
		PlayerStatsManager.getPlayerStats(event.getPlayer().getName()).calculateVariables();
	}

}
