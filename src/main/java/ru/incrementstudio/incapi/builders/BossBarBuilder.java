package ru.incrementstudio.incapi.builders;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BossBarBuilder {
    private BarColor color = BarColor.RED;
    private BarStyle style = BarStyle.SOLID;
    private List<BarFlag> flags = new ArrayList<>();

    public BarColor getColor() {
        return color;
    }

    public BarStyle getStyle() {
        return style;
    }

    public List<BarFlag> getFlags() {
        return flags;
    }

    public BossBarBuilder setColor(BarColor color) {
        this.color = color;
        return this;
    }

    public BossBarBuilder setStyle(BarStyle style) {
        this.style = style;
        return this;
    }

    public BossBarBuilder setFlags(List<BarFlag> flags) {
        this.flags = flags;
        return this;
    }

    public BossBarBuilder() {
        this(BarColor.RED);
    }

    public BossBarBuilder(BarColor color) {
        this(color, BarStyle.SOLID);
    }

    public BossBarBuilder(BarColor color, BarStyle style) {
        this(color, style, new ArrayList<>());
    }

    public BossBarBuilder(BarColor color, BarStyle style, BarFlag... flags) {
        this(color, style, Arrays.asList(flags));
    }

    public BossBarBuilder(BarColor color, BarStyle style, List<BarFlag> flags) {
        setColor(color);
        setStyle(style);
        setFlags(flags);
    }

    public BossBarBuilder(ConfigurationSection configSection) {
        if (configSection != null) {
            setColor(BarColor.valueOf(configSection.getString("color", "RED")));
            setStyle(BarStyle.valueOf(configSection.getString("style", "SOLID")));
            setFlags(configSection.getStringList("flags").stream()
                            .map(BarFlag::valueOf)
                            .collect(Collectors.toList()));
        }
    }

    public BossBar build(String title) {
        return Bukkit.createBossBar(title, color, style, flags.toArray(new BarFlag[0]));
    }
}