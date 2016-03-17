package RC2K7.Plugins.RPGAPI.CommandAPI;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import RC2K7.Plugins.RPGAPI.Util.MultiColorUtil;

public class SubInfo
{

	private final CommandSender Sender;
	private final Player Player;
	private final String BaseCommand;
	private final SubCommand SubCommand;
	private final String ReplyPrefix;
	
	private List<String> Args;
	
	public SubInfo(CommandSender sender, Player player, String basecommand, SubCommand subcommand, List<String> args)
	{
		Validate.notNull(sender);
		Validate.notEmpty(basecommand);
		Validate.notNull(subcommand);
		this.Sender = sender;
		this.Player = player;
		this.Args = args;
		this.BaseCommand = basecommand;
		this.SubCommand = subcommand;
		this.ReplyPrefix = "{blue}Reply: ";
	}
	
	public Player getPlayer(){ return this.Player; }
	public CommandSender getSender(){ return this.Sender; }
	public List<String> getArgs(){ return this.Args; }
	public String getIntArgs(int index){ return this.Args.get(index); }
	public String getJoinedArgsAfter(int index){ return StringUtils.join(this.Args.subList(index, this.Args.size()), " "); }
	public int getNumArgs(){ return this.Args.size(); }
	public String getBaseCommand(){ return this.BaseCommand; }
	public SubCommand getSubCommand(){ return this.SubCommand; }
	public void reply(String message, Object ... args){ reply(true, message, args); }
	
	public void reply(boolean prefix, String message, Object ... args)
	{
		if(prefix)
		{
			MultiColorUtil.send(this.Sender,this.ReplyPrefix + message, args);
		}else
		{
			MultiColorUtil.send(this.Sender, message, args);
		}
	}
	
}
