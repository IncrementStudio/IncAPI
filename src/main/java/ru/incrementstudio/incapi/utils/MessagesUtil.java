package ru.incrementstudio.incapi.utils;

import org.bukkit.plugin.Plugin;
import ru.incrementstudio.incapi.Logger;

public class MessagesUtil {
    public static void pluginMessage(Plugin plugin, Logger logger, MessageType messageType) {
        switch (messageType) {
            case ENABLING:
                logger.info("");
                logger.info("&8-&7-&f-----&7-&8-");
                for (String line : new String[] {
                        "&b" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion(),
                        "",
                        "&fServer info&7:",
                        "&fCore&7: &e" + plugin.getServer().getName() + " &7| &fRequired&7: &ePaper or its forks",
                        "&fVersion&7: &e" + plugin.getServer().getVersion() + " &7| &fRequired&7: &e" + plugin.getDescription().getAPIVersion() + "+",
                        "&fAddress&7: &e" + plugin.getServer().getIp() + "&7:&e" + plugin.getServer().getPort()
                }) {
                    logger.info("&8-&7-&f- | " + line);
                }
                logger.info("&8-&7-&f-----&7-&8-");
                break;
            case ENABLE:
                logger.info("&8-&7-&f- | " + "&aPlugin successfully enabled!");
                logger.info("&8-&7-&f-----&7-&8-");
                logger.info("");
                break;
            case DISABLING:
                logger.info("");
                logger.info("&8-&7-&f-----&7-&8-");
                logger.info("&8-&7-&f- | " + "Disabling&7...");
                break;
            case DISABLE:
                logger.info("&8-&7-&f- | " + "&cPlugin successfully disabled!");
                logger.info("&8-&7-&f-----&7-&8-");
                logger.info("");
                break;
            case DEPENDENCIES:
                logger.info("&8-&7-&f- | " + "Loading dependencies&7...");
                break;
            case COMMANDS:
                logger.info("&8-&7-&f- | " + "Loading commands&7...");
                break;
            case EVENTS:
                logger.info("&8-&7-&f- | " + "Loading events&7...");
                break;
            case THREADS:
                logger.info("&8-&7-&f- | " + "Loading threads&7...");
                break;
            case MODULES:
                logger.info("&8-&7-&f- | " + "Loading modules&7...");
                break;
            case SYSTEM:
                logger.info("&8-&7-&f- | " + "Loading system&7...");
                break;
            case SETTINGS:
                logger.info("&8-&7-&f- | " + "Loading settings&7...");
                break;
            case FILES:
                logger.info("&8-&7-&f- | " + "Loading files&7...");
                break;
            case MESSAGES:
                logger.info("&8-&7-&f- | " + "Loading messages&7...");
                break;
            case LANGUAGES:
                logger.info("&8-&7-&f- | " + "Loading languages&7...");
                break;
            case DATABASES:
                logger.info("&8-&7-&f- | " + "Loading data bases&7...");
                break;
            case SPLIT:
                logger.info("&8-&7-&f-----&7-&8-");
                break;
        }
    }

    public static void pluginMessage(Logger logger, String other) {
        logger.info("&8-&7-&f- | " + other);
    }

    public enum MessageType {
        ENABLING, ENABLE, DISABLING, DISABLE, DEPENDENCIES, COMMANDS, EVENTS, THREADS, MODULES, SYSTEM, SETTINGS, FILES, MESSAGES, LANGUAGES, DATABASES, SPLIT
    }
}


