package ru.incrementstudio.incapi.menu.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import ru.incrementstudio.incapi.menu.Data;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu {
    private final Data data = new Data();
    private final Map<Player, Data> viewers = new HashMap<>();

    public abstract void show(Player player);

    public void onPlayerClose(Player player, InventoryCloseEvent event) {}
    public void onPlayerOpen(Player player, InventoryOpenEvent event) {}

    public final Data getData() {
        return data;
    }
    public final Map<Player, Data> getViewers() {
        return viewers;
    }
}
