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

import net.minecraft.server.v1_7_R2.NBTTagCompound;
import offlineplayerutils.internal.ExpDataInterface;

import org.bukkit.OfflinePlayer;

public class ExpData implements ExpDataInterface {

	@Override
	public float getExp(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			return data.getFloat("XpP");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void setExp(OfflinePlayer player, float exp) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setFloat("XpP", exp);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getLevel(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			return data.getInt("XpLevel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public void setLevel(OfflinePlayer player, int level) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setInt("XpLevel", level);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
