package RC2K7.Plugins.RPGAPI.CommandAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;

public class MultiCommand implements CommandExecutor, MultiHandler
{
	
	private final Map<String, SubCommand> SubCommands = new LinkedHashMap<String, SubCommand>();
	
	public MultiCommand()
	{
		
	}

	@Override
	public void handle(SubInfo info) throws MultiException
	{
		String commandLabel = info.getBaseCommand() + " " + info.getSubCommand().getName();
		handleCommand(info.getSender(), info.getPlayer(), commandLabel, info.getArgs());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = null;
		if(sender instanceof Player)
		{
			player = (Player)sender;
			label = "/" + label;
		}
		List<String> argList = new ArrayList<String>(Arrays.asList(args));
		handleCommand(sender, player, label, argList);
		return false;
	}
	
	private void handleCommand(CommandSender sender, Player player, String label, List<String> args)
	{
		if(args.size() == 0)
		{
			showUsage(sender, player, label);
			return;
		}
		String scname = args.get(0).toLowerCase();
		SubCommand cmd = this.SubCommands.get(scname);
		if(cmd == null)
		{
			showUsage(sender, player, label);
			return;
		}else if(!cmd.checkConsole(sender))
		{
			MultiColorUtil.send(sender, "{red}You Can Not Use This Command From Console!!");
			return;
		}else if(!cmd.checkPermission(sender))
		{
			MultiColorUtil.send(sender, "{red}You Do Not Have Permission To Use This!!");
			return;
		}else if((args.size() - 1) < cmd.getMinArgs())
		{
			String msg = "{green}%s %s {gold}%s";
			MultiColorUtil.send(sender, msg, label, cmd.getName(), cmd.getUsage());
			return;
		}
		List<String> callArgs = new ArrayList<String>(args.subList(1, args.size()));
		SubInfo info = new SubInfo(sender, player, label, cmd, callArgs);
		try
		{
			cmd.getHandler().handle(info);
		}catch (MultiException e)
		{
			info.reply("{red}%s", e.getMessage());
		}
		return;
	}
	
	private void showUsage(CommandSender sender, Player player, String label)
	{
		MultiColorUtil.send(sender, "{gold}-- %s Command Menu --", label);
		String message = "{green}%s %s {gold}%s {dark_purple}%s";
		for(SubCommand sub : availableCommands(sender, player))
		{
			MultiColorUtil.send(sender, message, label, sub.getName(), sub.getUsage(), sub.getDescription());
		}
	}
	
	public SubCommand addSub(String name, String permission)
	{
		SubCommand cmd = new SubCommand(name, permission);
		this.SubCommands.put(name.toLowerCase(), cmd);
		return cmd;
	}
	
	public SubCommand addSub(String name)
	{
		return addSub(name, null);
	}
	
	private List<SubCommand> availableCommands(CommandSender sender, Player player)
	{
		ArrayList<SubCommand> items = new ArrayList<SubCommand>();
		boolean hasPlayer = (player != null);
		for(SubCommand sub : this.SubCommands.values())
		{
			if((hasPlayer || sub.isConsoleAllowed()) && sub.checkPermission(sender))
			{
				items.add(sub);
			}
		}
		return items;
	}
	
	public void autoRegisterFrom(Object handler)
	{
		new MultiCommandFinder(this).registerMethods(handler);
	}
	
	public List<SubCommand> listCommands()
	{
		return new ArrayList<SubCommand>(this.SubCommands.values());
	}

}
