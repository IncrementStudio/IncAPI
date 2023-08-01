package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.entity.LivingEntity;
import ru.incrementstudio.incrementstudioapi.configs.templates.PotionEffectTemplate;

import java.util.List;

public class EffectUtil {
    public static void addEffect(LivingEntity entity, PotionEffectTemplate effect) {
        entity.addPotionEffect(effect.getPotionEffect());
    }

    public static void addEffects(LivingEntity entity, List<PotionEffectTemplate> effects) {
        for (PotionEffectTemplate effect : effects) {
            addEffect(entity, effect);
        }
    }
}
