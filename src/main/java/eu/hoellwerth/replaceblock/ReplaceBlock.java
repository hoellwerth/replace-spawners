package eu.hoellwerth.replaceblock;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.hoellwerth.replaceblock.events.SpawnerListener;
import eu.hoellwerth.replaceblock.utils.LogLevels;
import eu.hoellwerth.replaceblock.utils.LogManager;
import eu.hoellwerth.replaceblock.utils.ReplaceSpawner;
import lombok.Getter;
import mc.rellox.spawnermeta.SpawnerMeta;
import mc.rellox.spawnermeta.api.APIInstance;
import mc.rellox.spawnermeta.api.events.SpawnerBreakEvent;
import mc.rellox.spawnermeta.api.events.SpawnerPostSpawnEvent;
import mc.rellox.spawnermeta.api.events.SpawnerPreSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
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
        if (api == null) {
            logManager.writeToLog("System", "SpawnerMeta has not been found!", LogLevels.ERROR);
            return;
        }

        api.register(SpawnerPreSpawnEvent.class, event -> {
            PersistentDataContainer container = new CustomBlockData(event.getSpawner().block(), ReplaceBlock.INSTANCE);
            if (container.has(new NamespacedKey(ReplaceBlock.INSTANCE, "replacedSpawner"), PersistentDataType.BOOLEAN)) {
                return;
            }

            ReplaceBlock.INSTANCE.getReplaceBlock().replaceSpawner(new ArrayList<>(), event.getSpawner().block());
            event.getGenerator().close();

            event.cancel(true);

            api.breakSpawner(event.getSpawner().block());
        });

        api.register(SpawnerBreakEvent.class, event -> {
            PersistentDataContainer container = new CustomBlockData(event.getSpawner().block(), ReplaceBlock.INSTANCE);

            if(!container.has(new NamespacedKey(ReplaceBlock.INSTANCE, "replacedSpawner"), PersistentDataType.BOOLEAN)) {
                event.cancel(true);
            }
        });
    }

    @Override
    public void onDisable() {
        logManager.writeToLog("System", "ReplaceBlock stopped", LogLevels.INFO);
    }
}
