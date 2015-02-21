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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import offlineplayerutils.simplenbt.NBTSerializer;
import offlineplayerutils.simplenbt.NBTTagCompound;

public class DataUtils {

	public static NBTTagCompound getData(File datafile) {
		if (datafile.exists()) {
			try {
				return NBTSerializer.read(new FileInputStream(datafile));
			} catch (FileNotFoundException e) {
			}
		}
		return null;
	}

	public static void saveData(File datafile, NBTTagCompound data) {
		File file1 = new File(datafile.getAbsolutePath()+".tmp");
		File file2 = new File(datafile.getAbsolutePath());
		try {
			NBTSerializer.write(data, new FileOutputStream(file1));
		} catch (FileNotFoundException e) {
		}
		if (file2.exists()) {
			file2.delete();
		}
		file1.renameTo(file2);
	}

}
