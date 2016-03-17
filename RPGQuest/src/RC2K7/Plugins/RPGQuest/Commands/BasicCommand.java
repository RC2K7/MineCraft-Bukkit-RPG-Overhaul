package RC2K7.Plugins.RPGQuest.Commands;

import org.bukkit.entity.EntityType;
import RC2K7.Plugins.RPGAPI.CommandAPI.MultiCmd;
import RC2K7.Plugins.RPGAPI.CommandAPI.SubInfo;
import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;
import RC2K7.Plugins.RPGQuest.Serialization.SerializeLocation;

public class BasicCommand {
	
	RPGQuest RPG;
	
	public BasicCommand(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	@MultiCmd(description="Add A QuestNPC To The World",
			permission="rpgq.admin.addnpc",
			minArgs=2,
			usage="<ENTITY_TYPE> <NAME>",
			allowConsole=false)
	public void addnpc(SubInfo info)
	{
		if(info.getArgs().get(0).equalsIgnoreCase("villager")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.VILLAGER, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("zombie")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.ZOMBIE, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("skeleton")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.SKELETON, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("pig")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.PIG, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("chicken")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.CHICKEN, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("cow")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.COW, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("creeper")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.CREEPER, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("wolf")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.WOLF, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("witch")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.WITCH, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("spider")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.SPIDER, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("slime")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.SLIME, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("snowman")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.SNOWMAN, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("cavespider")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.CAVE_SPIDER, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("giant")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.GIANT, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("irongolem")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.IRON_GOLEM, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("enderman")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.ENDERMAN, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("silverfish")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.SILVERFISH, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("sheep")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.SHEEP, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("pigzombie")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.PIG_ZOMBIE, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("ocelot")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.OCELOT, new SerializeLocation(info.getPlayer().getLocation()), null));
		if(info.getArgs().get(0).equalsIgnoreCase("blaze")) this.RPG.QNPCM.addQuestNPC(new QuestNPC(this.RPG, info.getArgs().get(1).replaceAll("_", " "), EntityType.WOLF, new SerializeLocation(info.getPlayer().getLocation()), null));
		this.RPG.LoadConfig.SaveNPCs();
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "{blue}%s {reset}Has Been Added.", info.getIntArgs(1).replaceAll("_", " "));
	}
	
	@MultiCmd(description="Deletes Clostest QuestNPC To Sender",
			permission="rpgq.admin.delnpc",
			minArgs=0,
			usage="",
			allowConsole=false)
	public void delnpc(SubInfo info)
	{
		QuestNPC qn = null;
		double dist = 200;
		for(QuestNPC npc : this.RPG.QNPCM.QuestNPCs)
		{
			double tmp = info.getPlayer().getLocation().distance(npc.getLocation().getLocation());
			if(tmp < dist)
			{
				qn = npc;
				dist = tmp;
			}
		}
		if(qn != null)
		{
			MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "{blue}%s {reset}Has Been Deleted.", qn.getName());
			qn.removeNPC();
			this.RPG.LoadConfig.SaveNPCs();
		}
	}
	
	@MultiCmd(description="Reloads All Configuration Files",
			permission="rpgq.admin.reload",
			minArgs=0,
			usage="",
			allowConsole=true)
	public void reload(SubInfo info)
	{
		this.RPG.QNPCM.deSpawnAll();
		this.RPG.NPCConfig.reloadCustomConfig();
		this.RPG.LoadConfig.LoadNPCs();
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "{green}Configs Have Been Reloaded");
	}
}
