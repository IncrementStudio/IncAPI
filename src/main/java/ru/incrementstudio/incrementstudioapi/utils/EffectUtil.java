package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class EffectUtil {
    public static void addEffects(LivingEntity entity, List<PotionEffect> effects) {
        for (PotionEffect effect : effects) {
            entity.addPotionEffect(effect);
        }
    }
}
