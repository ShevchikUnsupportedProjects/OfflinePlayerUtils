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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class NBTTagBase<T> {

	public abstract NBTTagType getType();

	public abstract T getValue();

	public abstract void setValue(T value);

	protected abstract NBTTagBase<T> writeValue(DataOutputStream stream) throws IOException;

	protected abstract NBTTagBase<T> readValue(DataInputStream stream) throws IOException;

	@SuppressWarnings("unchecked")
	public NBTTagBase<?> clone() {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			writeValue(new DataOutputStream(stream));
			return (NBTTagBase<T>) NBTTagType.createTagFromType(getType().getTypeId()).readValue(new DataInputStream(new ByteArrayInputStream(stream.toByteArray())));
		} catch (IOException e) {
		}
		return null;
	}

	public abstract Object toJava();

	@Override
	public String toString() {
		return String.valueOf(getValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof NBTTagBase)) {
			return false;
		}
		NBTTagBase<T> othertag = (NBTTagBase<T>) other;
		return getValue().equals(othertag.getValue());
	}

	@Override
	public int hashCode() {
		return getValue().hashCode();
	}

}
