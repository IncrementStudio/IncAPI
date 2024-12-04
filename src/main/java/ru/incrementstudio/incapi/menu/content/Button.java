package ru.incrementstudio.incapi.menu.content;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Button extends StaticItem {
    public Button(ItemStack itemStack) {
        super(itemStack);
    }

    public Button(Material material) {
        super(material);
    }

    public abstract void onClick(Player player, InventoryClickEvent event);

    @Override
    public Button clone() {
        return (Button) super.clone();
    }
}
