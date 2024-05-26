package ru.incrementstudio.incapi;

import org.bukkit.plugin.java.JavaPlugin;
import ru.incrementstudio.incapi.configs.ConfigManager;

import java.util.List;

public abstract class IncPlugin extends JavaPlugin {
    protected final Logger logger = new Logger(this);
    @Override
    public java.util.logging.Logger getLogger() {
        return logger;
    }
    public Logger logger() {
        return logger;
    }

    protected ConfigManager configManager;
    public ConfigManager getConfigManager() {
        return configManager;
    }

    public IncPlugin(List<String> configs) {
        configManager = new ConfigManager(this, configs);
    }
}
