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

import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SpecificMetaSerializer {

	protected static WrappedItemMeta readSpecificFromNBT(ItemMeta bukkit, NBTTagCompound from) {
		try {
			if (bukkit instanceof BannerMeta) {
				BannerMeta bannerbukkit = (BannerMeta) bukkit;
				WrappedBannerMeta bannerwrapped = new WrappedBannerMeta(from);
				bannerbukkit.setBaseColor(bannerwrapped.getBaseColor());
				if (bannerwrapped.numberOfPatterns() != 0) {
					for (Pattern pattern : bannerwrapped.getPatterns()) {
						bannerbukkit.addPattern(pattern);
					}
				}
				return bannerwrapped;
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof SkullMeta) {
				SkullMeta skullbukkit = (SkullMeta) bukkit;
				WrappedSkullMeta skullwrapped = new WrappedSkullMeta(from);
				if (skullwrapped.hasOwner()) {
					skullbukkit.setOwner(skullwrapped.getOwner());
				}
				return skullwrapped;
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof LeatherArmorMeta) {
				LeatherArmorMeta leatherarmorbukkit = (LeatherArmorMeta) bukkit;
				WrappedLeatherArmorrMeta leatherarmorwrapped = new WrappedLeatherArmorrMeta(from);
				leatherarmorbukkit.setColor(leatherarmorwrapped.getColor());
				return leatherarmorwrapped;
			}
		} catch (Throwable t) {
		}
		return new WrappedItemMeta(from);
	}

	protected static WrappedItemMeta saveSpecificToNBT(ItemMeta bukkit) {
		try {
			if (bukkit instanceof BannerMeta) {
				BannerMeta bannerbukkit = (BannerMeta) bukkit;
				WrappedBannerMeta bannerwrapped = new WrappedBannerMeta(new NBTTagCompound());
				bannerwrapped.setBaseColor(bannerbukkit.getBaseColor());
				if (bannerbukkit.numberOfPatterns() != 0) {
					for (Pattern pattern : bannerbukkit.getPatterns()) {
						bannerwrapped.addPattern(pattern);
					}
				}
				return bannerwrapped;
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof SkullMeta) {
				SkullMeta skullbukkit = (SkullMeta) bukkit;
				WrappedSkullMeta skullwrapped = new WrappedSkullMeta(new NBTTagCompound());
				if (skullbukkit.hasOwner()) {
					skullwrapped.setOwner(skullbukkit.getOwner());
				}
				return skullwrapped;
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof LeatherArmorMeta) {
				LeatherArmorMeta leatherarmorbukkit = (LeatherArmorMeta) bukkit;
				WrappedLeatherArmorrMeta leatherarmorwrapped = new WrappedLeatherArmorrMeta(new NBTTagCompound());
				leatherarmorwrapped.setColor(leatherarmorbukkit.getColor());
				return leatherarmorwrapped;
			}
		} catch (Throwable t) {
		}
		return new WrappedItemMeta(new NBTTagCompound());
	}

}
