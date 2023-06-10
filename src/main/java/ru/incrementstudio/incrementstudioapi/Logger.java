package ru.incrementstudio.incrementstudioapi;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class Logger {
    private java.util.logging.Logger logger;
    public Logger(Plugin plugin) {
        this.logger = plugin.getLogger();
    }

    public void log(Level level, String string) {
        logger.log(level, string);
    }

    public void info(String string) {
        logger.info(string);
    }

    public void warn(String string) {
        logger.warning(string);
    }

    public void importantWarn(String string) {
        logger.warning(ChatColor.RED + string);
    }

    public void error(String string) {
        logger.severe(ChatColor.RED + "ERROR: " + string);
    }

    public void fatalError(String string) {
        logger.severe(ChatColor.DARK_RED + "FATAL ERROR: " + string);
    }
}