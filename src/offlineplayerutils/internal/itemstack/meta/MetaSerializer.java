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

package offlineplayerutils.internal.itemstack.meta;

import offlineplayerutils.internal.itemstack.meta.specific.WrappedBannerMeta;
import offlineplayerutils.internal.itemstack.meta.specific.WrappedLeatherArmorMeta;
import offlineplayerutils.internal.itemstack.meta.specific.WrappedMapMeta;
import offlineplayerutils.internal.itemstack.meta.specific.WrappedSkullMeta;
import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class MetaSerializer {

	public static ItemMeta createMetaFromTag(Material type, NBTTagCompound tag) {
		ItemMeta bukkit = Bukkit.getItemFactory().getItemMeta(type);
		WrappedItemMeta wrapped = createMeta(bukkit, tag);
		wrapped.applyToBukkit(bukkit);
		return bukkit;
	}

	public static NBTTagCompound createTagFromMeta(Material type, ItemMeta bukkit) {
		WrappedItemMeta wrapped = createMeta(bukkit, new NBTTagCompound());
		wrapped.copyFromBukkit(bukkit);
		return wrapped.getTag();
	}

	public static WrappedItemMeta createMeta(ItemMeta bukkit, NBTTagCompound tag) {
		try {
			if (bukkit instanceof BannerMeta) {
				return new WrappedBannerMeta(tag);
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof SkullMeta) {
				return new WrappedSkullMeta(tag);
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof LeatherArmorMeta) {
				return new WrappedLeatherArmorMeta(tag);
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof MapMeta) {
				return new WrappedMapMeta(tag);
			}
		} catch (Throwable t) {
		}
		return new WrappedItemMeta(tag);
	}

}
