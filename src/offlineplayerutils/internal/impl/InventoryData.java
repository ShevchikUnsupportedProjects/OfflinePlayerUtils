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

import java.util.HashMap;
import java.util.Map.Entry;

import offlineplayerutils.internal.InventoryDataInterface;
import offlineplayerutils.internal.itemstack.ItemStackSerializer;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagList;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class InventoryData implements InventoryDataInterface {

	@Override
	public ItemStack[] getInventoryContents(OfflinePlayer player) {
		NBTTagCompound data = DataUtils.getData(player);
		HashMap<Integer, ItemStack> inventory = getItems(data);
		ItemStack[] items = new ItemStack[36];
		for (int i = 0; i < 36; i++) {
			items[i] = inventory.get(i);
		}
		return items;
	}

	@Override
	public ItemStack[] getArmorContents(OfflinePlayer player) {
		NBTTagCompound data = DataUtils.getData(player);
		HashMap<Integer, ItemStack> inventory = getItems(data);
		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < 4; i++) {
			armor[i] = inventory.get(100 + i);
		}
		return armor;
	}

	@Override
	public void setInventoryContents(OfflinePlayer player, ItemStack[] contents) {
		if (contents.length != 36) {
			throw new IllegalArgumentException("Inventory contents array should have a length of 36");
		}
		NBTTagCompound data = DataUtils.getData(player);
		HashMap<Integer, ItemStack> inventory = getItems(data);
		for (int i = 0; i < 36; i++) {
			inventory.put(i, contents[i]);
		}
		setItems(data, inventory);
		DataUtils.saveData(player, data);
	}

	@Override
	public void setArmorContents(OfflinePlayer player, ItemStack[] contents) {
		if (contents.length != 4) {
			throw new IllegalArgumentException("Armor contents array should have a length of 4");
		}
		NBTTagCompound data = DataUtils.getData(player);
		HashMap<Integer, ItemStack> inventory = getItems(data);
		for (int i = 0; i < 4; i++) {
			inventory.put(100 + i, contents[i]);
		}
		setItems(data, inventory);
		DataUtils.saveData(player, data);
	}

	@SuppressWarnings("unchecked")
	private HashMap<Integer, ItemStack> getItems(NBTTagCompound data) {
		HashMap<Integer, ItemStack> items = new HashMap<Integer, ItemStack>();
		if (data.hasListOfType("Inventory", NBTTagType.COMPOUND)) {
			NBTTagList<NBTTagCompound> nbttaglist = (NBTTagList<NBTTagCompound>) data.get("Inventory");
	        for (int i = 0; i < nbttaglist.size(); ++i) {
	            NBTTagCompound slotinfo = nbttaglist.get(i);
	            int slot = slotinfo.getByte("Slot") & 255;
	            ItemStack itemstack = ItemStackSerializer.createItemStack(slotinfo);
	            if (itemstack != null) {
	            	items.put(slot, itemstack);
	            }
	        }
		}
		return items;
	}

	private NBTTagCompound setItems(NBTTagCompound data, HashMap<Integer, ItemStack> items) {
		NBTTagList<NBTTagCompound> nbttaglist = new NBTTagList<NBTTagCompound>();
		for (Entry<Integer, ItemStack> entry : items.entrySet()) {
			if (entry.getValue() != null) {
	        	NBTTagCompound slotinfo = ItemStackSerializer.saveToNBT(entry.getValue());
	        	if (slotinfo != null) {
	        		slotinfo.setByte("Slot", entry.getKey().byteValue());
	        	}
	        	nbttaglist.add(slotinfo);
			}
		}
		data.set("Inventory", nbttaglist);
		return data;
	}

}
