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

import offlineplayerutils.internal.itemstack.meta.MetaSerializer;
import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagType;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WrappedItemStack extends ItemStack {

	private NBTTagCompound tag;
	private ItemMeta itemmeta;

	public WrappedItemStack(NBTTagCompound data) {
		tag = data;
		if (hasItemMeta()) {
			itemmeta = MetaSerializer.createMetaFromTag(getType(), (NBTTagCompound) tag.get("tag"));
		} else {
			itemmeta = Bukkit.getItemFactory().getItemMeta(getType());
		}
	}

	protected NBTTagCompound getTag() {
		return tag;
	}

	@Override
	public ItemStack clone() {
		return new WrappedItemStack((NBTTagCompound) tag.clone());
	}

	@Override
	public boolean hasItemMeta() {
		return tag.hasOfType("tag", NBTTagType.COMPOUND);
	}

	@Override
	public ItemMeta getItemMeta() {
		return itemmeta;
	}

	@Override
	public boolean setItemMeta(ItemMeta itemmeta) {
		if (super.setItemMeta(itemmeta)) {
			this.itemmeta = itemmeta;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Material getType() {
        if (tag.hasOfType("id", NBTTagType.STRING)) {
            return Bukkit.getUnsafe().getMaterialFromInternalName(tag.getString("id"));
        }
        else {
            return Material.getMaterial(tag.getShort("id"));
        }
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setType(final Material type) {
		tag.setShort("id", (short) type.getId());
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getTypeId() {
		return getType().getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setTypeId(int typeId) {
		setType(Material.getMaterial(typeId));
	}

	@Override
	public int getAmount() {
		return tag.getByte("Count");
	}

	@Override
	public void setAmount(int amount) {
		tag.setByte("Count", (byte) amount);
	}

	@Override
	public short getDurability() {
		return tag.getShort("Damage");
	}

	@Override
	public void setDurability(short durability) {
		tag.setShort("Damage", durability);
	}

}
