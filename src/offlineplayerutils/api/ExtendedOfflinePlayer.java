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

import offlineplayerutils.internal.ExpDataInterface;
import offlineplayerutils.internal.InternalAccessor;
import offlineplayerutils.internal.InventoryDataInterface;
import offlineplayerutils.internal.LocationDataInterface;
import offlineplayerutils.internal.StatusDataInterface;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class ExtendedOfflinePlayer {

	private boolean editsessionstarted = false;

	private OfflinePlayer bukkitOfflinePlayer;
	private InventoryDataInterface inventoryData;
	private LocationDataInterface locationData;
	private StatusDataInterface statusData;
	private ExpDataInterface expData;

	protected ExtendedOfflinePlayer(OfflinePlayer bukkitOfflinePlayer) {
		this.bukkitOfflinePlayer = bukkitOfflinePlayer;
		inventoryData = InternalAccessor.getInstance().newInvnetoryData();
		locationData = InternalAccessor.getInstance().newLocationData();
		statusData = InternalAccessor.getInstance().newStatusData();
		expData = InternalAccessor.getInstance().newExpData();
	}

	public void startEditSession() {
		OfflinePlayerUtilsAPI.addOfflinePlayerWithActiveEditSession(this);
		editsessionstarted = true;
	}

	public void stopEditSession() {
		editsessionstarted = false;
		OfflinePlayerUtilsAPI.removeOfflinePlayerWithActiveEditSession(this);
	}

	public OfflinePlayer getBukkitOfflinePlayer() {
		return bukkitOfflinePlayer;
	}

	public ItemStack[] getInventoryContents() {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		return inventoryData.getInventoryContents(bukkitOfflinePlayer);
	}

	public ItemStack[] getArmorContents() {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		return inventoryData.getArmorContents(bukkitOfflinePlayer);
	}

	public void setInventoryContents(ItemStack[] contents) {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		inventoryData.setInventoryContents(bukkitOfflinePlayer, contents);
	}

	public void setArmorContents(ItemStack[] contents) {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		inventoryData.setArmorContents(bukkitOfflinePlayer, contents);
	}

	public LocationInfo getLocation() {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		return locationData.getLocation(bukkitOfflinePlayer);
	}

	public void setLocation(LocationInfo location) {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		locationData.setLocation(bukkitOfflinePlayer, location);
	}

	public int getFoodLevel() {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		return statusData.getFoodLevel(bukkitOfflinePlayer);
	}

	public void setFoodLevel(int foodlevel) {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		statusData.setFoodLevel(bukkitOfflinePlayer, foodlevel);
	}

	public float getHealth() {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		return statusData.getHealth(bukkitOfflinePlayer);
	}

	public void setHealth(float health) {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		statusData.setHealth(bukkitOfflinePlayer, health);
	}

	public float getMaxHealth() {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		return statusData.getMaxHealth(bukkitOfflinePlayer);
	}

	public void setMaxHealth(float maxhealth) {
		if (!editsessionstarted) {
			throw new EditSessionNotStartedException();
		}
		statusData.setMaxHeath(bukkitOfflinePlayer, maxhealth);
	}

	@Override
	public int hashCode() {
		return bukkitOfflinePlayer.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return bukkitOfflinePlayer.equals(obj);
	}

}
