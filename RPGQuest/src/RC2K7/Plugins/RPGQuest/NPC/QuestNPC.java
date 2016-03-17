package RC2K7.Plugins.RPGQuest.NPC;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.Serialization.SerializeChunk;
import RC2K7.Plugins.RPGQuest.Serialization.SerializeLocation;

public class QuestNPC {
	
	RPGQuest RPG;
	String Name;
	SerializeLocation Loc;
	SerializeChunk Chunk;
	
	EntityType Ent;
	LivingEntity LE;
	UUID ID;
	
	Inventory Equipment;
	
	//Constructor
	public QuestNPC(RPGQuest rpg, String name, EntityType ent, SerializeLocation loc, Inventory inv)
	{
		this.RPG = rpg;
		this.Name = name;
		this.Ent = ent;
		this.Loc = loc;
		this.Loc.getLocation();
		this.Chunk = new SerializeChunk(this.Loc.getLocation().getChunk());
		if(inv == null) this.Equipment = Bukkit.createInventory(null, 9, this.Name);
		else this.Equipment = inv;
		this.spawn();
	}
	
	//Constructor
	public QuestNPC(RPGQuest rpg, String name, int ent, SerializeLocation loc, Inventory inv)
	{
		this.RPG = rpg;
		this.Name = name;
		this.Ent = EntityType.fromId(ent);
		this.Loc = loc;
		this.Chunk = new SerializeChunk(this.Loc.getLocation().getChunk());
		if(inv == null) this.Equipment = Bukkit.createInventory(null, 9, this.Name);
		else this.Equipment = inv;
		this.spawn();
	}
	
	//Spawn QuestNPC At Location
	public void spawn()
	{
		if(this.LE == null || this.LE.isDead())
		{
			this.LE = (LivingEntity)(this.Loc.getLocation().getWorld()).spawnEntity(this.Loc.getLocation(), this.Ent);
			this.ID = this.LE.getUniqueId();
			this.RPG.overWrite.overwriteLivingEntityAI(this.LE);
			this.LE.setCustomName(this.Name);
			this.LE.setCustomNameVisible(true);
			this.updateEquipment();
		}
	}
	
	//Remove QuestNPC From The World
	public void deSpawn()
	{
		if(this.LE != null)
		{
			this.LE.remove();
			this.LE = null;
			this.ID = null;
		}
	}
	
	//Remove QuestNPC Entirely
	public void removeNPC()
	{
		deSpawn();
		this.RPG.QNPCM.QuestNPCs.remove(this);
	}
	
	//Update QuestNPC's Equipment
	public void updateEquipment()
	{
		for(int x = 0; x <= 4; x++)
		{
			ItemStack itm = this.Equipment.getItem(x);
			if(x == 0) this.LE.getEquipment().setItemInHand(itm);
			else if(x == 1) this.LE.getEquipment().setBoots(itm);
			else if(x == 2) this.LE.getEquipment().setLeggings(itm);
			else if(x == 3) this.LE.getEquipment().setChestplate(itm);
			else if(x == 4) this.LE.getEquipment().setHelmet(itm);
		}
	}
	
	//Getters
	public Inventory getInventory(){ return this.Equipment; }
	public String getName(){ return this.Name; }
	public LivingEntity getLivingEntity(){ return this.LE; }
	public SerializeLocation getLocation(){ return this.Loc; }
	public UUID getUUID(){ return this.ID; }
	public EntityType getEntityType(){ return this.Ent; }
	public SerializeChunk getChunk(){ return this.Chunk; }
}
