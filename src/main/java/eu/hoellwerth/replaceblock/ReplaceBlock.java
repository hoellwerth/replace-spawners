package eu.hoellwerth.replaceblock;

import eu.hoellwerth.replaceblock.events.SpawnerListener;
import eu.hoellwerth.replaceblock.utils.LogLevels;
import eu.hoellwerth.replaceblock.utils.LogManager;
import eu.hoellwerth.replaceblock.utils.ReplaceSpawner;
import lombok.Getter;
import mc.rellox.spawnermeta.SpawnerMeta;
import mc.rellox.spawnermeta.api.APIInstance;
import mc.rellox.spawnermeta.api.events.SpawnerBreakEvent;
import mc.rellox.spawnermeta.api.events.SpawnerPostSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public final class ReplaceBlock extends JavaPlugin {
    public static ReplaceBlock INSTANCE;

    private LogManager logManager;
    private ReplaceSpawner replaceBlock;

    @Override
    public void onEnable() {
        replaceBlock = new ReplaceSpawner();
        saveDefaultConfig();

        INSTANCE = this;
        logManager = new LogManager();

        getServer().getPluginManager().registerEvents(new SpawnerListener(), this);

        SpawnerMeta sm = (SpawnerMeta) Bukkit.getPluginManager().getPlugin("SpawnerMeta");
        APIInstance api = Objects.requireNonNull(sm).getAPI();

        api.register(SpawnerPostSpawnEvent.class, event -> {
            ReplaceBlock.INSTANCE.getReplaceBlock().replaceSpawner(event.entities, event.getSpawner().block());
        });

        api.register(SpawnerBreakEvent.class, event -> {
            if(!event.getSpawner().block().hasMetadata("replacedSpawner")) {
                event.cancel(true);
            }
        });
    }

    @Override
    public void onDisable() {
        logManager.writeToLog("System", "ReplaceBlock stopped", LogLevels.INFO);
    }
}
