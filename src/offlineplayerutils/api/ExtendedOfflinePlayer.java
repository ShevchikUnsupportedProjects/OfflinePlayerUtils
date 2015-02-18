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
import offlineplayerutils.internal.InventoryDataInterface;
import offlineplayerutils.internal.LocationDataInterface;
import offlineplayerutils.internal.StatusDataInterface;
import offlineplayerutils.internal.impl.ExpData;
import offlineplayerutils.internal.impl.InventoryData;
import offlineplayerutils.internal.impl.LocationData;
import offlineplayerutils.internal.impl.StatusData;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class ExtendedOfflinePlayer {

	private static final InventoryDataInterface inventoryData = new InventoryData();
	private static final LocationDataInterface locationData = new LocationData();
	private static final StatusDataInterface statusData = new StatusData();
	private static final ExpDataInterface expData = new ExpData();

	private OfflinePlayer bukkitOfflinePlayer;

	protected ExtendedOfflinePlayer(OfflinePlayer bukkitOfflinePlayer) {
		this.bukkitOfflinePlayer = bukkitOfflinePlayer;
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

	public GameMode getGameMode() {
		return statusData.getGameMode(bukkitOfflinePlayer);
	}

	public void setGameMode(GameMode gamemode) {
		statusData.setGameMode(bukkitOfflinePlayer, gamemode);
	}

	public float getExp() {
		return expData.getExp(bukkitOfflinePlayer);
	}

	public void setExp(float exp) {
		expData.setExp(bukkitOfflinePlayer, exp);
	}

	public int getLevel() {
		return expData.getLevel(bukkitOfflinePlayer);
	}

	public void setLevel(int level) {
		expData.setLevel(bukkitOfflinePlayer, level);
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
