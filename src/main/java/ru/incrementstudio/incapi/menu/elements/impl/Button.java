package ru.incrementstudio.incapi.menu.elements.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.menu.elements.Item;

public abstract class Button extends Item {
    public Button(ItemStack itemStack) {
        super(itemStack);
    }

    public abstract void onClick(Player player, InventoryClickEvent event);

}
