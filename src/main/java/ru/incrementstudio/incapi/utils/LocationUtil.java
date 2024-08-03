package ru.incrementstudio.incapi.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LocationUtil {
    public static int getHighestBlockYInNetherAt(World world, int x, int y, int z) {
        if (y <= 0) {
            return -1;
        }
        Block block = world.getBlockAt(x, y, z);
        if (block.getType() == Material.AIR) {
            if (world.getBlockAt(x, y - 1, z).getType() == Material.AIR) {
                if (world.getBlockAt(x, y - 2, z).getType().isSolid()) {
                    return y - 1;
                }
                return getHighestBlockYInNetherAt(world, x, y - 1, z);
            }
            return getHighestBlockYInNetherAt(world, x, y - 1, z);
        }
        return getHighestBlockYInNetherAt(world,x, y - 1, z);
    }
    
    public static List<Block> getNearbyBlocks(Location center, int radius) {
        List<Block> blocks = new ArrayList<>();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    if (center.distance(new Location(center.getWorld(), x, y, z)) <= radius) {
                        Block block = center.getWorld().getBlockAt(x, y, z);
                        if (block.getType() != Material.AIR) {
                            blocks.add(block);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    public static void randomInArea(Location center, double radius, int count, Consumer<Location> func) {
        Location location;
        for (int i = 0; i < count; i++) {
            do {
                location = center.clone().add(
                        RandomUtil.getDouble(-radius, radius),
                        RandomUtil.getDouble(-radius, radius),
                        RandomUtil.getDouble(-radius, radius)
                );
            } while(center.distance(location) > radius);
            func.accept(location);
        }
    }
}
