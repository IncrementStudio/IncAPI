package ru.incrementstudio.incapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Config {
    private final Plugin plugin;
    private final int configVersion;
    private final File file;
    private final String fileName;
    private FileConfiguration config = null;

    public Config(Plugin plugin, int configVersion, String path) {
        this.plugin = plugin;
        this.configVersion = configVersion;
        this.file = new File(path);
        this.fileName = file.getPath().substring(file.getPath().indexOf("plugins" + File.separator + plugin.getName() + File.separator) + ("plugins" + File.separator + plugin.getName() + File.separator).length());
        reload();
    }

    public FileConfiguration get() {
        return config;
    }

    public void reload() {
        update();
        config = YamlConfiguration.loadConfiguration(file);
        if (config.getInt("config-version") < 1) {
            plugin.getLogger().severe("Конфиг " + file.getName() + " не содержит поля config-version или его значение < 1! Загружаем стандартный...");
            file.delete();
            update();
        }
        int serverConfigVersion = config.getInt("config-version");
        if (serverConfigVersion < configVersion) {
            FileConfiguration defaultConfiguration = YamlConfiguration.loadConfiguration(
                    new InputStreamReader(
                            Objects.requireNonNull(plugin.getResource(fileName.replace(File.separator, "/")))));
            for (String key : defaultConfiguration.getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defaultConfiguration.get(key));
                }
            }
            config.set("config-version", configVersion);
            save();
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
