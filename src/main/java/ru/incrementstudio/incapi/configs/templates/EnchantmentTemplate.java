package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantmentTemplate {
    private Enchantment enchantment = Enchantment.MENDING;
    private int level = 1;

    public Enchantment getEnchantment() { return enchantment; }
    public int getLevel() { return level; }

    public void setEnchantment(Enchantment enchantment) { this.enchantment = enchantment; }
    public void setLevel(int level) { this.level = level; }

    public EnchantmentTemplate(ConfigurationSection configSection) {
        if (configSection.contains("enchantment")) enchantment = Enchantment.getByName(configSection.getString("enchantment"));
        if (configSection.contains("level")) level = configSection.getInt("level");
    }

    public void apply(ItemStack item) {
        item.addUnsafeEnchantment(enchantment, level);
    }
}
