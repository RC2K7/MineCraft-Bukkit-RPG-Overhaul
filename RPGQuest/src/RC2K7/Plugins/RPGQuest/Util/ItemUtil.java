package RC2K7.Plugins.RPGQuest.Util;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import RC2K7.Plugins.RPGQuest.RPGQuest;

public class ItemUtil {
	
	RPGQuest RPG;
	
	public ItemUtil(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	public ItemStack setDetails(ItemStack itm, String name, List<String> lore)
	{
		ItemMeta im = itm.getItemMeta();
		if(name != null)
			im.setDisplayName(name);
		if(lore != null)
			im.setLore(lore);
		itm.setItemMeta(im);
		return itm;
	}

}
