package rc2k7.plugins.rpgstats.util;

import java.util.Random;

import org.bukkit.ChatColor;

import rc2k7.plugins.rpgstats.api.PlayerStats;

public class DmgUtil {
	
	private static Random random = new Random();
	
	//Calculates All The Damage
	public static int calculateDamage(PlayerStats a, PlayerStats b){
		int damage = Util.randomDamage(a.dmgMin, a.dmgMax);
		if(b != null){
			if(doChanceRoll(getDD(a.critChance, b.defense)))
			{
				damage = damage * a.critMult;
				Util.getPlayer(a.getPlayerName()).sendMessage(ChatColor.DARK_PURPLE + "Critical Strike: " + ChatColor.RED + damage);
			}
			damage -= (b.armor / 10);
			if(damage < 0)
				damage = 0;
			damage += getDD(a.elemFire, b.resistFire);
			damage += getDD(a.elemWater, b.resistWater);
			damage += getDD(a.elemElectricity, b.resistElectricity);
			damage += getDD(a.elemHoly, b.resistHoly);
			damage += getDD(a.elemUnHoly, b.resistUnHoly);
			damage += getDD(a.elemAcid, b.resistAcid);
		}else{
			if(doChanceRoll(a.critChance)) {damage = damage * a.critMult; Util.getPlayer(a.getPlayerName()).sendMessage(ChatColor.DARK_PURPLE + "Critical Strike: " + ChatColor.RED + damage);}
			damage += a.elemFire + a.elemWater + a.elemElectricity + a.elemHoly + a.elemUnHoly + a.elemAcid;
			
		}
		return damage;
	}
	
	//Does Chance Roll Ex. Crit Chance
	public static boolean doChanceRoll(int chance){
		if((random.nextInt(100)+1) <= chance)
			return true;
		return false;
	}
	
	//getDamageDifference
	public static int getDD(int damage, int resistance){
		int tmp = damage - resistance;
		if(tmp < 0)
			return 0;
		return tmp;
	}

}
