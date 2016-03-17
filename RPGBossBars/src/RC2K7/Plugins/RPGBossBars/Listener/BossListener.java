package RC2K7.Plugins.RPGBossBars.Listener;

import me.ThaH3lper.com.Boss.Boss;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import RC2K7.Plugins.RPGBossBars.RPGBossBars;

public class BossListener implements Listener {
	
	RPGBossBars RPG;
	
	public BossListener(RPGBossBars rpg)
	{
		this.RPG = rpg;
		Bukkit.getPluginManager().registerEvents(this, this.RPG);
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent event)
	{
		if(event.getEntity() == null) return;
		
		if(this.RPG.EB.bossCalculator.isBoss(event.getEntity()))
		{
			Boss boss = this.RPG.EB.bossCalculator.getBoss(event.getEntity());
			if(this.RPG.Setting.BossList.contains(boss.getName()))
			{
				boss.getLivingEntity().setCustomName(getHealthBar(boss.getName(), boss.getMaxHealth(), boss.getHealth()));
				boss.getLivingEntity().setCustomNameVisible(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		if(this.RPG.EB.bossCalculator.isBoss(event.getEntity()))
		{
			Boss boss = this.RPG.EB.bossCalculator.getBoss(event.getEntity());
			if(this.RPG.Setting.BossList.contains(boss.getName().replaceAll("_", " ")) || this.RPG.Setting.ShowOnAllBosses)
			{
				boss.getLivingEntity().setCustomName(getHealthBar(boss.getName().replaceAll("_", " "), boss.getMaxHealth(), boss.getHealth()));
				boss.getLivingEntity().setCustomNameVisible(true);
			}
		}
	}
	
	private String getHealthBar(String name, int Max, int Min)
	{
		//" + ChatColor.RESET + "
		double percent = (double)Min/(double)Max;
		if(percent > 0.90D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.GREEN + "♥♥♥♥♥♥♥♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.80D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.YELLOW + "♥♥♥♥♥♥♥♥♥" + ChatColor.BLACK + "♥" + ChatColor.RESET + "]";
		if(percent > 0.70D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.YELLOW + "♥♥♥♥♥♥♥♥" + ChatColor.BLACK + "♥♥" + ChatColor.RESET + "]";
		if(percent > 0.60D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.YELLOW + "♥♥♥♥♥♥♥" + ChatColor.BLACK + "♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.50D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.GOLD + "♥♥♥♥♥♥" + ChatColor.BLACK + "♥♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.40D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.GOLD + "♥♥♥♥♥" + ChatColor.BLACK + "♥♥♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.30D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.GOLD + "♥♥♥♥" + ChatColor.BLACK + "♥♥♥♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.20D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.RED + "♥♥♥" + ChatColor.BLACK + "♥♥♥♥♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.10D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.RED + "♥♥" + ChatColor.BLACK + "♥♥♥♥♥♥♥♥" + ChatColor.RESET + "]";
		if(percent > 0.00D) return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.RED + "♥" + ChatColor.BLACK + "♥♥♥♥♥♥♥♥♥" + ChatColor.RESET + "]";
		return ChatColor.DARK_RED + name + ChatColor.RESET + " ["+ ChatColor.BLACK + "♥♥♥♥♥♥♥♥♥♥]";
	}

}
