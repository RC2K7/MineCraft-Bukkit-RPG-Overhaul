package rc2k7.plugins.tradecommander.util;

import java.util.ArrayList;
import java.util.List;

import rc2k7.plugins.tradecommander.base.Trade;

public class TradeManager {
	
private static List<Trade> list = new ArrayList<>();

	public static void addTrade(String a, String b){
		list.add(new Trade(a, b));
	}
	
	public static void delTrade(Trade t){
		if(list.contains(t))
			list.remove(t);
	}
	
	public static Trade getTrade(int i){
		if(i >= list.size())
			return null;
		return list.get(i);
	}
	
	public static Trade getTrade(String name){
		for(Trade inv : list)
			if(inv.containsPlayer(name))
				return inv;
		return null;
	}
	
	public static List<Trade> getTradeList(){return list;}

}
