package offlineplayerutils.internal;

import offlineplayerutils.api.LocationInfo;

import org.bukkit.OfflinePlayer;

public interface LocationDataInterface {

	public LocationInfo getLocation(OfflinePlayer player);

	public void setLocation(OfflinePlayer player, LocationInfo location);

}
