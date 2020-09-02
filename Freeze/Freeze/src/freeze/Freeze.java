package freeze;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Freeze implements Listener {
	private File dataFolder;
	public static File isFrozenFile;
	public static YamlConfiguration isFrozenYml;

	public Freeze(FreezeMain freezeMain) {
		freezeMain.getServer().getPluginManager().registerEvents(this, freezeMain);
		dataFolder = FreezeMain.plugin.getDataFolder();
		isFrozenFile = new File(dataFolder, "freeze.yml");
		isFrozenYml = YamlConfiguration.loadConfiguration(isFrozenFile);
	}

	@EventHandler
	public void freezePlayer(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		UUID id = p.getUniqueId();
		String uuid = id.toString();

		if (isFrozenYml.contains(uuid)) {
			if (isFrozenYml.getBoolean(uuid)) {
				if (p.getAllowFlight() == false) {
					p.setAllowFlight(true);
				}
				p.setFlying(true);
				e.setCancelled(true);
			}
		}
	}
}
