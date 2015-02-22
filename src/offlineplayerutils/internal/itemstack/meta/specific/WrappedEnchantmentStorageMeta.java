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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import offlineplayerutils.internal.itemstack.meta.WrappedItemMeta;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class WrappedEnchantmentStorageMeta extends WrappedItemMeta implements EnchantmentStorageMeta {

	private static final String STORED_ENCHANTMENTS_TAG = "StoredEnchantments";
	private static final String STORED_ENCHANTMENT_ID_TAG = "id";
	private static final String STORED_ENCHANTMENT_LEVEL_TAG = "lvl";

	public WrappedEnchantmentStorageMeta(NBTTagCompound itemmetatag) {
		super(itemmetatag);
	}

	@Override
	public WrappedEnchantmentStorageMeta clone() {
		return new WrappedEnchantmentStorageMeta((NBTTagCompound) itemmetatag.clone());
	}

	@Override
	public boolean hasStoredEnchants() {
		return itemmetatag.hasListOfType(STORED_ENCHANTMENTS_TAG, NBTTagType.COMPOUND);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Map<Enchantment, Integer> getStoredEnchants() {
		HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
		if (hasStoredEnchants()) {
			NBTTagList<NBTTagCompound> list = (NBTTagList<NBTTagCompound>) itemmetatag.get(STORED_ENCHANTMENTS_TAG);
			for (NBTTagCompound compound : list) {
				int id = compound.getShort(STORED_ENCHANTMENT_ID_TAG) & 0xFFFF;
				Enchantment ench = Enchantment.getById(id);
				if (ench != null) {
					int level = compound.getShort(STORED_ENCHANTMENT_LEVEL_TAG) & 0xFFFF;
					enchants.put(ench, level);
				}
			}
		}
		return enchants;
	}

	@Override
	public boolean hasStoredEnchant(Enchantment ench) {
		return getStoredEnchants().containsKey(ench);
	}

	@Override
	public boolean addStoredEnchant(Enchantment ench, int level, boolean unsafe) {
		if (!unsafe && (level > ench.getMaxLevel() || level < ench.getStartLevel())) {
			return false;
		}
		Map<Enchantment, Integer> enchants = getStoredEnchants();
		Integer oldlevel = enchants.put(ench, level);
		saveEnchants(enchants);
		return oldlevel == null || oldlevel != level;
	}

	@Override
	public int getStoredEnchantLevel(Enchantment ench) {
		Integer level = getStoredEnchants().get(ench);
		if (level == null) {
			return 0;
		}
		return level;
	}

	@Override
	public boolean removeStoredEnchant(Enchantment ench) throws IllegalArgumentException {
		Map<Enchantment, Integer> enchants = getStoredEnchants();
		boolean had = enchants.remove(ench) != null;
		saveEnchants(enchants);
		return had;
	}

	@Override
	public boolean hasConflictingStoredEnchant(Enchantment otherench) {
		for (Enchantment ench : getStoredEnchants().keySet()) {
			if (ench.conflictsWith(otherench)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	private void saveEnchants(Map<Enchantment, Integer> enchants) {
		itemmetatag.remove(STORED_ENCHANTMENTS_TAG);
		if (enchants.isEmpty()) {
			return;
		}
		NBTTagList<NBTTagCompound> list = new NBTTagList<NBTTagCompound>();
		for (Entry<Enchantment, Integer> entry : enchants.entrySet()) {
			NBTTagCompound ench = new NBTTagCompound();
			ench.setShort(STORED_ENCHANTMENT_ID_TAG, (short) entry.getKey().getId());
			ench.setShort(STORED_ENCHANTMENT_LEVEL_TAG, entry.getValue().shortValue());
			list.add(ench);
		}
		itemmetatag.set(STORED_ENCHANTMENTS_TAG, list);
	}



	@Override
	public void applyToBukkit(ItemMeta bukkit) {
		super.applyToBukkit(bukkit);
		if (bukkit instanceof EnchantmentStorageMeta) {
			EnchantmentStorageMeta enchstoragebukkit = (EnchantmentStorageMeta) bukkit;
			if (hasStoredEnchants()) {
				for (Entry<Enchantment, Integer> entry : getStoredEnchants().entrySet()) {
					enchstoragebukkit.addStoredEnchant(entry.getKey(), entry.getValue(), true);
				}
			}
		}
	}

	@Override
	public void copyFromBukkit(ItemMeta bukkit) {
		super.copyFromBukkit(bukkit);
		if (bukkit instanceof EnchantmentStorageMeta) {
			EnchantmentStorageMeta enchstoragebukkit = (EnchantmentStorageMeta) bukkit;
			if (enchstoragebukkit.hasStoredEnchants()) {
				for (Entry<Enchantment, Integer> entry : enchstoragebukkit.getStoredEnchants().entrySet()) {
					addStoredEnchant(entry.getKey(), entry.getValue(), true);
				}
			}
		}
	}

}
