package offlineplayerutils.internal;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public interface InventoryDataInterface {
	
	public ItemStack[] getInventoryContents(OfflinePlayer player);

	public ItemStack[] getArmorContents(OfflinePlayer player);

}
