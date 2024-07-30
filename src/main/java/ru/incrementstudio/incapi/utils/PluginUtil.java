package ru.incrementstudio.incapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.SimplePluginManager;
import ru.incrementstudio.incapi.IncPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PluginUtil {
    public static void setCommand(IncPlugin plugin, String commandName, CommandExecutor commandExecutor, TabCompleter tabCompleter, List<String> aliases) {
        try {
            PluginCommand command;
            CommandMap map = null;
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, org.bukkit.plugin.Plugin.class);
            c.setAccessible(true);
            command = c.newInstance(commandName, plugin);
            command.setAliases(new ArrayList<>(aliases));
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                map = (CommandMap)f.get(Bukkit.getPluginManager());
            }
            if (map != null)
                map.register(plugin.getDescription().getName(), command);
            command.setExecutor(commandExecutor);
            command.setTabCompleter(tabCompleter);
        } catch (Exception e) {
            plugin.getLogger().error(
                    "Не удалось зарегистрировать команду &6" + commandName,
                    e.toString()
            );
        }
    }
}
