package RC2K7.Plugins.RPGQuest.Serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SerializeInventory {
	
	public static Inventory deSerializeInventory(final String string){
		final String[] blocks = string.split(";");
		Inventory custom = Bukkit.getServer().createInventory(null, Integer.valueOf(blocks[0]), blocks[1]);
		
		for(int i = 2; i < blocks.length; i++){
			final String[] block = blocks[i].split("#");
			if(Integer.valueOf(block[0]) >= custom.getSize())
				continue;
			
			ItemStack is = null;
			String[] itmStack = block[1].split(":");
			for(String info : itmStack){
				String[] itmAttrib = info.split("@");
				if(itmAttrib[0].equals("t"))
					is = new ItemStack(Material.getMaterial(Integer.valueOf(itmAttrib[1])));
				else if(itmAttrib[0].equals("d") && is != null)
					is.setDurability(Short.valueOf(itmAttrib[1]));
				else if(itmAttrib[0].equals("a") && is != null)
					is.setAmount(Integer.valueOf(itmAttrib[1]));
				else if(itmAttrib[0].equals("e") && is != null)
					is.addUnsafeEnchantment(Enchantment.getById(Integer.valueOf(itmAttrib[1])), Integer.valueOf(itmAttrib[2]));
				else if(itmAttrib[0].equals("n") && is != null)
					is = setItemName(is, itmAttrib[1], null);
				else if(itmAttrib[0].equals("l") && is != null){
					List<String> tmp = new ArrayList<>();
					for(int x = 1; x < itmAttrib.length; x++)
						tmp.add(itmAttrib[x]);
					is = setItemName(is, null, tmp);
				}
			}
			custom.setItem(Integer.valueOf(block[0]), is);
		}
		return custom;
	}
	
	public static String serializeInventory(Inventory inventory){
		String inv = inventory.getSize() + ";" + inventory.getName() + ";";
		for(int i = 0; i < inventory.getSize(); i++){
			ItemStack is = inventory.getItem(i);
			if(is == null)
				continue;
			String serialItemStack = new String();
			serialItemStack += "t@" + String.valueOf(is.getType().getId());
			if(is.getDurability() != 0)
				serialItemStack += ":d@" + String.valueOf(is.getDurability());
			if(is.getAmount() != 1)
				serialItemStack += ":a@" + String.valueOf(is.getAmount());
			if(is.getEnchantments().size() != 0)
				for(Entry<Enchantment, Integer> entry : is.getEnchantments().entrySet())
					serialItemStack += ":e@" + entry.getKey().getId() + "@" + entry.getValue();
			if(is.getItemMeta().hasDisplayName())
				serialItemStack += ":n@" + is.getItemMeta().getDisplayName();
			if(is.getItemMeta().hasLore()){
				serialItemStack += ":l";
				for(String str : is.getItemMeta().getLore())
					serialItemStack += "@" + str;
			}
			inv += i + "#" + serialItemStack + ";";
		}
		return inv;
	}
	
	private static ItemStack setItemName(ItemStack itm, String name, List<String> lore){
		ItemMeta im = itm.getItemMeta();
		if(name != null)
			im.setDisplayName(name);
		if(lore != null)
			im.setLore(lore);
		itm.setItemMeta(im);
		return itm;
	}

}
