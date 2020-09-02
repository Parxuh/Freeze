package freezeCommands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import freeze.Freeze;
import freeze.FreezeMain;

public class UnFreezeCommand implements CommandExecutor {

	public UnFreezeCommand() {
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (!(sender instanceof Player)) {
			sender.sendMessage("Console cannot use this command");
			return true;
		}

		Player s = (Player) sender;

		if (!FreezeMain.hasPerms(s)) {
			s.sendMessage("Only Moderators can use that command.");
			return true;
		}

		if (args.length == 0) {
			s.sendMessage(ChatColor.WHITE + "usage: " + ChatColor.GRAY + "/" + ChatColor.AQUA + "freeze "
					+ ChatColor.GRAY + "<player>" + ChatColor.WHITE + " - freezes a player in place");
			s.sendMessage(ChatColor.WHITE + "usage: " + ChatColor.GRAY + "/" + ChatColor.AQUA + "unfreeze "
					+ ChatColor.GRAY + "<player>" + ChatColor.WHITE + " - unfreezes a player");
			return true;
		}

		if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			Player p = Bukkit.getPlayer(args[0]);
			UUID id = target.getUniqueId();
			String uuid = id.toString();
			
			if (!target.isOnline()) {
				s.sendMessage(ChatColor.RED + "You cannot unfreeze an offline player");
				return true;
			}

			if (Freeze.isFrozenYml.contains(uuid)) {
				if (Freeze.isFrozenYml.getBoolean(uuid) == false) {
					s.sendMessage(ChatColor.AQUA + "This player is not frozen.");
					return true;
				}
			}
				Freeze.isFrozenYml.set(uuid, false);
				FreezeMain.saveFile(Freeze.isFrozenYml, Freeze.isFrozenFile);
				if (p.getGameMode() == GameMode.SURVIVAL) {
					p.setAllowFlight(false);
				}
				p.setFlying(false);
				s.sendMessage(
						args[0] + ChatColor.GRAY + " is no longer " + ChatColor.AQUA + "frozen" + ChatColor.GRAY + ".");
				Bukkit.broadcast(ChatColor.RED + "[!] " + ChatColor.GRAY + s.getName() + " unfroze " + args[0],
						"freeze.notif");
				return true;
		} else {
			s.sendMessage(ChatColor.RED + "That player has not played before.");
			return true;
		}
	}
}
