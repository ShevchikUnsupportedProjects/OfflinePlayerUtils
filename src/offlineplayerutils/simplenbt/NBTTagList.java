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
import java.util.Iterator;

public class NBTTagList<T extends NBTTagBase<?>> extends NBTTagBase<ArrayList<T>> implements Iterable<T> {

	public NBTTagList() {
	}

	protected ArrayList<T> list = new ArrayList<T>();

	@Override
	public NBTTagType getType() {
		return NBTTagType.LIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<T> getValue() {
		return (ArrayList<T>) list.clone();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValue(ArrayList<T> value) {
		list = (ArrayList<T>) value.clone();
	}

	@Override
	protected NBTTagBase<ArrayList<T>> writeValue(DataOutputStream stream) throws IOException {
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
	protected NBTTagBase<ArrayList<T>> readValue(DataInputStream stream) throws IOException {
		list.clear();
		byte typeId = stream.readByte();
		int size = stream.readInt();
		for (int i = 0; i < size; i++) {
			list.add((T) NBTTagType.createTagFromType(typeId).readValue(stream));
		}
		return this;
	}

	@Override
	public ArrayList<?> toJava() {
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
		if (getElementsType() != NBTTagType.END && getElementsType() != value.getType()) {
			throw new IllegalArgumentException("Value should be type of "+value.getType());
		}
		list.set(index, value);
	}

	public void add(T value) {
		if (getElementsType() != NBTTagType.END && getElementsType() != value.getType()) {
			throw new IllegalArgumentException("Value should be type of "+value.getType());
		}
		list.add(value);
	}

	public NBTTagType getElementsType() {
		if (list.isEmpty()) {
			return NBTTagType.END;
		} else {
			return list.get(0).getType();
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new NBTTagListIterator();
	}

	private class NBTTagListIterator implements Iterator<T> {

		private int i = 0;

		@Override
		public boolean hasNext() {
			return i < list.size();
		}

		@Override
		public T next() {
			T value = list.get(i);
			i++;
			return value;
		}

	}

}
