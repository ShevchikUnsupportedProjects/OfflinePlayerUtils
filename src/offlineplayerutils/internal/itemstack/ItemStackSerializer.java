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

package offlineplayerutils.internal.itemstack;

import offlineplayerutils.api.inventory.IWrappedItemStack;
import offlineplayerutils.internal.itemstack.meta.MetaSerializer;
import offlineplayerutils.simplenbt.NBTSerializer;
import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.inventory.ItemStack;

public class ItemStackSerializer {

	public static IWrappedItemStack createItemStack(NBTTagCompound compound) {
		return new WrappedItemStack(compound);
	}

	public static NBTTagCompound saveToNBT(ItemStack itemstack) {
		if (itemstack instanceof WrappedItemStack) {
			WrappedItemStack wrappedstack = (WrappedItemStack) itemstack;
			NBTTagCompound itemmetatag = (NBTTagCompound) wrappedstack.getRootTag().get("tag");
			if (itemmetatag != null && itemmetatag.size() == 0) {
				wrappedstack.getRootTag().remove("tag");
			}
			return wrappedstack.getRootTag();
		} else if (itemstack instanceof IWrappedItemStack) {
			IWrappedItemStack iwrappedstack = (IWrappedItemStack) itemstack;
			NBTTagCompound stacktag = fromBukkit(itemstack);
			NBTTagCompound itemmetatag = NBTSerializer.fromJava(iwrappedstack.getRawNBTData());
			if (itemmetatag.size() == 0) {
				stacktag.remove("tag");
			} else {
				stacktag.set("tag", itemmetatag);
			}
			return stacktag;
		} else {
			return fromBukkit(itemstack);
		}
	}

	private static NBTTagCompound fromBukkit(ItemStack itemstack) {
		NBTTagCompound compound = new NBTTagCompound();
		WrappedItemStack wrappedstack = new WrappedItemStack(compound);
		wrappedstack.setType(itemstack.getType());
		wrappedstack.setAmount(itemstack.getAmount());
		wrappedstack.setDurability(itemstack.getDurability());
		if (itemstack.hasItemMeta()) {
			wrappedstack.getRootTag().set("tag", MetaSerializer.createTagFromMeta(wrappedstack.getType(), itemstack.getItemMeta()));
		}
		return wrappedstack.getRootTag();
	}

}
