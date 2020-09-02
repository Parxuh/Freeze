package freeze;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import freezeCommands.FreezeCommand;
import freezeCommands.UnFreezeCommand;

public class FreezeMain extends JavaPlugin implements Listener {
	public static String version = "1.0.0";
	Logger logger = Bukkit.getLogger();
	public static FreezeMain plugin;

	public void onEnable() {
		plugin = this;

		this.logger.info("FreezePlugin " + version + " has been enabled.");
		getServer().getPluginManager().registerEvents(this, this);

		new Freeze(this);

		this.getCommand("freeze").setExecutor(new FreezeCommand());
		this.getCommand("unfreeze").setExecutor(new UnFreezeCommand());

	}

	public void onDisable() {
		this.logger.info("FreezePlugin " + version + " has been disabled.");
	}

	public static boolean hasPerms(Player p) {
		return p.hasPermission("freeze.use");
	}

	public boolean notificationsEnabled(Player p) {
		return p.hasPermission("freeze.notif");
	}
	
	public static void saveFile(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
