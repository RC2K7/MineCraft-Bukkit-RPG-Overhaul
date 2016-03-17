package rc2k7.plugins.partycreator.teleport;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import rc2k7.plugins.partycreator.Util;

public class TeleTimer implements Runnable {
	
	private Player a;
	private Player b;
	private int posX;
	private int posY;
	private int posZ;
	private int posXA;
	private int posYA;
	private int posZA;
	
	public TeleTimer(Player a, Player b){
		this.a = a;
		this.b = b;
		posXA = (int)a.getLocation().getX();
		posYA = (int)a.getLocation().getY();
		posZA = (int)a.getLocation().getZ();
		posX = (int)b.getLocation().getX();
		posY = (int)b.getLocation().getY();
		posZ = (int)b.getLocation().getZ();
	}
	
	public boolean isSame(Location loc, int posX, int posY, int posZ){
		if( posX != ( int )loc.getX() || posY != ( int )loc.getY() || posZ != ( int )loc.getZ())
			return false;
		return true;
	}
	
	public void run(){
		try{
			int count = 5;
			while( count > 0 ){
				a.sendMessage( "Wait " + count + " Seconds." );
				b.sendMessage( "Wait " + count + " Seconds." );
				if(!isSame(Util.getPlayer(b.getDisplayName()).getLocation(), posX, posY, posZ)){ Util.sendMessage(b, "Teleport Canceled."); Util.sendMessage(a, b.getDisplayName() + " Teleport Canceled"); return; }
				if(!isSame(Util.getPlayer(a.getDisplayName()).getLocation(), posXA, posYA, posZA)){ Util.sendMessage(b, "Teleport Canceled."); Util.sendMessage(a, b.getDisplayName() + " Teleport Canceled"); return; }
				Thread.sleep( 1000 );
				count--;
			}
			b.teleport(a);
			Util.sendMessage(a, b.getDisplayName() + " Has Been Teleported.");
			Util.sendMessage(b, "You Have Been Teleported.");
		} catch (InterruptedException e) {
		}
	}

}
