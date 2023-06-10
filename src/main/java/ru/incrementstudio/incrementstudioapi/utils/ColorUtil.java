package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {
    public static String toColor(String string) {
        String coloredString = null;
        if (string != null) {
            coloredString = ChatColor.translateAlternateColorCodes('&', string);
        }
        return coloredString;
    }

    public static List<String> toColor(List<String> list) {
        List<String> coloredList = new ArrayList<>();
        if (list != null) {
            for (String string : list) {
                coloredList.add(toColor(string));
            }
        }
        return coloredList;
    }
}
