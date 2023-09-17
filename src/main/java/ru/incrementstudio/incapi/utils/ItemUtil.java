package ru.incrementstudio.incapi.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.incrementstudio.incapi.configs.templates.EnchantmentTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material) {
        return new ItemStack(material);
    }
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material, int amount) {
        return new ItemStack(material, amount);
    }
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material, int amount, String name) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore) {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    @Deprecated(forRemoval = true)
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
    @Deprecated(forRemoval = true)
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
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, List<EnchantmentTemplate> enchants, List<ItemFlag> flags, Map<NamespacedKey, String> persistentData) {
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
        for (NamespacedKey key : persistentData.keySet()) {
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, persistentData.get(key));
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material, int amount, String name, List<String> lore, List<EnchantmentTemplate> enchants, List<ItemFlag> flags, Map<NamespacedKey, String> persistentData, int customModelData) {
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
        for (NamespacedKey key : persistentData.keySet()) {
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, persistentData.get(key));
        }
        itemMeta.setCustomModelData(customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    @Deprecated(forRemoval = true)
    public static ItemStack createItemStack(Material material, String name, int amount, List<String> lore, List<ItemFlag> flags, Map<NamespacedKey, String> persistentData, boolean coloredName, boolean coloredLore, boolean enchanted) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(coloredName ? ColorUtil.toColor(name) : name);
        List<String> editedLore = new ArrayList<>(lore);
        itemMeta.setLore(coloredLore ? ColorUtil.toColor(editedLore) : editedLore);
        for (int i = 0; i < flags.size(); i++) {
            itemMeta.addItemFlags(flags.get(i));
        }
        for (NamespacedKey key : persistentData.keySet()) {
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, persistentData.get(key));
        }
        if (enchanted) {
            itemMeta.addEnchant(Enchantment.LUCK, 1, true);
        }
        item.setItemMeta(itemMeta);
        return item;
    }


    public static ItemStack getHead(OfflinePlayer player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(player);
        item.setItemMeta(skull);
        return item;
    }

    public static int calcItemStackInPlayerInventory(Player player, ItemStack itemStack) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getSize(); ++i) {
            ItemStack stack;
            if (i == 40 || i == 38 || i == 37 || i == 36 || i == 39 || (stack = player.getInventory().getItem(i)) == null || !stack.isSimilar(itemStack)) continue;
            count += stack.getAmount();
        }
        return count;
    }


}
