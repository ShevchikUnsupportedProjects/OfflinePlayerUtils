package offlineplayerutils.api;

import java.util.UUID;

import offlineplayerutils.internal.WorldUUIDToNameResolverInterface;
import offlineplayerutils.internal.impl.WorldUUIDToNameResolver;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationInfo {

	private static WorldUUIDToNameResolverInterface worldUUIDTOname = new WorldUUIDToNameResolver();

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
		return x;
	}

	public double getZ() {
		return x;
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
