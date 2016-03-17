package rc2k7.plugins.rpgitemcreator.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import rc2k7.plugins.rpgitemcreator.RPGItemCreator;

public class CommandHandler implements CommandExecutor{
	
	public RPGItemCreator rpg;
	public CommandPlayer cp;
	public CommandConsole cc;
	
	public CommandHandler(RPGItemCreator rpg){
		this.rpg = rpg;
		this.cp = new CommandPlayer(this.rpg);
		this.cc = new CommandConsole(this.rpg);
	}

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(arg0 instanceof Player){
			return this.cp.Command((Player)arg0, arg2, arg3);
		}else{
			this.cc.Command(arg0, arg2, arg3);
		}
		return false;
	}

}
