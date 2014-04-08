package offlineplayerutils.internal.bukkit.v1_7_R2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.server.v1_7_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R2.NBTTagCompound;
import net.minecraft.server.v1_7_R2.NBTTagList;

import offlineplayerutils.internal.InventoryDataInterface;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_7_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class InventoryData implements InventoryDataInterface {

	@Override
	public ItemStack[] getInventoryContents(OfflinePlayer player) {
		try {
			NBTTagCompound data = getData(player);
			NBTTagList nbttaglist = data.getList("Inventory", 10);
			ItemStack[] items = new ItemStack[36];
	        for (int i = 0; i < nbttaglist.size(); ++i) {
	            NBTTagCompound nbttagcompound = nbttaglist.get(i);
	            int j = nbttagcompound.getByte("Slot") & 255;
	            ItemStack itemstack = CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R2.ItemStack.createStack(nbttagcompound));
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
			NBTTagCompound data = getData(player);
			NBTTagList nbttaglist = data.getList("Inventory", 10);
			ItemStack[] armor = new ItemStack[4];
	        for (int i = 0; i < nbttaglist.size(); ++i) {
	            NBTTagCompound nbttagcompound = nbttaglist.get(i);
	            int j = nbttagcompound.getByte("Slot") & 255;
	            ItemStack itemstack = CraftItemStack.asCraftMirror(net.minecraft.server.v1_7_R2.ItemStack.createStack(nbttagcompound));
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
	
	public void setInventoryContents(OfflinePlayer player, ItemStack[] contents) {
		try {
			ItemStack[] items = getInventoryContents(player);
			ItemStack[] armor = getArmorContents(player);
			if (contents != null) {
				for (int c = 0; c < 36; c++) {
					if (c < contents.length && contents[c] != null) {
						items[c] = contents[c];
					}
				}
			}
			NBTTagCompound data = getData(player);
			saveInvToNBT(data, items, armor);
			saveData(player, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setArmorContents(OfflinePlayer player, ItemStack[] contents) {
		try {
			ItemStack[] items = getInventoryContents(player);
			ItemStack[] armor = getArmorContents(player);
			if (contents != null) {
				for (int c = 0; c < 4; c++) {
					if (c < contents.length && contents[c] != null) {
						armor[c] = contents[c];
					}
				}
			}
			NBTTagCompound data = getData(player);
			saveInvToNBT(data, items, armor);
			saveData(player, data);
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

    private NBTTagCompound getData(OfflinePlayer player) throws FileNotFoundException {
        File file = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsolutePath() + File.separator + "players" + File.separator + player.getName() + ".dat");
        if (file.exists()) {
            return NBTCompressedStreamTools.a((InputStream) (new FileInputStream(file)));
        }
        return null;
    }

    private void saveData(OfflinePlayer player, NBTTagCompound data) throws FileNotFoundException {
		File file1 = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsolutePath() + File.separator + "players" + File.separator + player.getName() + ".dat.tmp");
		File file2 = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsolutePath() + File.separator + "players" + File.separator + player.getName() + ".dat");
		NBTCompressedStreamTools.a(data, (OutputStream) (new FileOutputStream(file1)));
		if (file2.exists()) {
			file2.delete();
		}
		file1.renameTo(file2);
    }

}
