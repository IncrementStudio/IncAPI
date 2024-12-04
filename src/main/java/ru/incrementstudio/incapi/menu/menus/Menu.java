package ru.incrementstudio.incapi.menu.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import ru.incrementstudio.incapi.menu.Data;

public abstract class Menu {
    private final Data data = new Data();

    public Data getData() {
        return data;
    }

    public void onOpen(Player player, InventoryOpenEvent event) {
    }

    public void onClose(Player player, InventoryCloseEvent event) {
    }

    public abstract Menu open(Player player);
}