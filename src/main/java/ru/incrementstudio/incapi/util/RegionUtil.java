package ru.incrementstudio.incapi.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegionUtil {
    public static RegionManager getRegionManager(World world) {
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
    }
    public static boolean isRegionsExistsAt(World world, int x, int y, int z) {
        try {
            return getRegionOnLocation(new Location(world, x, y, z)) != null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Location getCenter(ProtectedRegion region, World world, boolean setHighestBlock) {
        double locationX = (region.getMinimumPoint().getX() + region.getMaximumPoint().getX()) / 2.0;
        double locationY = (region.getMinimumPoint().getY() + region.getMaximumPoint().getY()) / 2.0;
        double locationZ = (region.getMinimumPoint().getZ() + region.getMaximumPoint().getZ()) / 2.0;
        if (setHighestBlock) {
            locationY = world.getHighestBlockYAt((int) locationX, (int) locationZ);
            return new Location(world, locationX, locationY, locationZ);
        }
        return new Location(world, locationX, locationY, locationZ);
    }
    public static List<ProtectedRegion> getRegionsInWorld(World world) {
        List<ProtectedRegion> readyList = new ArrayList<>();
        if (WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(world)).getRegions().isEmpty()) return readyList;
        for (Map.Entry<String, ProtectedRegion> map: WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(world)).getRegions().entrySet()) {
            if (!map.getValue().isPhysicalArea()) continue;
            readyList.add(map.getValue());
        }
        return readyList;
    }

    public static ProtectedRegion getRegionOnPlayerLocation(Player player) {
        return getRegionOnLocation(player.getLocation());
    }

    public static ProtectedRegion getRegionOnLocation(Location location) {
        List<ProtectedRegion> regions = new ArrayList<>(
                WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(location.getWorld())).getApplicableRegions(
                        BlockVector3.at(
                                location.getX(),
                                location.getY(),
                                location.getZ()))
                        .getRegions()
        );
        for (ProtectedRegion protectedRegion: regions) {
            if (protectedRegion.isPhysicalArea()) return protectedRegion;
        }
        return null;
    }
}
