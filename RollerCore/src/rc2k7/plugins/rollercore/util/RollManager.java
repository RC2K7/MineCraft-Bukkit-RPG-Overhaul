package rc2k7.plugins.rollercore.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import rc2k7.plugins.rollercore.base.Roll;

public class RollManager {
	
	private static List<Roll> rList = new ArrayList<>();
	
	/*Add Roll To List
	 * 
	 * @param a The Roll That Will Be Added
	 */
	public static void addRoll(Roll a){
		if(!inRollList(a))
			rList.add(a);
	}
	
	/*Remove Roll From List
	 * 
	 * @param a The Roll To Be Removed
	 */
	public static void remRoll(Roll a){
		if(inRollList(a))
			rList.remove(a);
	}
	
	/*Remove Roll From List
	 * 
	 * @param a The Index Of The Roll To Be Removed
	 */
	public static void remRoll(int a){
		if(rList.size() > a)
			rList.remove(a);
	}
	
	/*Get Roll List
	 * 
	 * @param a The Index Of The Roll Getting Grabbed
	 */
	public static Roll getRollFromList(int a){
		return rList.get(a);
	}
	
	/*Get Roll List
	 * 
	 * @param a The Player Inside The Desired Roll
	 */
	public static Roll getRollFromList(Player p){
		for(Roll r : RollManager.getRollList())
			for(String l : r.getPlayers())
				if(l.equals(p.getDisplayName()))
					return r;
		return null;
	}
	
	/*Get Roll List
	 * 
	 */
	public static List<Roll> getRollList(){
		return rList;
	}
	
	
	/*Check If Roll Exists In List
	 * 
	 * @param A The Roll Being Checked For
	 */
	private static boolean inRollList(Roll a){
		return rList.contains(a);
	}

}
