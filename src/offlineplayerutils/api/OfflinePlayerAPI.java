package offlineplayerutils.api;

import org.bukkit.OfflinePlayer;

public class OfflinePlayerAPI {

	public static ExtendedOfflinePlayer getExtendedOfflinePlayer(OfflinePlayer offlineplayer) {
		return new ExtendedOfflinePlayer(offlineplayer);
	}

}
