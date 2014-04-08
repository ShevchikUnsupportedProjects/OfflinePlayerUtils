package offlineplayerutils.api;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class ExtendedOfflinePlayer implements OfflinePlayer {

	private OfflinePlayer bukkitOfflinePlayer;

	protected ExtendedOfflinePlayer(OfflinePlayer bukkitOfflinePlayer) {
		this.bukkitOfflinePlayer = bukkitOfflinePlayer;
	}

	@Override
	public boolean isOp() {
		return bukkitOfflinePlayer.isOp();
	}

	@Override
	public void setOp(boolean op) {
		bukkitOfflinePlayer.setOp(op);
	}

	@Override
	public Map<String, Object> serialize() {
		return bukkitOfflinePlayer.serialize();
	}

	@Override
	public Location getBedSpawnLocation() {
		return bukkitOfflinePlayer.getBedSpawnLocation();
	}

	@Override
	public long getFirstPlayed() {
		return bukkitOfflinePlayer.getFirstPlayed();
	}

	@Override
	public long getLastPlayed() {
		return bukkitOfflinePlayer.getLastPlayed();
	}

	@Override
	public String getName() {
		return bukkitOfflinePlayer.getName();
	}

	@Override
	public Player getPlayer() {
		return bukkitOfflinePlayer.getPlayer();
	}

	@Override
	public boolean hasPlayedBefore() {
		return bukkitOfflinePlayer.hasPlayedBefore();
	}

	@Override
	public boolean isBanned() {
		return bukkitOfflinePlayer.isBanned();
	}

	@Override
	public boolean isOnline() {
		return bukkitOfflinePlayer.isOnline();
	}

	@Override
	public boolean isWhitelisted() {
		return bukkitOfflinePlayer.isWhitelisted();
	}

	@Deprecated
	@Override
	public void setBanned(boolean banned) {
		bukkitOfflinePlayer.setBanned(banned);
	}

	@Override
	public void setWhitelisted(boolean whitelisted) {
		bukkitOfflinePlayer.setWhitelisted(whitelisted);
	}

}
