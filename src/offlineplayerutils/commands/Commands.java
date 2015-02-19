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

package offlineplayerutils.commands;

import offlineplayerutils.api.ExtendedOfflinePlayer;
import offlineplayerutils.api.LocationInfo;
import offlineplayerutils.api.OfflinePlayerUtilsAPI;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player executor = (Player) sender;
		if (sender.hasPermission("opu.use")) {
			if (args.length >= 3) {
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
				ExtendedOfflinePlayer eplayer = OfflinePlayerUtilsAPI.getExtendedOfflinePlayer(player);
				if (args[0].equalsIgnoreCase("get")) {
					switch (args[2]) {
						case "inventory": {
							executor.getInventory().setContents(eplayer.getInventoryContents());
							executor.sendMessage("Applied "+player.getName()+" inventory to you");
							return true;
						}
						case "armor": {
							executor.getInventory().setArmorContents(eplayer.getArmorContents());
							executor.sendMessage("Applied "+player.getName()+" armor to you");
							return true;
						}
						case "location": {
							LocationInfo linfo = eplayer.getLocation();
							if (linfo.getLocation() != null) {
								executor.teleport(linfo.getLocation());
								executor.sendMessage("Teleported you to "+player.getName()+" location");
							} else {
								executor.sendMessage(player.getName()+" location: "+linfo);
							}
							return true;
						}
						case "health": {
							executor.sendMessage(player.getName()+" health: "+eplayer.getHealth());
							return true;
						}
						case "maxhealth": {
							executor.sendMessage(player.getName()+" maxhealth: "+eplayer.getMaxHealth());
							return true;
						}
						case "food": {
							executor.sendMessage(player.getName()+" food: "+eplayer.getFoodLevel());
							return true;
						}
						case "gamemode": {
							executor.sendMessage(player.getName()+" gamemode: "+eplayer.getGameMode());
							return true;
						}
						case "xp": {
							executor.sendMessage(player.getName()+" xp percent: "+eplayer.getExp());
							return true;
						}
						case "level": {
							executor.sendMessage(player.getName()+" level: "+eplayer.getLevel());
							return true;
						}
						case "raw": {
							executor.sendMessage(eplayer.getRawNBTData().toString());
							return true;
						}
					}
				} else if (args[0].equalsIgnoreCase("set")) {
					switch (args[2]) {
						case "inventory": {
							eplayer.setInventoryContents(executor.getInventory().getContents());
							executor.sendMessage("Applied your inventory to "+player.getName());
							return true;
						}
						case "armor": {
							eplayer.setArmorContents(executor.getInventory().getArmorContents());
							executor.sendMessage("Applied your armor to "+player.getName());
							return true;
						}
						case "location": {
							eplayer.setLocation(new LocationInfo(executor.getLocation()));
							executor.sendMessage("Applied your location to "+player.getName());
							return true;
						}
						case "health": {
							if (args.length < 4) {
								return false;
							}
							eplayer.setHealth(Float.parseFloat(args[3]));
							executor.sendMessage("Set "+player.getName()+" health to "+args[3]);
							return true;
						}
						case "maxhealth": {
							if (args.length < 4) {
								return false;
							}
							eplayer.setMaxHealth(Float.parseFloat(args[3]));
							executor.sendMessage("Set "+player.getName()+" maxhealth to "+args[3]);
							return true;
						}
						case "food": {
							if (args.length < 4) {
								return false;
							}
							eplayer.setFoodLevel(Integer.parseInt(args[3]));
							executor.sendMessage("Set "+player.getName()+" food to "+args[3]);
							return true;
						}
						case "gamemode": {
							eplayer.setGameMode(executor.getGameMode());
							executor.sendMessage("Applied your gamemode to "+player.getName());
							return true;
						}
						case "xp": {
							if (args.length < 4) {
								return false;
							}
							eplayer.setExp(executor.getExp());
							executor.sendMessage("Applied your xp percent to "+player.getName());
							return true;
						}
						case "level": {
							if (args.length < 4) {
								return false;
							}
							eplayer.setLevel(executor.getLevel());
							executor.sendMessage("Applied your level to "+player.getName());
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
