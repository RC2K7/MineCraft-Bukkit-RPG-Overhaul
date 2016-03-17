package rc2k7.plugins.partycreator;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Listener implements org.bukkit.event.Listener {

	@EventHandler
	public void onPlayerDmg(EntityDamageByEntityEvent event){
		if(event.getEntity() instanceof Player){
			Player a = (Player)event.getEntity();
			Player b = null;
			if(event.getDamager() instanceof Player)
				b = (Player)event.getEntity();
			if(event.getDamager() instanceof Arrow)
				if (((Arrow)event.getDamager()).getShooter() instanceof Player)
					b = (Player)((Arrow)event.getDamager()).getShooter();
			Party p = null;
			if((p = Util.getParty(a.getDisplayName())) == null)
				return;
			if(p.getMembers().contains(b))
				event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void partyChat(AsyncPlayerChatEvent event){
		Player sender = event.getPlayer();
			Party party = null;
			if(PartyCreator.pcList.contains(sender.getDisplayName())){
				event.setCancelled(true);
				for(Player player : event.getRecipients())
					if((party = Util.getParty(sender.getDisplayName())) != null)
						if(Util.getPlayerFromList(party.getMembers(), player.getDisplayName()) != null)
							player.sendMessage(sender.getDisplayName() + " > " + ChatColor.AQUA + event.getMessage());
			}
	}
	
	@EventHandler
	public void onLogout(PlayerQuitEvent event){
		Player player = event.getPlayer();
		Party party = null;
		if((party = Util.getParty(player.getDisplayName())) != null)
			party.removePlayer(player);
		if(PartyCreator.pcList.contains(player.getDisplayName()))
			PartyCreator.pcList.remove(player.getDisplayName());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event){
		Player player = event.getPlayer();
		Party party = null;
		if((party = Util.getParty(player.getDisplayName())) != null)
			party.removePlayer(player);
		if(PartyCreator.pcList.contains(player.getDisplayName()))
			PartyCreator.pcList.remove(player.getDisplayName());
	}
	
	@EventHandler
	public void onPickup(PlayerPickupItemEvent event){
		if(PartyCreator.hasHighRoller){
			Player player = event.getPlayer();
			ItemStack itm = event.getItem().getItemStack();
			if(itm == null)
				return;
			if(itm.getEnchantments().isEmpty())
				return;
			Party party = null;
			if((party = Util.getParty(player.getDisplayName())) == null)
				return;
			party.addItem(itm);
			event.getItem().remove();
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onItemClick(InventoryClickEvent event){
		if(!(event.getWhoClicked() instanceof Player))
			return;
		if(PartyCreator.hasHighRoller){
			Player player = (Player)event.getWhoClicked();
			ItemStack itm = event.getCurrentItem();
			if(itm == null)
				return;
			if(itm.getEnchantments().isEmpty())
				return;
			Party party = null;
			if((party = Util.getParty(player.getDisplayName())) == null)
				return;
			if(event.getInventory().getType() != InventoryType.CHEST)
				return;
			if(event.getCurrentItem().isSimilar(player.getInventory().getItem(event.getSlot())))
				return;
			if(event.getInventory().getName().equals("Rolling For:"))
				return;
			if(event.getInventory().getName().equals("Bank:"))
				return;
			party.addItem(itm);
			event.getInventory().remove(itm);
			
			event.setCancelled(true);
		}
	}
	
}
