package ru.incrementstudio.incapi;

import org.bukkit.Bukkit;
import ru.incrementstudio.incapi.util.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Logger extends java.util.logging.Logger {
    private final IncPlugin plugin;
    public IncPlugin getPlugin() {
        return plugin;
    }

    private final List<String> messagePrefixes = new ArrayList<>();
    public List<String> getMessagePrefixes() {
        return messagePrefixes;
    }
    public String getMessagePrefix() {
        return String.join("", messagePrefixes);
    }

    public Logger(IncPlugin plugin) {
        super(plugin.getName(), null);
        this.plugin = plugin;
        this.setParent(plugin.getServer().getLogger());
        this.setLevel(Level.ALL);
    }

    @Override
    public void log(Level level, String msg) {
        if (!plugin.isEnabling() && !plugin.isDisabling()) {
            simple(level, ColorUtil.toColor(getPrefix(level) + getMessagePrefix() + msg));
        } else {
            simple(level, ColorUtil.toColor("&f" + getMessagePrefix() + msg));
        }
    }

    protected String getPrefix(Level level) {
        if (level == Level.INFO) return "&a[&f" + getName() + "&a] &f";
        if (level == Level.WARNING) return "&e[&f" + getName() + "&e] ";
        if (level == Level.SEVERE) return "&c[&f" + getName() + "&c] ";
        return "&f[&7" + getName() + "&f] ";
    }

    @Override
    public void info(String msg) {
        log(Level.INFO, msg);
    }

    @Override
    public void warning(String msg) {
        log(Level.WARNING, msg);
    }

    @Override
    public void severe(String msg) {
        log(Level.SEVERE, msg);
    }

    public void error(String header, String msg) {
        severe(header + "&c:");
        severe("> " + msg);
    }
    public void warning(String header, String msg) {
        warning(header + "&e:");
        warning("> " + msg);
    }
    @Deprecated(forRemoval = true)
    public void action(String msg) {
        getMessagePrefixes().add(Prefix.ACTION);
        log(Level.INFO, msg);
        getMessagePrefixes().remove(getMessagePrefixes().size() - 1);
    }
    @Deprecated(forRemoval = true)
    public void subaction(String msg) {
        getMessagePrefixes().add(Prefix.SUBACTION);
        log(Level.INFO, msg);
        getMessagePrefixes().remove(getMessagePrefixes().size() - 1);
    }
    public void simple(Level level, String msg) {
        Bukkit.getLogger().log(level, msg);
    }
    public void simple(String msg) {
        simple(Level.INFO, msg);
    }

    public static class Messages {
        public static class EN {
            public static final String ENABLING = "&fPlugin enabling&7...";
            public static final String ENABLE = "&aPlugin enabled!";
            public static final String DISABLING = "&fPlugin disabling&7...";
            public static final String DISABLE = "&cPlugin disabled!";
            public static final String CONFIGS = "&fLoading configurations&7...";
            public static final String DEPENDENCIES = "&fLoading dependencies&7...";
            public static final String COMMANDS = "&fCommand registration&7...";
            public static final String EVENTS = "&fEvent registration&7...";
            public static final String THREADS = "&fStarting threads&7...";
            public static final String MODULES = "&fLoading modules&7...";
            public static final String SYSTEM = "&fLoading systems&7...";
            public static final String SETTINGS = "&fLoading settings&7...";
            public static final String FILES = "&fLoading files&7...";
            public static final String MESSAGES = "&fLoading messages&7...";
            public static final String LANGUAGES = "&fLoading languages&7...";
            public static final String DATABASES = "&fLoading databases&7...";
        }
        public static class RU {
            public static final String ENABLING = "&fВключение плагина...";
            public static final String ENABLE = "&aПлагин включен!";
            public static final String DISABLING = "&fВыключение плагина...";
            public static final String DISABLE = "&cПлагин выключен!";
            public static final String CONFIGS = "&fЗагрузка конфигурации&7...";
            public static final String DEPENDENCIES = "&fЗагрузка зависимостей&7...";
            public static final String COMMANDS = "&fРегистрация команд&7...";
            public static final String EVENTS = "&fРегистрация ивентов&7...";
            public static final String THREADS = "&fЗапуск потоков&7...";
            public static final String MODULES = "&fЗагрузка модулей&7...";
            public static final String SYSTEM = "&fЗагрузка систем&7...";
            public static final String SETTINGS = "&fЗагрузка настроек&7...";
            public static final String FILES = "&fЗагрузка файлов&7...";
            public static final String MESSAGES = "&fЗагрузка сообщений&7...";
            public static final String LANGUAGES = "&fЗагрузка языков&7...";
            public static final String DATABASES = "&fЗагрузка баз данных&7...";
        }
    }
    public static class Prefix {
        public static final String OFFSET = "  ";
        public static final String ACTION = "&8- &f";
        public static final String SUBACTION = "  &8| &f";
    }
}
