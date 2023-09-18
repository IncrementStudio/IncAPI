package ru.incrementstudio.incapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.incrementstudio.incapi.database.Materials;
import ru.incrementstudio.incapi.utils.ItemBuilder;
import ru.incrementstudio.incapi.utils.MessagesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
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
        Messages.load();
        Materials.load();
        Bukkit.getPluginManager().registerEvents(new Input(), this);
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
