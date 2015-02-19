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

public enum NBTTagType {

	END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTEARRAY, STRING, LIST, COMPOUND, INTARRAY;

	protected static NBTTagBase<?> createTagFromType(byte typeId) {
		switch (typeId) {
			case 0: {
				return new NBTTagEnd();
			}
			case 1: {
				return new NBTTagNumber<Byte>(NBTTagType.BYTE);
			}
			case 2: {
				return new NBTTagNumber<Short>(NBTTagType.SHORT);
			}
			case 3: {
				return new NBTTagNumber<Integer>(NBTTagType.INT);
			}
			case 4: {
				return new NBTTagNumber<Long>(NBTTagType.LONG);
			}
			case 5: {
				return new NBTTagNumber<Float>(NBTTagType.FLOAT);
			}
			case 6: {
				return new NBTTagNumber<Double>(NBTTagType.DOUBLE);
			}
			case 7: {
				return new NBTTagByteArray();
			}
			case 8: {
				return new NBTTagString();
			}
			case 9: {
				return new NBTTagList<NBTTagBase<?>>();
			}
			case 10: {
				return new NBTTagCompound();
			}
			case 11: {
				return new NBTTagIntArray();
			}
			default: {
				throw new IllegalArgumentException("Unknown nbt tag type: "+typeId);
			}
		}
	}

	public byte getTypeId() {
		return (byte) ordinal();
	}

	public boolean isNumber() {
		switch (this) {
			case BYTE:
			case SHORT:
			case INT:
			case LONG:
			case FLOAT:
			case DOUBLE: {
				return true;
			}
			default: {
				return false;
			}
		}
	}

}
