package ru.incrementstudio.incapi.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Button extends Item {
    public Button(ItemStack itemStack) {
        super(itemStack);
    }

    public abstract void onClick(InventoryClickEvent event);
}
