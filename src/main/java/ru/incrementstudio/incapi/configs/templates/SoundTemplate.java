package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import ru.incrementstudio.incapi.utils.StringUtil;

public class SoundTemplate {
    private Sound sound = Sound.BLOCK_GRASS_BREAK;
    private float volume = 1, pitch = 0;

    public Sound getSound() { return sound; }
    public float getVolume() { return volume; }
    public float getPitch() { return pitch; }

    public SoundTemplate(ConfigurationSection configSection) {
        if (configSection.contains("sound")) sound = Sound.valueOf(configSection.getString("sound"));
        if (configSection.contains("volume")) volume = (float) configSection.getDouble("volume");
        if (configSection.contains("pitch")) pitch = (float) configSection.getDouble("pitch");
    }
}
