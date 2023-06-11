package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
public class ParticleUtil {
    public static void createParticleArea(Location center, double range, Particle particle) {
        for (double x = center.getBlockX() - range; x <= center.getBlockX() + range; x += 0.5) {
            for (double y = center.getBlockY() - range; y <= center.getBlockY() + range; y += 0.5) {
                for (double z = center.getBlockZ() - range; z <= center.getBlockZ() + range; z += 0.5) {
                    if (RandomUtil.getInt(0, 100) < 10) {
                        Location particleLocation = new Location(center.getWorld(), x, y, z);
                        if (particleLocation.distance(center) <= range) {
                            center.getWorld().spawnParticle(particle, particleLocation, 5);
                        }
                    }
                }
            }
        }
    }
}
