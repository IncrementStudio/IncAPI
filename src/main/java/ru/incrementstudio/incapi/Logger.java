package ru.incrementstudio.incapi;

import org.bukkit.plugin.Plugin;
import ru.incrementstudio.incapi.utils.ColorUtil;

import java.util.logging.Level;

public class Logger extends java.util.logging.Logger {
    public Logger(Plugin plugin) {
        super(plugin.getName(), null);
        this.setParent(plugin.getServer().getLogger());
        this.setLevel(Level.ALL);
    }

    @Override
    public void log(Level level, String msg) {
        super.log(level, ColorUtil.toColor(msg));
    }

    public void error(String header, String msg) {
        severe(header + "&c:");
        severe("> " + msg);
    }
    public void action(String msg) {
        simple("  &8- &r" + msg);
    }
    public void subaction(String msg) {
        simple("    &8| &r" + msg);
    }
    public void simple(String msg) {
        System.out.println(ColorUtil.toColor(msg));
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
}
