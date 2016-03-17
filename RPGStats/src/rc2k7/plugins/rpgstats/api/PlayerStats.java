package rc2k7.plugins.rpgstats.api;

import java.io.File;
import java.io.IOException;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import rc2k7.plugins.rpgstats.RPGStats;
import rc2k7.plugins.rpgstats.effects.FireWorkEffect;
import rc2k7.plugins.rpgstats.util.Util;
import rc2k7.plugins.rpgstats.util.Variables;

public class PlayerStats {
	
	private String playerName;

	//Base
	public int level;
	public int experience;
	private int baseDamage;
	private int baseHealth;
	
	//Useable Variables
	public int maxHealth;
	public int armor;
	public int defense;
	
	//Weapon Damages
	public int dmgMin = 0;
	public int dmgMax = 0;
	public int critChance = 0;
	public int critMult = 0;
	
	//Elemental Damages
	public int elemFire = 0;
	public int elemWater = 0;
	public int elemElectricity = 0;
	public int elemHoly = 0;
	public int elemUnHoly = 0;
	public int elemAcid = 0;
	
	//Elemental Resistances
	public int resistFire = 0;
	public int resistWater = 0;
	public int resistElectricity = 0;
	public int resistHoly = 0;
	public int resistUnHoly = 0;
	public int resistAcid = 0;
	
	public int healer = 0;
	public int magic = 0;
	
	//ItemStacks
	public ItemStats helmet = new ItemStats(null, "Armor");
	public ItemStats body = new ItemStats(null, "Armor");
	public ItemStats legs = new ItemStats(null, "Armor");
	public ItemStats boots = new ItemStats(null, "Armor");
	public ItemStats weapon = new ItemStats(null, "Weapon");
	
	public PlayerStats(String name){
		playerName = name;
		try {
			if(!RPGStats.playerConfig.contains(playerName + ".Experience")) savePlayerStats();
			else experience = RPGStats.playerConfig.getInt(playerName + ".Experience");
			setLevel();
			calculateVariables();
		} catch (Exception e) {}
	}
	
	public void updateEquipment(){
		helmet.setItemStack(Util.getPlayer(playerName).getInventory().getHelmet());
		body.setItemStack(Util.getPlayer(playerName).getInventory().getChestplate());
		legs.setItemStack(Util.getPlayer(playerName).getInventory().getLeggings());
		boots.setItemStack(Util.getPlayer(playerName).getInventory().getBoots());
		calculateVariables();
	}
	
	public void addExp(int x){
		experience += x;
		Integer maxlevel = Variables.levels.size()-1;
		Integer capexp = Variables.expNeeded.get(maxlevel);
		if (experience >= capexp){
			experience = capexp;
		}
		int tmp = level;
		setLevel();
		savePlayerStats();
		calculateVariables();
		if(level > tmp)
			levelUp();
	}
	
	public void setExp(int x){
		experience = x;
		Integer maxlevel = Variables.levels.size()-1;
		Integer capexp = Variables.expNeeded.get(maxlevel);
		if (experience >= capexp){
			experience = capexp;
		}
		setLevel();
		savePlayerStats();
		calculateVariables();
	}
	
	public void setLevel(){
		level = Util.getLevel(experience);
		baseHealth = Util.getHealth(experience);
		baseDamage = Util.getBaseDamage(experience);
		Util.getPlayer(playerName).setLevel(level);
		Util.getPlayer(playerName).setMaxHealth(baseHealth);
	}
	
