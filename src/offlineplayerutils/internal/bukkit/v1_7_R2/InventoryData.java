package offlineplayerutils.internal.bukkit.v1_7_R2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.server.v1_7_R2.NBTTagCompound;
import net.minecraft.server.v1_7_R2.NBTTagList;
import offlineplayerutils.internal.InventoryDataInterface;

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
			ItemStack[] armor = new ItemStack[36];
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
	
	public void setInventoryContents(OfflinePlayer player) {
		
	}

    private NBTTagCompound getData(OfflinePlayer player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Method getDataMethod = player.getClass().getDeclaredMethod("getData");
    	getDataMethod.setAccessible(true);
    	return (NBTTagCompound) getDataMethod.invoke(player);
    }

}
