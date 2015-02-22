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

import java.io.File;
import java.io.FileNotFoundException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerUtilsAPI {

	/**
	 * Get ExtendedOfflinePlayer instance
	 * @param offlineplayer OfflinePlayer instance
	 * @return ExtendedOfflinePlayer instance
	 * @throws FileNotFoundException if player data file wasn't found
	 */
	public static ExtendedOfflinePlayer getExtendedOfflinePlayer(OfflinePlayer offlineplayer) throws FileNotFoundException {
		return getExtendedOfflinePlayer(offlineplayer, new File(new File(Bukkit.getWorlds().get(0).getWorldFolder(), "playerdata"), offlineplayer.getUniqueId()+".dat"));
	}

	/**
	 * Get ExtendedOfflinePlayer instance
	 * @param offlineplayer OfflinePlayer instance
	 * @param player data file
	 * @return ExtendedOfflinePlayer instance 
	 * @throws FileNotFoundException if player data file wasn't found
	 */
	public static ExtendedOfflinePlayer getExtendedOfflinePlayer(OfflinePlayer offlineplayer, File datafile) throws FileNotFoundException {
		if (!datafile.exists()) {
			throw new FileNotFoundException("Player data file "+datafile.getAbsolutePath()+" not found");
		}
		return new ExtendedOfflinePlayer(offlineplayer, datafile);
	}

}
