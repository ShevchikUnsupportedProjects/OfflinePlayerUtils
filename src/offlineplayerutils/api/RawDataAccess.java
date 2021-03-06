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

package offlineplayerutils.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import offlineplayerutils.simplenbt.NBTSerializer;

public class RawDataAccess {

	/**
	 * Read raw nbt data from input stream
	 * @param input steam
	 * @return raw data
	 */
	public static Map<String, Object> getRawNBTDataFrom(InputStream inputsteam) {
		return NBTSerializer.read(inputsteam).toJava();
	}

	/**
	 * Write raw nbt data to output steam
	 * @param raw data
	 * @param output stream
	 */
	public static void writeRawNBTDataTo(HashMap<String, Object> rawdata, OutputStream stream) {
		NBTSerializer.write(NBTSerializer.fromJava(rawdata), stream);
	}

}
