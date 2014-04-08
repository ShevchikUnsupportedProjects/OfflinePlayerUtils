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

package offlineplayerutils.internal;

import org.bukkit.Server;

public class InternalAccessor {

	private static InternalAccessor instance;
	public static InternalAccessor getInstance() {
		return instance;
	}
	
	private String version = "v1_7_R2";
	private String server = "bukkit";

	public static void initialize(Server server) {
		instance = new InternalAccessor();
		String packageName = server.getClass().getPackage().getName();
		instance.version = packageName.substring(packageName.lastIndexOf('.') + 1);
	}

	public InventoryDataInterface newInvnetoryData() {
		return (InventoryDataInterface) createObject(InventoryDataInterface.class, "InventoryData");
	}

	private Object createObject(Class<? extends Object> assignableClass, String className) {
		try {
			Class<?> internalClass = Class.forName("offlineplayerutils.internal." + version + "." + server + "." + className);
			if (assignableClass.isAssignableFrom(internalClass)) {
				return internalClass.getConstructor().newInstance();
			}
		}
		catch (Exception e) {
		}

		return null;
	}

}
