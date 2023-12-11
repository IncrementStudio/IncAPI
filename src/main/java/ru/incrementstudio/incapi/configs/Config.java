package ru.incrementstudio.incapi.configs;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Config {
    private final Plugin plugin;
    private int configVersion;
    private final File file;
    private final String fileName;
    private FileConfiguration config = null;

    public Config(Plugin plugin, String path) {
        this.plugin = plugin;
        this.file = new File(path);
        this.fileName = file.getPath().substring(file.getPath().indexOf("plugins" + File.separator + plugin.getName() + File.separator) + ("plugins" + File.separator + plugin.getName() + File.separator).length());
        reload();
    }

    public Config(Plugin plugin, int configVersion, String path) {
        this.plugin = plugin;
        this.configVersion = configVersion;
        this.file = new File(path);
        this.fileName = file.getPath().substring(file.getPath().indexOf("plugins" + File.separator + plugin.getName() + File.separator) + ("plugins" + File.separator + plugin.getName() + File.separator).length());
        reload();
    }

    public Config(Plugin plugin, int configVersion, String path, HashMap<String, Object> importantNewKeys) {
        this.plugin = plugin;
        this.configVersion = configVersion;
        this.file = new File(path);
        this.fileName = file.getPath().substring(file.getPath().indexOf("plugins" + File.separator + plugin.getName() + File.separator) + ("plugins" + File.separator + plugin.getName() + File.separator).length());
        reload(importantNewKeys);
    }

    public FileConfiguration get() {
        return config;
    }

    public void reload() {
        update();
        config = YamlConfiguration.loadConfiguration(file);
        if (configVersion > 0 && config.getInt("config-version") > 0) {
            int serverConfigVersion = config.getInt("config-version");
            if (serverConfigVersion < configVersion) {
                setDefaultValues();
                config.set("config-version", configVersion);
                save();
            }
        }
    }

    private void reload(HashMap<String, Object> importantNewKeys) {
        update();
        config = YamlConfiguration.loadConfiguration(file);
        if (configVersion > 0 && config.getInt("config-version") > 0) {
            int serverConfigVersion = config.getInt("config-version");
            if (serverConfigVersion < configVersion) {
                setDefaultValues();
                for (Map.Entry<String, Object> keys: importantNewKeys.entrySet()) {
                    config.set(keys.getKey(), keys.getValue());
                }
                config.set("config-version", configVersion);
                save();
            }
        }
    }

    private void setDefaultValues() {
        FileConfiguration defaultConfiguration = YamlConfiguration.loadConfiguration(
                new InputStreamReader(
                        Objects.requireNonNull(plugin.getResource(fileName.replace(File.separator, "/")))));
        for (String key : defaultConfiguration.getKeys(true)) {
            if (!config.contains(key)) {
                config.set(key, defaultConfiguration.get(key));
            }
        }
    }

    public void save() {
        try {
            get().save(file);
        } catch (IOException ignored) {}
    }

    public void update() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }
    }
}
