package rc2k7.plugins.rollercore.api;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import rc2k7.plugins.rollercore.RollerCore;
import rc2k7.plugins.rollercore.base.Roll;
import rc2k7.plugins.rollercore.util.ActionManager;
import rc2k7.plugins.rollercore.util.PlayerManager;
import rc2k7.plugins.rollercore.util.RollManager;

public class RollerAPI {
	
	public RollerAPI(){}
	
	/*This Does A Roll Action
	 * 
	 * @param p The Player That Is Rolling
	 */
	public static void doRoll(Player p){
		ActionManager.doRoll(p);
	}
	
	/*This Does A Pass Action
	 * 
	 * @param p The Player That Is Passing
	 */
	public static void doPass(Player p){
		ActionManager.doPass(p);
	}
	
	/*This Does A Roll Action
	 * 
	 * @param p The Player That Is Rolling
	 */
	public static void doInfo(Player p){
		ActionManager.doInfo(p);
	}
	
	/*This Adds A Roll To The List
	 * 
	 * @param a The Roll Being Added
	 */
	public static void addRoll(Roll a){
		RollManager.addRoll(a);
	}
	
	/*This Removes A Roll From The List
	 * 
	 * @param a The Index Of The Roll Being Removed
	 */
	public static void remRoll(int a){
		RollManager.remRoll(a);
	}
	
	/*This Adds An ItemStack To A Specified Roll
	 * 
	 * @param itm This Is The ItemStack Being Added
	 * @param i This Is The Index Of The Roll
	 */
	public static void addItem(ItemStack itm, int i){
		ActionManager.addItem(itm, i);
	}
	
	/*This Sets The List Within A Roll
	 * 
	 * @param l This Is The Player List Of Names Taht Will Be Set
	 * @param i This Is The Index Of The Roll
	 */
	public static void setPlayers(List<String> l, int i){
		PlayerManager.setPlayers(l, i);
	}
	
	/*This Reloads The Configuration File
	 * 
	 */
	public static void reloadConfig(){
		RollerCore.rc.loadConfig();
	}

}
