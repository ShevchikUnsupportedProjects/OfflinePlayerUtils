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

package offlineplayerutils.internal.v1_7_R2;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import net.minecraft.server.v1_7_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R2.NBTTagCompound;
import offlineplayerutils.internal.WorldUUIDToNameResolverInterface;

import org.bukkit.Bukkit;

public class WorldUUIDToNameResolver implements WorldUUIDToNameResolverInterface {

	@Override
	public String resolveWorldName(UUID uuid) {
		if (Bukkit.getWorld(uuid) != null) {
			return Bukkit.getWorld(uuid).getName();
		}
		return visitFoldersAndScanForWorlds(Bukkit.getWorldContainer(), uuid);
	}

	private String visitFoldersAndScanForWorlds(File folder, UUID uuid) {
		File uid = new File(folder, "uid.dat");
		File dat = new File(folder, "level.dat");
		if (uid.exists() && dat.exists()) {
			if (checkUUIDMatch(uid, uuid)) {
				return getWorldNameFromData(dat);
			}
		}
		for (File file : folder.listFiles()) {
			if (file.isDirectory()) {
				visitFoldersAndScanForWorlds(file, uuid);
			}
		}
		return null;
	}
	
	private boolean checkUUIDMatch(File uid, UUID uuid) {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(uid));
			return new UUID(dis.readLong(), dis.readLong()).equals(uuid);
		} catch (IOException ex) {
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException ex) {
				}
			}
		}
        return false;
	}

	private String getWorldNameFromData(File dat) {
		try {
			NBTTagCompound root = NBTCompressedStreamTools.a((InputStream) (new FileInputStream(dat)));
	        NBTTagCompound data = root.getCompound("Data");
	        return data.getString("LevelName");
		} catch (Exception e) {
		}
		return null;
	}

}
