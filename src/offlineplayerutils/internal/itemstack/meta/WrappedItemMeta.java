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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import offlineplayerutils.api.inventory.IWrappedItemMeta;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagString;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class WrappedItemMeta implements IWrappedItemMeta, Repairable {

	private static final String DISPLAY_TAG = "display";
	private static final String NAME_TAG = "Name";
	private static final String LORE_TAG = "Lore";
	private static final String ENCHANTMENTS_TAG = "ench";
	private static final String ENCHANTMENT_ID_TAG = "id";
	private static final String ENCHANTMENT_LEVEL_TAG = "lvl";
	private static final String REPAIR_COST_TAG = "RepairCost";
	private static final String ITEM_FLAGS_TAG = "HideFlags";

	protected NBTTagCompound itemmetatag;

	public WrappedItemMeta(NBTTagCompound itemmetatag) {
		this.itemmetatag = itemmetatag;
	}

	protected NBTTagCompound getTag() {
		return itemmetatag;
	}

	@Override
	public WrappedItemMeta clone() {
		return new WrappedItemMeta((NBTTagCompound) itemmetatag.clone());
	}

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<String, Object>();
	}

	protected NBTTagCompound getDispalyTag() {
		if (!itemmetatag.hasOfType(DISPLAY_TAG, NBTTagType.COMPOUND)) {
			return new NBTTagCompound();
		}
		return (NBTTagCompound) itemmetatag.get(DISPLAY_TAG);
	}

	protected void saveDisplayTag(NBTTagCompound display) {
		if (display.size() == 0) {
			itemmetatag.remove(DISPLAY_TAG);
		} else {
			itemmetatag.set(DISPLAY_TAG, display);
		}
	}

	@Override
	public boolean hasDisplayName() {
		if (itemmetatag.hasOfType(DISPLAY_TAG, NBTTagType.COMPOUND)) {
			return getDispalyTag().hasOfType(NAME_TAG, NBTTagType.STRING);
		}
		return false;
	}

	@Override
	public String getDisplayName() {
		if (hasDisplayName()) {
			return getDispalyTag().getString(NAME_TAG);
		}
		return null;
	}

	@Override
	public void setDisplayName(String name) {
		NBTTagCompound display = getDispalyTag();
		if (name == null || name.equals("")) {
			display.remove(NAME_TAG);
		} else {
			display.setString(NAME_TAG, name);
		}
		saveDisplayTag(display);
	}

	@Override
	public boolean hasLore() {
		if (itemmetatag.hasOfType(DISPLAY_TAG, NBTTagType.COMPOUND)) {
			return getDispalyTag().hasListOfType(LORE_TAG, NBTTagType.STRING);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getLore() {
		if (hasLore()) {
			NBTTagList<NBTTagString> list = (NBTTagList<NBTTagString>) getDispalyTag().get(LORE_TAG);
			ArrayList<String> lore = new ArrayList<String>();
			for (NBTTagString stringtag : list) {
				lore.add(stringtag.getValue());
			}
			return lore;
		}
		return null;
	}

	@Override
	public void setLore(List<String> lore) {
		NBTTagCompound display = getDispalyTag();
		if (lore == null || lore.isEmpty()) {
			display.remove(LORE_TAG);
		} else {
			NBTTagList<NBTTagString> list = new NBTTagList<NBTTagString>();
			for (String element : lore) {
				list.add(new NBTTagString(element));
			}
			display.set(LORE_TAG, list);
		}
		saveDisplayTag(display);
	}

	@Override
	public boolean hasEnchants() {
		return itemmetatag.hasListOfType(ENCHANTMENTS_TAG, NBTTagType.COMPOUND);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Map<Enchantment, Integer> getEnchants() {
		HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
		if (hasEnchants()) {
			NBTTagList<NBTTagCompound> list = (NBTTagList<NBTTagCompound>) itemmetatag.get(ENCHANTMENTS_TAG);
			for (NBTTagCompound compound : list) {
				int id = compound.getShort(ENCHANTMENT_ID_TAG) & 0xFFFF;
				Enchantment ench = Enchantment.getById(id);
				if (ench != null) {
					int level = compound.getShort(ENCHANTMENT_LEVEL_TAG) & 0xFFFF;
					enchants.put(ench, level);
				}
			}
		}
		return enchants;
	}

	@Override
	public boolean hasEnchant(Enchantment ench) {
		return getEnchants().containsKey(ench);
	}

	@Override
	public int getEnchantLevel(Enchantment ench) {
		Integer level = getEnchants().get(ench);
		if (level == null) {
			return 0;
		}
		return level;
	}

	@Override
	public boolean addEnchant(Enchantment ench, int level, boolean unsafe) {
		if (!unsafe && (level > ench.getMaxLevel() || level < ench.getStartLevel())) {
			return false;
		}
		Map<Enchantment, Integer> enchants = getEnchants();
		Integer oldlevel = enchants.put(ench, level);
		saveEnchants(enchants);
		return oldlevel == null || oldlevel != level;
	}

	@Override
	public boolean removeEnchant(Enchantment ench) {
		Map<Enchantment, Integer> enchants = getEnchants();
		boolean had = enchants.remove(ench) != null;
		saveEnchants(enchants);
		return had;
	}

	@SuppressWarnings("deprecation")
	private void saveEnchants(Map<Enchantment, Integer> enchants) {
		itemmetatag.remove(ENCHANTMENTS_TAG);
		if (enchants.isEmpty()) {
			return;
		}
		NBTTagList<NBTTagCompound> list = new NBTTagList<NBTTagCompound>();
		for (Entry<Enchantment, Integer> entry : enchants.entrySet()) {
			NBTTagCompound ench = new NBTTagCompound();
			ench.setShort(ENCHANTMENT_ID_TAG, (short) entry.getKey().getId());
			ench.setShort(ENCHANTMENT_LEVEL_TAG, entry.getValue().shortValue());
			list.add(ench);
		}
		itemmetatag.set(ENCHANTMENTS_TAG, list);
	}

	@Override
	public boolean hasConflictingEnchant(Enchantment otherench) {
		for (Enchantment ench : getEnchants().keySet()) {
			if (ench.conflictsWith(otherench)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Spigot spigot() {
		return new SpigotMeta(itemmetatag);
	}

	@Override
	public Set<ItemFlag> getItemFlags() {
		int flagbitset = itemmetatag.getInt(ITEM_FLAGS_TAG);
		Set<ItemFlag> flags = EnumSet.noneOf(ItemFlag.class);
		for (ItemFlag flag : ItemFlag.values()) {
			int modifier = (1 << flag.ordinal());
			if ((flagbitset & modifier) == modifier) {
				flags.add(flag);
			}
        }
        return flags;
	}

	@Override
	public boolean hasItemFlag(ItemFlag flag) {
		return getItemFlags().contains(flag);
	}

	@Override
	public void addItemFlags(ItemFlag... addflags) {
		Set<ItemFlag> flags = getItemFlags();
		for (ItemFlag flag : flags) {
			flags.add(flag);
		}
		saveItemFlags(flags);
	}

	@Override
	public void removeItemFlags(ItemFlag... removeflags) {
		Set<ItemFlag> flags = getItemFlags();
		for (ItemFlag flag : flags) {
			flags.remove(flag);
		}
		saveItemFlags(flags);
	}

	private void saveItemFlags(Set<ItemFlag> flags) {
		int flagbitset = 0;
		for (ItemFlag flag : flags) {
			flagbitset |= (1 << flag.ordinal());
		}
		itemmetatag.setInt(ITEM_FLAGS_TAG, flagbitset);
	}

	@Override
	public boolean hasRepairCost() {
		return itemmetatag.hasOfNumberType(REPAIR_COST_TAG);
	}

	@Override
	public int getRepairCost() {
		return itemmetatag.getInt(REPAIR_COST_TAG);
	}

	@Override
	public void setRepairCost(int cost) {
		itemmetatag.setInt(REPAIR_COST_TAG, cost);
	}



	public void applyToBukkit(ItemMeta bukkit) {
		if (hasDisplayName()) {
			bukkit.setDisplayName(getDisplayName());
		}
		if (hasLore()) {
			bukkit.setLore(getLore());
		}
		if (hasEnchants()) {
			for (Entry<Enchantment, Integer> entry : getEnchants().entrySet()) {
				bukkit.addEnchant(entry.getKey(), entry.getValue(), true);
			}
		}
		try {
			if (spigot().isUnbreakable()) {
				bukkit.spigot().setUnbreakable(true);
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof Repairable) {
				Repairable repairablebukkit = (Repairable) bukkit;
				if (hasRepairCost()) {
					repairablebukkit.setRepairCost(getRepairCost());
				}
			}
		} catch (Throwable t) {
		}
		try {
			for (ItemFlag flag : getItemFlags()) {
				bukkit.addItemFlags(flag);
			}
		} catch (Throwable t) {
		}
	}

	public void copyFromBukkit(ItemMeta bukkit) {
		if (bukkit.hasDisplayName()) {
			setDisplayName(bukkit.getDisplayName());
		}
		if (bukkit.hasLore()) {
			setLore(bukkit.getLore());
		}
		if (bukkit.hasEnchants()) {
			for (Entry<Enchantment, Integer> entry : bukkit.getEnchants().entrySet()) {
				addEnchant(entry.getKey(), entry.getValue(), true);
			}
		}
		try {
			if (bukkit.spigot().isUnbreakable()) {
				spigot().setUnbreakable(true);
			}
		} catch (Throwable t) {
		}
		try {
			if (bukkit instanceof Repairable) {
				Repairable repairablebukkit = (Repairable) bukkit;
				if (repairablebukkit.hasRepairCost()) {
					setRepairCost(repairablebukkit.getRepairCost());
				}
			}
		} catch (Throwable t) {
		}
		try {
			for (ItemFlag flag : bukkit.getItemFlags()) {
				addItemFlags(flag);
			}
		} catch (Throwable t) {
		}
	}

}
