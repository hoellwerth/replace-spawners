package eu.hoellwerth.replaceblock.utils;

import eu.hoellwerth.replaceblock.ReplaceBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;

public class ReplaceSpawner {
    public void replaceSpawner(List<Entity> entities, Block block) {
        entities.forEach(entity -> {
            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.setMetadata("spawnedByASpawner", new org.bukkit.metadata.FixedMetadataValue(ReplaceBlock.INSTANCE, true));
            livingEntity.setHealth(0);
        });

        block.setType(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(
                ReplaceBlock.INSTANCE.getConfig().getString("interimBlock")))));
        block.getState().update();
        new BukkitRunnable() {
            @Override
            public void run() {
                block.setType(Objects.requireNonNull(Material.getMaterial(Objects.requireNonNull(
                        ReplaceBlock.INSTANCE.getConfig().getString("finalBlock")
                ))));
                block.getState().update();
            }
        }.runTaskLater(ReplaceBlock.INSTANCE, 20);

        block.setMetadata("replacedSpawner", new org.bukkit.metadata.FixedMetadataValue(ReplaceBlock.INSTANCE, true));
        ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                "Spawner", "Replaced a spawner at " +
                        block.getLocation().getX() + " " +
                        block.getLocation().getY() + " " +
                        block.getLocation().getZ(),
                LogLevels.INFO
        );
    }
}
