package ru.incrementstudio.incapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class IncPlugin extends JavaPlugin {
    protected final Logger logger = new Logger(this);
    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }
}
