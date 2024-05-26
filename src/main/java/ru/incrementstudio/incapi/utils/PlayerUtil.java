package ru.incrementstudio.incapi.utils;

import org.bukkit.entity.Player;
public class PlayerUtil {
    public static String getName(Player player) {
        String name;
        if (player != null) {
            name = player.getName();
        } else {
            name = "Player";
        }
        return name;
    }
}
