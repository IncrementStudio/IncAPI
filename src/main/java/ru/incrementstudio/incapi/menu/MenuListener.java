package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class MenuListener implements Listener {
    public static List<Menu> menus = new ArrayList<>();
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        for (Menu menu: menus) {
            for (Page page: menu.getPages()) {
                if (event.getWhoClicked().getOpenInventory().getTopInventory() == page.getInventory()) {
                    event.setCancelled(true);
                    ItemStack item = event.getCurrentItem();
                    if (item != null) {
                        int slot = event.getSlot();
                        Item itemData = page.getDisplay().getItems()[slot];
                        if (itemData instanceof Button) {
                            Button button = (Button) itemData;
                            button.onClick(event);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        for (Menu menu: menus) {
            for (Page page : menu.getPages()) {
                if (event.getWhoClicked().getOpenInventory().getTopInventory() == page.getInventory()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        for (Menu menu: menus) {
            for (Page page : menu.getPages()) {
                if (event.getPlayer().getOpenInventory().getTopInventory() == page.getInventory()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        for (Menu menu: menus) {
            for (Page page : menu.getPages()) {
                Player player = (Player) event.getPlayer();
                if (event.getInventory() == page.getInventory()) {
                    if (page.getViewers().get(player).getData().containsKey("task")) {
                        if (page.getViewers().get(player).getData().get("task") instanceof BukkitTask) {
                            BukkitTask task = (BukkitTask) page.getViewers().get(player).getData().get("task");
                            if (task != null)
                                task.cancel();
                        }
                    }
                    if (page.getEndFunction() != null) {
                        page.getEndFunction().accept(page.getViewers().get(player));
                        page.setEndFunction(null);
                    }
                    page.getViewers().remove(player);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        for (Menu menu: menus) {
            for (Page page : menu.getPages()) {
                Player player = event.getPlayer();
                if (page.getViewers().containsKey(player)) {
                    if (page.getViewers().get(player).getData().containsKey("task")) {
                        if (page.getViewers().get(player).getData().get("task") instanceof BukkitTask) {
                            BukkitTask task = (BukkitTask) page.getViewers().get(player).getData().get("task");
                            if (task != null)
                                task.cancel();
                        }
                    }
                    if (page.getEndFunction() != null) {
                        page.getEndFunction().accept(page.getViewers().get(player));
                        page.setEndFunction(null);
                    }
                    page.getViewers().remove(player);
                }
            }
        }
    }


}
