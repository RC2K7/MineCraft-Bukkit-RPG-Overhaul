package rc2k7.plugins.rpgstats.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.server.v1_5_R3.Packet201PlayerInfo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import rc2k7.plugins.rpgstats.manager.PlayerStatsManager;
import rc2k7.plugins.rpgstats.util.Variables.Armortypes;

public class Util {
	
	public static Random random = new Random();
	
	public static Player getPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getName().contains(name))
				return player;
		return null;
	}
	
	public static int getLevel(int exp){
		for(int x = Variables.levels.size() - 1; x >= 0; x--)
			if(exp >= Variables.expNeeded.get(x))
				return Variables.levels.get(x);
		return 0;
	}
	
	public static int getHealth(int exp){
		for(int x = Variables.expNeeded.size() - 1; x >= 0; x--)
			if(exp >= Variables.expNeeded.get(x))
				return Variables.health.get(x);
		return 0;
	}
	
	public static int getBaseDamage(int exp){
		for(int x = Variables.expNeeded.size() - 1; x >= 0; x--)
			if(exp >= Variables.expNeeded.get(x))
				return Variables.baseDamages.get(x);
		return 0;
	}
	
	public static Armortypes getItemClass(ItemStack itm){
		if(itm == null)
			return Armortypes.MISC;
		if(isObjectInArray(Variables.helmetTypes, itm.getTypeId()))
			return Armortypes.HELMET;
		if(isObjectInArray(Variables.chestTypes, itm.getTypeId()))
			return Armortypes.CHEST;
		if(isObjectInArray(Variables.legTypes, itm.getTypeId()))
			return Armortypes.LEGS;
		if(isObjectInArray(Variables.bootTypes, itm.getTypeId()))
			return Armortypes.BOOTS;
		return Armortypes.MISC;
	}
	
	public static boolean isObjectInArray(int[] array, int x){
		for(Object obj : array)
			if(obj.equals(x))
				return true;
		return false;
	}
	
	public static ItemStack setItemDetails(ItemStack itm, String name, List<String> lore){
		ItemMeta im = itm.getItemMeta();
		if(im != null && name != null)
			im.setDisplayName(name);
		if(im != null && lore != null)
			im.setLore(lore);
		itm.setItemMeta(im);
		return itm;
	}
	
	public static ItemStack addLore(ItemStack itm, String lore){
		ItemMeta im = itm.getItemMeta();
		if(im != null && lore != null){
			List<String> tmp = im.getLore();
			if(tmp == null)
				tmp = new ArrayList<String>();
			tmp.add(lore.toString());
			im.setLore(tmp);
		}
		itm.setItemMeta(im);
		return itm;
	}
	
	public static ItemStack delLore(ItemStack itm, String lore){
		ItemMeta im = itm.getItemMeta();
		if(im != null && lore != null && im.hasLore()){
			List<String> tmp = im.getLore();
			List<String> tmp2 = new ArrayList<>();
			for(String str : tmp)
				if(!str.contains(lore))
					tmp2.add(str);
			im.setLore(tmp2);
		}
		itm.setItemMeta(im);
		return itm;
	}
	
	public static int randomDamage(int min, int max){
		int tmp = max - min;
		if(tmp <= 0)
			return max;
		int tmp2 = random.nextInt(tmp) + min;
		return tmp2;
	}
	
	static String[] Stats = {"", "Health:", "Armor:", "Defense:", "Damage Min:", "Damage Max:", "Crit Chance:",
		"Crit Multiplier:", "Fire Damage:", "Water Damage:", "Elec. Damage:", "Holy Damage:", "UnHoly Dmg:", "Acid Damage:",
		"Fire Resist:", "Water Resist:", "Elec. Resist:", "Holy Resist:", "UnHoly Resist:", "Acid Resist:",
		"Magic:", "Healing:", "Exp:", "ExpReq:", ChatColor.DARK_GREEN + "==============", ChatColor.DARK_GREEN + "=============", ChatColor.DARK_GREEN + "============"};
	
	public static void setupStatList(Player player)
	{
		Stats[0] = player.getName();
		for(Player online : Bukkit.getOnlinePlayers())
		{
			Packet201PlayerInfo packet = new Packet201PlayerInfo(player.getName(), false, 0);
			((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
			Packet201PlayerInfo packet2 = new Packet201PlayerInfo(online.getName(), false, 0);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet2);
		}
		
		for(String str : Stats)
		{
			Packet201PlayerInfo packet = new Packet201PlayerInfo(str, true, 0);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public static void setStatsToList(Player player)
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		board.registerNewObjective("Stats", "dummy");
		Objective objective = board.getObjective("Stats");
		objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		Integer newLevel = (PlayerStatsManager.getPlayerStats(player.getName()).level);
		if (newLevel >= Variables.levels.size()-1){
			newLevel = Variables.levels.size()-1;
		}
		Integer needed = Variables.expNeeded.get(newLevel);
		if (needed <= (PlayerStatsManager.getPlayerStats(player.getName()).experience)){
			needed = 0;
		}
		
		Score score = objective.getScore(Bukkit.getPlayer(player.getName()));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).level);
		score = objective.getScore(Bukkit.getOfflinePlayer("Health:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).maxHealth);
		score = objective.getScore(Bukkit.getOfflinePlayer("Armor:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).armor);
		score = objective.getScore(Bukkit.getOfflinePlayer("Defense:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).defense);
		score = objective.getScore(Bukkit.getOfflinePlayer("Damage Min:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).dmgMin);
		score = objective.getScore(Bukkit.getOfflinePlayer("Damage Max:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).dmgMax);
		score = objective.getScore(Bukkit.getOfflinePlayer("Crit Chance:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).critChance);
		score = objective.getScore(Bukkit.getOfflinePlayer("Crit Multiplier:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).critMult);
		score = objective.getScore(Bukkit.getOfflinePlayer("Fire Damage:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).elemFire);
		score = objective.getScore(Bukkit.getOfflinePlayer("Water Damage:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).elemWater);
		score = objective.getScore(Bukkit.getOfflinePlayer("Elec. Damage:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).elemElectricity);
		score = objective.getScore(Bukkit.getOfflinePlayer("Holy Damage:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).elemHoly);
		score = objective.getScore(Bukkit.getOfflinePlayer("UnHoly Damage:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).elemUnHoly);
		score = objective.getScore(Bukkit.getOfflinePlayer("Acid Damage:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).elemAcid);
		score = objective.getScore(Bukkit.getOfflinePlayer("Fire Resist:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).resistFire);
		score = objective.getScore(Bukkit.getOfflinePlayer("Water Resist:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).resistWater);
		score = objective.getScore(Bukkit.getOfflinePlayer("Elec. Resist:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).resistElectricity);
		score = objective.getScore(Bukkit.getOfflinePlayer("Holy Resist:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).resistHoly);
		score = objective.getScore(Bukkit.getOfflinePlayer("UnHoly Resist:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).resistHoly);
		score = objective.getScore(Bukkit.getOfflinePlayer("Acid Resist:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).resistAcid);
		score = objective.getScore(Bukkit.getOfflinePlayer("Magic:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).magic);
		score = objective.getScore(Bukkit.getOfflinePlayer("Healing:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).healer);
		score = objective.getScore(Bukkit.getOfflinePlayer("Exp:"));
		score.setScore(PlayerStatsManager.getPlayerStats(player.getName()).experience);
		score = objective.getScore(Bukkit.getOfflinePlayer("ExpReq:"));
		score.setScore(needed);
		
		player.setScoreboard(board);
	}

}
