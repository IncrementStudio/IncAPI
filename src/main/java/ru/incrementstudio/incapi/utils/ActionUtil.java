package ru.incrementstudio.incapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionUtil {
    public static void executeAction(Plugin plugin, List<Player> players, List<String> actions) {
        for (String string : actions) {
            String[] stringsElems = getAction(string);
            if (stringsElems == null) continue;
            String action = stringsElems[0];
            String value = stringsElems[1];

            long delay = 0;
            long period = 0;
            int repeats = 1;

            Matcher actionArgumentsMatcher = Pattern.compile("^(\\w+)\\((.*)\\)$").matcher(action);
            if (actionArgumentsMatcher.matches()) {
                action = actionArgumentsMatcher.group(1);
                String args = actionArgumentsMatcher.group(2);
                String[] arguments = args.split(";");
                for (String arg : arguments) {
                    Matcher argumentMatcher = Pattern.compile("^(\\w+)=(.*)$").matcher(arg);
                    if (argumentMatcher.matches()) {
                        String argName = argumentMatcher.group(1);
                        String argValue = argumentMatcher.group(2);
                        if (argName.equalsIgnoreCase("delay") || argName.equalsIgnoreCase("del") || argName.equalsIgnoreCase("d")) {
                            Matcher delayMatcher = Pattern.compile("^(\\d+)(\\w+)$").matcher(argValue);
                            if (delayMatcher.matches()) {
                                long delayValue = Long.parseLong(delayMatcher.group(1));
                                String delayMetric = delayMatcher.group(2);
                                if (delayMetric.equalsIgnoreCase("t") || delayMetric.equalsIgnoreCase("tick"))
                                    delay = delayValue;
                                else if (delayMetric.equalsIgnoreCase("s") || delayMetric.equalsIgnoreCase("sec"))
                                    delay = delayValue * 20;
                                else if (delayMetric.equalsIgnoreCase("m") || delayMetric.equalsIgnoreCase("min"))
                                    delay = delayValue * 20 * 60;
                                else
                                    Bukkit.getLogger().severe("Неверная единица измерения параметра '" + argName + "': '" + delayMetric + "'!");
                            } else {
                                Bukkit.getLogger().severe("Значение параметра '" + argName + "' имеет неверный формат: '" + argValue + "'!");
                            }
                        } else if (argName.equalsIgnoreCase("period") || argName.equalsIgnoreCase("per") || argName.equalsIgnoreCase("p")) {
                            Matcher periodMatcher = Pattern.compile("^(\\d+)(\\w+)$").matcher(argValue);
                            if (periodMatcher.matches()) {
                                long periodValue = Long.parseLong(periodMatcher.group(1));
                                String delayMetric = periodMatcher.group(2);
                                if (delayMetric.equalsIgnoreCase("t") || delayMetric.equalsIgnoreCase("tick"))
                                    period = periodValue;
                                else if (delayMetric.equalsIgnoreCase("s") || delayMetric.equalsIgnoreCase("sec"))
                                    period = periodValue * 20;
                                else if (delayMetric.equalsIgnoreCase("m") || delayMetric.equalsIgnoreCase("min"))
                                    period = periodValue * 20 * 60;
                                else
                                    Bukkit.getLogger().severe("Неверная единица измерения параметра '" + argName + "': '" + delayMetric + "'!");
                            } else {
                                Bukkit.getLogger().severe("Значение параметра '" + argName + "' имеет неверный формат: '" + argValue + "'!");
                            }
                        } else if (argName.equalsIgnoreCase("repeats") || argName.equalsIgnoreCase("rep") || argName.equalsIgnoreCase("r")) {
                            Matcher repeatMathcer = Pattern.compile("^(\\d+)$").matcher(argValue);
                            if (repeatMathcer.matches()) {
                                repeats = Integer.parseInt(repeatMathcer.group(1));
                            } else {
                                Bukkit.getLogger().severe("Значение параметра '" + argName + "' имеет неверный формат: '" + argValue + "'!");
                            }
                        } else {
                            Bukkit.getLogger().severe("Параметр '" + argName + "' не найден!");
                        }
                    } else {
                        Bukkit.getLogger().severe("Аргумент '" + arg + "' имеет неверный формат!");
                    }
                }
            } else if (!Pattern.compile("^(\\w+)$").matcher(action).matches()) {
                Bukkit.getLogger().severe("Неверное действие: '" + action + "'");
                continue;
            }

            String finalAction = action;
            int finalRepeats = repeats;
            new BukkitRunnable() {
                int r = 0;
                @Override
                public void run() {
                    for (Player player : players) {
                        switch (finalAction) {
                            case "message": {
                                if (value != null) {
                                    player.sendMessage(ColorUtil.toColor(value
                                            .replace("%player%", player.getName())));
                                } else {
                                    Bukkit.getLogger().severe("[message] Ожидается 1 аргумент: <message>");
                                }
                                break;
                            }
                            case "title": {
                                if (value != null) {
                                    String[] elements = value.split(" && ");
                                    if (elements.length == 1) {
                                        player.sendTitle(ColorUtil.toColor(elements[0])
                                                .replace("%player%", player.getName()), "");
                                    } else if (elements.length == 2) {
                                            player.sendTitle(ColorUtil.toColor(elements[0])
                                                    .replace("%player%", player.getName()),
                                                    ColorUtil.toColor(elements[1])
                                                    .replace("%player%", player.getName()));
                                    } else {
                                        Bukkit.getLogger().severe("[title] Ожидаются аргументы: <title> && [subtitle]");
                                    }
                                } else {
                                    Bukkit.getLogger().severe("[title] Ожидаются аргументы: <title> && [subtitle]");
                                }
                                break;
                            }
                            case "lightning": {
                                if (value != null) {
                                    for (int i = 0; i < Integer.parseInt(value); i++) {
                                        player.getWorld().strikeLightningEffect(player.getLocation());
                                    }
                                } else {
                                    Bukkit.getLogger().severe("[lightning] Ожидается 1 аргумент: <amount>");
                                }
                                break;
                            }
                            case "console": {
                                if (value != null) {
                                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), ColorUtil.toColor(value
                                            .replace("%player%", player.getName())));
                                } else {
                                    Bukkit.getLogger().severe("[console] Ожидается 1 аргумент: <command>");
                                }
                                break;
                            }
                            case "command": {
                                if (value != null) {
                                    Bukkit.getServer().dispatchCommand(player, ColorUtil.toColor(value
                                            .replace("%player%", player.getName())));
                                } else {
                                    Bukkit.getLogger().severe("[command] Ожидается 1 аргумент: <command>");
                                }
                                break;
                            }
                            case "sound": {
                                if (value != null) {
                                    String[] elements = value.split(":");
                                    if (elements.length == 3) {
                                        try {
                                            float volume = Float.parseFloat(elements[1]);
                                            try {
                                                float pitch = Float.parseFloat(elements[2]);
                                                try {
                                                    Sound sound = Sound.valueOf(elements[0]);
                                                    player.playSound(
                                                            player.getLocation(),
                                                            sound,
                                                            volume,
                                                            pitch
                                                    );
                                                } catch (IllegalArgumentException e) {
                                                    player.playSound(
                                                            player.getLocation(),
                                                            elements[0],
                                                            volume,
                                                            pitch
                                                    );
                                                }

                                            } catch (NumberFormatException e) {
                                                Bukkit.getLogger().severe("[sound] Третий аргумент должен быть числом");
                                            }
                                        } catch (NumberFormatException e) {
                                            Bukkit.getLogger().severe("[sound] Второй аргумент должен быть числом");
                                        }
                                    }
                                    else if (elements.length == 2) {
                                        try {
                                            float volume = Float.parseFloat(elements[1]);
                                            try {
                                                Sound sound = Sound.valueOf(elements[0]);
                                                player.playSound(
                                                        player.getLocation(),
                                                        sound,
                                                        volume,
                                                        5
                                                );
                                            } catch (IllegalArgumentException e) {
                                                player.playSound(
                                                        player.getLocation(),
                                                        elements[0],
                                                        volume,
                                                        5
                                                );
                                            }
                                        } catch (NumberFormatException e) {
                                            Bukkit.getLogger().severe("[sound] Второй аргумент должен быть числом");
                                        }
                                    }
                                    else if (elements.length == 1) {
                                        try {
                                            Sound sound = Sound.valueOf(elements[0]);
                                            player.playSound(
                                                    player.getLocation(),
                                                    sound,
                                                    10,
                                                    5
                                            );
                                        } catch (IllegalArgumentException e) {
                                            player.playSound(
                                                    player.getLocation(),
                                                    elements[0],
                                                    10,
                                                    5
                                            );
                                        }
                                    } else {
                                        Bukkit.getLogger().severe("[sound] Ожидаются аргументы: <sound>:[volume]:[pitch]");
                                    }
                                } else {
                                    Bukkit.getLogger().severe("[sound] Ожидаются аргументы: <sound>:[volume]:[pitch]");
                                }
                                break;
                            }
                            case "particles": {
                                if (value != null) {
                                    String[] elements = value.split(":");
                                    if (elements.length == 2) {
                                        try {
                                            Particle particle = Particle.valueOf(elements[0]);
                                            try {
                                                int count = Integer.parseInt(elements[1]);
                                                player.getWorld().spawnParticle(
                                                        particle,
                                                        player.getLocation(),
                                                        count);

                                            } catch (NumberFormatException e) {
                                                Bukkit.getLogger().severe("[particles] Второй аргумент должен быть целым числом");
                                            }
                                        } catch (IllegalArgumentException e) {
                                            Bukkit.getLogger().severe("[particles] Первый аргумент должен быть названием частицы");
                                        }
                                    } else if (elements.length == 1) {
                                        try {
                                            Particle particle = Particle.valueOf(elements[0]);
                                            player.getWorld().spawnParticle(
                                                    particle,
                                                    player.getLocation(),
                                                    1);
                                        } catch (IllegalArgumentException e) {
                                            Bukkit.getLogger().severe("[particles] Первый аргумент должен быть названием частицы");
                                        }
                                    } else {
                                        Bukkit.getLogger().severe("[particles] Ожидаются аргументы: <particle>:[count]");
                                    }
                                } else {
                                    Bukkit.getLogger().severe("[particles] Ожидаются аргументы: <particle>:[count]");
                                }
                                break;
                            }
                            case "effect": {
                                if (value != null) {
                                    String[] elements = value.split(":");
                                    if (elements.length == 3) {
                                        PotionEffectType effectType = PotionEffectType.getByName(elements[0]);
                                        if (effectType != null) {
                                            try {
                                                int level = Integer.parseInt(elements[2]);
                                                try {
                                                    int duration = Integer.parseInt(elements[1]);
                                                    EffectUtil.addEffects(player, List.of(new PotionEffect(effectType, duration, level)));
                                                } catch (NumberFormatException e) {
                                                    Bukkit.getLogger().severe("[effect] Третий аргумент должен быть целым числом");
                                                }
                                            } catch (NumberFormatException e) {
                                                Bukkit.getLogger().severe("[effect] Второй аргумент должен быть целым числом");
                                            }
                                        } else {
                                            Bukkit.getLogger().severe("[effect] Первый аргумент должен быть названием эффекта");
                                        }
                                    } else if (elements.length == 2) {
                                        PotionEffectType effectType = PotionEffectType.getByName(elements[0]);
                                        if (effectType != null) {
                                            try {
                                                int duration = Integer.parseInt(elements[1]);
                                                EffectUtil.addEffects(player, List.of(new PotionEffect(effectType, duration, 1)));
                                            } catch (NumberFormatException e) {
                                                Bukkit.getLogger().severe("[effect] Второй аргумент должен быть целым числом");
                                            }
                                        } else {
                                            Bukkit.getLogger().severe("[effect] Первый аргумент должен быть названием эффекта");
                                        }
                                    } else if (elements.length == 1) {
                                        PotionEffectType effectType = PotionEffectType.getByName(elements[0]);
                                        if (effectType != null) {
                                            EffectUtil.addEffects(player, List.of(new PotionEffect(effectType, 10, 1)));
                                        } else {
                                            Bukkit.getLogger().severe("[effect] Первый аргумент должен быть названием эффекта");
                                        }
                                    } else {
                                        Bukkit.getLogger().severe("[effect] Ожидаются аргументы: <effect>:[duration]:[level]");
                                    }
                                } else {
                                    Bukkit.getLogger().severe("[effect] Ожидаются аргументы: <effect>:[duration]:[level]");
                                }
                                break;
                            }
                        }
                    }
                    if (r == finalRepeats) {
                        cancel();
                    }
                    r++;
                }
            }.runTaskTimer(plugin, delay, period);
        }
    }

    private static String[] getAction(String string) {
        if (!string.isEmpty()) {
            if (string.contains(" ")) {
                String action = string.substring(0, string.indexOf(" "));
                if (action.length() > 2) {
                    if (action.startsWith("[") && action.endsWith("]")) {
                        action = action.substring(1, action.length() - 1);
                        String value = string.substring(string.indexOf(" ") + 1);
                        if (!value.isEmpty())
                            return new String[]{action, value};
                    }
                }
            } else {
                if (string.length() > 2) {
                    if (string.startsWith("[") && string.endsWith("]")) {
                        return new String[]{string.substring(1, string.length() - 1), null};
                    }
                }
            }
        }
        return null;
    }
}
