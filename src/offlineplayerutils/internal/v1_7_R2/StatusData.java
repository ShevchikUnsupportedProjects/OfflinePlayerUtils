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

package offlineplayerutils.internal.v1_7_R2;

import net.minecraft.server.v1_7_R2.NBTBase;
import net.minecraft.server.v1_7_R2.NBTTagCompound;
import net.minecraft.server.v1_7_R2.NBTTagFloat;
import net.minecraft.server.v1_7_R2.NBTTagInt;
import net.minecraft.server.v1_7_R2.NBTTagShort;
import offlineplayerutils.internal.StatusDataInterface;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;

public class StatusData implements StatusDataInterface {

	@Override
	public int getFoodLevel(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			return data.getInt("foodLevel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void setFoodLevel(OfflinePlayer player, int foodlevel) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setInt("foodLevel", foodlevel);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public float getHealth(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			if (data.hasKeyOfType("HealF", 99)) {
				return data.getFloat("HealF");
			}
			NBTBase nbtbase = data.get("Health");
			if (nbtbase == null) {
				return getMaxHealth(player);
			} else if (nbtbase.getTypeId() == 5) {
				return ((NBTTagFloat) nbtbase).h();
			} else if (nbtbase.getTypeId() == 2) {
                return ((NBTTagShort) nbtbase).e();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void setHealth(OfflinePlayer player, float health) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setFloat("HealF", health);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public float getMaxHealth(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
	        if (data.hasKey("Bukkit.MaxHealth")) {
				NBTBase nbtbase = data.get("Bukkit.MaxHealth");
	            if (nbtbase.getTypeId() == 5) {
	                return ((NBTTagFloat) nbtbase).c();
	            } else if (nbtbase.getTypeId() == 3) {
	                return ((NBTTagInt) nbtbase).d();
	            }
	        } else {
	        	return 20F;
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void setMaxHeath(OfflinePlayer player, float maxhealth) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setFloat("Bukkit.MaxHealth", maxhealth);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public GameMode getGameMode(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			return GameMode.getByValue(data.getInt("playerGameType"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setGameMode(OfflinePlayer player, GameMode mode) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setInt("playerGameType", mode.getValue());
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
