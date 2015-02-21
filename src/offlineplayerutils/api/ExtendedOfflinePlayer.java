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

	public OfflinePlayer getBukkitOfflinePlayer() {
		return bukkitOfflinePlayer;
	}

	public IWrappedItemStack[] getInventoryContents() {
		return inventoryData.getInventoryContents(datafile);
	}

	public IWrappedItemStack[] getArmorContents() {
		return inventoryData.getArmorContents(datafile);
	}

	public void setInventoryContents(ItemStack[] contents) {
		inventoryData.setInventoryContents(datafile, contents);
	}

	public void setArmorContents(ItemStack[] contents) {
		inventoryData.setArmorContents(datafile, contents);
	}

	public LocationInfo getLocation() {
		return locationData.getLocation(datafile);
	}

	public void setLocation(LocationInfo location) {
		locationData.setLocation(datafile, location);
	}

	public int getFoodLevel() {
		return statusData.getFoodLevel(datafile);
	}

	public void setFoodLevel(int foodlevel) {
		statusData.setFoodLevel(datafile, foodlevel);
	}

	public float getHealth() {
		return statusData.getHealth(datafile);
	}

	public void setHealth(float health) {
		statusData.setHealth(datafile, health);
	}

	public float getMaxHealth() {
		return statusData.getMaxHealth(datafile);
	}

	public void setMaxHealth(float maxhealth) {
		statusData.setMaxHeath(datafile, maxhealth);
	}

	public GameMode getGameMode() {
		return statusData.getGameMode(datafile);
	}

	public void setGameMode(GameMode gamemode) {
		statusData.setGameMode(datafile, gamemode);
	}

	public float getExp() {
		return expData.getExp(datafile);
	}

	public void setExp(float exp) {
		expData.setExp(datafile, exp);
	}

	public int getLevel() {
		return expData.getLevel(datafile);
	}

	public void setLevel(int level) {
		expData.setLevel(datafile, level);
	}

	public HashMap<String, Object> getRawNBTData() {
		return DataUtils.getData(datafile).toJava();
	}

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
