package ru.incrementstudio.incrementstudioapi.configs.templates;

import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import ru.incrementstudio.incrementstudioapi.utils.StringUtil;

public class SoundTemplate {
    private Sound sound;
    private float volume, pitch;

    public Sound getSound() { return sound; }
    public float getVolume() { return volume; }
    public float getPitch() { return pitch; }

    public SoundTemplate(ConfigurationSection configSection) {
        sound = Sound.valueOf(StringUtil.getStringFromString(configSection.getString("sound")));
        volume = (float) StringUtil.getDoubleFromString(configSection.getString("volume"));
        pitch = (float) StringUtil.getDoubleFromString(configSection.getString("pitch"));
    }
}
