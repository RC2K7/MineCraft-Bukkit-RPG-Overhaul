package rc2k7.plugins.rpgitemcreator.listener;

import me.ThaH3lper.com.Api.BossDeathEvent;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import rc2k7.plugins.rpgitemcreator.RPGItemCreator;
import rc2k7.plugins.rpgitemcreator.createitem.CreateBossItem;
import rc2k7.plugins.rpgitemcreator.load.LoadBoss;

public class DeathListener implements Listener{
	
	RPGItemCreator rpg;
	
	public DeathListener(RPGItemCreator rpg) {
		this.rpg = rpg;
		Bukkit.getPluginManager().registerEvents(this, this.rpg);
	}
	
	@EventHandler
	public void onDrop(EntityDeathEvent event)
	{
		if(!(event.getEntity() instanceof Player)){
		event.getDrops().clear();
		}
	}
	
	@EventHandler
	public void onBossDeath(BossDeathEvent event)
	{
		event.getDrops().clear();
		if(inBossList(event.getBossName()))
		{
			for(ItemStack itm: new CreateBossItem(this.rpg, event.getBossName()).getItemStacks())
			{
				event.getDrops().add(itm);
			}
		}
	}
	
	public boolean inBossList(String name)
	{
		for(LoadBoss boss : this.rpg.BossList)
		{
			if(boss.getBossName().equals(name))
			{
				return true;
			}
		}
		return false;
	}

}
