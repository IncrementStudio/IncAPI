package ru.incrementstudio.incapi;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.incrementstudio.incapi.utils.MessagesUtil;

import java.util.List;

public final class Main extends JavaPlugin {

    private static Plugin instance;
    public static Plugin getInstance() {
        return instance;
    }
    private static Logger logger;
    public static Logger logger() { return logger; }
    private static ConfigManager configManager;
    public static ConfigManager getConfigManager() { return configManager; }

    public static Config config;

    @Override
    public void onEnable() {
        instance = this;
        logger = new Logger(this);

        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.ENABLING, null);

        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.FILES, null);
        configManager = new ConfigManager(this, List.of("config"));
        configManager.updateAll();
        config = Main.getConfigManager().getConfig("config");
        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.ENABLE, null);
    }

    @Override
    public void onDisable() {
        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.DISABLING, null);
        Main.getConfigManager().reloadAll();
        Main.getConfigManager().saveAll();
        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.DISABLE, null);
    }


}
