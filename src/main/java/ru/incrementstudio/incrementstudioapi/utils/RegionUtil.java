package ru.incrementstudio.incrementstudioapi.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.World;
import ru.incrementstudio.incrementstudioapi.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
public class RegionUtil {
    public static RegionManager getRegionManager(World world) {
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
    }
    public static boolean isRegionsExistsAt(World world, int x, int y, int z) {
        try {
            return Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(world))).getApplicableRegions(BlockVector3.at(x, y, z)).size() > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<ProtectedRegion> getRegionsInWorld(World world) {
        List<ProtectedRegion> readyList = new ArrayList<>();
        if (WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(world)).getRegions().size() == 0) return readyList;
        for (Map.Entry<String, ProtectedRegion> map: WorldGuard.getInstance().getPlatform().getRegionContainer().get(new BukkitWorld(world)).getRegions().entrySet()) {
            if (!map.getValue().isPhysicalArea()) continue;
            readyList.add(map.getValue());
        }
        return readyList;
    }
}
