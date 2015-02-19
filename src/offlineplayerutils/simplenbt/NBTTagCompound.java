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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class NBTTagCompound extends NBTTagBase<HashMap<String, NBTTagBase<?>>> {

	private HashMap<String, NBTTagBase<?>> map = new HashMap<String, NBTTagBase<?>>();

	public NBTTagCompound() {
	}

	@Override
	public NBTTagType getType() {
		return NBTTagType.COMPOUND;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, NBTTagBase<?>> getValue() {
		return (HashMap<String, NBTTagBase<?>>) map.clone();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(HashMap<String, NBTTagBase<?>> value) {
		map = (HashMap<String, NBTTagBase<?>>) value.clone();
	}

	@Override
	public NBTTagCompound writeValue(DataOutputStream stream) throws IOException {
		for (Entry<String, NBTTagBase<?>> entry : map.entrySet()) {
			byte typeId = entry.getValue().getType().getTypeId();
			stream.writeByte(typeId);
			if (typeId == 0) {
				continue;
			}
			stream.writeUTF(entry.getKey());
			entry.getValue().writeValue(stream);
		}
		return this;
	}

	@Override
	public NBTTagCompound readValue(DataInputStream stream) throws IOException {
		map.clear();
		byte typeId = 0;
		while ((typeId = stream.readByte()) != 0) {
			map.put(stream.readUTF(), NBTTagType.createTagFromType(typeId).readValue(stream));
		}
		return this;
	}

	@Override
	public Object toJava() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (Entry<String, NBTTagBase<?>> entry : getValue().entrySet()) {
			map.put(entry.getKey(), entry.getValue().toJava());
		}
		return map;
	}


	public int size() {
		return map.size();
	}

	public boolean has(String key) {
		return map.containsKey(key);
	}

	public boolean hasOfType(String key, NBTTagType type) {
		NBTTagBase<?> tag = map.get(key);
		return tag != null && tag.getType() == type;
	}

	public boolean hasOfNumberType(String key) {
		NBTTagBase<?> tag = map.get(key);
		return tag != null && tag.getType().isNumber();
	}

	public boolean hasListOfType(String key, NBTTagType type) {
		if (hasOfType(key, NBTTagType.LIST)) {
			return ((NBTTagList<?>) get(key)).getElementsType() == type;
		}
		return false;
	}

	public boolean hasListOfNumberType(String key) {
		if (hasOfType(key, NBTTagType.LIST)) {
			return ((NBTTagList<?>) get(key)).getElementsType().isNumber();
		}
		return false;
	}


	public NBTTagBase<?> get(String key) {
		return map.get(key);
	}

	public void remove(String key) {
		map.remove(key);
	}

	public void set(String key, NBTTagBase<?> value) {
		map.put(key, value);
	}


	public Number getNumber(String key, Number defaultValue) {
		if (hasOfNumberType(key)) {
			return (Number) map.get(key).getValue();
		}
		return defaultValue;
	}

	public byte getByte(String key) {
		if (hasOfNumberType(key)) {
			return getNumber(key, 0).byteValue();
		}
		return 0;
	}

	public void setByte(String key, byte value) {
		map.put(key, new NBTTagNumber<Byte>(value));
	}

	public short getShort(String key) {
		if (hasOfNumberType(key)) {
			return getNumber(key, 0).shortValue();
		}
		return 0;
	}

	public void setShort(String key, short value) {
		map.put(key, new NBTTagNumber<Short>(value));
	}

	public int getInt(String key) {
		if (hasOfNumberType(key)) {
			return getNumber(key, 0).intValue();
		}
		return 0;
	}

	public void setInt(String key, int value) {
		map.put(key, new NBTTagNumber<Integer>(value));
	}

	public long getLong(String key) {
		if (hasOfNumberType(key)) {
			return getNumber(key, 0).longValue();
		}
		return 0;
	}

	public void setLong(String key, long value) {
		map.put(key, new NBTTagNumber<Long>(value));
	}

	public float getFloat(String key) {
		if (hasOfNumberType(key)) {
			return getNumber(key, 0).floatValue();
		}
		return 0;
	}

	public void setFloat(String key, float value) {
		map.put(key, new NBTTagNumber<Float>(value));
	}

	public double getDouble(String key) {
		if (hasOfNumberType(key)) {
			return getNumber(key, 0).doubleValue();
		}
		return 0;
	}

	public void setDouble(String key, double value) {
		map.put(key, new NBTTagNumber<Double>(value));
	}

	public String getString(String key) {
		if (hasOfType(key, NBTTagType.STRING)) {
			return (String) get(key).getValue();
		}
		return "";
	}

	public void setString(String key, String value) {
		map.put(key, new NBTTagString(value));
	}

}
