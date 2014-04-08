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

package offlineplayerutils.internal.v1_6_R3;


import java.util.UUID;

import net.minecraft.server.v1_6_R3.NBTTagCompound;
import net.minecraft.server.v1_6_R3.NBTTagDouble;
import net.minecraft.server.v1_6_R3.NBTTagList;
import offlineplayerutils.api.LocationInfo;
import offlineplayerutils.internal.LocationDataInterface;

import org.bukkit.OfflinePlayer;

public class LocationData implements LocationDataInterface {

	@Override
	public LocationInfo getLocation(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			UUID world = new UUID(data.getLong("WorldUUIDMost"), data.getLong("WorldUUIDLeast"));
			NBTTagList nbttaglist = data.getList("Pos");
			double x = ((NBTTagDouble)nbttaglist.get(0)).data;
			double y = ((NBTTagDouble)nbttaglist.get(1)).data;
			double z = ((NBTTagDouble)nbttaglist.get(2)).data;
			return new LocationInfo(world, x, y, z);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setLocation(OfflinePlayer player, LocationInfo location) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			data.setLong("WorldUUIDMost", location.getWorldUUID().getMostSignificantBits());
			data.setLong("WorldUUIDLeast", location.getWorldUUID().getLeastSignificantBits());
			NBTTagList nbttaglist = new NBTTagList();
			nbttaglist.add(new NBTTagDouble(null, location.getX()));
			nbttaglist.add(new NBTTagDouble(null, location.getY()));
			nbttaglist.add(new NBTTagDouble(null, location.getZ()));
			data.set("Pos", nbttaglist);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
