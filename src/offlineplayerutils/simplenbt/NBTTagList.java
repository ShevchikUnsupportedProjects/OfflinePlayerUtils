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
import java.util.ArrayList;

public class NBTTagList<T extends NBTTagBase<?>> extends NBTTagBase<ArrayList<NBTTagBase<?>>> {

	protected NBTTagList() {
	}

	private ArrayList<T> list = new ArrayList<T>();

	@Override
	public NBTTagType getType() {
		return NBTTagType.LIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<NBTTagBase<?>> getValue() {
		return (ArrayList<NBTTagBase<?>>) list.clone();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(ArrayList<NBTTagBase<?>> value) {
		list = (ArrayList<T>) value.clone();
	}

	@Override
	protected NBTTagBase<ArrayList<NBTTagBase<?>>> writeValue(DataOutputStream stream) throws IOException {
		if (list.isEmpty()) {
			stream.writeByte(0);
		} else {
			stream.writeByte(list.get(0).getType().getTypeId());
		}
		stream.writeInt(list.size());
		for (T tag : list) {
			tag.writeValue(stream);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected NBTTagBase<ArrayList<NBTTagBase<?>>> readValue(DataInputStream stream) throws IOException {
		list.clear();
		byte typeId = stream.readByte();
		int size = stream.readInt();
		for (int i = 0; i < size; i++) {
			list.add((T) NBTTagType.createTagFromType(typeId).readValue(stream));
		}
		return this;
	}

	@Override
	public Object toJava() {
		ArrayList<Object> list = new ArrayList<Object>();
		for (NBTTagBase<?> value : getValue()) {
			list.add(value.toJava());
		}
		return list;
	}


	public int size() {
		return list.size();
	}

	public T get(int index) {
		return list.get(index);
	}

	public void remove(int index) {
		list.remove(index);
	}

	public void set(int index, T value) {
		list.set(index, value);
	}

	public void add(T value) {
		list.add(value);
	}

	public NBTTagType getElementsType() {
		if (list.isEmpty()) {
			return NBTTagType.END;
		} else {
			return list.get(0).getType();
		}
	}

}
