package RC2K7.Plugins.RPGAPI.Util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class MultiColorUtil {
	
	private static HashMap<String, String> ColorMap = new HashMap<String, String>();
	static
	{
		for(ChatColor color : ChatColor.values())
		{
			ColorMap.put("{" + color.name().toLowerCase() + "}", color.toString());
		}
	}
	
	public static String colorText(String message, Object ... args)
	{
		for(Entry<String, String> entry : ColorMap.entrySet())
		{
			message = message.replace(entry.getKey(), entry.getValue());
		}
		if(args.length > 0)
		{
			return String.format(message, args);
		}
		return message;
	}
	
	public static void send(CommandSender sender, String message, Object ... args)
	{
		sender.sendMessage(colorText(message, args));
	}
	
	public static void send(String name, String message, Object ... args)
	{
		PlayerUtil.getPlayer(name).sendMessage(colorText(message, args));
	}

}
