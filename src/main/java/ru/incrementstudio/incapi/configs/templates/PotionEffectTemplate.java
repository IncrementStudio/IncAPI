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
        if (configSection.contains("type")) type = PotionEffectType.getByName(configSection.getString("type"));
        if (configSection.contains("duration")) duration = configSection.getInt("duration");
        if (configSection.contains("amplifier")) amplifier = configSection.getInt("amplifier");
        if (configSection.contains("ambient")) ambient = configSection.getBoolean("ambient");
        if (configSection.contains("particles")) particles = configSection.getBoolean("particles");
        if (configSection.contains("icon")) icon = configSection.getBoolean("icon");
    }

    public PotionEffect getPotionEffect() {
        return new PotionEffect(
                type, duration, amplifier, ambient, particles, icon
        );
    }
}
