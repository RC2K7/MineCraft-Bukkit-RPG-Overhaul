package rc2k7.plugins.rpgitemcreator.createitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.me.tft_02.soulbound.api.ItemAPI;

import rc2k7.plugins.rpgitemcreator.RPGItemCreator;
import rc2k7.plugins.rpgitemcreator.load.LoadBoss;

public class CreateBossItem {
	
	RPGItemCreator rpg;
	LoadBoss LB;
	String BossName;
	List<ItemStack> ItemStacks = new ArrayList<>();
	Random rand;
	
	String Name;
	String Roll;
	
	public CreateBossItem(RPGItemCreator r, String bossname)
	{
		this.rpg = r;
		this.BossName = bossname;
		this.rand = new Random();
		getLoadBoss();
		checkWeaponList();
		checkBossWeapons();
	}
	
	public List<ItemStack> getItemStacks(){ return this.ItemStacks; }
	
	private void checkWeaponList()
	{
		for(String str : this.LB.getLootLevelList())
		{
			String[] blocks = str.split(",");
			if(getRollOutcome(Integer.parseInt(blocks[1])))
			{
				this.ItemStacks.add(new CreateLevelItem(this.rpg, Integer.parseInt(blocks[0])).getItemStack());
			}
		}
	}
	
	private void checkBossWeapons()
	{
		for(String weapon : this.LB.getWeapons())
		{
			ItemStack itm;
			int ID = 0;
			String Name = new String();
			List<String> Lore = new ArrayList<>();
			int Chance = 100;
			String Roll = new String();
			for(String block : weapon.split(";"))
			{
				String[] args = block.split("@");
				
				if(args[0].equals("ID")) ID = Integer.parseInt(args[1]);
				if(args[0].equals("NAME")) Name = args[1];
				if(args[0].equals("LORE")) for(String str : args[1].split(",")) Lore.add(str);
				if(args[0].equals("CHANCE")) Chance = Integer.parseInt(args[1]);
				if(args[0].equals("ROLL")) Roll = args[1];
			}
			itm = new ItemStack(Material.getMaterial(ID));
			itm = setItemDetails(itm, Name, Lore);
			itm = setSoulBound(itm, Roll);
			if(getRollOutcome(Chance))
			{
				this.ItemStacks.add(itm);
			}
		}
	}
	
	private ItemStack setSoulBound(ItemStack itm, String Roll)
	{
		if(Roll.equals("USE"))
		{
			ItemAPI.bindOnUseItem(itm);
		}else if(Roll.equals("EQUIP"))
		{
			ItemAPI.bindOnEquipItem(itm);
		}else if(Roll.equals("PICKUP"))
		{
			ItemAPI.bindOnPickupItem(itm);
		}
		return itm;
	}
	
	private void getLoadBoss()
	{
		for(LoadBoss ll : this.rpg.BossList)
		{
			if(ll.getBossName().equals(this.BossName))
			{
				this.LB = ll;
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
	
	private ItemStack setItemDetails(ItemStack itm, String name, List<String> lore)
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
