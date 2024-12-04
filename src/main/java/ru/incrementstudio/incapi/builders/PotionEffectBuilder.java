package ru.incrementstudio.incapi.builders;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PotionEffectBuilder {
    private PotionEffectType type;
    private int duration, amplifier;
    private boolean ambient = true, particles = true, icon = true;

    public PotionEffectType getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean isParticles() {
        return particles;
    }

    public boolean isIcon() {
        return icon;
    }

    public PotionEffectBuilder setType(PotionEffectType type) {
        this.type = type;
        return this;
    }

    public PotionEffectBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public PotionEffectBuilder setAmplifier(int amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    public PotionEffectBuilder setAmbient(boolean ambient) {
        this.ambient = ambient;
        return this;
    }

    public PotionEffectBuilder setParticles(boolean particles) {
        this.particles = particles;
        return this;
    }

    public PotionEffectBuilder setIcon(boolean icon) {
        this.icon = icon;
        return this;
    }

    public PotionEffectBuilder() {
        this(PotionEffectType.POISON);
    }

    public PotionEffectBuilder(PotionEffectType type) {
        this(type, 10);
    }

    public PotionEffectBuilder(PotionEffectType type, int duration) {
        this(type, duration, 1);
    }

    public PotionEffectBuilder(PotionEffectType type, int duration, int amplifier) {
        this(type, duration, amplifier, true);
    }

    public PotionEffectBuilder(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        this(type, duration, amplifier, ambient, true);
    }

    public PotionEffectBuilder(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles) {
        this(type, duration, amplifier, ambient, particles, true);
    }

    public PotionEffectBuilder(PotionEffectType type, int duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        setType(type);
        setDuration(duration);
        setAmplifier(amplifier);
        setAmbient(ambient);
        setParticles(particles);
        setIcon(icon);
    }

    public PotionEffectBuilder(ConfigurationSection configSection) {
        this(
                PotionEffectType.getByName(configSection.getString("type", "POISON")),
                configSection.getInt("duration", 10),
                configSection.getInt("amplifier", 1),
                configSection.getBoolean("ambient", true),
                configSection.getBoolean("particles", true),
                configSection.getBoolean("icon", true)
        );
    }

    public PotionEffectBuilder(String string) throws IllegalArgumentException {
        String[] elems = string.split(":");
        if (elems.length < 3) throw new IllegalArgumentException();
        try {
            setType(Objects.requireNonNull(PotionEffectType.getByName(elems[0])));
            setAmplifier(Integer.parseInt(elems[1]));
            setDuration(Integer.parseInt(elems[2]));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public PotionEffect build() {
        return new PotionEffect(
                type, duration, amplifier, ambient, particles, icon
        );
    }

    public PotionEffectBuilder apply(LivingEntity... entities) {
        apply(Arrays.asList(entities));
        return this;
    }

    public PotionEffectBuilder apply(List<LivingEntity> entities) {
        for (LivingEntity entity : entities)
            entity.addPotionEffect(build());
        return this;
    }
}
