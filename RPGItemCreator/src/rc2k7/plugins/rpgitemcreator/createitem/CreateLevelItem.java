package rc2k7.plugins.rpgitemcreator.createitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.me.tft_02.soulbound.api.ItemAPI;

import rc2k7.plugins.rpgitemcreator.RPGItemCreator;
import rc2k7.plugins.rpgitemcreator.load.LoadLevel;

public class CreateLevelItem {
	
	RPGItemCreator rpg;
	LoadLevel LL;
	int Level;
	ItemStack ItemStack;
	Random rand;
	
	String Name;
	String Roll;
	String Class;
	List<String> Lore = new ArrayList<>();
	String Prefix = new String();
	String Suffix = new String();
	
	public CreateLevelItem(RPGItemCreator r, int level)
	{
		this.rpg = r;
		this.Level = level;
		this.rand = new Random();
		getLoadLevel();
		setItemType();
		getLores();
		getPrefix();
		getSuffix();
		setInfo();
		if(this.rpg.SB != null)
			setSoulBound();
	}
	
	public ItemStack getItemStack(){ return this.ItemStack; }
	
	private void setItemType()
	{
		List<String> list = new ArrayList<>(this.LL.getItemIDs());
		String select = list.get(this.rand.nextInt(list.size()));
		String[] blocks = select.split(":");
		int dmg = getValueBetweem(Integer.parseInt(blocks[1].split("-")[1]), Integer.parseInt(blocks[1].split("-")[0]));
		this.ItemStack = new ItemStack(Material.getMaterial(Integer.parseInt(blocks[0])));
		this.ItemStack.setDurability((short)dmg);
		String[] name = blocks[2].split(",");
		this.Name = name[this.rand.nextInt(name.length)];
		this.Roll = blocks[3];
	}
	
	private void getLores()
	{
		String[] tmp = this.LL.getLores().keySet().toArray(new String[0]);
		this.Class = tmp[this.rand.nextInt(tmp.length)];
		this.Lore.add("ReqLvl:" + String.valueOf(this.Level));
		int amount = getValueBetweem(Integer.parseInt(this.LL.getAmount().split("-")[1]), Integer.parseInt(this.LL.getAmount().split("-")[0]));
		int x = 0;
		while(x < amount)
		{
			for(String lore : this.LL.getLores().get(this.Class))
			{
				String[] blocks = lore.split(":");
				if(blocks[0].equals("Dmg"))
				{
					int min = getValueBetweem(Integer.parseInt(blocks[1].split("-")[1]), Integer.parseInt(blocks[1].split("-")[0]));
					int max = getValueBetweem(Integer.parseInt(blocks[2].split("-")[1]), Integer.parseInt(blocks[2].split("-")[0]));
					if(getRollOutcome(Integer.parseInt(blocks[3])))
					{
						if(!this.Lore.contains(blocks[0] + ":" + String.valueOf(min) + "-" + String.valueOf(max)) && x < amount)
						{
							this.Lore.add(blocks[0] + ":" + String.valueOf(min) + "-" + String.valueOf(max));
							x++;
						}
					}
					continue;
				}
				int val = getValueBetweem(Integer.parseInt(blocks[1].split("-")[1]), Integer.parseInt(blocks[1].split("-")[0]));
				if(getRollOutcome(Integer.parseInt(blocks[2])))
				{
					if(!this.Lore.contains(blocks[0] + ":" + String.valueOf(val)) && x < amount)
					{
						this.Lore.add(blocks[0] + ":" + String.valueOf(val));
						x++;
					}
				}
			}
		}
	}
	
	private void getPrefix()
	{
		if(this.LL.getPrefixes().get(this.Class) != null && this.LL.getPrefixes().get(this.Class).size() > 0)
		{
			List<String> prefixes = new ArrayList<>();
			for(String str : this.LL.getPrefixes().get(this.Class))
			{
				String[] blocks = str.split(":");
				if(listContainsSubString(this.Lore, blocks[1]))
				{
					prefixes.add(blocks[0]);
				}
			}
			this.Prefix = prefixes.get(this.rand.nextInt(prefixes.size()));
		}
	}
	
	private void getSuffix()
	{
		if(this.LL.getSuffixes().get(this.Class) != null && this.LL.getSuffixes().get(this.Class).size() > 0)
		{
			List<String> suffixes = new ArrayList<>();
			for(String str : this.LL.getSuffixes().get(this.Class))
			{
				String[] blocks = str.split(":");
				if(listContainsSubString(this.Lore, blocks[1]))
				{
					suffixes.add(blocks[0]);
				}
			}
			this.Suffix = suffixes.get(this.rand.nextInt(suffixes.size()));
		}
	}
	
	private void setSoulBound()
	{
		if(this.Roll.equals("USE"))
		{
			ItemAPI.bindOnUseItem(this.ItemStack);
		}else if(this.Roll.equals("EQUIP"))
		{
			ItemAPI.bindOnEquipItem(this.ItemStack);
		}else if(this.Roll.equals("PICKUP"))
		{
			ItemAPI.bindOnPickupItem(this.ItemStack);
		}
	}
	
	private void setInfo()
	{
		setItemDetails(this.Prefix + " " + this.Name + " " + this.Suffix, this.Lore);
	}
	
	private int getValueBetweem(int max, int min)
	{
		if((max - min) == 0)
			return min;
		return (this.rand.nextInt(max-min) + min);
	}
	
	private void getLoadLevel()
	{
		for(LoadLevel ll : this.rpg.LevelList)
		{
			if(ll.getLevel() == this.Level)
			{
				this.LL = ll;
				return;
			}
		}
	}
	
	private boolean getRollOutcome(int chance)
	{
		if((this.rand.nextInt(100)+1) <= chance)
		{
			return true;
		}
		return false;
	}
	
	private void setItemDetails(String name, List<String> lore)
	{
		ItemMeta im = this.ItemStack.getItemMeta();
		if(name != null)
			im.setDisplayName(name);
		if(lore != null)
			im.setLore(lore);
		this.ItemStack.setItemMeta(im);
	}
	
	private boolean listContainsSubString(List<String> l, String s){
		for(String str : l)
			if(str.contains(s))
				return true;
		return false;
	}

}
