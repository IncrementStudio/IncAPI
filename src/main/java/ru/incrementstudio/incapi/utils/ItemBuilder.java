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
    private boolean isColored;

    public ItemBuilder() {
        itemStack = new ItemStack(Material.STONE);
        itemMeta = itemStack.getItemMeta();
        isColored = true;
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
                if (isColored) resultLore.add(ColorUtil.toColor(line));
                else resultLore.add(line);
            }
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
                if (isColored) resultLore.add(ColorUtil.toColor(line));
                else resultLore.add(line);
            }
        }
        itemMeta.setLore(resultLore);
        return this;
    }

    public ItemBuilder setColored(boolean isColored) {
        this.isColored = isColored;
        return this;
    }


    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantments(Enchantment... enchantments) {
        itemStack.addUnsafeEnchantments(new HashMap<>(){{
            for (Enchantment enchantment: enchantments) {
                put(enchantment, 1);
            }
        }});
        return this;
    }

    public ItemBuilder addEnchantments(int level, Enchantment... enchantments) {
        itemStack.addUnsafeEnchantments(new HashMap<>(){{
            for (Enchantment enchantment: enchantments) {
                put(enchantment, level);
            }
        }});
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder removeEnchantments(Enchantment... enchantments) {
        for (Enchantment enchantment: enchantments) {
            itemStack.removeEnchantment(enchantment);
        }
        return this;
    }

    public ItemBuilder setGlowing(boolean isGlowing) {
        if (isGlowing) {
            itemStack.addEnchantment(Enchantment.LUCK, 1);
            itemStack.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            itemStack.removeEnchantment(Enchantment.LUCK);
            itemStack.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
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
        itemStack.addItemFlags(flags);
        return this;
    }

    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        itemStack.removeItemFlags(flags);
        return this;
    }

    public ItemBuilder addPersistentData(String key, PersistentDataType persistentDataType, Object value) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), persistentDataType, value);

        return this;
    }

    public ItemBuilder addPersistentData(PersistentData persistentData) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(persistentData.getKey()), persistentData.getPersistentDataType(), persistentData.getValue());
        return this;
    }

    public ItemBuilder addPersistentData(List<PersistentData> persistentData) {
        for (PersistentData persistentData1: persistentData)
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(persistentData1.getKey()), persistentData1.getPersistentDataType(), persistentData1.getValue());
        return this;
    }


    public ItemBuilder removePersistentData(String key) {
        itemMeta.getPersistentDataContainer().remove(NamespacedKey.fromString(key));
        return this;
    }

    public ItemBuilder addValue(String key, String value) {
        itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(key), PersistentDataType.STRING, value);
        return this;
    }

    public ItemBuilder addValues(HashMap<String, String> values) {
        for (Map.Entry<String, String> value: values.entrySet()) {
            itemMeta.getPersistentDataContainer().set(NamespacedKey.fromString(value.getKey()), PersistentDataType.STRING, value.getValue());
        }
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
