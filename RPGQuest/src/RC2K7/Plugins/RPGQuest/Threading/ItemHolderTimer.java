package RC2K7.Plugins.RPGQuest.Threading;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHolderTimer implements Runnable {
	
	Player Player;
	ItemStack Item;
	
	public ItemHolderTimer(Player player, ItemStack itm)
	{
		this.Player = player;
		this.Item = itm;
	}

	@Override
	public void run() {
		this.Player.getInventory().setItem(0, this.Item);
	}

}
