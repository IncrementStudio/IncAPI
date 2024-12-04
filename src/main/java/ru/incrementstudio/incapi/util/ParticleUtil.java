package ru.incrementstudio.incapi.util;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleUtil {
    public static void createParticleArea(Location center, double range, Particle particle, int count) {
        Location particleLocation;
        for (int i = 0; i < count; i++) {
            do {
                particleLocation = new Location(center.getWorld(), RandomUtil.getDouble(-range, range), RandomUtil.getDouble(-range, range), RandomUtil.getDouble(-range, range));
                particleLocation.add(center);
            } while (particleLocation.distance(center) > range);
            center.getWorld().spawnParticle(particle, particleLocation, 1);
        }
    }

    public static void createColoredParticleArea(Location center, double range, Particle particle, int count, Particle.DustOptions dustOptions, Color color) {
        Location particleLocation;
        for (int i = 0; i < count; i++) {
            do {
                particleLocation = new Location(center.getWorld(), RandomUtil.getDouble(-range, range), RandomUtil.getDouble(-range, range), RandomUtil.getDouble(-range, range));
                particleLocation.add(center);
            } while (particleLocation.distance(center) > range);
            center.getWorld().spawnParticle(particle, particleLocation, 0,
                    color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, dustOptions);
        }
    }
}
