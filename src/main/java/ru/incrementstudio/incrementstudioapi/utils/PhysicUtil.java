package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class PhysicUtil {
    public static void pushEntity(Entity entity, Location center, double power) {
        Location direction = center.clone();
        direction.subtract(entity.getLocation());
        Vector normalizedDirection = direction.toVector().normalize();
        entity.setVelocity(normalizedDirection.multiply(power * -1));
    }
}
