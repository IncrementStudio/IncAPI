package ru.incrementstudio.incapi;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private Plugin plugin;

    public ConfigManager(Plugin plugin, List<String> configNames) {
        this.plugin = plugin;
        initialize(configNames);
    }

    private Map<String, Config> configs;

    public void initialize(List<String> configNames) {
        configs = new HashMap<>();
        for (String name : configNames) {
            configs.put(name, new Config(plugin, "plugins//" + plugin.getName() + "//" + name + ".yml"));
        }
    }

    public Config getConfig(String name) {
        if (configs.containsKey(name)) return configs.get(name);
        plugin.getLogger().severe("Конфиг '" + name + ".yml' не найден");
        return null;
    }

    public void reloadAll() {
        for (String config : configs.keySet()) {
            configs.get(config).reload();
        }
    }

    public void updateAll() {
        for (String config : configs.keySet()) {
            configs.get(config).update();
        }
    }

    public void saveAll() {
        for (String config : configs.keySet()) {
            configs.get(config).save();
        }
    }
}
