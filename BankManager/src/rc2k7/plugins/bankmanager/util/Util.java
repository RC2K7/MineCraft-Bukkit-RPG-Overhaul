package rc2k7.plugins.bankmanager.util;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import rc2k7.plugins.bankmanager.BankManager;

public class Util {
	
	public static String stub = ChatColor.GOLD + "BM: " + ChatColor.RESET;
	
	public static Player getPlayer(String name){
		for(Player player : Bukkit.getOnlinePlayers())
			if(player.getDisplayName().contains(name))
				return player;
		return null;
	}
	
	public static void sendMessage(Player player, String m){
		player.sendMessage(stub + m);
	}
	
	public static Inventory upgradeInventory(Inventory inv, int size){
		if(inv == null)
			return inv;
		if(inv.getSize() == size)
			return inv;
		Inventory temp = Bukkit.createInventory(null, size, "Bank:");
		for(int x = 0; x < inv.getSize(); x++)
			if(inv.getItem(x) != null)
				temp.setItem(x, inv.getItem(x));
		return temp;
	}
	
	public static boolean doesRegionContainLoc(Location loc){
		for(Entry<String, ProtectedRegion> entry : BankManager.wgPlugin.getRegionManager(loc.getWorld()).getRegions().entrySet())
			if(entry.getValue().contains((int)loc.getX(), (int)loc.getY(), (int)loc.getZ()))
				return true;
		return false;
	}

}
