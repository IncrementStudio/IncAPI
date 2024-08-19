package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryHolder;
import ru.incrementstudio.incapi.menu.holders.IncInventoryHolder;
import ru.incrementstudio.incapi.menu.holders.impl.PageInventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof IncInventoryHolder) {
            IncInventoryHolder incInventoryHolder = (IncInventoryHolder) inventoryHolder;
            if (!incInventoryHolder.isCanDrag()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        InventoryHolder inventoryHolder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof IncInventoryHolder) {
            IncInventoryHolder incInventoryHolder = (IncInventoryHolder) inventoryHolder;
            if (!incInventoryHolder.isCanDrop()) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof IncInventoryHolder) {
            IncInventoryHolder incInventoryHolder = (IncInventoryHolder) inventoryHolder;
            if (event.getReason() == InventoryCloseEvent.Reason.PLAYER)
                incInventoryHolder.getMenu().onPlayerClose((Player) event.getPlayer(), event);
            if (inventoryHolder instanceof PageInventoryHolder) {
                PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
                pageInventoryHolder.getPage().getViewers().remove((Player) event.getPlayer());
            }
            incInventoryHolder.getMenu().getViewers().remove((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof IncInventoryHolder) {
            IncInventoryHolder incInventoryHolder = (IncInventoryHolder) inventoryHolder;
            incInventoryHolder.getMenu().onPlayerOpen((Player) event.getPlayer(), event);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        InventoryHolder inventoryHolder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (inventoryHolder == null) return;
        if (inventoryHolder instanceof IncInventoryHolder) {
            IncInventoryHolder incInventoryHolder = (IncInventoryHolder) inventoryHolder;
            if (inventoryHolder instanceof PageInventoryHolder) {
                PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
                pageInventoryHolder.getPage().getViewers().remove(event.getPlayer());
            }
            incInventoryHolder.getMenu().getViewers().remove(event.getPlayer());
        }
    }
}
