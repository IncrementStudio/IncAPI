package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EffectUtil {
    public static void addEffects(LivingEntity entity, List<PotionEffect> effects) {
        for (PotionEffect effect : effects) {
            entity.addPotionEffect(effect);
        }
    }

    public static void addEffects(LivingEntity entity, List<String> effects, String separator) {
        for (String effect : effects) {
            String[] strings = effect.split(separator);
            if (strings.length < 2 || strings.length > 3) continue;
            PotionEffectType effectType = PotionEffectType.getByName(strings[0]);
            if (effectType == null) return;
            int duration = 9999;
            if (strings.length == 3) {
                try {
                    duration = Integer.parseInt(strings[1]);
                } catch (NumberFormatException ignored) {
                    continue;
                }
            }
            int value = 0;
            try {
                if (strings.length == 3) value = Integer.parseInt(strings[2]);
                if (strings.length == 2) value = Integer.parseInt(strings[1]);
            } catch (NumberFormatException ignored) {
                continue;
            }
            entity.addPotionEffect(new PotionEffect(effectType, duration * 20, value));
        }
    }
}
