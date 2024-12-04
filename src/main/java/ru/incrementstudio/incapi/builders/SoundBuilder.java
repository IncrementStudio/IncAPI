package ru.incrementstudio.incapi.builders;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

public class SoundBuilder {
    private Sound sound = Sound.BLOCK_GRASS_BREAK;
    private float volume = 1, pitch = 0;

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    public SoundBuilder setSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    public SoundBuilder setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    public SoundBuilder setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public SoundBuilder() {
    }

    public SoundBuilder(Sound sound) {
        this(sound, 1);
    }

    public SoundBuilder(Sound sound, float volume) {
        this(sound, volume, 0);
    }

    public SoundBuilder(Sound sound, float volume, float pitch) {
        setSound(sound);
        setVolume(volume);
        setPitch(pitch);
    }

    public SoundBuilder(ConfigurationSection configSection) {
        if (configSection != null) {
            setSound(Sound.valueOf(configSection.getString("sound", "BLOCK_GRASS_BREAK")));
            setVolume((float) configSection.getDouble("volume", 1));
            setPitch((float) configSection.getDouble("pitch", 1));
        }
    }

    public SoundBuilder play(Location location) {
        location.getWorld().playSound(location, sound, volume, pitch);
        return this;
    }
}
