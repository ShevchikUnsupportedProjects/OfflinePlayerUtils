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

import java.io.File;
import java.util.HashMap;

import offlineplayerutils.api.inventory.IWrappedItemStack;
import offlineplayerutils.internal.impl.DataUtils;
import offlineplayerutils.internal.impl.ExpData;
import offlineplayerutils.internal.impl.InventoryData;
import offlineplayerutils.internal.impl.LocationData;
import offlineplayerutils.internal.impl.StatusData;
import offlineplayerutils.simplenbt.NBTSerializer;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class ExtendedOfflinePlayer {

	private static final InventoryData inventoryData = new InventoryData();
	private static final LocationData locationData = new LocationData();
	private static final StatusData statusData = new StatusData();
	private static final ExpData expData = new ExpData();

	private OfflinePlayer bukkitOfflinePlayer;
	private File datafile;

	protected ExtendedOfflinePlayer(OfflinePlayer bukkitOfflinePlayer, File playerfile) {
		this.bukkitOfflinePlayer = bukkitOfflinePlayer;
		this.datafile = playerfile;
	}

	/**
	 * Get OfflinePlayer
	 * @return OfflinePlayer instance used for creation of this object
	 */
	public OfflinePlayer getBukkitOfflinePlayer() {
		return bukkitOfflinePlayer;
	}

	/**
	 * Get player inventory contents
	 */
	public IWrappedItemStack[] getInventoryContents() {
		return inventoryData.getInventoryContents(datafile);
	}

	/**
	 * Get player armor contents
	 */
	public IWrappedItemStack[] getArmorContents() {
		return inventoryData.getArmorContents(datafile);
	}

	/**
	 * Set player inventory contents
	 * @param contents array of itemstacks, should have a length of 36
	 */
	public void setInventoryContents(ItemStack[] contents) {
		inventoryData.setInventoryContents(datafile, contents);
	}

	/**
	 * Set player armor contents
	 * @param contents array of itemstacks, should have a length of 4, order is: helmet, chestplate, leggins, boots
	 */
	public void setArmorContents(ItemStack[] contents) {
		inventoryData.setArmorContents(datafile, contents);
	}

	/**
	 * Get player location info
	 */
	public LocationInfo getLocation() {
		return locationData.getLocation(datafile);
	}

	/**
	 * Set player location info
	 */
	public void setLocation(LocationInfo location) {
		locationData.setLocation(datafile, location);
	}

	/**
	 * Get player food level
	 */
	public int getFoodLevel() {
		return statusData.getFoodLevel(datafile);
	}

	/**
	 * Set player food level
	 */
	public void setFoodLevel(int foodlevel) {
		statusData.setFoodLevel(datafile, foodlevel);
	}

	/**
	 * Get player health
	 */
	public float getHealth() {
		return statusData.getHealth(datafile);
	}

	/**
	 * Set player health
	 */
	public void setHealth(float health) {
		statusData.setHealth(datafile, health);
	}

	/**
	 * Get player max health
	 * @return player max health or 20 if player didn't have maxhealth set
	 */
	public float getMaxHealth() {
		return statusData.getMaxHealth(datafile);
	}

	/**
	 * Set player max health
	 */
	public void setMaxHealth(float maxhealth) {
		statusData.setMaxHeath(datafile, maxhealth);
	}

	/**
	 * Get player game mode
	 * @return player game mode or null if internal game mode state couldn't be decoded into the GameMode enum
	 */
	public GameMode getGameMode() {
		return statusData.getGameMode(datafile);
	}

	/**
	 * Set player game mode
	 */
	public void setGameMode(GameMode gamemode) {
		statusData.setGameMode(datafile, gamemode);
	}

	/**
	 * Get player xp percent
	 */
	public float getExp() {
		return expData.getExp(datafile);
	}

	/**
	 * Set player xp percent
	 */
	public void setExp(float exp) {
		expData.setExp(datafile, exp);
	}

	/**
	 * Get player level
	 */
	public int getLevel() {
		return expData.getLevel(datafile);
	}

	/**
	 * Set player level
	 * @param level
	 */
	public void setLevel(int level) {
		expData.setLevel(datafile, level);
	}

	/**
	 * Get player raw nbt data
	 */
	public HashMap<String, Object> getRawNBTData() {
		return DataUtils.getData(datafile).toJava();
	}

	/**
	 * Set player raw nbt data
	 */
	public void setRawNBTData(HashMap<String, Object> rawdata) {
		DataUtils.saveData(datafile, NBTSerializer.fromJava(rawdata));
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
