package ru.incrementstudio.incapi.menu.elements;

import org.bukkit.inventory.ItemStack;

public class Item {
    private final ItemStack itemStack;
    public ItemStack getItemStack() { return itemStack; }

    public Item(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
