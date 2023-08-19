package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.incrementstudio.incapi.utils.StringUtil;

public class PotionEffectTemplate {
    private PotionEffectType type = PotionEffectType.POISON;
    private int duration = 10, amplifier = 1;
    private boolean ambient = true, particles = true, icon = true;

    public PotionEffectType getType() { return type; }
    public int getDuration() { return duration; }
    public int getAmplifier() { return amplifier; }
    public boolean isAmbient() { return ambient; }
    public boolean isParticles() { return particles; }
    public boolean isIcon() { return icon; }

    public PotionEffectTemplate(ConfigurationSection configSection) {
        if (configSection.contains("type")) type = PotionEffectType.getByName(StringUtil.getStringFromString(configSection.getString("type")));
        if (configSection.contains("duration")) duration = StringUtil.getIntFromString(configSection.getString("duration"));
        if (configSection.contains("amplifier")) amplifier = StringUtil.getIntFromString(configSection.getString("amplifier"));
        if (configSection.contains("ambient")) ambient = StringUtil.getBooleanFromString(configSection.getString("ambient"));
        if (configSection.contains("particles")) particles = StringUtil.getBooleanFromString(configSection.getString("particles"));
        if (configSection.contains("icon")) icon = StringUtil.getBooleanFromString(configSection.getString("icon"));
    }

    public PotionEffect getPotionEffect() {
        return new PotionEffect(
                type, duration, amplifier, ambient, particles, icon
        );
    }
}
