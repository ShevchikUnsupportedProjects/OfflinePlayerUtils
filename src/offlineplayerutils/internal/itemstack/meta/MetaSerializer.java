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

import java.util.Map.Entry;

import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public class MetaSerializer {

	public static ItemMeta createMetaFromTag(Material type, NBTTagCompound tag) {
		ItemMeta wrapped = new WrappedItemMeta(tag);
		ItemMeta bukkit = Bukkit.getItemFactory().getItemMeta(type);
		if (wrapped.hasDisplayName()) {
			bukkit.setDisplayName(wrapped.getDisplayName());
		}
		if (wrapped.hasLore()) {
			bukkit.setLore(wrapped.getLore());
		}
		if (wrapped.hasEnchants()) {
			for (Entry<Enchantment, Integer> entry : wrapped.getEnchants().entrySet()) {
				bukkit.addEnchant(entry.getKey(), entry.getValue(), true);
			}
		}
		try {
			if (wrapped.spigot().isUnbreakable()) {
				bukkit.spigot().setUnbreakable(true);
			}
		} catch (Throwable t) {
		}
		return bukkit;
	}

	public static NBTTagCompound createTagFromMeta(Material type, ItemMeta bukkit) {
		WrappedItemMeta wrapped = new WrappedItemMeta(new NBTTagCompound());
		if (bukkit.hasDisplayName()) {
			wrapped.setDisplayName(bukkit.getDisplayName());
		}
		if (bukkit.hasLore()) {
			wrapped.setLore(bukkit.getLore());
		}
		if (bukkit.hasEnchants()) {
			for (Entry<Enchantment, Integer> entry : bukkit.getEnchants().entrySet()) {
				wrapped.addEnchant(entry.getKey(), entry.getValue(), true);
			}
		}
		try {
			if (bukkit.spigot().isUnbreakable()) {
				wrapped.spigot().setUnbreakable(true);
			}
		} catch (Throwable t) {
		}
		return wrapped.getTag();
	}

}
