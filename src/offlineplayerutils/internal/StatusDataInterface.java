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

package offlineplayerutils.internal;

import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;

public interface StatusDataInterface {

	public int getFoodLevel(OfflinePlayer player);

	public void setFoodLevel(OfflinePlayer player, int foodlevel);

	public float getHealth(OfflinePlayer player);

	public void setHealth(OfflinePlayer player, float health);

	public float getMaxHealth(OfflinePlayer player);

	public void setMaxHeath(OfflinePlayer player, float maxhealth);

	public GameMode getGameMode(OfflinePlayer player);

	public void setGameMode(OfflinePlayer player, GameMode mode);

}
