package ru.incrementstudio.incairdrops;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class HologrammUtil {
    public static ArmorStand spawnHologramm(Location location, String text) {
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
        return armorStand;
    }
}
