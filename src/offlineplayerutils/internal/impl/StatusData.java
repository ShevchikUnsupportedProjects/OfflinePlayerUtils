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

package offlineplayerutils.internal.impl;

import offlineplayerutils.internal.StatusDataInterface;
import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;

public class StatusData implements StatusDataInterface {

	@Override
	public int getFoodLevel(OfflinePlayer player) {
		NBTTagCompound data = DataUtils.getData(player);
		return data.getInt("foodLevel");
	}

	@Override
	public void setFoodLevel(OfflinePlayer player, int foodlevel) {
		NBTTagCompound data = DataUtils.getData(player);
		data.setInt("foodLevel", foodlevel);
		DataUtils.saveData(player, data);
	}

	@Override
	public float getHealth(OfflinePlayer player) {
		NBTTagCompound data = DataUtils.getData(player);
		if (data.hasOfNumberType("HealF")) {
			return data.getFloat("HealF");
		}
		return data.getNumber("Health", getMaxHealth(player)).floatValue();
	}

	@Override
	public void setHealth(OfflinePlayer player, float health) {
		NBTTagCompound data = DataUtils.getData(player);
		data.setFloat("HealF", health);
		DataUtils.saveData(player, data);
	}

	@Override
	public float getMaxHealth(OfflinePlayer player) {
		NBTTagCompound data = DataUtils.getData(player);
		return data.getNumber("Bukkit.MaxHealth", 20F).floatValue();
	}

	@Override
	public void setMaxHeath(OfflinePlayer player, float maxhealth) {
		NBTTagCompound data = DataUtils.getData(player);
		data.setFloat("Bukkit.MaxHealth", maxhealth);
		DataUtils.saveData(player, data);
	}

	@SuppressWarnings("deprecation")
	@Override
	public GameMode getGameMode(OfflinePlayer player) {
		NBTTagCompound data = DataUtils.getData(player);
		return GameMode.getByValue(data.getInt("playerGameType"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setGameMode(OfflinePlayer player, GameMode mode) {
		NBTTagCompound data = DataUtils.getData(player);
		data.setInt("playerGameType", mode.getValue());
		DataUtils.saveData(player, data);
	}

}
