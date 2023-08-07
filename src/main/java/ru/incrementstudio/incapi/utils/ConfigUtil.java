package ru.incrementstudio.incapi.utils;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {
    public static String combinePath(String... elements) {
        String path = "";
        for (int i = 0; i < elements.length; i++) {
            if (i > 0) path += ".";
            path += elements[i];
        }
        return path;
    }

    public static String combinePath(char ch, String... elements) {
        String path = "";
        for (int i = 0; i < elements.length; i++) {
            if (i > 0) path += ch;
            path += elements[i];
        }
        return path;
    }

    public static void rename(FileConfiguration config, String path, String newName) {
        copy(config, path, path.split("\\.").length > 1 ? ConfigUtil.combinePath(path.substring(0, path.lastIndexOf('.')), newName) : newName);
        delete(config, path);
    }

    public static void copy(FileConfiguration config, String from, String to) {
        if (config.isConfigurationSection(from)) {
            for (String key : config.getConfigurationSection(from).getKeys(false)) {
                copy(config, ConfigUtil.combinePath(from, key), ConfigUtil.combinePath(to, key));
            }
        } else {
            config.set(to, config.get(from));
        }
    }

    public static void copy(FileConfiguration fromConfig, FileConfiguration toConfig, String from, String to) {
        if (fromConfig.isConfigurationSection(from)) {
            toConfig.createSection(to);
            for (String key : fromConfig.getConfigurationSection(from).getKeys(false)) {
                copy(fromConfig, toConfig, ConfigUtil.combinePath(from, key), ConfigUtil.combinePath(to, key));
            }
        } else {
            toConfig.set(to, fromConfig.get(from));
        }
    }

    public static void delete(FileConfiguration config, String path) {
        config.set(path, null);
    }
}
