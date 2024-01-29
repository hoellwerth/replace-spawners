package eu.hoellwerth.replaceblock.utils;

import com.jeff_media.customblockdata.CustomBlockData;
import eu.hoellwerth.replaceblock.ReplaceBlock;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
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

                    PersistentDataContainer container = new CustomBlockData(block, ReplaceBlock.INSTANCE);
                    container.set(new NamespacedKey(ReplaceBlock.INSTANCE, "replacedSpawner"), PersistentDataType.BOOLEAN, true);

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
