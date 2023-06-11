package ru.incrementstudio.incrementstudioapi;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import ru.incrementstudio.incrementstudioapi.utils.ColorUtil;

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
        logger.info(ColorUtil.toColor(string));
    }

    public void warn(String string) {
        logger.warning(ColorUtil.toColor(string));
    }

    public void importantWarn(String string) {
        logger.warning(ChatColor.RED + ColorUtil.toColor(string));
    }

    public void error(String string) {
        logger.severe(ChatColor.RED + "ERROR: " + ColorUtil.toColor(string));
    }

    public void fatalError(String string) {
        logger.severe(ChatColor.DARK_RED + "FATAL ERROR: " + ColorUtil.toColor(string));
    }
}