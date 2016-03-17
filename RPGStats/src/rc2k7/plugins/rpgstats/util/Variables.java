package rc2k7.plugins.rpgstats.util;

import java.util.List;

public class Variables {
	
	public static enum Armortypes{
		HELMET,
		CHEST,
		LEGS,
		BOOTS,
		MISC
	}
	
	public final static int helmetTypes[] = {298, 302, 306, 310, 314};
	public final static int chestTypes[] = {299, 303, 307, 311, 315};
	public final static int legTypes[] = {300, 304, 308, 312, 316};
	public final static int bootTypes[] = {301, 305, 309, 313, 317};
	
	
	public static List<Integer> levels;
	public static List<Integer> expNeeded;
	public static List<Integer> health;
	public static List<Integer> baseDamages;

}
