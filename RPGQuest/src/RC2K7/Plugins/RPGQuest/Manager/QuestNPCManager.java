package RC2K7.Plugins.RPGQuest.Manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;

import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;

public class QuestNPCManager
{
    
    public RPGQuest RPG;
    public List<QuestNPC> QuestNPCs = new ArrayList<>();
    
    public QuestNPCManager(RPGQuest rpg)
    {
        this.RPG = rpg;
    }
    
    public void addQuestNPC(QuestNPC qnpc)
    {
        if(!this.QuestNPCs.contains(qnpc))
        {
            this.QuestNPCs.add(qnpc);
        }
    }
    
    public void delQuestNPC(QuestNPC qnpc)
    {
        if(this.QuestNPCs.contains(qnpc))
        {
            qnpc.removeNPC();
        }
    }
    
    public boolean isQuestNPC(Entity ent)
    {
        for(QuestNPC npc : this.QuestNPCs)
        {
            if(npc.getLivingEntity() != null && npc.getUUID() == ent.getUniqueId())
            {
                return true;
            }
        }
        return false;
    }
    
    public QuestNPC getQuestNPC(Entity ent)
    {
        for(QuestNPC npc : this.QuestNPCs)
        {
            if(npc.getLivingEntity() != null && npc.getUUID() == ent.getUniqueId())
            {
                return npc;
            }
        }
        return null;
    }
    
    public void deSpawnAll()
    {
        for(QuestNPC npc : this.QuestNPCs)
        {
            npc.deSpawn();
        }
    }
    
    public boolean isNPCEquip(Inventory inv)
    {
        for(QuestNPC qnpc : this.QuestNPCs)
        {
            if(qnpc.getInventory().getName().equals(inv.getName()))
            {
                return true;
            }
        }
        return false;
    }
    
    public QuestNPC getNPCFromEquip(Inventory inv)
    {
        for(QuestNPC qnpc : this.QuestNPCs)
        {
            if(qnpc.getInventory().getName().equals(inv.getName()))
            {
                return qnpc;
            }
        }
        return null;
    }
    
    public QuestNPC getNPCAtLocation(Location loc)
    {
        if(loc == null)
            return null;
        QuestNPC npc = null;
        for(QuestNPC qnpc : this.QuestNPCs)
        {
            qnpc.getLocation().getLocation().equals(loc);
            return qnpc;
        }
        return null;
    }
    
    public QuestNPC getNPCClosestToLocation(Location loc)
    {
        QuestNPC npc = null;
        for(QuestNPC qnpc : this.QuestNPCs)
        {
            if(npc == null || this.isNPCCloserThanNPC(npc, qnpc, loc))
            {
                npc = qnpc;
            }
        }
        return npc;
    }
    
    private boolean isNPCCloserThanNPC(QuestNPC a, QuestNPC b, Location loc)
    {
        return a.getLocation().getLocation().distance(loc) > b.getLocation().getLocation().distance(loc);
    }
    
}
