package rc2k7.plugins.rpgstats.api;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import rc2k7.plugins.rpgstats.RPGStats;

public class ItemStats {
	
	//Main Variables
	public ItemStack item;
	public int itemStackType;
	public String itemType;
	
	//Item Attributes
	public int requiredLvl = 0;
	public int health = 0;
	public int armor = 0;
	public int dmgMin = 0;
	public int dmgMax = 0;
	public int weaponDmg = 0;
	public int critChance = 0;
	public int critMult = 0;
	public int critReduction = 0; //defense
	public int speed = 0;
	
	//Elemental Damages
	public int elemFire = 0;
	public int elemWater = 0;
	public int elemElectricity = 0;
	public int elemHoly = 0;
	public int elemUnHoly = 0;
	public int elemAcid = 0;
		
	//Elemental Resistances
	public int resistFire = 0;
	public String strFire = new String();
	public int resistWater = 0;
	public String strWater = new String();
	public int resistElectricity = 0;
	public String strElectricity = new String();
	public int resistHoly = 0;
	public String strHolt = new String();
	public int resistUnHoly = 0;
	public String strUnHoly = new String();
	public int resistAcid = 0;
	public String strAcid = new String();
	
	//Chance Effects
	public int posionDuration = 0;
	public int posionDPS = 0;
	public int posionChance = 0;
	
	public int blindingDuration = 0;
	public int blindingChance = 0;
	
	public int confusionDuration = 0;
	public int confusionChance = 0;
	
	public int antigravVerticalVelocity = 0;
	public int antigravChance = 0;
	
	public int slowDuration = 0;
	public int slowPercentage = 0;
	public int slowChance = 0;
	
	public int witherDuration = 0;
	public int witherChance = 0;
	
	public int lifestealAmount = 0;
	public int lifestealChance = 0;
	
	public int burstDamage = 0;
	public int burstChance = 0;
	
	public int armorspikesDamage = 0;
	public int armorChance = 0;
	
	public int blindingarmorDuration = 0;
	public int blindingarmorChance = 0;
	
	public int disarmingDuration = 0;
	public int disarmingChance = 0;
	
	public int regenarmorDuration = 0;
	public int regenarmorHPS = 0;
	public int regenarmorChance = 0;

	public int healingAmount = 0;
	public int healingCooldown = 0;
	public int healingChance = 0;
	
	public int teleportChance = 0;
	
	//Active Effects
	public int healSelfAmount = 0;
	public int healSelfCooldown = 0;
	
	public int shieldSelfDuration = 0;
	public int shieldSelfCooldown = 0;
	public int shieldSelfChance = 0;
	
	public int snareDuration = 0;
	public int snareCooldown = 0;
	
	public int pullCooldown = 0;
	
	public int explosionDamage = 0;
	public int explosionRadius = 0;
	public int explosionCooldown = 0;
	
	public int vortexRadius = 0;
	public int vortexCooldown = 0;
	
	public int protectorDuration = 0;
	public int protectorCooldown = 0;
	
	//Config Path Location
	private String path = new String();
	
	public ItemStats(ItemStack itm, String itmType){
		item = itm;
		itemType = itmType;
		if(itm != null){
			itemStackType = itm.getTypeId();
			getPath();
			loadFromItemStack();
		}
	}
	
	public void setItemStack(ItemStack itm){
		item = itm;
		resetItemStats();
		if(item != null){
			itemStackType = itm.getTypeId();
			getPath();
			loadFromItemStack();
		}
	}
	
