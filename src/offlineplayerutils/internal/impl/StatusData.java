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

import java.io.File;

import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.GameMode;

public class StatusData {

	public int getFoodLevel(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		return data.getInt("foodLevel");
	}

	public void setFoodLevel(File datafile, int foodlevel) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setInt("foodLevel", foodlevel);
		DataUtils.saveData(datafile, data);
	}

	public float getHealth(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		if (data.hasOfNumberType("HealF")) {
			return data.getFloat("HealF");
		}
		return data.getNumber("Health", getMaxHealth(datafile)).floatValue();
	}

	public void setHealth(File datafile, float health) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setFloat("HealF", health);
		DataUtils.saveData(datafile, data);
	}

	public float getMaxHealth(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		return data.getNumber("Bukkit.MaxHealth", 20F).floatValue();
	}

	public void setMaxHeath(File datafile, float maxhealth) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setFloat("Bukkit.MaxHealth", maxhealth);
		DataUtils.saveData(datafile, data);
	}

	@SuppressWarnings("deprecation")
	public GameMode getGameMode(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		return GameMode.getByValue(data.getInt("playerGameType"));
	}

	@SuppressWarnings("deprecation")
	public void setGameMode(File datafile, GameMode mode) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setInt("playerGameType", mode.getValue());
		DataUtils.saveData(datafile, data);
	}

}
