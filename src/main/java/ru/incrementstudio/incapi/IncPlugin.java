package ru.incrementstudio.incapi;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class IncPlugin extends JavaPlugin {
    protected final Logger logger = new Logger(this);
    @Override
    public Logger getLogger() {
        return logger;
    }

    private boolean enabling = false;
    public boolean isEnabling() {
        return enabling;
    }
    private boolean disabling = false;
    public boolean isDisabling() {
        return disabling;
    }

    @Override
    public final void onEnable() {
        enabling = true;
        onPluginEnable();
        enabling = false;
    }

    @Override
    public final void onDisable() {
        disabling = true;
        onPluginDisable();
        disabling = false;
    }

    public abstract void onPluginEnable();
    public abstract void onPluginDisable();
}