	//Load and Set Names
	public void loadFromItemStack(){
		if(item.getItemMeta() == null || !item.getItemMeta().hasLore())
			return;
		for(String blocks : item.getItemMeta().getLore()){
			String[] block = blocks.split(":");
			if(block[0].equals("Dmg")){
				dmgMin = Integer.parseInt(block[1].split("-")[0]);
				dmgMax = Integer.parseInt(block[1].split("-")[1]);
			}else if(block[0].equals("ReqLvl")){
				requiredLvl = Integer.parseInt(block[1]);
			}else if(block[0].equals("Health")){
				health = Integer.parseInt(block[1]);
			}else if(block[0].equals("WeaponDmg")){
				weaponDmg = Integer.parseInt(block[1]);
			}else if(block[0].equals("CritChance")){
				critChance = Integer.parseInt(block[1]);
			}else if(block[0].equals("CritMult")){
				critMult = Integer.parseInt(block[1]);
			}else if(block[0].equals("Armor")){
				armor = Integer.parseInt(block[1]);
			}else if(block[0].equals("Defense")){
				critReduction = Integer.parseInt(block[1]);
			}else if(block[0].equals("FireDmg")){
				elemFire = Integer.parseInt(block[1]);
			}else if(block[0].equals("WaterDmg")){
				elemWater = Integer.parseInt(block[1]);
			}else if(block[0].equals("ElectricityDmg")){
				elemElectricity = Integer.parseInt(block[1]);
			}else if(block[0].equals("HolyDmg")){
				elemHoly = Integer.parseInt(block[1]);
			}else if(block[0].equals("UnHolyDmg")){
				elemUnHoly = Integer.parseInt(block[1]);
			}else if(block[0].equals("AcidDmg")){
				elemAcid = Integer.parseInt(block[1]);
			}else if(block[0].equals("FireResist")){
				resistFire = Integer.parseInt(block[1]);
			}else if(block[0].equals("WaterResist")){
				resistWater = Integer.parseInt(block[1]);
			}else if(block[0].equals("ElectricityResist")){
				resistElectricity = Integer.parseInt(block[1]);
			}else if(block[0].equals("HolyResist")){
				resistHoly = Integer.parseInt(block[1]);
			}else if(block[0].equals("UnHolyResist")){
				resistUnHoly = Integer.parseInt(block[1]);
			}else if(block[0].equals("AcidResist")){
				resistAcid = Integer.parseInt(block[1]);
			}
		}
	}
	
	public void saveToItemStack(){
		
	}
	
	//Check Functions
	public boolean isItemCustom(){
		return RPGStats.plugin.getConfig().contains(path);
	}
	
