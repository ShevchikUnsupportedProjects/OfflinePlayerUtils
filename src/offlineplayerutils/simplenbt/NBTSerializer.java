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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	public static NBTTagCompound fromJava(Map<String, Object> map) {
		NBTTagCompound compound = new NBTTagCompound();
		for (Entry<String, Object> entry : map.entrySet()) {
			compound.set(entry.getKey(), decodeObject(entry.getValue()));
		}
		return compound;
	}

	@SuppressWarnings("unchecked")
	private static NBTTagBase<?> decodeObject(Object obj) {
		if (obj instanceof Number) {
			return new NBTTagNumber<Number>((Number) obj);
		}
		if (obj instanceof String) {
			return new NBTTagString((String) obj);
		}
		if (obj instanceof byte[]) {
			return new NBTTagByteArray((byte[]) obj);
		}
		if (obj instanceof int[]) {
			return new NBTTagIntArray((int[]) obj);
		}
		if (obj instanceof List) {
			NBTTagList<NBTTagBase<?>> list = new NBTTagList<NBTTagBase<?>>();
			for (Object element : (List<Object>) obj) {
				list.add(decodeObject(element));
			}
			return list;
		}
		if (obj instanceof Map) {
			NBTTagCompound compound = new NBTTagCompound();
			for (Entry<Object, Object> entry : ((Map<Object, Object>) obj).entrySet()) {
				if (!(entry.getKey() instanceof String)) {
					throw new IllegalArgumentException("Map key should be String");
				}
				compound.set((String) entry.getKey(), decodeObject(entry.getValue()));
			}
		}
		throw new IllegalArgumentException("Can't convert "+obj+" to NBT, object is not of any possible types");
	}

	@SuppressWarnings("unchecked")
	private static <T extends Throwable> void sneakyThrow(Throwable throwable) throws T {
		throw (T) throwable;
	}

}
