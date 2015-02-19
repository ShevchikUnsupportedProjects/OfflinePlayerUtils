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

public class NBTTagString extends NBTTagBase<String> {

	protected NBTTagString() {
	}

	public NBTTagString(String string) {
		this.string = string;
	}

	private String string;

	@Override
	public NBTTagType getType() {
		return NBTTagType.STRING;
	}

	@Override
	public String getValue() {
		return string;
	}

	@Override
	public void setValue(String value) {
		string = value;
	}

	@Override
	protected NBTTagBase<String> writeValue(DataOutputStream stream) throws IOException {
		stream.writeUTF(string);
		return this;
	}

	@Override
	protected NBTTagBase<String> readValue(DataInputStream stream) throws IOException {
		string = stream.readUTF();
		return this;
	}

	@Override
	public Object toJava() {
		return string;
	}

}
