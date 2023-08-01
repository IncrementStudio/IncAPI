package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.incrementstudio.incrementstudioapi.configs.templates.EnchantmentTemplate;

import java.util.HashMap;
import java.util.List;

public class ItemUtil {
    public static ItemStack createItemStack(Material material) {
        return new ItemStack(material);
    }

    public static ItemStack createItemStack(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static ItemStack createItemStack(Material material, int amount, String name) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, List<EnchantmentTemplate> enchants) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (EnchantmentTemplate enchant : enchants) {
            itemMeta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, List<EnchantmentTemplate> enchants, List<ItemFlag> flags) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (EnchantmentTemplate enchant : enchants) {
            itemMeta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
        }
        for (int i = 0; i < flags.size(); i++) {
            itemMeta.addItemFlags(flags.get(i));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, List<EnchantmentTemplate> enchants, List<ItemFlag> flags, HashMap<String, String> persistentData) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (EnchantmentTemplate enchant : enchants) {
            itemMeta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
        }
        for (int i = 0; i < flags.size(); i++) {
            itemMeta.addItemFlags(flags.get(i));
        }
        for (String key : persistentData.keySet()) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), PersistentDataType.STRING, persistentData.get(key));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
