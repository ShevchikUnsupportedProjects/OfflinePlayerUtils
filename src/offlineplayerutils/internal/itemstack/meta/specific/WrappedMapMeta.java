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

package offlineplayerutils.internal.itemstack.meta.specific;

import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;

import offlineplayerutils.internal.itemstack.meta.WrappedItemMeta;
import offlineplayerutils.simplenbt.NBTTagCompound;

public class WrappedMapMeta extends WrappedItemMeta implements MapMeta {

	private static final String SCALING_TAG = "map_is_scaling";

	public WrappedMapMeta(NBTTagCompound itemmetatag) {
		super(itemmetatag);
	}

	@Override
	public WrappedMapMeta clone() {
		return new WrappedMapMeta(itemmetatag);
	}

	@Override
	public boolean isScaling() {
		return itemmetatag.getByte(SCALING_TAG) != 0;
	}

	@Override
	public void setScaling(boolean isScaling) {
		if (isScaling) {
			itemmetatag.setByte(SCALING_TAG, (byte) 1);
		} else {
			itemmetatag.remove(SCALING_TAG);
		}
	}



	@Override
	public void applyToBukkit(ItemMeta bukkit) {
		super.applyToBukkit(bukkit);
		if (bukkit instanceof MapMeta) {
			MapMeta mapbukkit = (MapMeta) bukkit;
			mapbukkit.setScaling(isScaling());
		}
	}

	@Override
	public void copyFromBukkit(ItemMeta bukkit) {
		super.copyFromBukkit(bukkit);
		if (bukkit instanceof MapMeta) {
			MapMeta mapbukkit = (MapMeta) bukkit;
			setScaling(mapbukkit.isScaling());
		}
	}

}
