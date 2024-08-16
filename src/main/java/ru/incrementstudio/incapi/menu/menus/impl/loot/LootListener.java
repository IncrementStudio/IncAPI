package ru.incrementstudio.incapi.menu.menus.impl.loot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.IncPlugin;
import ru.incrementstudio.incapi.menu.holders.impl.LootInventoryHolder;

public class LootListener implements Listener {

    @EventHandler
    public void onClickItemInLootMenu(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        ItemStack item = event.getCurrentItem();
        if (inventory.getHolder() instanceof LootInventoryHolder) {
            LootInventoryHolder holder = (LootInventoryHolder) inventory.getHolder();
            LootMenu lootMenu = holder.getMenu();
            event.setCancelled(false);
            if (item != null && !item.getType().isAir() && event.getClickedInventory() == inventory) {
                if (event.isRightClick()) {
                    event.setCancelled(true);
                    lootMenu.showEditMenu(player, event.getSlot());
                }
            }
            Bukkit.getScheduler().runTask(IncPlugin.getInstance(), lootMenu::save);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (inventory.getHolder() instanceof LootInventoryHolder) {
            LootInventoryHolder holder = (LootInventoryHolder) inventory.getHolder();
            LootMenu lootMenu = holder.getMenu();
            event.setCancelled(false);
            Bukkit.getScheduler().runTask(IncPlugin.getInstance(), lootMenu::save);
        }
    }
}
