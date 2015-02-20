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

public class NBTTagByteArray extends NBTTagBase<byte[]> {

	protected NBTTagByteArray() {
	}

	public NBTTagByteArray(byte[] array) {
		this.array = array.clone();
	}

	private byte[] array;

	@Override
	public NBTTagType getType() {
		return NBTTagType.BYTEARRAY;
	}

	@Override
	public byte[] getValue() {
		return array.clone();
	}

	@Override
	public void setValue(byte[] value) {
		array = value.clone();
	}

	@Override
	public NBTTagBase<byte[]> writeValue(DataOutputStream stream) throws IOException {
		stream.writeInt(array.length);
		stream.write(array);
		return this;
	}

	@Override
	public NBTTagBase<byte[]> readValue(DataInputStream stream) throws IOException {
		array = new byte[stream.readInt()];
		stream.read(array);
		return this;
	}

	@Override
	public byte[] toJava() {
		return array.clone();
	}

}