	//Debugging Function
	public void saveDefaultItemStats(){
		List<String> eleDmg = new ArrayList<>();
		eleDmg.add("Fire:" + String.valueOf(elemFire));
		eleDmg.add("Water:" + String.valueOf(elemWater));
		eleDmg.add("Electricity:" + String.valueOf(elemElectricity));
		eleDmg.add("Holy:" + String.valueOf(elemHoly));
		eleDmg.add("UnHoly:" + String.valueOf(elemUnHoly));
		eleDmg.add("Acid:" + String.valueOf(elemAcid));
		List<String> eleRes = new ArrayList<>();
		eleRes.add("Fire:" + String.valueOf(resistFire) + ":NAME HOLDER");
		eleRes.add("Water:" + String.valueOf(resistWater) + ":NAME HOLDER");
		eleRes.add("Electricity:" + String.valueOf(resistElectricity) + ":NAME HOLDER");
		eleRes.add("Holy:" + String.valueOf(resistHoly) + ":NAME HOLDER");
		eleRes.add("UnHoly:" + String.valueOf(resistUnHoly) + ":NAME HOLDER");
		eleRes.add("Acid:" + String.valueOf(resistAcid) + ":NAME HOLDER");
		Plugin pl = RPGStats.plugin;
		pl.getConfig().set(path + ".RequiredLvl", requiredLvl);
		pl.getConfig().set(path + ".Type", itemStackType);
		pl.getConfig().set(path + ".Health", health);
		pl.getConfig().set(path + ".DamageMin", dmgMin);
		pl.getConfig().set(path + ".DamageMax", dmgMax);
		pl.getConfig().set(path + ".WeaponDamage", weaponDmg);
		pl.getConfig().set(path + ".CritChance", critChance);
		pl.getConfig().set(path + ".CritMult", critMult);
		pl.getConfig().set(path + ".CritReduction", critReduction);
		pl.getConfig().set(path + ".Speed", speed);
		pl.getConfig().set(path + ".ElementalDamage", eleDmg);
		pl.getConfig().set(path + ".ElementalResist", eleRes);
		pl.getConfig().set(path + ".ChanceEffects.Posion.Duration", posionDuration);
		pl.getConfig().set(path + ".ChanceEffects.Posion.DPS", posionDPS);
		pl.getConfig().set(path + ".ChanceEffects.Posion.Chance", posionChance);
		pl.getConfig().set(path + ".ChanceEffects.Blinding.Duration", blindingDuration);
		pl.getConfig().set(path + ".ChanceEffects.Blinding.Chance", blindingChance);
		pl.getConfig().set(path + ".ChanceEffects.Confusion.Duration", confusionDuration);
		pl.getConfig().set(path + ".ChanceEffects.Confusion.Chance", confusionChance);
		pl.getConfig().set(path + ".ChanceEffects.AntiGrav.VerticalVelocity", antigravVerticalVelocity);
		pl.getConfig().set(path + ".ChanceEffects.AntiGrav.Chance", antigravChance);
		pl.getConfig().set(path + ".ChanceEffects.Slow.Duration", slowDuration);
		pl.getConfig().set(path + ".ChanceEffects.Slow.Percentage", slowPercentage);
		pl.getConfig().set(path + ".ChanceEffects.Slow.Chance", slowChance);
		pl.getConfig().set(path + ".ChanceEffects.Wither.Duration", witherDuration);
		pl.getConfig().set(path + ".ChanceEffects.Wither.Chance", witherChance);
		pl.getConfig().set(path + ".ChanceEffects.LifeSteal.Amount", lifestealAmount);
		pl.getConfig().set(path + ".ChanceEffects.LifeSteal.Chance", lifestealChance);
		pl.getConfig().set(path + ".ChanceEffects.Burst.Damage", burstDamage);
		pl.getConfig().set(path + ".ChanceEffects.Burst.Chance", burstChance);
		pl.getConfig().set(path + ".ChanceEffects.ArmorSpikes.Damage", armorspikesDamage);
		pl.getConfig().set(path + ".ChanceEffects.ArmorSpikes.Chance", armorChance);
		pl.getConfig().set(path + ".ChanceEffects.BlindingArmor.Duration", blindingarmorDuration);
		pl.getConfig().set(path + ".ChanceEffects.BlindingArmor.Chance", blindingarmorChance);
		pl.getConfig().set(path + ".ChanceEffects.Disarming.Duration", disarmingDuration);
		pl.getConfig().set(path + ".ChanceEffects.Disarming.Chance", disarmingChance);
		pl.getConfig().set(path + ".ChanceEffects.RegenArmor.Duration", regenarmorDuration);
		pl.getConfig().set(path + ".ChanceEffects.RegenArmor.HPS", regenarmorHPS);
		pl.getConfig().set(path + ".ChanceEffects.RegenArmor.Chance", regenarmorChance);
		pl.getConfig().set(path + ".ChanceEffects.Healing.Amount", healingAmount);
		pl.getConfig().set(path + ".ChanceEffects.Healing.Cooldown", healingCooldown);
		pl.getConfig().set(path + ".ChanceEffects.Healing.Chance", healingChance);
		pl.getConfig().set(path + ".ChanceEffects.Teleport.Chance", teleportChance);
		pl.getConfig().set(path + ".ActiveEffects.HealSelf.Amount", healSelfAmount);
		pl.getConfig().set(path + ".ActiveEffects.HealSelf.Cooldown", healSelfCooldown);
		pl.getConfig().set(path + ".ActiveEffects.ShieldSelf.Duration", shieldSelfDuration);
		pl.getConfig().set(path + ".ActiveEffects.ShieldSelf.Cooldown", shieldSelfCooldown);
		pl.getConfig().set(path + ".ActiveEffects.ShieldSelf.Chance", shieldSelfChance);
		pl.getConfig().set(path + ".ActiveEffects.Snare.Duration", snareDuration);
		pl.getConfig().set(path + ".ActiveEffects.Snare.Cooldown", snareCooldown);
		pl.getConfig().set(path + ".ActiveEffects.Pull.Cooldown", pullCooldown);
		pl.getConfig().set(path + ".ActiveEffects.Explosion.Damage", explosionDamage);
		pl.getConfig().set(path + ".ActiveEffects.Explosion.Radius", explosionRadius);
		pl.getConfig().set(path + ".ActiveEffects.Explosion.Cooldown", explosionCooldown);
		pl.getConfig().set(path + ".ActiveEffects.Vortex.Radius", vortexRadius);
		pl.getConfig().set(path + ".ActiveEffects.Vortex.Cooldown", vortexCooldown);
		pl.getConfig().set(path + ".ActiveEffects.Protector.Duration", protectorDuration);
		pl.getConfig().set(path + ".ActiveEffects.Protector.Cooldown", protectorCooldown);
		pl.saveConfig();
	}
	
	//Private Functions
	private void resetItemStats(){
		//Item Attributes
		requiredLvl = 0;
		health = 0;
		armor = 0;
		dmgMin = 0;
		dmgMax = 0;
		weaponDmg = 0;
		critChance = 0;
		critMult = 0;
		critReduction = 0;
		speed = 0;
		
		//Elemental Damages
		elemFire = 0;
		elemWater = 0;
		elemElectricity = 0;
		elemHoly = 0;
		elemUnHoly = 0;
		elemAcid = 0;
			
		//Elemental Resistances
		resistFire = 0;
		strFire = new String();
		resistWater = 0;
		strWater = new String();
		resistElectricity = 0;
		strElectricity = new String();
		resistHoly = 0;
		strHolt = new String();
		resistUnHoly = 0;
		strUnHoly = new String();
		resistAcid = 0;
		strAcid = new String();
	}

	private void getPath(){
		if(item.getItemMeta() != null && item.getItemMeta().hasDisplayName())
			path = itemType + "." + item.getItemMeta().getDisplayName();
	}
	
}
