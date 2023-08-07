package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import ru.incrementstudio.incapi.utils.StringUtil;

public class EnchantmentTemplate {
    private Enchantment enchantment;
    private int level;

    public Enchantment getEnchantment() { return enchantment; }
    public int getLevel() { return level; }

    public EnchantmentTemplate(ConfigurationSection configSection) {
        enchantment = Enchantment.getByName(StringUtil.getStringFromString(configSection.getString("enchantment")));
        level = StringUtil.getIntFromString(configSection.getString("level"));
    }
}
