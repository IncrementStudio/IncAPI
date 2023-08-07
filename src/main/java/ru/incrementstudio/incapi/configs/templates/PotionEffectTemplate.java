package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.incrementstudio.incapi.utils.StringUtil;

public class PotionEffectTemplate {
    private PotionEffectType type;
    private int duration, amplifier;
    private boolean ambient, particles, icon;

    public PotionEffectType getType() { return type; }
    public int getDuration() { return duration; }
    public int getAmplifier() { return amplifier; }
    public boolean isAmbient() { return ambient; }
    public boolean isParticles() { return particles; }
    public boolean isIcon() { return icon; }

    public PotionEffectTemplate(ConfigurationSection configSection) {
        type = PotionEffectType.getByName(StringUtil.getStringFromString(configSection.getString("type")));
        duration = StringUtil.getIntFromString(configSection.getString("duration"));
        amplifier = StringUtil.getIntFromString(configSection.getString("amplifier"));
        ambient = StringUtil.getBooleanFromString(configSection.getString("ambient"));
        particles = StringUtil.getBooleanFromString(configSection.getString("particles"));
        icon = StringUtil.getBooleanFromString(configSection.getString("icon"));
    }

    public PotionEffect getPotionEffect() {
        return new PotionEffect(
                type, duration, amplifier, ambient, particles, icon
        );
    }
}
