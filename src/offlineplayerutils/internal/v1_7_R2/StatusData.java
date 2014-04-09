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

import offlineplayerutils.internal.StatusDataInterface;

import org.bukkit.OfflinePlayer;

import net.minecraft.server.v1_7_R2.NBTTagCompound;

public class StatusData implements StatusDataInterface {

	public int getFoodLevel(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			return data.getInt("foodLevel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void setFoodLevel(OfflinePlayer player, int foodlevel) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setInt("foodLevel", foodlevel);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
