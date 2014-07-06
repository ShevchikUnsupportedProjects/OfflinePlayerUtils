/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package offlineplayerutils.api;

import offlineplayerutils.internal.InternalAccessor;
import offlineplayerutils.internal.InventoryDataInterface;
import offlineplayerutils.internal.LocationDataInterface;
import offlineplayerutils.internal.StatusDataInterface;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class ExtendedOfflinePlayer {

	private OfflinePlayer bukkitOfflinePlayer;
	private InventoryDataInterface inventoryData;
	private LocationDataInterface locationData;
	private StatusDataInterface statusData;

	protected ExtendedOfflinePlayer(OfflinePlayer bukkitOfflinePlayer) {
		this.bukkitOfflinePlayer = bukkitOfflinePlayer;
		inventoryData = InternalAccessor.getInstance().newInvnetoryData();
		locationData = InternalAccessor.getInstance().newLocationData();
	}

	public OfflinePlayer getBukkitOfflinePlayer() {
		return bukkitOfflinePlayer;
	}

	public ItemStack[] getInventoryContents() {
		return inventoryData.getInventoryContents(bukkitOfflinePlayer);
	}

	public ItemStack[] getArmorContents() {
		return inventoryData.getArmorContents(bukkitOfflinePlayer);
	}

	public void setInventoryContents(ItemStack[] contents) {
		inventoryData.setInventoryContents(bukkitOfflinePlayer, contents);
	}

	public void setArmorContents(ItemStack[] contents) {
		inventoryData.setArmorContents(bukkitOfflinePlayer, contents);
	}

	public LocationInfo getLocation() {
		return locationData.getLocation(bukkitOfflinePlayer);
	}

	public void setLocation(LocationInfo location) {
		locationData.setLocation(bukkitOfflinePlayer, location);
	}

	public int getFoodLevel() {
		return statusData.getFoodLevel(bukkitOfflinePlayer);
	}

	public void setFoodLevel(int foodlevel) {
		statusData.setFoodLevel(bukkitOfflinePlayer, foodlevel);
	}

	public float getHealth() {
		return statusData.getHealth(bukkitOfflinePlayer);
	}

	public void setHealth(float health) {
		statusData.setHealth(bukkitOfflinePlayer, health);
	}

	public float getMaxHealth() {
		return statusData.getMaxHealth(bukkitOfflinePlayer);
	}

	public void setMaxHealth(float maxhealth) {
		statusData.setMaxHeath(bukkitOfflinePlayer, maxhealth);
	}

}
