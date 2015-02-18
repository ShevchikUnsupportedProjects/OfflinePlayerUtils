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

package offlineplayerutils.simplenbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class NBTSerializer {

	public static NBTTagCompound read(InputStream stream) {
		try (DataInputStream datastream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(stream)))) {
			datastream.readByte();
			datastream.readUTF();
			return new NBTTagCompound().readValue(datastream);
		} catch (Throwable t) {
			NBTSerializer.<Error>sneakyThrow(t);
		}
		return null;
	}

	public static void write(NBTTagCompound compound, OutputStream stream) {
		try (DataOutputStream datastream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(stream)))) {
			datastream.writeByte(NBTTagType.COMPOUND.getTypeId());
			datastream.writeUTF("");
			compound.writeValue(datastream);
			datastream.flush();
		} catch (Throwable t) {
			NBTSerializer.<Error>sneakyThrow(t);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Throwable> void sneakyThrow(Throwable throwable) throws T {
		throw (T) throwable;
	}

}
