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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagString;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;

public class WrappedItemMeta implements ItemMeta {

	private static final String DISPLAY_TAG = "display";
	private static final String NAME_TAG = "Name";
	private static final String LORE_TAG = "Lore";
	private static final String ENCHANTMENTS_TAG = "ench";
	private static final String ENCHANTMENT_ID_TAG = "id";
	private static final String ENCHANTMENT_LEVEL_TAG = "lvl";

	protected NBTTagCompound itemmetatag;

	public WrappedItemMeta(NBTTagCompound itemmetatag) {
		this.itemmetatag = itemmetatag;
	}

	protected NBTTagCompound getTag() {
		return itemmetatag;
	}

	@Override
	public ItemMeta clone() {
		return new WrappedItemMeta((NBTTagCompound) itemmetatag.clone());
	}

	@Override
	public Map<String, Object> serialize() {
		return new HashMap<String, Object>();
	}

	private NBTTagCompound getDispalyTag() {
		if (!itemmetatag.hasOfType(DISPLAY_TAG, NBTTagType.COMPOUND)) {
			return new NBTTagCompound();
		}
		return (NBTTagCompound) itemmetatag.get(DISPLAY_TAG);
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
		saveDisplay(display);
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
		saveDisplay(display);
	}

	private void saveDisplay(NBTTagCompound display) {
		if (display.size() == 0) {
			itemmetatag.remove(DISPLAY_TAG);
		} else {
			itemmetatag.set(DISPLAY_TAG, display);
		}
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
		return getEnchants().get(ench);
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
		boolean had = enchants.remove(enchants) != null;
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

}