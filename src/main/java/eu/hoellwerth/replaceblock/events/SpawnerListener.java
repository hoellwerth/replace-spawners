package eu.hoellwerth.replaceblock.events;

import eu.hoellwerth.replaceblock.ReplaceBlock;
import eu.hoellwerth.replaceblock.utils.LogLevels;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class SpawnerListener implements Listener {

    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        LivingEntity entity = (LivingEntity) event.getEntity();
        entity.setMetadata("spawner", new org.bukkit.metadata.FixedMetadataValue(ReplaceBlock.INSTANCE, true));
        entity.setHealth(0);

        Block block = event.getSpawner().getBlock();
        block.setType(Material.AIR);
        block.getState().update();
        block.setType(Material.SPAWNER);
        block.getState().update();
        ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                "Spawner", "Replaced a spawner at " +
                        event.getSpawner().getLocation().getX() + " " +
                        event.getSpawner().getLocation().getY() + " " +
                        event.getSpawner().getLocation().getZ(),
                LogLevels.INFO
        );
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!event.getEntity().hasMetadata("spawner")) {
            return;
        }
        event.setDroppedExp(0);
        event.getDrops().clear();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType() == Material.SPAWNER) {
            ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                    "Spawner", event.getPlayer().getDisplayName() + " place a spawner at " +
                            event.getBlock().getLocation().getX() + " " +
                            event.getBlock().getLocation().getY() + " " +
                            event.getBlock().getLocation().getZ(),
                    LogLevels.INFO
            );
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.SPAWNER) {
            ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                    "Spawner", event.getPlayer().getDisplayName() + " broke a spawner at " +
                            event.getBlock().getLocation().getX() + " " +
                            event.getBlock().getLocation().getY() + " " +
                            event.getBlock().getLocation().getZ(),
                    LogLevels.INFO
            );
        }
    }
}
