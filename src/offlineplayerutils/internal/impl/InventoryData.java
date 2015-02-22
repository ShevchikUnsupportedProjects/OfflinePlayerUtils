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

package offlineplayerutils.internal.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import offlineplayerutils.api.inventory.IWrappedItemStack;
import offlineplayerutils.internal.itemstack.ItemStackSerializer;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InventoryData {

	private static final String PLAYER_INVENTORY_TAG = "Inventory";
	private static final String ENDER_ITEMS_TAG = "EnderItems";

	public IWrappedItemStack[] getInventoryContents(File datafile) {
		return getSpecificItems(getItems(DataUtils.getData(datafile), PLAYER_INVENTORY_TAG), 0, 36);
	}

	public IWrappedItemStack[] getArmorContents(File datafile) {
		return getSpecificItems(getItems(DataUtils.getData(datafile), PLAYER_INVENTORY_TAG), 100, 104);
	}

	public IWrappedItemStack[] getEnderChestContents(File datafile) {
		return getSpecificItems(getItems(DataUtils.getData(datafile), ENDER_ITEMS_TAG), 0, 27);
	}

	public void setInventoryContents(File datafile, ItemStack[] contents) {
		if (contents.length != 36) {
			throw new IllegalArgumentException("Inventory contents array should have a length of 36");
		}
		NBTTagCompound data = DataUtils.getData(datafile);
		DataUtils.saveData(datafile, setItems(data, PLAYER_INVENTORY_TAG, setSpecificItems(getItems(data, PLAYER_INVENTORY_TAG), 0, 36, contents)));
	}

	public void setArmorContents(File datafile, ItemStack[] contents) {
		if (contents.length != 4) {
			throw new IllegalArgumentException("Armor contents array should have a length of 4");
		}
		NBTTagCompound data = DataUtils.getData(datafile);
		DataUtils.saveData(datafile, setItems(data, PLAYER_INVENTORY_TAG, setSpecificItems(getItems(data, PLAYER_INVENTORY_TAG), 100, 104, contents)));
	}

	public void setEnderChestContents(File datafile, ItemStack[] contents) {
		if (contents.length != 27) {
			throw new IllegalArgumentException("Armor contents array should have a length of 4");
		}
		NBTTagCompound data = DataUtils.getData(datafile);
		DataUtils.saveData(datafile, setItems(data, ENDER_ITEMS_TAG, setSpecificItems(getItems(data, ENDER_ITEMS_TAG), 0, 27, contents)));
	}


	private IWrappedItemStack[] getSpecificItems(HashMap<Integer, ItemStack> items, int startslot, int endslot) {
		IWrappedItemStack[] stacks = new IWrappedItemStack[endslot - startslot];
		for (int invslot = startslot; invslot < endslot; invslot++) {
			stacks[invslot - startslot] = (IWrappedItemStack) items.get(invslot);
		}
		return stacks;
	}

	private HashMap<Integer, ItemStack> setSpecificItems(HashMap<Integer, ItemStack> items, int startslot, int endslot, ItemStack[] setitems) {
		for (int invslot = startslot, setitem = 0; invslot < endslot; invslot++, setitem++) {
			if (setitems[setitem] != null && setitems[setitem].getType() == Material.AIR) {
				items.put(invslot, null);
			} else {
				items.put(invslot, setitems[setitem]);
			}
		}
		return items;
	}

	@SuppressWarnings("unchecked")
	private HashMap<Integer, ItemStack> getItems(NBTTagCompound data, String invtagname) {
		HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		if (data.hasListOfType("Inventory", NBTTagType.COMPOUND)) {
			NBTTagList<NBTTagCompound> nbttaglist = (NBTTagList<NBTTagCompound>) data.get(invtagname);
	        for (NBTTagCompound slotinfo : nbttaglist) {
	            int slot = slotinfo.getByte("Slot") & 255;
	            ItemStack itemstack = ItemStackSerializer.createItemStack(slotinfo);
	            if (itemstack != null) {
	            	items.put(slot, itemstack);
	            }
	        }
		}
		return items;
	}

	private NBTTagCompound setItems(NBTTagCompound data, String invtagname, HashMap<Integer, ItemStack> items) {
		NBTTagList<NBTTagCompound> nbttaglist = new NBTTagList<NBTTagCompound>();
		for (Entry<Integer, ItemStack> entry : items.entrySet()) {
			if (entry.getValue() != null) {
	        	NBTTagCompound slotinfo = ItemStackSerializer.saveToNBT(entry.getValue());
	        	if (slotinfo != null) {
	        		slotinfo.setByte("Slot", entry.getKey().byteValue());
		        	nbttaglist.add(slotinfo);
	        	}
			}
		}
		data.set(invtagname, nbttaglist);
		return data;
	}

}
