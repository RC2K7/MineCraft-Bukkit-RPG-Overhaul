package RC2K7.Plugins.RPGAPI.CommandAPI;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class SubCommand
{
	
	private final String name;
	private final String permission;
	private boolean allow_console = false;
	private int minAgs = 0;
	private MultiHandler handler = null;
	private String description;
	private String usage = null;
	
	public SubCommand(String name, String permission)
	{
		this.name = name;
		this.permission = permission;
	}
	
	public SubCommand allowConsole()
	{
		this.allow_console = true;
		return this;
	}
	
	public boolean isConsoleAllowed()
	{
		return this.allow_console;
	}
	
	public int getMinArgs()
	{
		return this.minAgs;
	}
	
	public SubCommand setMinArgs(int minArgs)
	{
		Validate.isTrue(minArgs >= 0, "minArgs Cannot Be Less Than 0");
		this.minAgs = minArgs;
		return this;
	}
	
	public MultiHandler getHandler()
	{
		return this.handler;
	}
	
	public SubCommand setHandler(MultiHandler handler)
	{
		Validate.notNull(handler);
		this.handler = handler;
		return this;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getUsage()
	{
		return this.usage;
	}
	
	public SubCommand setUsage(String usage)
	{
		this.usage = usage;
		return this;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public SubCommand setDescription(String description)
	{
		this.description = description;
		return this;
	}
	
	public String getPermission()
	{
		return this.permission;
	}
	
	public boolean checkPermission(CommandSender sender)
	{
		if(this.permission == null) return true;
		return sender.hasPermission(this.permission);
	}
	
	public boolean checkConsole(CommandSender sender)
	{
		if(!(sender instanceof Player) && !this.allow_console) return false;
		return true;
	}

}
