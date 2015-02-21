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
import java.util.UUID;

import offlineplayerutils.api.LocationInfo;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagNumber;

public class LocationData {

	@SuppressWarnings("unchecked")
	public LocationInfo getLocation(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		UUID world = new UUID(data.getLong("WorldUUIDMost"), data.getLong("WorldUUIDLeast"));
		if (data.hasListOfNumberType("Pos")) {
			NBTTagList<NBTTagNumber<Number>> nbttaglist = (NBTTagList<NBTTagNumber<Number>>) data.get("Pos");
			if (nbttaglist.size() >= 3) {
				double x = nbttaglist.get(0).getValue().doubleValue();
				double y = nbttaglist.get(1).getValue().doubleValue();
				double z = nbttaglist.get(2).getValue().doubleValue();
				return new LocationInfo(world, x, y, z);
			}
		}
		return null;
	}

	public void setLocation(File datafile, LocationInfo location) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setLong("WorldUUIDMost", location.getWorldUUID().getMostSignificantBits());
		data.setLong("WorldUUIDLeast", location.getWorldUUID().getLeastSignificantBits());
		NBTTagList<NBTTagNumber<Double>> nbttaglist = new NBTTagList<NBTTagNumber<Double>>();
		nbttaglist.add(new NBTTagNumber<Double>(location.getX()));
		nbttaglist.add(new NBTTagNumber<Double>(location.getY()));
		nbttaglist.add(new NBTTagNumber<Double>(location.getZ()));
		data.set("Pos", nbttaglist);
		DataUtils.saveData(datafile, data);
	}

}
