package ru.incrementstudio.incapi.configs.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import ru.incrementstudio.incapi.utils.StringUtil;

public class EnchantmentTemplate {
    private Enchantment enchantment = Enchantment.MENDING;
    private int level = 1;

    public Enchantment getEnchantment() { return enchantment; }
    public int getLevel() { return level; }

    public EnchantmentTemplate(ConfigurationSection configSection) {
        if (configSection.contains("enchantment")) enchantment = Enchantment.getByName(StringUtil.getStringFromString(configSection.getString("enchantment")));
        if (configSection.contains("level")) level = StringUtil.getIntFromString(configSection.getString("level"));
    }
}
