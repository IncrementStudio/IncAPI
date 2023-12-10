package ru.incrementstudio.incapi;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    private final Plugin plugin;
    private Map<String, Config> configs;

    public ConfigManager(Plugin plugin, int configVersion, List<String> configNames, HashMap<String, Object> importantNewKeys) {
        this.plugin = plugin;
        initialize(configVersion, configNames, importantNewKeys);
    }

    public ConfigManager(Plugin plugin, int configVersion, List<String> configNames) {
        this.plugin = plugin;
        initialize(configVersion, configNames, new HashMap<>());
    }

    public ConfigManager(Plugin plugin, List<String> configNames) {
        this.plugin = plugin;
        initialize(0, configNames, new HashMap<>());
    }

    public void initialize(int configVersion, List<String> configNames, HashMap<String, Object> importantNewKeys) {
        configs = new HashMap<>();
        for (String name : configNames) {
            configs.put(name, new Config(plugin, configVersion, "plugins//" + plugin.getName() + "//" + name + ".yml", importantNewKeys));
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
