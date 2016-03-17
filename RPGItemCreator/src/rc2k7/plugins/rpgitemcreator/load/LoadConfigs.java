package rc2k7.plugins.rpgitemcreator.load;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import rc2k7.plugins.rpgitemcreator.RPGItemCreator;

public class LoadConfigs {
	
	public RPGItemCreator rpg;
	
	public LoadConfigs(RPGItemCreator rpg){
		this.rpg = rpg;
		LoadLevels();
		LoadBosses();
	}
	
	public void LoadLevels()
	{
		this.rpg.LevelList.clear();
		String path = new String();
		if(this.rpg.itemConfig.getConfig().contains("Levels"))
		{
			path = "Levels";
			for(String level : this.rpg.itemConfig.getConfig().getConfigurationSection(path).getKeys(false))
			{
				path = "Levels." + level;
				int ilvl = Integer.parseInt(level);
				List<String> ItemIDs = new ArrayList<>();
				String Amount = new String();
				Map<String, List<String>> Lores = new HashMap<>();
				Map<String, List<String>> Prefixes = new HashMap<>();
				Map<String, List<String>> Suffixes = new HashMap<>();
				if(this.rpg.itemConfig.getConfig().contains(path + ".ItemIDs"))
				{
					ItemIDs = this.rpg.itemConfig.getConfig().getStringList(path + ".ItemIDs");
				}
				if(this.rpg.itemConfig.getConfig().contains(path + ".Amount"))
				{
					Amount = this.rpg.itemConfig.getConfig().getString(path + ".Amount");
				}
				if(this.rpg.itemConfig.getConfig().contains(path + ".Group.Lore"))
				{
					for(String attrib : this.rpg.itemConfig.getConfig().getConfigurationSection(path + ".Group.Lore").getKeys(false)){
						if(this.rpg.itemConfig.getConfig().contains(path + ".Group.Lore." + attrib))
						{
							Lores.put(attrib, this.rpg.itemConfig.getConfig().getStringList(path + ".Group.Lore." + attrib));
						}
					}
				}
				if(this.rpg.itemConfig.getConfig().contains(path + ".Group.Prefix"))
				{
					for(String attrib : this.rpg.itemConfig.getConfig().getConfigurationSection(path + ".Group.Prefix").getKeys(false)){
						if(this.rpg.itemConfig.getConfig().contains(path + ".Group.Prefix." + attrib))
						{
							Prefixes.put(attrib, this.rpg.itemConfig.getConfig().getStringList(path + ".Group.Prefix." + attrib));
						}
					}
				}
				if(this.rpg.itemConfig.getConfig().contains(path + ".Group.Suffix"))
				{
					for(String attrib : this.rpg.itemConfig.getConfig().getConfigurationSection(path + ".Group.Suffix").getKeys(false)){
						if(this.rpg.itemConfig.getConfig().contains(path + ".Group.Suffix." + attrib))
						{
							Suffixes.put(attrib, this.rpg.itemConfig.getConfig().getStringList(path + ".Group.Suffix." + attrib));
						}
					}
				}
				
				this.rpg.LevelList.add(new LoadLevel(ilvl, ItemIDs, Amount, Lores, Prefixes, Suffixes));
			}
		}
	}
	
	public void LoadBosses()
	{
		this.rpg.BossList.clear();
		String path = new String();
		if(this.rpg.itemConfig.getConfig().contains("Bosses"))
		{
			path = "Bosses";
			for(String name : this.rpg.itemConfig.getConfig().getConfigurationSection(path).getKeys(false))
			{
				path = "Bosses." + name;
				List<String> LootLevelList = new ArrayList<>();
				List<String> Weapons = new ArrayList<>();
				if(this.rpg.itemConfig.getConfig().contains(path + ".LootLevelList"))
				{
					String[] blocks = this.rpg.itemConfig.getConfig().getString(path + ".LootLevelList").split(";");
					for(String str : blocks)
					{
						LootLevelList.add(str);
					}
				}
				
				if(this.rpg.itemConfig.getConfig().contains(path + ".Weapons"))
				{
					Weapons = this.rpg.itemConfig.getConfig().getStringList(path + ".Weapons");
				}
				
				this.rpg.BossList.add(new LoadBoss(name, LootLevelList, Weapons));
			}
		}
	}

}
