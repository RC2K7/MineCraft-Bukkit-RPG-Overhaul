package RC2K7.Plugins.RPGBossBars.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGBossBars.RPGBossBars;

public class CommandHandler implements CommandExecutor {
	
	RPGBossBars RPG;
	
	String Stub = ChatColor.GOLD + "RPGBossBar: " + ChatColor.RESET;
	
	public CommandHandler(RPGBossBars rpg)
	{
		this.RPG = rpg;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player)
		{
			if(!((Player)arg0).isOp())
			{
				arg0.sendMessage(this.Stub + ChatColor.RED + "You Do Not Have Permission To Use This Command.");
				return true;
			}
		}
		if(arg3.length >= 1)
		{
			if(arg3[0].equalsIgnoreCase("reload"))
			{
				this.RPG.BossesConfig.reloadConfig();
				this.RPG.LC.LoadConfigs();
				arg0.sendMessage(this.Stub + ChatColor.GREEN + "Configs Have Been Reloaded.");
				return true;
			}
			if(arg3[0].equalsIgnoreCase("add"))
			{
				if(arg3.length != 2)
				{
					return false;
				}
				String name = arg3[1].replaceAll("_", " ");
				if(this.RPG.Setting.BossList.contains(name))
				{
					arg0.sendMessage(this.Stub + name + " Already Exists.");
					return true;
				}
				this.RPG.Setting.BossList.add(name);
				arg0.sendMessage(this.Stub + name + " Has Been Added.");
				this.RPG.LC.SaveConfigs();
				return true;
			}
			if(arg3[0].equalsIgnoreCase("del"))
			{
				if(arg3.length != 2)
				{
					return false;
				}
				String name = arg3[1].replaceAll("_", " ");
				if(!this.RPG.Setting.BossList.contains(name))
				{
					arg0.sendMessage(this.Stub + name + " Does Not Exist.");
					return true;
				}
				this.RPG.Setting.BossList.remove(name);
				arg0.sendMessage(this.Stub + name + " Has Been Removed.");
				this.RPG.LC.SaveConfigs();
				return true;
			}
		}
		return false;
	}

}
