package ru.incrementstudio.incapi.utils;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

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
}
