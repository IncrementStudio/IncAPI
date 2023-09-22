package ru.incrementstudio.incapi.menu;

import org.bukkit.inventory.ItemStack;

public class Item {
    private ItemStack item;
    public ItemStack getItem() { return item; }

    public Item(ItemStack item) {
        this.item = item;
    }
}
