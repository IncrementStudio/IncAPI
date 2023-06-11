package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.plugin.Plugin;
import ru.incrementstudio.incrementstudioapi.Logger;

public class MessagesUtil {
    public static void sendPluginMessage(Plugin plugin, Logger logger, MessageType messageType, String other) {
        switch (messageType) {
            case ENABLING:
                String[] lines = new String[]{
                        "&b" + plugin.getDescription().getName() + " v" + plugin.getDescription().getVersion(),
                        "",
                        "&fИнформация о сервере&7:",
                        "&fЯдро&7: &e" + plugin.getServer().getName() + " &7| &fТребуется&7: &ePaper и все его форки",
                        "&fВерсия&7: &e" + plugin.getServer().getVersion() + " &7| &fТребуется&7: &e" + plugin.getDescription().getAPIVersion() + "+",
                        "&fАйпи&7: &e" + plugin.getServer().getIp() + "&7:&e" + plugin.getServer().getPort()
                };
                int maxLength = 0;
                for (String line : lines) {
                    if (ColorUtil.disableColor(line).length() > maxLength)
                        maxLength = ColorUtil.disableColor(line).length();
                }
                logger.info("");
                logger.info("&8-&7-&f----" + StringUtil.repeat("-", (maxLength - " IncrementStudio++ ".length()) / 2)
                        + " &dIncrementStudio++ &f" +
                        StringUtil.repeat("-", (maxLength - " IncrementStudio++ ".length()) % 2 == 0 ?
                                (maxLength - " IncrementStudio++ ".length()) / 2 :
                                (maxLength - " IncrementStudio++ ".length()) / 2 + 1)
                        + "----&7-&8-");
                for (int i = 0; i < lines.length; i++) {
                    logger.info("&8-&7-&f- | " + lines[i] + StringUtil.repeat(" ", maxLength - ColorUtil.disableColor(lines[i]).length()) + " &f| -&7-&8-");
                }
                logger.info("&8-&7-&f----" + StringUtil.repeat("-", maxLength) + "----&7-&8-");
                break;
            case ENABLE:
                logger.info("&8-&7-&f- | " + "&aПлагин успешно включён!");
                logger.info("&8-&7-&f-----&7-&8-");
                logger.info("");
                break;
            case DISABLING:
                logger.info("");
                logger.info("&8-&7-&f-----&7-&8-");
                logger.info("&8-&7-&f- | " + "Выключение плагина&7...");
                break;
            case DISABLE:
                logger.info("&8-&7-&f- | " + "&cПлагин успешно выключён!");
                logger.info("&8-&7-&f-----&7-&8-");
                logger.info("");
                break;
            case DEPENDENCIES:
                logger.info("&8-&7-&f- | " + "Загрузка способностей&7...");
                break;
            case COMMANDS:
                logger.info("&8-&7-&f- | " + "Загрузка команд&7...");
                break;
            case EVENTS:
                logger.info("&8-&7-&f- | " + "Загрузка событий&7...");
                break;
            case THREADS:
                logger.info("&8-&7-&f- | " + "Загрузка потоков&7...");
                break;
            case MODULES:
                logger.info("&8-&7-&f- | " + "Загрузка модулей&7...");
                break;
            case OTHER:
                logger.info("&8-&7-&f- | " + ColorUtil.toColor(other) +"&7...");
                break;
        }
    }

    public enum MessageType {
        ENABLING,
        ENABLE,
        DISABLING,
        DISABLE,
        DEPENDENCIES,
        COMMANDS,
        EVENTS,
        THREADS,
        MODULES,
        OTHER
    }
}


