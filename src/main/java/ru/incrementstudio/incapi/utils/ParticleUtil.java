package ru.incrementstudio.incapi.utils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
public class ParticleUtil {
    public static void createParticleArea(Location center, double range, Particle particle, double chance, double step) {
        for (double x = center.getBlockX() - range; x <= center.getBlockX() + range; x += step) {
            for (double y = center.getBlockY() - range; y <= center.getBlockY() + range; y += step) {
                for (double z = center.getBlockZ() - range; z <= center.getBlockZ() + range; z += step) {
                    if (RandomUtil.getDouble(0, 100) <= chance) {
                        Location particleLocation = new Location(center.getWorld(), x, y, z);
                        if (particleLocation.distance(center) <= range) {
                            center.getWorld().spawnParticle(particle, particleLocation, 1);
                        }
                    }
                }
            }
        }
    }

    public static void createColoredParticleArea(Location center, double range, Particle particle, double chance, double step, Particle.DustOptions dustOptions, Color color) {
        for (double x = center.getBlockX() - range; x <= center.getBlockX() + range; x += step) {
            for (double y = center.getBlockY() - range; y <= center.getBlockY() + range; y += step) {
                for (double z = center.getBlockZ() - range; z <= center.getBlockZ() + range; z += step) {
                    if (RandomUtil.getDouble(0, 100) <= chance) {
                        Location particleLocation = new Location(center.getWorld(), x, y, z);
                        if (particleLocation.distance(center) <= range) {
                            center.getWorld().spawnParticle(particle, particleLocation, 0,
                                    color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, dustOptions);
                        }
                    }
                }
            }
        }
    }
}
