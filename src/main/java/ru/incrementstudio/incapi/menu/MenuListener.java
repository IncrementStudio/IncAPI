package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
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


    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof PageInventoryHolder) {
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
            if (!pageInventoryHolder.isCanDrag()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        InventoryHolder inventoryHolder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof PageInventoryHolder) {
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
            if (!pageInventoryHolder.isCanDrop()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof PageInventoryHolder) {
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
            if (event.getReason() == InventoryCloseEvent.Reason.PLAYER)
                pageInventoryHolder.getPage().getMenu().onPlayerClose((Player) event.getPlayer(), event);
            pageInventoryHolder.getPage().getViewers().remove((Player) event.getPlayer());
            pageInventoryHolder.getPage().getMenu().getViewers().remove((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        InventoryHolder inventoryHolder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof PageInventoryHolder) {
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
            pageInventoryHolder.getPage().getViewers().remove(event.getPlayer());
            pageInventoryHolder.getPage().getMenu().getViewers().remove(event.getPlayer());
        }
    }
}
