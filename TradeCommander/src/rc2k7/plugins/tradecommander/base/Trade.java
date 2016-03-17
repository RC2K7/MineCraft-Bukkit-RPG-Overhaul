package rc2k7.plugins.tradecommander.base;

import rc2k7.plugins.tradecommander.util.PlayerManager;
import rc2k7.plugins.tradecommander.util.TradeHolder;
import rc2k7.plugins.tradecommander.util.TradeHolder.Slot;
import rc2k7.plugins.tradecommander.util.TradeManager;

public class Trade {
	
	private String a;
	private String b;
	private boolean bA = false;
	private boolean bB = false;
	private TradeHolder tradeInv = new TradeHolder();
	
	public Trade(String a, String b) {
		this.a = a;
		this.b = b;
		PlayerManager.getExactPlayer(a).openInventory(tradeInv.getInventory());
		PlayerManager.getExactPlayer(b).openInventory(tradeInv.getInventory());
	}
	
	public void doAccept(String name){
		if(name.equals(a)){
			tradeInv.PlayerAConfirm();
			bA = true;
		}
		if(name.equals(b)){
			tradeInv.PlayerBConfirm();
			bB = true;
		}
		checkTrade();
	}
	
	public void doDeny(String name){
		if(name.equals(a)){
			tradeInv.PlayerAWaiting();
			bA = false;
		}
		if(name.equals(b)){
			tradeInv.PlayerBWaiting();
			bA = false;
		}
	}
	
	public void setBothDeny(){
		tradeInv.PlayerAWaiting();
		bA = false;
		tradeInv.PlayerBWaiting();
		bB = false;
	}
	
	public void checkTrade(){
		if(bA && bB){
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(a), Slot.SLOTB);
			tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(b), Slot.SLOTA);
			tradeInv.clearInv();
			PlayerManager.getExactPlayer(a).closeInventory();
			PlayerManager.getExactPlayer(b).closeInventory();
			PlayerManager.sendStubMessage(a, "Trade Completed.");
			PlayerManager.sendStubMessage(b, "Trade Completed.");
			TradeManager.delTrade(this);
		}
	}
	
	public void doClose(String name){
		if(name.equals(a)){
			PlayerManager.sendStubMessage(b, a + " Has Denied The Trade.");
			PlayerManager.sendStubMessage(a, "You Denied The Trade.");
			PlayerManager.getExactPlayer(b).closeInventory();
		}else{
			PlayerManager.sendStubMessage(a, b + " Has Denied The Trade.");
			PlayerManager.sendStubMessage(b, "You Denied The Trade.");
			PlayerManager.getExactPlayer(a).closeInventory();
		}
		tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(a), Slot.SLOTA);
		tradeInv.givePlayerSlot(PlayerManager.getExactPlayer(b), Slot.SLOTB);
		TradeManager.delTrade(this);
	}
	
	public void doShowTrade(String name){
		PlayerManager.getExactPlayer(name).openInventory(tradeInv.getInventory());
	}
	
	public boolean containsPlayer(String name){
		if(a.equals(name) || b.equals(name))
			return true;
		return false;
	}
	
	public TradeHolder getHolder(){
		return tradeInv;
	}
	
	public Slot getPlayerSlot(String name){
		if(a.equals(name))
			return Slot.SLOTA;
		if(b.equals(name))
			return Slot.SLOTB;
		return null;
	}

}
