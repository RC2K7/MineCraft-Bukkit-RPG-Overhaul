package RC2K7.Plugins.RPGQuest.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;
import RC2K7.Plugins.RPGQuest.Quest.PlayerQuestLog;
import RC2K7.Plugins.RPGQuest.Quest.Quest;

public class PlayerListener implements Listener {

	RPGQuest RPG;
	
	public PlayerListener(RPGQuest rpg)
	{
		this.RPG = rpg;
		Bukkit.getPluginManager().registerEvents(this, this.RPG);
	}
	
	//When Playing Clicks On An Entity
	@EventHandler
	public void onClickEntity(PlayerInteractEntityEvent event)
	{
		//Cancel Villager Default Trade Events
		if(event.getRightClicked() instanceof Villager)
		{
			event.setCancelled(true);
		}
		Player player = event.getPlayer();
		
		//Open OP Window To Modify NPC's
		if(player.isOp() && player.isSneaking())
		{
			if(player.getItemInHand() != null)
			{
				if(this.RPG.QNPCM.isQuestNPC(event.getRightClicked()))
				{
					QuestNPC qnpc = this.RPG.QNPCM.getQuestNPC(event.getRightClicked());
					player.openInventory(qnpc.getInventory());
				}
			}
		}
		
		//Quest Interaction
		if(event.getRightClicked() instanceof LivingEntity)
		{
			LivingEntity le = (LivingEntity)event.getRightClicked();
			Quest quest;
			if((quest = this.RPG.QM.getQuest(le)) != null)
			{
				if(!quest.isEnabled())
				{
					MultiColorUtil.send(player, this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Is Disabled!!", quest.getQuestName());
					return;
				}
				PlayerQuestLog pql = this.RPG.PQLM.getPlayerQuestLog(player.getName());
				if(!pql.containsQuest(quest))
				{
					pql.addQuest(quest);
					player.sendMessage(this.RPG.QuestSettings.Stub + quest.getQuestName() + " Has Been Started.");
				}
			}
		}
	}
	
	//When Inventory Is Closed
	@EventHandler
	public void onInvClose(InventoryCloseEvent event)
	{
		Player player = (Player)event.getPlayer();
		if(player.isOp())
		{
			if(this.RPG.QNPCM.isNPCEquip(event.getInventory()))
			{
				QuestNPC qnpc = this.RPG.QNPCM.getNPCFromEquip(event.getInventory());
				qnpc.updateEquipment();
				this.RPG.LoadConfig.SaveNPCs();
			}
		}
	}
	
	//On Player Join Event
	public void onJoin(PlayerJoinEvent event)
	{
		this.RPG.PQLM.addPlayerQuestLog(event.getPlayer().getName());
	}
	
	//On Player Leave Event
	public void onLeave(PlayerQuitEvent event)
	{
		this.RPG.PQLM.delPlayerQuestLog(event.getPlayer().getName());
	}
	
}
