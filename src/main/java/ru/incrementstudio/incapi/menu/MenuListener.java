package ru.incrementstudio.incapi.menu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;
        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder instanceof PageInventoryHolder) {
            event.setCancelled(true);
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
            Page page = pageInventoryHolder.getPage();
            if (event.getCurrentItem() != null) {
                int slot = event.getSlot();
                Item itemData = page.getDisplay().getItems()[slot];
                if (itemData instanceof Button) {
                    Button button = (Button) itemData;
                    button.onClick(event);
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder instanceof PageInventoryHolder)
            event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        InventoryHolder inventoryHolder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        if (inventoryHolder instanceof PageInventoryHolder)
            event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();
        if (inventoryHolder instanceof PageInventoryHolder) {
            PageInventoryHolder pageInventoryHolder = (PageInventoryHolder) inventoryHolder;
//            Player player = (Player) event.getPlayer();
//            boolean isReopen = false;
//            if (page.getViewers().get(player) != null && page.getViewers().get(player).getData() != null) {
//                if (page.getViewers().get(player).getData().containsKey("reopen")) {
//                    isReopen = (boolean) page.getViewers().get(player).getData().get("reopen");
//                }
//                if (!isReopen) {
//                    if (page.getViewers().get(player).getData().containsKey("task")) {
//                        if (page.getViewers().get(player).getData().get("task") instanceof BukkitTask) {
//                            BukkitTask task = (BukkitTask) page.getViewers().get(player).getData().get("task");
//                            if (task != null)
//                                task.cancel();
//                        }
//                    }
//                    if (page.getEndFunctions().get(player) != null) {
//                        page.getEndFunctions().get(player).accept(page.getViewers().get(player));
//                        page.getEndFunctions().remove(player);
//                    }
//                }
//            }
//            if (!isReopen) {
//                page.getViewers().remove(player);
//                if (page.getViewers().isEmpty()) {
//                    MenuListener.pages.remove(page);
//                }
//            } else {
//                if (page.getViewers().get(player) != null && page.getViewers().get(player).getData() != null) {
//                    page.getViewers().get(player).removeData("reopen");
//                }
//            }
        }
    }
}
