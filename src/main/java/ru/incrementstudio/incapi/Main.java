package ru.incrementstudio.incapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.incrementstudio.incapi.database.Materials;
import ru.incrementstudio.incapi.menu.MenuListener;
import ru.incrementstudio.incapi.menu.Page;
import ru.incrementstudio.incapi.utils.ItemBuilder;
import ru.incrementstudio.incapi.utils.MessagesUtil;

import java.util.*;

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
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new Input(), this);
        getCommand("thrds").setExecutor(this);
        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.ENABLE, null);
    }

    @Override
    public void onDisable() {
        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.DISABLING, null);
        Main.getConfigManager().reloadAll();
        Main.getConfigManager().saveAll();
        MessagesUtil.sendPluginMessage(this, logger, MessagesUtil.MessageType.DISABLE, null);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (Map.Entry<Thread, StackTraceElement[]> trace : Thread.getAllStackTraces().entrySet()) {
            if (trace.getKey().getName().startsWith(args[0])) {
                if (trace.getKey().isAlive())
                    logger().info(trace.getKey().toString());
                else
                    logger().error(trace.getKey().toString());
                for (StackTraceElement el : trace.getValue()) {
                    logger().warn("    " + el.toString());
                }
            }
        }
        return true;
    }
}
