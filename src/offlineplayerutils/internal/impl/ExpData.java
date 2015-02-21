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

import offlineplayerutils.simplenbt.NBTTagCompound;

public class ExpData {

	public float getExp(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		return data.getFloat("XpP");
	}

	public void setExp(File datafile, float exp) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setFloat("XpP", exp);
		DataUtils.saveData(datafile, data);
	}

	public int getLevel(File datafile) {
		NBTTagCompound data = DataUtils.getData(datafile);
		return data.getInt("XpLevel");
	}

	public void setLevel(File datafile, int level) {
		NBTTagCompound data = DataUtils.getData(datafile);
		data.setInt("XpLevel", level);
		DataUtils.saveData(datafile, data);
	}

}
