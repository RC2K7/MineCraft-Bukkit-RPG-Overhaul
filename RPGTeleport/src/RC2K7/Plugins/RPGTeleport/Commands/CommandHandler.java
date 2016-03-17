package RC2K7.Plugins.RPGTeleport.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;

public class CommandHandler implements CommandExecutor {
	
	RPGTeleport RPG;
	CommandPlayer CP;
	CommandConsole CC;
	
	public CommandHandler(RPGTeleport rpg)
	{
		this.RPG = rpg;
		this.CP = new CommandPlayer(this.RPG);
		this.CC = new CommandConsole(this.RPG);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(sender instanceof Player)
			return this.CP.onCommand((Player)sender, args);
		else
			return this.CC.onCommand(sender, args);
	}
	
}
