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

package offlineplayerutils.internal.v1_7_R1;

import net.minecraft.server.v1_7_R1.NBTTagCompound;
import net.minecraft.server.v1_7_R1.NBTTagList;
import offlineplayerutils.internal.InventoryDataInterface;

import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class InventoryData implements InventoryDataInterface {

	@Override
	public ItemStack[] getInventoryContents(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			NBTTagList nbttaglist = data.getList("Inventory", 10);
			ItemStack[] items = new ItemStack[36];
	        for (int i = 0; i < nbttaglist.size(); ++i) {
	            NBTTagCompound nbttagcompound = nbttaglist.get(i);
	            int j = nbttagcompound.getByte("Slot") & 255;
	            ItemStack itemstack = CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R1.ItemStack.createStack(nbttagcompound));
	            if (itemstack != null) {
	                if (j >= 0 && j < 36) {
	                    items[j] = itemstack;
	                }
	            }
	        }
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ItemStack[] getArmorContents(OfflinePlayer player) {
		try {
			NBTTagCompound data = DataUtils.getData(player);
			NBTTagList nbttaglist = data.getList("Inventory", 10);
			ItemStack[] armor = new ItemStack[4];
	        for (int i = 0; i < nbttaglist.size(); ++i) {
	            NBTTagCompound nbttagcompound = nbttaglist.get(i);
	            int j = nbttagcompound.getByte("Slot") & 255;
	            ItemStack itemstack = CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R1.ItemStack.createStack(nbttagcompound));
	            if (itemstack != null) {
	                if (j >= 100 && j < 104) {
	                	armor[j] = itemstack;
	                }
	            }
	        }
			return armor;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setInventoryContents(OfflinePlayer player, ItemStack[] contents) {
		try {
			ItemStack[] items = new ItemStack[36];
			ItemStack[] armor = getArmorContents(player);
			if (contents != null) {
				for (int c = 0; c < 36; c++) {
					if (c < contents.length && contents[c] != null) {
						items[c] = contents[c];
					}
				}
			}
			NBTTagCompound data = DataUtils.getData(player);
			saveInvToNBT(data, items, armor);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setArmorContents(OfflinePlayer player, ItemStack[] contents) {
		try {
			ItemStack[] items = getInventoryContents(player);
			ItemStack[] armor = new ItemStack[4];
			if (contents != null) {
				for (int c = 0; c < 4; c++) {
					if (c < contents.length && contents[c] != null) {
						armor[c] = contents[c];
					}
				}
			}
			NBTTagCompound data = DataUtils.getData(player);
			saveInvToNBT(data, items, armor);
			DataUtils.saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private NBTTagCompound saveInvToNBT(NBTTagCompound data, ItemStack[] items, ItemStack[] armor) {
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < items.length; ++i) {
            if (items[i] != null) {
            	NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                CraftItemStack.asNMSCopy(items[i]).save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
            }
		}
		for (int i = 100; i < armor.length; ++i) {
			if (armor[i] != null) {
            	NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                CraftItemStack.asNMSCopy(armor[i]).save(nbttagcompound);
                nbttaglist.add(nbttagcompound);
			}
		}
		data.set("Inventory", nbttaglist);
		return data;
	}

}
