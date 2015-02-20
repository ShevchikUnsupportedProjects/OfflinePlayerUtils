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

import org.bukkit.inventory.meta.SkullMeta;

import offlineplayerutils.simplenbt.NBTTagCompound;
import offlineplayerutils.simplenbt.NBTTagType;

public class WrappedSkullMeta extends WrappedItemMeta implements SkullMeta {

	private static final String PROFILE_TAG = "SkullOwner";
	private static final String NAME_TAG = "Name";

	public WrappedSkullMeta(NBTTagCompound itemmetatag) {
		super(itemmetatag);
	}

	@Override
	public WrappedSkullMeta clone() {
		return new WrappedSkullMeta((NBTTagCompound) itemmetatag.clone());
	}

	@Override
	public boolean hasOwner() {
		return itemmetatag.hasOfType(PROFILE_TAG, NBTTagType.COMPOUND) || itemmetatag.hasOfType(PROFILE_TAG, NBTTagType.STRING);
	}

	@Override
	public String getOwner() {
		if (hasOwner()) {
			if (itemmetatag.hasOfType(PROFILE_TAG, NBTTagType.STRING)) {
				return itemmetatag.getString(PROFILE_TAG);
			}
			if (itemmetatag.hasOfType(PROFILE_TAG, NBTTagType.COMPOUND)) {
				NBTTagCompound profiletag = (NBTTagCompound) itemmetatag.get(PROFILE_TAG);
				if (profiletag.hasOfType(NAME_TAG, NBTTagType.STRING)) {
					return profiletag.getString(NAME_TAG);
				}
			}
		}
		return null;
	}

	@Override
	public boolean setOwner(String owner) {
		NBTTagCompound profiletag = new NBTTagCompound();
		profiletag.setString(NAME_TAG, owner);
		itemmetatag.set(PROFILE_TAG, profiletag);
		return true;
	}

}
