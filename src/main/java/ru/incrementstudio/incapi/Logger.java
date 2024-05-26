package ru.incrementstudio.incapi;

import org.bukkit.plugin.Plugin;
import ru.incrementstudio.incapi.utils.ColorUtil;

import java.util.logging.Level;

public class Logger extends java.util.logging.Logger {
    public Logger(Plugin plugin) {
        super(plugin.getName(), null);
        this.setParent(plugin.getServer().getLogger());
        this.setLevel(Level.ALL);
    }

    @Override
    public void log(Level level, String msg) {
        super.log(level, ColorUtil.toColor(msg));
    }

    public void error(String header, String msg) {
        severe(header + "&c:");
        severe("> " + msg);
    }
}
