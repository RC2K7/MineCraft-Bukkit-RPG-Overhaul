package RC2K7.Plugins.RPGQuest.Listeners;

import me.ThaH3lper.com.Api.BossDeathEvent;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import RC2K7.Plugins.RPGAPI.Events.RegionEnterEvent;
import RC2K7.Plugins.RPGQuest.RPGQuest;

public class GoalListener implements Listener {
	
	RPGQuest RPG;
	
	public GoalListener(RPGQuest rpg)
	{
		this.RPG = rpg;
		Bukkit.getPluginManager().registerEvents(this, this.RPG);
	}
	
	//Only Handles Deaths By EpicBossRecoded
	@EventHandler
	public void onDeath(BossDeathEvent event)
	{
		this.RPG.GA.OnKillMonster(event.getPlayer().getName(), event.getBossName().replaceAll("_", " "));
	}
	
	//Handles Player Pickup Event [Check For Quest Item]
	@EventHandler
	public void onPickUp(PlayerPickupItemEvent event)
	{
		String itemname = event.getItem().getItemStack().getItemMeta().getDisplayName();
		if(itemname != null)
		{
			if(this.RPG.GA.onItemPickup(event.getPlayer().getName(), itemname))
			{
				event.setCancelled(true);
				event.getItem().remove();
			}
		}
	}
	
	//Handles Player Region Enter Event
	@EventHandler
	public void onRegionEnter(RegionEnterEvent event)
	{
		this.RPG.GA.onRegionEnter(event.getPlayer().getName(), event.getProtectedRegion().getId());
	}
	
	//Handles When A Player Talks To An NPC
	@EventHandler
	public void onTalk(PlayerInteractEntityEvent event)
	{
		if(this.RPG.QNPCM.isQuestNPC(event.getRightClicked()))
		{
			this.RPG.GA.onTalk(event.getPlayer().getName(), this.RPG.QNPCM.getQuestNPC(event.getRightClicked()).getName());
		}
	}

}