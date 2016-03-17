package rc2k7.plugins.rollercore.timer;

import org.bukkit.scheduler.BukkitRunnable;

import rc2k7.plugins.rollercore.base.Roll;

public class RollTimer extends BukkitRunnable {
	
	private final Roll roll;
	
	/*Constructor For Roll Timer
	 * 
	 * @param roll This Is The Roll Which Initializes THIS
	 */
	public RollTimer(Roll roll){
		this.roll = roll;
	}

	/*Calls RollDone After Timer
	 * 
	 */
	@Override
	public void run() {
		roll.RollDone();
	}

}
