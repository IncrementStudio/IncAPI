package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.configuration.ConfigurationSection;
import ru.incrementstudio.incapi.utils.StringUtil;

import java.util.List;

public class BarTemplate {
    private BarColor color;
    private BarStyle style;
    private List<BarFlag> flags;

    public BarColor getColor() { return color; }
    public BarStyle getStyle() { return style; }
    public List<BarFlag> getFlags() { return flags; }

    public BarTemplate(ConfigurationSection configSection) {
        color = BarColor.valueOf(StringUtil.getStringFromString(configSection.getString("color")));
        style = BarStyle.valueOf(StringUtil.getStringFromString(configSection.getString("style")));
        flags = configSection.getStringList("flags").stream()
                .map(x -> BarFlag.valueOf(StringUtil.getStringFromString(x)))
                .toList();
    }
}
