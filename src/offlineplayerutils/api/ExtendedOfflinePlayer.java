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

import java.util.Map;

import offlineplayerutils.internal.InternalAccessor;
import offlineplayerutils.internal.InventoryDataInterface;
import offlineplayerutils.internal.LocationDataInterface;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExtendedOfflinePlayer implements OfflinePlayer {

	private OfflinePlayer bukkitOfflinePlayer;
	private InventoryDataInterface inventoryData;
	private LocationDataInterface locationData;

	protected ExtendedOfflinePlayer(OfflinePlayer bukkitOfflinePlayer) {
		this.bukkitOfflinePlayer = bukkitOfflinePlayer;
		inventoryData = InternalAccessor.getInstance().newInvnetoryData();
		locationData = InternalAccessor.getInstance().newLocationData();
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

	@Override
	public boolean isOp() {
		return bukkitOfflinePlayer.isOp();
	}

	@Override
	public void setOp(boolean op) {
		bukkitOfflinePlayer.setOp(op);
	}

	@Override
	public Map<String, Object> serialize() {
		return bukkitOfflinePlayer.serialize();
	}

	@Override
	public Location getBedSpawnLocation() {
		return bukkitOfflinePlayer.getBedSpawnLocation();
	}

	@Override
	public long getFirstPlayed() {
		return bukkitOfflinePlayer.getFirstPlayed();
	}

	@Override
	public long getLastPlayed() {
		return bukkitOfflinePlayer.getLastPlayed();
	}

	@Override
	public String getName() {
		return bukkitOfflinePlayer.getName();
	}

	@Override
	public Player getPlayer() {
		return bukkitOfflinePlayer.getPlayer();
	}

	@Override
	public boolean hasPlayedBefore() {
		return bukkitOfflinePlayer.hasPlayedBefore();
	}

	@Override
	public boolean isBanned() {
		return bukkitOfflinePlayer.isBanned();
	}

	@Override
	public boolean isOnline() {
		return bukkitOfflinePlayer.isOnline();
	}

	@Override
	public boolean isWhitelisted() {
		return bukkitOfflinePlayer.isWhitelisted();
	}

	@Deprecated
	@Override
	public void setBanned(boolean banned) {
		bukkitOfflinePlayer.setBanned(banned);
	}

	@Override
	public void setWhitelisted(boolean whitelisted) {
		bukkitOfflinePlayer.setWhitelisted(whitelisted);
	}

}
