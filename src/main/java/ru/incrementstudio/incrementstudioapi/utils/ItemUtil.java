package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static ItemStack createItemStack(Material material, int amount, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, Map<Enchantment, Integer> enchants) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Enchantment enchant : enchants.keySet()) {
            itemMeta.addEnchant(enchant, enchants.get(enchant), true);
        }
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

    public static ItemStack createItemStack(Material material, int amount, String name, Map<Enchantment, Integer> enchants) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        for (Enchantment enchant : enchants.keySet()) {
            itemMeta.addEnchant(enchant, enchants.get(enchant), true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, List<String> lore, Map<Enchantment, Integer> enchants) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        for (Enchantment enchant : enchants.keySet()) {
            itemMeta.addEnchant(enchant, enchants.get(enchant), true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchants) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (Enchantment enchant : enchants.keySet()) {
            itemMeta.addEnchant(enchant, enchants.get(enchant), true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchants, List<ItemFlag> flags) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (Enchantment enchant : enchants.keySet()) {
            itemMeta.addEnchant(enchant, enchants.get(enchant), true);
        }
        for (int i = 0; i < flags.size(); i++) {
            itemMeta.addItemFlags(flags.get(i));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, Map<Enchantment, Integer> enchants, List<ItemFlag> flags, HashMap<String, String> persistentData) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        for (Enchantment enchant : enchants.keySet()) {
            itemMeta.addEnchant(enchant, enchants.get(enchant), true);
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


    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, List<ItemFlag> flags, HashMap<String, String> persistentData, boolean coloredName, boolean coloredLore, boolean enchanted) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(coloredName ? ColorUtil.toColor(name) : name);
        List<String> editedLore = new ArrayList<>(lore);
        itemMeta.setLore(coloredLore ? ColorUtil.toColor(editedLore) : editedLore);
        for (int i = 0; i < flags.size(); i++) {
            itemMeta.addItemFlags(flags.get(i));
        }
        for (String key : persistentData.keySet()) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), PersistentDataType.STRING, persistentData.get(key));
        }
        if (enchanted) {
            itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        }
        item.setItemMeta(itemMeta);
        return item;
    }
}
