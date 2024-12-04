package ru.incrementstudio.incapi.builders;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class EnchantmentBuilder {
    private Enchantment enchantment;
    private int level;

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public int getLevel() {
        return level;
    }

    public EnchantmentBuilder setEnchantment(Enchantment enchantment) {
        this.enchantment = enchantment;
        return this;
    }

    public EnchantmentBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    public EnchantmentBuilder() {
        this(Enchantment.MENDING);
    }

    public EnchantmentBuilder(Enchantment enchantment) {
        this(enchantment, 1);
    }

    public EnchantmentBuilder(Enchantment enchantment, int level) {
        setEnchantment(enchantment);
        setLevel(level);
    }

    public EnchantmentBuilder(ConfigurationSection configSection) {
        this(
                Enchantment.getByKey(NamespacedKey.minecraft(configSection.getString("enchantment", "mending").toLowerCase())),
                configSection.getInt("level", 1)
        );
    }

    public EnchantmentBuilder(String string, String regexSeparator) {
        String[] elements = string.split(regexSeparator);
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(elements[0].toLowerCase()));
        if (enchantment == null)
            enchantment = Enchantment.MENDING;
        setEnchantment(enchantment);
        int level = 1;
        try {
            level = Integer.parseInt(elements[1]);
        } catch (Exception ignored) {
        }
        setLevel(level);
    }

    public EnchantmentBuilder apply(ItemStack... items) {
        apply(Arrays.asList(items));
        return this;
    }

    public EnchantmentBuilder apply(List<ItemStack> items) {
        for (ItemStack item : items)
            item.addEnchantment(enchantment, level);
        return this;
    }

    public EnchantmentBuilder applyUnsafe(ItemStack... items) {
        applyUnsafe(Arrays.asList(items));
        return this;
    }

    public EnchantmentBuilder applyUnsafe(List<ItemStack> items) {
        for (ItemStack item : items)
            item.addUnsafeEnchantment(enchantment, level);
        return this;
    }
}