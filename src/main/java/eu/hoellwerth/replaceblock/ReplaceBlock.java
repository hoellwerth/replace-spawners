package eu.hoellwerth.replaceblock;

import eu.hoellwerth.replaceblock.events.SpawnerListener;
import eu.hoellwerth.replaceblock.utils.LogLevels;
import eu.hoellwerth.replaceblock.utils.LogManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ReplaceBlock extends JavaPlugin {
    public static ReplaceBlock INSTANCE;

    private LogManager logManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        INSTANCE = this;
        logManager = new LogManager();

        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
    }

    @Override
    public void onDisable() {
        logManager.writeToLog("System", "ReplaceBlock stopped", LogLevels.INFO);
    }
}
