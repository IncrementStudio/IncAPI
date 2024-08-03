package ru.incrementstudio.incapi.utils;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
public class ParticleUtil {
    public static void createParticleArea(Location center, double range, Particle particle, int count) {
        LocationUtil.randomInArea(center, range, count,
                location -> center.getWorld().spawnParticle(particle, location, 0)
        );
    }

    @Deprecated
    public static void createColoredParticleArea(Location center, double range, Particle particle, int count, Particle.DustOptions dustOptions, Color color) {
        LocationUtil.randomInArea(center, range, count,
                location -> center.getWorld().spawnParticle(particle, location, 0,
                                color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, dustOptions)
        );
    }

    public static void redstoneParticleArea(Location center, double range, int count, Particle.DustOptions dustOptions) {
        LocationUtil.randomInArea(center, range, count,
                location -> location.getWorld().spawnParticle(Particle.REDSTONE, location, 0, dustOptions)
        );
    }

    public static void coloredParticleArea(Location center, double range, int count, Particle particle, Color color, double brightness) {
        LocationUtil.randomInArea(center, range, count,
                location -> location.getWorld().spawnParticle(particle, location, 0,
                        color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, MathUtil.clamp(0, 1, brightness))
        );
    }
}
