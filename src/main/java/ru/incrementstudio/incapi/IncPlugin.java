package ru.incrementstudio.incapi;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class IncPlugin extends JavaPlugin {
    private static IncPlugin instance;
    protected final Logger logger = new Logger(this);

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
    public static IncPlugin getInstance() {
        return instance;
    }

}
