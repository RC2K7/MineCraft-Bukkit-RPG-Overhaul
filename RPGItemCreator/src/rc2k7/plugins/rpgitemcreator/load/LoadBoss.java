package rc2k7.plugins.rpgitemcreator.load;

import java.util.List;

public class LoadBoss {
	
	String BossName;
	List<String> LootLevelList;
	List <String> Weapons;
	
	public LoadBoss(String boss, List<String> lootlevellist, List<String> weapons){
		this.BossName = boss;
		this.LootLevelList = lootlevellist;
		this.Weapons = weapons;
	}
	
	public String getBossName(){ return this.BossName; }
	
	public List<String> getLootLevelList(){ return this.LootLevelList; }
	
	public List<String> getWeapons(){ return this.Weapons; }

}
