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

import java.util.UUID;

import offlineplayerutils.internal.impl.WorldUUIDToNameResolver;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationInfo {

	private static final WorldUUIDToNameResolver worldUUIDTOname = new WorldUUIDToNameResolver();

	private UUID worlduuid;
	private double x;
	private double y;
	private double z;

	public LocationInfo(UUID worlduuid, double x, double y, double z) {
		this.worlduuid = worlduuid;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public LocationInfo(Location location) {
		this.worlduuid = location.getWorld().getUID();
		this.worldname = location.getWorld().getName();
		this.x = location.getX();
		this.y = location.getY();
		this.z = location.getZ();
	}

	public UUID getWorldUUID() {
		return worlduuid;
	}

	private String worldname;
	public String getWorldName() {
		if (worldname == null) {
			worldname = worldUUIDTOname.resolveWorldName(worlduuid);
		}
		return worldname;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public Location getLocation() {
		if (Bukkit.getWorld(worlduuid) != null) {
			return new Location(Bukkit.getWorld(worlduuid), x, y, z);
		}
		return null;
	}

	@Override
	public String toString() {
		return "WorldUUID: "+getWorldUUID()+", WorldName: "+getWorldName()+", X: "+getX()+", Y: "+getY()+", Z:"+getZ();
	}

	@Override
	public int hashCode() {
		return (int) (getWorldUUID().hashCode() * 31 ^ Double.doubleToLongBits(getX()) ^ Double.doubleToLongBits(getY()) ^ Double.doubleToLongBits(getZ()));
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof LocationInfo)) {
			return false;
		}
		LocationInfo otherInfo = (LocationInfo) other;
		return
			getWorldUUID().equals(otherInfo.getWorldUUID()) &&
			Double.doubleToLongBits(getX()) == Double.doubleToLongBits(otherInfo.getX()) &&
			Double.doubleToLongBits(getY()) == Double.doubleToLongBits(otherInfo.getY()) &&
			Double.doubleToLongBits(getZ()) == Double.doubleToLongBits(otherInfo.getZ());
	}

}
