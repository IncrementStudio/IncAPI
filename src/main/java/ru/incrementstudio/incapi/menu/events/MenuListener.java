package ru.incrementstudio.incapi.menu.events;

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
import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.menu.Page;
import ru.incrementstudio.incapi.menu.PageHolder;
import ru.incrementstudio.incapi.menu.content.Button;
import ru.incrementstudio.incapi.menu.content.Item;
import ru.incrementstudio.incapi.menu.content.StaticItem;

import java.util.Map;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;
        InventoryHolder holder = inventory.getHolder();
        if (holder == null) return;
        if (holder instanceof PageHolder) {
            PageHolder pageHolder = (PageHolder) holder;
            Page page = pageHolder.getPage();
            if (!page.isClickable()) {
                event.setCancelled(true);
            }
            if (page.getClickMask() != null) {
                if (!page.getClickMask().check(event.getSlot())) {
                    event.setCancelled(true);
                }
            }
            if (event.getCurrentItem() != null) {
                int slot = event.getSlot();
                Item item = page.getDisplay().getSlot(slot);
                if (item instanceof StaticItem) {
                    event.setCancelled(true);
                    if (item instanceof Button) {
                        Button button = (Button) item;
                        button.onClick((Player) event.getWhoClicked(), event);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder == null) return;
        if (holder instanceof PageHolder) {
            PageHolder pageHolder = (PageHolder) holder;
            Page page = pageHolder.getPage();
            if (!page.isDraggable()) {
                event.setCancelled(true);
                return;
            }
            Map<Integer, ItemStack> items = event.getNewItems();
            for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
                int slot = entry.getKey();
                ItemStack item = entry.getValue();
                if (page.getClickMask() != null) {
                    event.setCancelled(true);
                    if (page.getDragMask().check(slot))
                        event.getInventory().setItem(slot, item);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        InventoryHolder holder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (holder == null) return;
        if (holder instanceof PageHolder) {
            PageHolder pageHolder = (PageHolder) holder;
            Page page = pageHolder.getPage();
            if (!page.isDroppable()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder == null) return;
        if (holder instanceof PageHolder) {
            PageHolder pageHolder = (PageHolder) holder;
            Page page = pageHolder.getPage();
            page.onClose((Player) event.getPlayer(), event);
            page.getMenu().onClose((Player) event.getPlayer(), event);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        InventoryHolder holder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (holder == null) return;
        if (holder instanceof PageHolder) {
            PageHolder pageHolder = (PageHolder) holder;
            Page page = pageHolder.getPage();
            page.close(event.getPlayer());
        }
    }
}
