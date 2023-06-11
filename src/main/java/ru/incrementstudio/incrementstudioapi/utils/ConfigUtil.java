package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import ru.incrementstudio.incrementstudioapi.Config;

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

    public static void rename(Config config, String path, String newName) {
        ConfigurationSection parent = config.get().getConfigurationSection(path).getParent();
        copy(config, path, ConfigUtil.combinePath(parent.getCurrentPath(), newName));
        delete(config, path);
    }

    public static void copy(Config config, String from, String to) {
        if (config.get().isConfigurationSection(from)) {
            for (String key : config.get().getConfigurationSection(from).getKeys(false)) {
                copy(config, ConfigUtil.combinePath(from, key), ConfigUtil.combinePath(to, key));
            }
        } else {
            config.get().set(to, config.get().get(from));
        }
    }

    public static void copy(Config fromConfig, Config toConfig, String from, String to) {
        if (fromConfig.get().isConfigurationSection(from)) {
            for (String key : fromConfig.get().getConfigurationSection(from).getKeys(false)) {
                copy(fromConfig, toConfig, ConfigUtil.combinePath(from, key), ConfigUtil.combinePath(to, key));
            }
        } else {
            toConfig.get().set(to, fromConfig.get().get(from));
        }
    }

    public static void delete(Config config, String path) {
        config.get().set(path, null);
    }
}
