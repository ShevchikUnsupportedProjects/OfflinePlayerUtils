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

import java.util.HashSet;

import org.bukkit.OfflinePlayer;

public class OfflinePlayerUtilsAPI {

	public static ExtendedOfflinePlayer getExtendedOfflinePlayer(OfflinePlayer offlineplayer) {
		return new ExtendedOfflinePlayer(offlineplayer);
	}

	private static HashSet<ExtendedOfflinePlayer> inEditSession = new HashSet<ExtendedOfflinePlayer>();

	public static HashSet<ExtendedOfflinePlayer> getOfflinePlayersWithActiveEditSession() {
		return inEditSession;
	}

	protected static void addOfflinePlayerWithActiveEditSession(ExtendedOfflinePlayer player) {
		inEditSession.add(player);
	}

	protected static void removeOfflinePlayerWithActiveEditSession(ExtendedOfflinePlayer player) {
		inEditSession.remove(player);
	}

}
