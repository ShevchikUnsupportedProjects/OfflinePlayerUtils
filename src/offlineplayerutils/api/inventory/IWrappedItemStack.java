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

package offlineplayerutils.api.inventory;

import java.util.Map;

import offlineplayerutils.internal.itemstack.WrappedItemStack;
import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.inventory.ItemStack;

public abstract class IWrappedItemStack extends ItemStack {

	/**
	 * Create wrapped itemstack from raw nbt data
	 */
	public static IWrappedItemStack createFromRawNBTData(Map<String, Object> rawdata) {
		WrappedItemStack wrappedstack = new WrappedItemStack(new NBTTagCompound());
		wrappedstack.setRawNBTData(rawdata);
		return wrappedstack;
	}

	/**
	 * Get direct ItemMeta, any changes on it reflect on itemstack
	 */
	public abstract IWrappedItemMeta getDirectMeta();

	/**
	 * Get itemstack raw nbt data
	 */
	public abstract Map<String, Object> getRawNBTData();

	/**
	 * Set itemstack raw nbt data
	 */
	public abstract void setRawNBTData(Map<String, Object> rawdata);

}
