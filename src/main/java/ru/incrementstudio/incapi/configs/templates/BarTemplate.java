package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import ru.incrementstudio.incapi.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BarTemplate {
    private BarColor color = BarColor.RED;
    private BarStyle style = BarStyle.SOLID;
    private List<BarFlag> flags = new ArrayList<>();

    public BarColor getColor() { return color; }
    public BarStyle getStyle() { return style; }
    public List<BarFlag> getFlags() { return flags; }

    public BarTemplate(ConfigurationSection configSection) {
        if (configSection.contains("color")) color = BarColor.valueOf(StringUtil.getStringFromString(configSection.getString("color")));
        if (configSection.contains("style")) style = BarStyle.valueOf(StringUtil.getStringFromString(configSection.getString("style")));
        if (configSection.contains("flags")) flags = configSection.getStringList("flags").stream()
                .map(x -> BarFlag.valueOf(StringUtil.getStringFromString(x)))
                .collect(Collectors.toList());
    }
}
