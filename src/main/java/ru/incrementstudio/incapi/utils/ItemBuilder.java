package ru.incrementstudio.incapi.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private boolean isColored = true;

    public ItemBuilder() {
        itemStack = new ItemStack(Material.STONE);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
        this.itemMeta = itemStack.getItemMeta();
        return this;
    }

    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    public ItemBuilder setMaterial(Material material) {
        ItemMeta itemMeta1 = itemMeta.clone();
        itemStack = new ItemStack(material);
        itemStack.setItemMeta(itemMeta1);
        itemMeta = itemMeta1;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder addAmount(int amount) {
        itemStack.setAmount(itemStack.getAmount() + amount);
        return this;
    }

    public ItemBuilder removeAmount(int amount) {
        itemStack.setAmount(itemStack.getAmount() - amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        if (isColored) itemMeta.setDisplayName(ColorUtil.toColor(name));
        else itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setName(String name, HashMap<String, String> values) {
        for (Map.Entry<String, String> value: values.entrySet()) {
            if (name.contains(value.getKey())) name = name.replace(value.getKey(), value.getValue());
        }
        if (isColored) itemMeta.setDisplayName(ColorUtil.toColor(name));
        else itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder addName(String name) {
        if (isColored) itemMeta.setDisplayName(itemMeta.getDisplayName() + ColorUtil.toColor(name));
        else itemMeta.setDisplayName(itemMeta.getDisplayName() + name);
        return this;
    }

    public ItemBuilder addName(String name, HashMap<String, String> values) {
        for (Map.Entry<String, String> value: values.entrySet()) {
            if (name.contains(value.getKey())) name = name.replace(value.getKey(), value.getValue());
        }
        if (isColored) itemMeta.setDisplayName(itemMeta.getDisplayName() + ColorUtil.toColor(name));
        else itemMeta.setDisplayName(itemMeta.getDisplayName() + name);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> resultLore = new ArrayList<>();
        for (String line: lore) {
            if (isColored) resultLore.add(ColorUtil.toColor(line));
            else resultLore.add(line);
        }
        itemMeta.setLore(resultLore);
        return this;
    }

    public ItemBuilder setLore(List<String> lore, HashMap<String, String> values) {
        List<String> resultLore = new ArrayList<>();
        for (String line: lore) {
            for (Map.Entry<String, String> value: values.entrySet()) {
                if (line.contains(value.getKey())) line = line.replace(value.getKey(), value.getValue());
            }
            if (isColored) resultLore.add(ColorUtil.toColor(line));
            else resultLore.add(line);
        }
        itemMeta.setLore(resultLore);
        return this;
    }

    public ItemBuilder addLore(List<String> lore) {
        List<String> resultLore = itemMeta.getLore();
        for (String line: lore) {
            if (isColored) resultLore.add(ColorUtil.toColor(line));
            else resultLore.add(line);
        }
        itemMeta.setLore(resultLore);
        return this;
    }

    public ItemBuilder addLore(List<String> lore, HashMap<String, String> values) {
        List<String> resultLore = itemMeta.getLore();
        for (String line: lore) {
            for (Map.Entry<String, String> value: values.entrySet()) {
                if (line.contains(value.getKey())) line = line.replace(value.getKey(), value.getValue());
            }
            if (isColored) resultLore.add(ColorUtil.toColor(line));
            else resultLore.add(line);
        }
        itemMeta.setLore(resultLore);
        return this;
    }

    public ItemBuilder setColored(boolean isColored) {
        this.isColored = isColored;
        return this;
    }


    public ItemBuilder addEnchantment(Enchantment enchantment) {
        return addEnchantment(enchantment, 1);
    }
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }
    public ItemBuilder addEnchantments(Enchantment... enchantments) {
        return addEnchantments(1, enchantments);
    }
    public ItemBuilder addEnchantments(int level, Enchantment... enchantments) {
        for (Enchantment enchantment : enchantments)
            itemMeta.addEnchant(enchantment, level, true);
        return this;
    }
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemMeta.removeEnchant(enchantment);
        return this;
    }
    public ItemBuilder removeEnchantments(Enchantment... enchantments) {
        for (Enchantment enchantment: enchantments)
            removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder setGlowing(boolean isGlowing) {
        if (isGlowing) {
            addEnchantment(Enchantment.LUCK);
            addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            removeEnchantment(Enchantment.LUCK);
            removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder addCustomModelData(int data) {
        itemMeta.setCustomModelData(data);
        return this;
    }

    public ItemBuilder removeCustomModelData() {
        itemMeta.setCustomModelData(null);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {
        itemMeta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        itemMeta.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder setPersistentData(String key, PersistentDataType persistentDataType, Object value) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), persistentDataType, value);
        return this;
    }

    public ItemBuilder setPersistentData(PersistentData persistentData) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(persistentData.getKey()), persistentData.getPersistentDataType(), persistentData.getValue());
        return this;
    }

    public ItemBuilder setPersistentData(List<PersistentData> persistentData) {
        for (PersistentData persistentData1: persistentData)
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(persistentData1.getKey()), persistentData1.getPersistentDataType(), persistentData1.getValue());
        return this;
    }
    public ItemBuilder removePersistentData(String key, PersistentDataType persistentDataType) {
        itemMeta.getPersistentDataContainer().remove(NamespacedKey.fromString(key));
        return this;
    }


    public ItemBuilder removeData(String key) {
        itemMeta.getPersistentDataContainer().remove(NamespacedKey.fromString(key));
        return this;
    }

    public ItemBuilder setData(String key, String value) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder setData(HashMap<String, String> values) {
        for (Map.Entry<String, String> value: values.entrySet())
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(value.getKey()), PersistentDataType.STRING, value.getValue());
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static class PersistentData {
        private final String key;
        private final PersistentDataType persistentDataType;
        private final Object value;
        public PersistentData(String key, PersistentDataType persistentDataType, Object value) {
            this.key = key;
            this.persistentDataType = persistentDataType;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public PersistentDataType getPersistentDataType() {
            return persistentDataType;
        }

        public Object getValue() {
            return value;
        }
    }


}