	//Private Functions
	public void calculateVariables(){
		maxHealth = baseHealth + weapon.health + helmet.health + body.health + legs.health + boots.health;
		armor = weapon.armor + helmet.armor + body.armor + legs.armor + boots.armor;
		defense = weapon.critReduction + helmet.critReduction + body.critReduction + legs.critReduction + boots.critReduction;
		dmgMin = baseDamage + weapon.dmgMin + helmet.weaponDmg + body.weaponDmg + legs.weaponDmg + boots.weaponDmg;
		dmgMax = baseDamage + weapon.dmgMax + helmet.weaponDmg + body.weaponDmg + legs.weaponDmg + boots.weaponDmg;
		critChance = weapon.critChance + helmet.critChance + body.critChance + legs.critChance + boots.critChance;
		critMult = weapon.critMult + helmet.critMult + body.critMult + legs.critChance + boots.critChance;
		elemFire = weapon.elemFire + helmet.elemFire + body.elemFire + legs.elemFire + boots.elemFire;
		elemWater = weapon.elemWater + helmet.elemWater + body.elemWater + legs.elemWater + boots.elemWater;
		elemElectricity = weapon.elemElectricity + helmet.elemElectricity + body.elemElectricity + legs.elemElectricity + boots.elemElectricity;
		elemHoly = weapon.elemHoly + helmet.elemHoly + body.elemHoly + legs.elemHoly + boots.elemHoly;
		elemUnHoly = weapon.elemUnHoly + helmet.elemUnHoly + body.elemUnHoly + legs.elemUnHoly + boots.elemUnHoly;
		elemAcid = weapon.elemAcid + helmet.elemAcid + body.elemAcid + legs.elemAcid + boots.elemAcid;
		resistFire = weapon.resistFire + helmet.resistFire + body.resistFire + legs.resistFire + boots.resistFire;
		resistWater = weapon.resistWater + helmet.resistWater + body.resistWater + legs.resistWater + boots.resistWater;
		resistElectricity = weapon.resistElectricity + helmet.resistElectricity + body.resistElectricity + legs.resistElectricity + boots.resistElectricity;
		resistHoly = weapon.resistHoly + helmet.resistHoly + body.resistHoly + legs.resistHoly + boots.resistHoly;
		resistUnHoly = weapon.resistUnHoly + helmet.resistUnHoly + body.resistUnHoly + legs.resistUnHoly + boots.resistUnHoly;
		resistAcid = weapon.resistAcid + helmet.resistAcid + body.resistAcid + legs.resistAcid + boots.resistAcid;
		setHealth();
		Util.setStatsToList(Util.getPlayer(playerName));
	}
	
	private void setHealth(){
		if(Util.getPlayer(playerName).getHealth() > maxHealth)
			Util.getPlayer(playerName).setHealth(maxHealth);
		Util.getPlayer(playerName).setMaxHealth(maxHealth);
	}
	
	private void levelUp(){
		Player player = Util.getPlayer(playerName);
		Location loc = player.getLocation();
		//player.getLocation().getWorld().strikeLightningEffect(Util.getPlayer(playerName).getLocation());
		((org.bukkit.craftbukkit.v1_5_R3.CraftWorld)player.getWorld()).getHandle().addParticle("hugeexplosion", loc.getX(), loc.getY(), loc.getZ(), 0.0D, 0.0D, 0.0D);
		player.getLocation().getWorld().playSound(loc, Sound.EXPLODE, 1.0F, 0.1F);
		FireWorkEffect.playFireWork(player.getWorld(), player.getLocation(), FireworkEffect.builder().with(Type.STAR).withColor(Color.YELLOW).build());
		FireWorkEffect.playFireWork(player.getWorld(), player.getLocation(), FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.BLUE).build());
		FireWorkEffect.playFireWork(player.getWorld(), player.getLocation(), FireworkEffect.builder().with(Type.CREEPER).withColor(Color.GREEN).build());
		FireWorkEffect.playFireWork(player.getWorld(), player.getLocation(), FireworkEffect.builder().with(Type.BALL_LARGE).withColor(Color.BLUE).build());
		FireWorkEffect.playFireWork(player.getWorld(), player.getLocation(), FireworkEffect.builder().with(Type.STAR).withColor(Color.YELLOW).build());
		player.setHealth(maxHealth);
	}
	
	private void savePlayerStats(){
		RPGStats.playerConfig.set(playerName + ".Experience", experience);
		try {
			RPGStats.playerConfig.save(new File("plugins/RPGStats/Players.yml"));
		} catch (IOException e) {}
	}
	
	//Getters
	public String getPlayerName(){
		return playerName;
	}

}
