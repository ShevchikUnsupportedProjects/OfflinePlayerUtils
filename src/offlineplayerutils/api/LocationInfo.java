package offlineplayerutils.api;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationInfo {

	private UUID worlduuid;
	private String worldname;
	private double x;
	private double y;
	private double z;
	public LocationInfo(UUID worlduuid, double x, double y, double z) {
		this.worlduuid = worlduuid;
		this.worldname = resolveWorldName();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public UUID getWorldUUID() {
		return worlduuid;
	}

	public String getWorldName() {
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
	
	private String resolveWorldName() {
		if (Bukkit.getWorld(worlduuid) != null) {
			return Bukkit.getWorld(worlduuid).getName();
		}
		return null;
	}

}
