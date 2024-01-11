package eu.hoellwerth.replaceblock.events;

import eu.hoellwerth.replaceblock.ReplaceBlock;
import eu.hoellwerth.replaceblock.utils.LogLevels;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class SpawnerListener implements Listener {

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        List<Entity> entities = new ArrayList<>();
        entities.add(event.getEntity());

        ReplaceBlock.INSTANCE.getReplaceBlock().replaceSpawner(entities, event.getSpawner().getBlock());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!event.getEntity().hasMetadata("spawnedByASpawner")) {
            return;
        }
        event.setDroppedExp(0);
        event.getDrops().clear();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) {
            return;
        }

        ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                "Spawner", event.getPlayer().getDisplayName() + " placed a spawner at " +
                        event.getBlock().getLocation().getX() + " " +
                        event.getBlock().getLocation().getY() + " " +
                        event.getBlock().getLocation().getZ(),
                LogLevels.INFO
        );

        event.getBlock().setMetadata("replacedSpawner", new org.bukkit.metadata.FixedMetadataValue(ReplaceBlock.INSTANCE, true));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.SPAWNER) {
            return;
        }

        if (!event.getBlock().hasMetadata("replacedSpawner")) {
            event.setCancelled(true);
            return;
        }

        ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                "Spawner", event.getPlayer().getDisplayName() + " broke a spawner at " +
                        event.getBlock().getLocation().getX() + " " +
                        event.getBlock().getLocation().getY() + " " +
                        event.getBlock().getLocation().getZ(),
                LogLevels.INFO
        );
    }
}
