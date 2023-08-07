package ru.incrementstudio.incapi.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramUtil {
    public static ArmorStand spawnHologram(Plugin plugin, Location location, String text) {
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setCollidable(false);
        armorStand.setInvulnerable(true);
        armorStand.setSilent(true);
        armorStand.setSmall(true);
        armorStand.setFireTicks(0);
        armorStand.setVisible(false);
        armorStand.setCanMove(false);
        armorStand.setGravity(false);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (armorStand.isDead()) {
                    cancel();
                    return;
                }
                armorStand.teleport(location);
            }
        }.runTaskTimer(plugin, 5L, 5L);
        return armorStand;
    }
}
