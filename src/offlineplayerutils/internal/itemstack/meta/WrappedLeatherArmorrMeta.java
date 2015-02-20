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

import offlineplayerutils.simplenbt.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class WrappedLeatherArmorrMeta extends WrappedItemMeta implements LeatherArmorMeta {

	private static final String COLOR_TAG = "color";

	public WrappedLeatherArmorrMeta(NBTTagCompound itemmetatag) {
		super(itemmetatag);
	}

	@Override
	public WrappedLeatherArmorrMeta clone() {
		return new WrappedLeatherArmorrMeta(itemmetatag);
	}

	@Override
	public Color getColor() {
		NBTTagCompound display = getDispalyTag();
		if (display.hasOfNumberType(COLOR_TAG)) {
			return Color.fromRGB(display.getInt(COLOR_TAG));
		}
		return Bukkit.getItemFactory().getDefaultLeatherColor();
	}

	@Override
	public void setColor(Color color) {
		NBTTagCompound display = getDispalyTag();
		if (color == null || Bukkit.getItemFactory().getDefaultLeatherColor().equals(color)) {
			display.remove(COLOR_TAG);
		} else {
			display.setInt(COLOR_TAG, color.asRGB());
		}
		saveDisplayTag(display);
	}

}
