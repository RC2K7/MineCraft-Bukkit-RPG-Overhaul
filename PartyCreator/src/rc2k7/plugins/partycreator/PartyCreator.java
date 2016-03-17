package rc2k7.plugins.partycreator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import rc2k7.plugins.partycreator.teleport.TeleInvite;
import rc2k7.plugins.rollercore.api.RollerAPI;

public class PartyCreator extends JavaPlugin {
	
	public static List<Party> pList = new ArrayList<>();
	public static List<Invite> iList = new ArrayList<>();
	public static List<String> pcList = new ArrayList<>();
	
	public static List<TeleInvite> tiList = new ArrayList<>();
	
	public static boolean hasHighRoller = false;
	
	public static int maxPlayers = 4;
	public static Logger log = null;
	public static JavaPlugin plugin;
	
	public void onEnable() {
		pList = new ArrayList<>();
		iList = new ArrayList<>();
		pcList = new ArrayList<>();
		plugin = this;
		log = Bukkit.getLogger();
		if(Bukkit.getPluginManager().isPluginEnabled("RollerCore")){
			hasHighRoller = true;
			log.info(ChatColor.YELLOW + "Hooked Onto RollerCore Plugin");
		}
		Bukkit.getPluginManager().registerEvents(new Listener(), this);
		this.saveDefaultConfig();
		loadConfig();
	}
	
