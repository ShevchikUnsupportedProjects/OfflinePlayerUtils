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

package offlineplayerutils.internal.bukkit.v1_7_R2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.server.v1_7_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R2.NBTTagCompound;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class DataUtils {
	
	public static NBTTagCompound getData(OfflinePlayer player) throws FileNotFoundException {
        File file = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsolutePath() + File.separator + "players" + File.separator + player.getName() + ".dat");
        if (file.exists()) {
            return NBTCompressedStreamTools.a((InputStream) (new FileInputStream(file)));
        }
        return null;
    }

	public static void saveData(OfflinePlayer player, NBTTagCompound data) throws FileNotFoundException {
		File file1 = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsolutePath() + File.separator + "players" + File.separator + player.getName() + ".dat.tmp");
		File file2 = new File(Bukkit.getWorlds().get(0).getWorldFolder().getAbsolutePath() + File.separator + "players" + File.separator + player.getName() + ".dat");
		NBTCompressedStreamTools.a(data, (OutputStream) (new FileOutputStream(file1)));
		if (file2.exists()) {
			file2.delete();
		}
		file1.renameTo(file2);
    }

}
