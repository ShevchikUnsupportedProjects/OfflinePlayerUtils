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

public class NBTTagIntArray extends NBTTagBase<int[]> {

	protected NBTTagIntArray() {
	}

	public NBTTagIntArray(int[] array) {
		this.array = array.clone();
	}

	private int[] array;

	@Override
	public NBTTagType getType() {
		return NBTTagType.INTARRAY;
	}

	@Override
	public int[] getValue() {
		return array.clone();
	}

	@Override
	public void setValue(int[] value) {
		array = value.clone();
	}

	@Override
	public NBTTagBase<int[]> writeValue(DataOutputStream stream) throws IOException {
		stream.writeInt(array.length);
		for (int i = 0; i < array.length; i++) {
			stream.writeInt(i);
		}
		return this;
	}

	@Override
	public NBTTagBase<int[]> readValue(DataInputStream stream) throws IOException {
		array = new int[stream.readInt()];
		for (int i = 0; i < array.length; i++) {
			array[i] = stream.readInt();
		}
		return this;
	}

	@Override
	public Object toJava() {
		return array.clone();
	}

}
