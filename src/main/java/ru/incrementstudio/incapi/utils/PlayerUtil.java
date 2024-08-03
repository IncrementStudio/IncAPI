package ru.incrementstudio.incapi.utils;

import org.bukkit.entity.Player;

public class PlayerUtil {
    public static String getName(Player player, String def) {
        if (player != null)
            return player.getName();
        return def;
    }
}
