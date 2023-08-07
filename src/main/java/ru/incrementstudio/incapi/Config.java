package ru.incrementstudio.incapi;

import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    private Plugin plugin;
    private File file;
    private FileConfiguration config = null;

    public Config(Plugin plugin, String path) {
        this.plugin = plugin;
        file = new File(path);
    }

    public FileConfiguration get() {
        if (config == null) {
            reload();
        }
        return config;
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
        final InputStream defConfigStream = plugin.getResource(file.getName());
        if (defConfigStream == null) {
            return;
        }
        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
    }

    public void save() {
        try {
            get().save(file);
        } catch (IOException e) { }
    }

    public void update() {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(file.getName(), false);
        }
    }
}
