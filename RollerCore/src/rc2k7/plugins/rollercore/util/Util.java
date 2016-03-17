package rc2k7.plugins.rollercore.util;

import java.util.Random;

public class Util {
	
	private static Random rand = new Random();
	
	public static int getRand(){
		return rand.nextInt(100)+1;
	}

}