	public void onDisable() {
		pList = new ArrayList<>();
		iList = new ArrayList<>();
		pcList = new ArrayList<>();
	}
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(sender instanceof Player && (label.equalsIgnoreCase("party") || label.equalsIgnoreCase("pc")) && args.length >= 1){
			Player player = (Player)sender;
			//TELEPORT COMMAND-----------------------------------------------------------------
			if(args[0].equalsIgnoreCase("tp")){
				if(args.length != 2){
					Util.sendMessage(player,  "/party tp <NAME>");
					return true;
				}
				Party party = null;
				if((party = Util.getParty(player.getDisplayName())) != null){
					if(!party.getLeader().equals(player.getDisplayName())){
						Util.sendMessage(player, "You Are Not The Party Leader.");
						return true;
					}
					if(!player.hasPermission("rc2k7.partycreator.summon")){
						Util.sendMessage(player, ChatColor.RED + "You Do Not Have Permission To Use This Command.");
						return true;
					}
				}else{
					Util.sendMessage(player, "You Are Not In A Party.");
					return true;
				}
				Player b = Bukkit.getPlayer(args[1]);
				if( b == null || player.getDisplayName().equals(b.getDisplayName())){
					Util.sendMessage(player, "Could Not Find Player.");
					return true;
				}
				Party pB = Util.getParty(b.getDisplayName());
				if(pB == null || (party != pB)){
					Util.sendMessage(player, "You Are Not In The Same Party.");
					return true;
				}
				if(Util.getTeleInvite(b.getDisplayName()) != null){
					Util.sendMessage(player, "Player Already Has A Teleport Request.");
					return true;
				}
				tiList.add(new TeleInvite(player, b));
				return true;
			}
			//TELEPORTALL COMMAND-----------------------------------------------------------------
			if(args[0].equalsIgnoreCase("tpall")){
				if(args.length != 1){
					Util.sendMessage(player,  "/party tpall");
					return true;
				}
				Party party = null;
				if((party = Util.getParty(player.getDisplayName())) != null){
					if(!party.getLeader().equals(player.getDisplayName())){
						Util.sendMessage(player, "You Are Not The Party Leader.");
						return true;
					}
					if(!player.hasPermission("rc2k7.partycreator.summon")){
						Util.sendMessage(player, ChatColor.RED + "You Do Not Have Permission To Use This Command.");
						return true;
					}
				}else{
					Util.sendMessage(player, "You Are Not In A Party.");
					return true;
				}
				for(Player play : party.getMembers()){
					if(Util.getTeleInvite(play.getDisplayName()) != null)
						continue;
					if(player.getDisplayName().equals(play.getDisplayName()))
						continue;
					tiList.add(new TeleInvite(player, play));
				}
				return true;
			}
			//INVITE COMMAND-------------------------------------------------------------------
			if(args[0].equalsIgnoreCase("invite")){
				if(!player.hasPermission("rc2k7.partycreator.invite")){
					Util.sendMessage(player, ChatColor.RED + "You Do Not Have Permission To Use This Command.");
					return true;
				}
				if(args.length != 2){
					Util.sendMessage(player, "/party invite <NAME>");
					return true;
				}
				Party party = null;
				//Checks If Player Is Leader
				if((party = Util.getParty(player.getDisplayName())) != null){
					if(!party.getLeader().equals(player.getDisplayName())){
						Util.sendMessage(player, "You Are Not The Party Leader.");
						return true;
					}
				}else{
					pList.add(party = new Party(player));
				}
				//Checks If Party Is Max Size
				if(party.getMembers().size() == maxPlayers){
					Util.sendMessage(player, "Your Party Is Full.");
					return true;
				}
				Player b = Bukkit.getPlayer(args[1]);
				//Checks If Target Player Was Not Found Or Is The Current Player
				if(b == null || player == b){
					Util.sendMessage(player, "Player Not Found.");
					if(party.getMembers().size() == 1 && !Util.hasPlayerInvited(player.getDisplayName()))
						party.removePlayer(player);
					return true;
				}
				//Checks If Target Player Already Has An Invitation
				if(Util.getInvite(b.getDisplayName()) != null){
					Util.sendMessage(player, b.getDisplayName() + " Already Has A Pending Invite.");
					return true;
				}
				//Check If Target Player Is Already In A Party
				if(Util.getParty(b.getDisplayName()) != null){
					Util.sendMessage(player, b.getDisplayName() + " Is Already In A Party.");
					return true;
				}
				iList.add(new Invite(player, b));
				Util.sendMessage(party.getMembers(), b.getDisplayName() + " Invited To Party.");
				return true;
				//ACCEPT COMMAND-----------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("accept")){
				Invite invite = null;
				TeleInvite tinvite = null;
				if((invite = Util.getInvite(player.getDisplayName())) == null && ((tinvite = Util.getTeleInvite(player.getDisplayName()))) == null){
					Util.sendMessage(player, "You Do Not Have Any Pending Invites.");
					return true;
				}
				if(invite != null)
					invite.acceptInvite();
				if(tinvite != null)
					tinvite.accept();
				return true;
				//DENY COMMAND-------------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("deny")){
				Invite invite = null;
				TeleInvite tinvite = null;
				if((invite = Util.getInvite(player.getDisplayName())) == null && ((tinvite = Util.getTeleInvite(player.getDisplayName()))) == null){
					Util.sendMessage(player, "You Do Not Have Any Pending Invites.");
					return true;
				}
				if(invite != null)
					invite.denyInvite();
				if(tinvite != null)
					tinvite.deny();
				return true;
				//KICK COMMAND-------------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("kick")){
				if(args.length != 2){
					Util.sendMessage(player, "/party kick <NAME>");
					return true;
				}
				Party party = null;
				if((party = Util.getParty(player.getDisplayName())) != null){
					if(!party.getLeader().equals(player.getDisplayName())){
						Util.sendMessage(player, "You Are Not The Party Leader.");
						return true;
					}
					Player b = Bukkit.getPlayer(args[1]);
					if(!party.getMembers().contains(b.getDisplayName())){
						Util.sendMessage(player, b.getDisplayName() + " Is Not In The Party.");
						return true;
					}
					party.removePlayer(b);
					Util.sendMessage(b, "You Were Kicked From The Party.");
					if(pcList.contains(b.getDisplayName()))
						pcList.remove(b.getDisplayName());
					return true;
				}else{
					Util.sendMessage(player, "You Are Not In A Party.");
				}
				//PROMOTE COMMAND----------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("promote")){
				if(args.length != 2){
					Util.sendMessage(player, "/party promote <NAME>");
					return true;
				}
				Party party = null;
				if((party = Util.getParty(player.getDisplayName())) != null){
					if(!party.getLeader().equals(player.getDisplayName())){
						Util.sendMessage(player, "You Are Not The Party Leader.");
						return true;
					}
					Player b = Bukkit.getPlayer(args[1]);
					if(Util.getPlayerFromList(party.getMembers(), b.getDisplayName()) == null){
						Util.sendMessage(player, b.getDisplayName() + " Is Not In The Party.");
						return true;
					}
					party.promotePlayer(b);
					return true;
				}else{
					Util.sendMessage(player, "You Are Not In A Party.");
				}
				//CHAT COMMAND-------------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("chat")){
				if(Util.getParty(player.getDisplayName()) == null){
					Util.sendMessage(player, "You Are Not In A Party.");
					return true;
				}
				if(pcList.contains(player.getDisplayName())){
					pcList.remove(player.getDisplayName());
					Util.sendMessage(player, "Party Chat Disabled.");
					return true;
				}else{
					pcList.add(player.getDisplayName());
					Util.sendMessage(player, "Party Chat Enabled.");
					return true;
				}
				//LEAVE COMMAND-----------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("leave")){
				Party party = null;
				if((party = Util.getParty(player.getDisplayName())) == null){
					Util.sendMessage(player, "You Are Not In A Party.");
					return true;
				}
				party.removePlayer(player);
				Util.sendMessage(player, "You Left The Party.");
				return true;
				//RELOAD COMMAND-----------------------------------------------------------------
			}else if(args[0].equalsIgnoreCase("reload")){
				if(!player.isOp())
					return true;
				loadConfig();
				Util.sendMessage(player, "Configs Reloaded.");
				return true;
			}else if(args[0].equalsIgnoreCase("roll")){
				RollerAPI.doRoll(player);
				return true;
			}else if(args[0].equalsIgnoreCase("pass")){
				RollerAPI.doPass(player);
				return true;
			}else if(args[0].equalsIgnoreCase("info")){
				RollerAPI.doInfo(player);
				return true;
			}else if(args[0].equalsIgnoreCase("reloadroll")){
				if(!player.isOp())
					return true;
				RollerAPI.reloadConfig();
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("pcc") && sender instanceof Player){
			if(args.length == 0)
				return false;
			Player send = (Player)sender;
			Party party = null;
			if((party = Util.getParty(send.getDisplayName())) != null){
				for(Player player : Bukkit.getOnlinePlayers())
					if(Util.getPlayerFromList(party.getMembers(), player.getDisplayName()) != null)
						player.sendMessage(send.getDisplayName() + " > " + ChatColor.AQUA + Util.combineArray(args));
			}else{
				Util.sendMessage(send, "You Are Not In A Party.");
			}
			return true;
		}
		return false;
	}

	
	public void loadConfig(){
		maxPlayers = getConfig().getInt("MaxPlayers");
	}
}
