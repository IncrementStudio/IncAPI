package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;

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

    public void setColor(BarColor color) { this.color = color; }
    public void setStyle(BarStyle style) { this.style = style; }
    public void setFlags(List<BarFlag> flags) { this.flags = flags; }

    public BarTemplate(ConfigurationSection configSection) {
        if (configSection.contains("color")) color = BarColor.valueOf(configSection.getString("color"));
        if (configSection.contains("style")) style = BarStyle.valueOf(configSection.getString("style"));
        if (configSection.contains("flags")) flags = configSection.getStringList("flags").stream()
                .map(BarFlag::valueOf)
                .collect(Collectors.toList());
    }

    public BossBar create(String title) {
        return Bukkit.createBossBar(title, color, style, flags.toArray(new BarFlag[0]));
    }
}
