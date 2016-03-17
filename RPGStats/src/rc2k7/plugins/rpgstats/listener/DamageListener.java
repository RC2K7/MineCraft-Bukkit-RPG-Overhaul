package rc2k7.plugins.rpgstats.listener;

import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import rc2k7.plugins.rpgstats.RPGStats;
import rc2k7.plugins.rpgstats.api.PlayerStats;
import rc2k7.plugins.rpgstats.manager.PlayerStatsManager;
import rc2k7.plugins.rpgstats.util.DmgUtil;

public class DamageListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onStarvation(EntityDamageEvent event){
		if(event.getCause().equals(DamageCause.STARVATION) || event.getCause().equals(DamageCause.DROWNING) || event.getCause().equals(DamageCause.LAVA) || event.getCause().equals(DamageCause.FIRE_TICK) || event.getCause().equals(DamageCause.SUFFOCATION)){
			if(event.getEntity() instanceof Player){
				Player player = (Player)event.getEntity();
				if(player.isDead() || player.getGameMode() == GameMode.CREATIVE)
					return;
				event.setCancelled(true);
				event.setDamage(0);
				int minus = player.getMaxHealth() / 20;
				int tmp = player.getHealth() - minus;
				if(tmp < 0) player.setHealth(0);
				else player.setHealth(player.getHealth() - minus);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onDamageByEntity(EntityDamageByEntityEvent event){
		Player damager = null;
		if(event.getDamager() instanceof org.bukkit.craftbukkit.v1_5_R3.entity.CraftArrow)
			if(((org.bukkit.craftbukkit.v1_5_R3.entity.CraftArrow)event.getDamager()).getShooter() instanceof Player)
				damager = (Player)((org.bukkit.craftbukkit.v1_5_R3.entity.CraftArrow)event.getDamager()).getShooter();
		if(event.getDamager() instanceof Player)
			damager = (Player)event.getDamager();
		if(damager == null)
			return;
		PlayerStats ps = PlayerStatsManager.getPlayerStats(damager.getName());
		if(ps.level < ps.weapon.requiredLvl){
			event.setDamage(0);
			event.setCancelled(true);
			return;
		}
		if(event.getEntity() instanceof Player){
			//event.setDamage(DmgUtil.calculateDamage(ps, PlayerStatsManager.getPlayerStats(((Player)event.getEntity()).getName())));
			event.setDamage(0);
			int tmphealth = ((Player)event.getEntity()).getHealth();
			tmphealth -= (DmgUtil.calculateDamage(ps, PlayerStatsManager.getPlayerStats(((Player)event.getEntity()).getName())));
			if(tmphealth < 0)((Player)event.getEntity()).setHealth(0);
			else ((Player)event.getEntity()).setHealth(tmphealth);
		}
		else{
			if(event.getEntity() instanceof LivingEntity){
				LivingEntity el = (LivingEntity)event.getEntity();
				if(RPGStats.eb != null){
					Boss boss = RPGStats.eb.bossCalculator.getBoss(el);
					if(boss != null){
						boss.getLivingEntity().setMaxHealth(boss.getMaxHealth());
						boss.getLivingEntity().setHealth(boss.getHealth());
						int tmphealth = boss.getHealth();
						int damage = DmgUtil.calculateDamage(ps, null);
						tmphealth -= damage;
						if(tmphealth < 0) boss.sethealth(0);
						else boss.sethealth(tmphealth);
						event.setDamage(0);
						return;
					}
				}
				int tmphealth = el.getHealth();
				int damage = DmgUtil.calculateDamage(ps, null);
				tmphealth -= damage;
				if(tmphealth < 0)event.setDamage(el.getHealth());
				else event.setDamage(damage);
				return;
			}
		}
	}

}
