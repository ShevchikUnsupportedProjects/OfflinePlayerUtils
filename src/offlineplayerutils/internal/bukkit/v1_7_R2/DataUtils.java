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
