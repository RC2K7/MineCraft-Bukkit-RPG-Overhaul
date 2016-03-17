package RC2K7.Plugins.RPGQuest.Commands;

import RC2K7.Plugins.RPGAPI.CommandAPI.MultiCmd;
import RC2K7.Plugins.RPGAPI.CommandAPI.SubInfo;
import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;
import RC2K7.Plugins.RPGQuest.RPGQuest;
import RC2K7.Plugins.RPGQuest.NPC.QuestNPC;

public class QuestEditCommands {
	
	RPGQuest RPG;
	
	public QuestEditCommands(RPGQuest rpg)
	{
		this.RPG = rpg;
	}
	
	@MultiCmd(description="List All Quests",
			permission="rpgq.admin.q.list",
			minArgs=0,
			usage="",
			allowConsole=true)
	public void list(SubInfo info)
	{
		if(this.RPG.QM.getQuests().isEmpty())
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "No Quests Have Been Created!");
			return;
		}
		for(int x = 0; x < this.RPG.QM.getQuests().size(); x++)
		{
			MultiColorUtil.send(info.getSender(), "{gold}%d {reset}%s", x, this.RPG.QM.getQuests().get(x).getQuestName());
		}
	}
	
	@MultiCmd(description="Add A New Quest",
			permission="rpgq.admin.q.add",
			minArgs=2,
			usage="<QUEST_NAME> <MAJOR[TRUE/FALSE]>",
			allowConsole=true)
	public void add(SubInfo info)
	{
		if(!info.getIntArgs(1).equalsIgnoreCase("true") && !info.getIntArgs(1).equalsIgnoreCase("false"))
		{
			MultiColorUtil.send(info.getSender(), "{green}/rpgq q add {gold}<QUEST_NAME> <MAJOR[TRUE/FALSE]>");
			return;
		}
		if(this.RPG.QM.containsQuest(info.getIntArgs(0).replaceAll("_", " ")))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Already Exists!");
			return;
		}
		this.RPG.QM.addQuest(info.getIntArgs(0).replaceAll("_", " "), info.getIntArgs(1).equalsIgnoreCase("true"));
                this.RPG.LoadConfig.SaveQuest(this.RPG.QM.getQuest(info.getIntArgs(0).replaceAll("_", " ")));
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Has Been Added.", info.getIntArgs(0).replaceAll("_", " "));
	}
	
	@MultiCmd(description="Delete A Quest",
			permission="rpgq.admin.q.del",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=true)
	public void del(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
		}
		this.RPG.QM.delQuest(name);
		MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Has Been Deleted.", name);
	}
	
	@MultiCmd(description="Link Quest Start To Closest NPC",
			permission="rpgq.admin.q.linkstart",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=false)
	public void linkstart(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		QuestNPC qnpc = this.RPG.QNPCM.getNPCClosestToLocation(info.getPlayer().getLocation());
		this.RPG.QM.getQuest(name).linkStart(qnpc);
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Start NPC Has Been Linked To {green}%s", name, qnpc.getName());
	}
	
	@MultiCmd(description="Link Quest End To Closest NPC",
			permission="rpgq.admin.q.linkend",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=false)
	public void linkend(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		QuestNPC qnpc = this.RPG.QNPCM.getNPCClosestToLocation(info.getPlayer().getLocation());
		this.RPG.QM.getQuest(name).linkEnd(qnpc);
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}End NPC Has Been Linked To {green}%s", name, qnpc.getName());
	}
	
	@MultiCmd(description="UnLink Quest Start",
			permission="rpgq.admin.q.unlinkstart",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=true)
	public void unlinkstart(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		this.RPG.QM.getQuest(name).linkStart(null);
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Start NPC Has Been UnLinked", name);
	}
	
	@MultiCmd(description="UnLink Quest End",
			permission="rpgq.admin.q.unlinkend",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=true)
	public void unlinkend(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		this.RPG.QM.getQuest(name).linkEnd(null);
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}End NPC Has Been UnLinked", name);
	}
	
	@MultiCmd(description="Enable A Quest",
			permission="rpgq.admin.q.enable",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=true)
	public void enable(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		this.RPG.QM.getQuest(name).setEnable(true);
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Has Been Enabled", name);
	}
	
	@MultiCmd(description="Disable A Quest",
			permission="rpgq.admin.q.disable",
			minArgs=1,
			usage="<QUEST_NAME>",
			allowConsole=true)
	public void disable(SubInfo info)
	{
		String name = info.getIntArgs(0).replaceAll("_", " ");
		if(!this.RPG.QM.containsQuest(name))
		{
			MultiColorUtil.send(info.getSender(), this.RPG.QuestSettings.Stub + "Quest Does Not Exist!");
			return;
		}
		this.RPG.QM.getQuest(name).setEnable(true);
		MultiColorUtil.send(info.getPlayer(), this.RPG.QuestSettings.Stub + "Quest {blue}%s {reset}Has Been Disabled", name);
	}

}
