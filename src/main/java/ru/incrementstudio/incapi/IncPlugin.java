package ru.incrementstudio.incapi;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class IncPlugin extends JavaPlugin {
    protected final Logger logger = new Logger(this);

    @Override
    public Logger getLogger() {
        return logger;
    }

}
