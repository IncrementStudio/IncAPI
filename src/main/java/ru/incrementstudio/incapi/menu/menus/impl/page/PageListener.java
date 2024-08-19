package ru.incrementstudio.incapi.menu.menus.impl.page;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import ru.incrementstudio.incapi.menu.MenuListener;
import ru.incrementstudio.incapi.menu.elements.Item;
import ru.incrementstudio.incapi.menu.elements.impl.Button;
import ru.incrementstudio.incapi.menu.holders.impl.PageInventoryHolder;

public class PageListener extends MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;
        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof PageInventoryHolder) {
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
            if (!pageInventoryHolder.isCanClick()) event.setCancelled(true);
            Page page = pageInventoryHolder.getPage();
            if (event.getCurrentItem() != null) {
                int slot = event.getSlot();
                Item itemData = page.getDisplay().getItems()[slot];
                if (itemData instanceof Button) {
                    Button button = (Button) itemData;
                    button.onClick((Player) event.getWhoClicked(), event);
                }
            }
        }
    }
}
