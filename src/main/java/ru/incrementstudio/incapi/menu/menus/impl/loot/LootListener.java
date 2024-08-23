package ru.incrementstudio.incapi.menu.menus.impl.loot;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.incrementstudio.incapi.menu.MenuListener;
import ru.incrementstudio.incapi.menu.holders.impl.LootInventoryHolder;
import ru.incrementstudio.incapi.utils.ColorUtil;

import java.util.Arrays;

public class LootListener extends MenuListener implements Listener {

    @EventHandler
    public void onClickItemInLootMenu(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        ItemStack item = event.getCurrentItem();
        if (inventory.getHolder() instanceof LootInventoryHolder) {
            LootInventoryHolder holder = (LootInventoryHolder) inventory.getHolder();
            if (holder.getLootMenuType() != LootMenuType.MAIN) return;
            LootMenu lootMenu = holder.getMenu();
            event.setCancelled(false);
            if (item != null && !item.getType().isAir() && event.getClickedInventory() == inventory) {
                if (event.isRightClick()) {
                    event.setCancelled(true);
                    lootMenu.showEditMenu(player, event.getSlot());
                }
            }
            Bukkit.getScheduler().runTask(holder.getMenu().getPlugin(), lootMenu::save);
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
            if (holder.getLootMenuType() != LootMenuType.MAIN) return;
            LootMenu lootMenu = holder.getMenu();
            event.setCancelled(false);
            Bukkit.getScheduler().runTask(holder.getMenu().getPlugin(), lootMenu::save);
        }
    }

    @EventHandler
    public void onClickChanceItem(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (inventory.getHolder() instanceof LootInventoryHolder) {
            LootInventoryHolder holder = (LootInventoryHolder) inventory.getHolder();
            if (holder.getLootMenuType() != LootMenuType.CHANCE) return;
            event.setCancelled(true);
            if (event.getSlot() == 13) {
                ItemStack itemStack = event.getCurrentItem();
                if (itemStack == null) return;
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) return;
                if (!itemMeta.getPersistentDataContainer().has(NamespacedKey.fromString("slot"), PersistentDataType.INTEGER)) return;
                int slot = itemMeta.getPersistentDataContainer().get(NamespacedKey.fromString("slot"), PersistentDataType.INTEGER);
                if (event.isLeftClick()) {
                    if (event.isShiftClick()) {
                        int chance = Math.min(100, Math.max(0, holder.getMenu().getLootSection().getInt(slot + ".chance", 100) + 10));
                        holder.getMenu().getLootSection().set(slot + ".chance", chance);
                    } else {
                        int chance = Math.min(100, Math.max(0, holder.getMenu().getLootSection().getInt(slot + ".chance", 100) + 1));
                        holder.getMenu().getLootSection().set(slot + ".chance", chance);
                    }
                } else if (event.isRightClick()) {
                    if (event.isShiftClick()) {
                        int chance = Math.min(100, Math.max(0, holder.getMenu().getLootSection().getInt(slot + ".chance", 100) - 10));
                        holder.getMenu().getLootSection().set(slot + ".chance", chance);
                    } else {
                        int chance = Math.min(100, Math.max(0, holder.getMenu().getLootSection().getInt(slot + ".chance", 100) - 1));
                        holder.getMenu().getLootSection().set(slot + ".chance", chance);
                    }
                }
                holder.getMenu().getConfig().save();
                itemMeta.setLore(ColorUtil.toColor(Arrays.asList(
                        "&fШанс предмета: &a" + holder.getMenu().getLootSection().getInt(slot + ".chance", 100) + "%",
                        "&fНажмите &eЛКМ&f, чтобы увеличить на &e1",
                        "&fНажмите &eПКМ&f, чтобы уменьшить на &e1",
                        "&fНажмите &eShift + ЛКМ&f, чтобы увеличить на &e10",
                        "&fНажмите &eShift + ПКМ&f, чтобы уменьшить на &e10"))
                );
                itemStack.setItemMeta(itemMeta);
                event.getClickedInventory().setItem(13, itemStack);
            }
        }
    }

    @EventHandler
    public void onPlayerClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player))
            return;
        Player player = (Player) event.getPlayer();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (inventory.getHolder() instanceof LootInventoryHolder) {
            LootInventoryHolder holder = (LootInventoryHolder) inventory.getHolder();
            if (holder.getLootMenuType() != LootMenuType.CHANCE) return;
            Bukkit.getScheduler().runTask(holder.getMenu().getPlugin(), () ->
                    holder.getMenu().show(player));
        }
    }
}
