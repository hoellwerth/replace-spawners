package eu.hoellwerth.replaceblock.utils;

import eu.hoellwerth.replaceblock.ReplaceBlock;
import mc.rellox.spawnermeta.api.spawner.IGenerator;
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

        block.breakNaturally();

        Material newMaterial = Material.getMaterial(Objects.requireNonNull(
                ReplaceBlock.INSTANCE.getConfig().getString("finalBlock")));
        if (!(newMaterial == Material.AIR)) {
            assert newMaterial != null;

            new BukkitRunnable() {
                @Override
                public void run() {
                    block.getLocation().getBlock().setType(newMaterial);

                    block.setMetadata("replacedSpawner", new org.bukkit.metadata.FixedMetadataValue(ReplaceBlock.INSTANCE, true));
                    ReplaceBlock.INSTANCE.getLogManager().writeToLog(
                            "Spawner", "Replaced a spawner at " +
                                    block.getLocation().getX() + " " +
                                    block.getLocation().getY() + " " +
                                    block.getLocation().getZ(),
                            LogLevels.INFO
                    );
                }
            }.runTaskLater(ReplaceBlock.INSTANCE, 5);
        }
    }
}
