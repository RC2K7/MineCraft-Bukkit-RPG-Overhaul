package RC2K7.Plugins.RPGQuest.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;

public class QuestNPCListeners implements Listener {
	
	public RPGQuest RPG;
	
	public QuestNPCListeners(RPGQuest rpg)
	{
		this.RPG = rpg;
		Bukkit.getPluginManager().registerEvents(this, this.RPG);
	}
	
	@EventHandler
	public void onCombust(EntityCombustEvent event)
	{
		if(this.RPG.QNPCM.isQuestNPC(event.getEntity()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event)
	{
		if(this.RPG.QNPCM.isQuestNPC(event.getEntity()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void on(EntityTargetEvent event)
	{
		if(event.getEntity() != null && this.RPG.QNPCM.isQuestNPC(event.getEntity()))
		{
			event.setCancelled(true);
		}
		if(event.getTarget() != null && this.RPG.QNPCM.isQuestNPC(event.getTarget()))
		{
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event)
	{
		final Chunk chunk = event.getChunk();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.RPG, new Runnable() {
                public void run() {
                        if (chunk.isLoaded()) {
                                loadQuestNPCS(chunk);
                        }
                }
        }, 2);
	}
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event)
	{
		for(Entity ent : event.getChunk().getEntities())
		{
			if(this.RPG.QNPCM.isQuestNPC(ent))
			{
				this.RPG.QNPCM.getQuestNPC(ent).deSpawn();
			}
		}
	}
	
	public void loadQuestNPCS(Chunk chunk)
	{
		for(QuestNPC npc : this.RPG.QNPCM.QuestNPCs)
		{
			if(npc.getChunk().equals(chunk))
			{
				npc.spawn();
			}
		}
	}

}
