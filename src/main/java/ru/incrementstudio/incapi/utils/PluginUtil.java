package ru.incrementstudio.incapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import ru.incrementstudio.incapi.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class PluginUtil {
    public static void setCommand(Plugin plugin, Logger logger, String commandName, CommandExecutor commandExecutor, TabCompleter tabCompleter) {
        try {
            PluginCommand command;
            CommandMap map = null;
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, org.bukkit.plugin.Plugin.class);
            c.setAccessible(true);
            command = c.newInstance(commandName, plugin);
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                map = (CommandMap)f.get(Bukkit.getPluginManager());
            }
            if (map != null) {
                map.register(plugin.getDescription().getName(), command);
            }
            command.setExecutor(commandExecutor);
            command.setTabCompleter(tabCompleter);
        }
        catch (Exception exception) {
            logger.error("Не удалось зарегистрировать команду &6'&e" + commandName + "&6'&c. Ошибка: ");
            exception.printStackTrace();
            try {
                Bukkit.getPluginManager().disablePlugin(plugin);
            } catch (Exception exception1) {
                logger.error("Произошла ошибка во время остановки плагина. Ошибка: ");
                exception1.printStackTrace();
            }
        }
    }
}
