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
import ru.incrementstudio.incapi.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuListener implements Listener {
    public static List<Page> pages = new ArrayList<>();
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        List<Page> tempPages = new ArrayList<>(pages);
        System.out.println("Вызвался слушатель onClick. Действие: " + event.getAction() + ". Клик: " + event.getClick() + ". Курсор: " + event.getCursor() + ". Предмет: " + (event.getCurrentItem() != null ? event.getCurrentItem().getType() : "null"));
        int i = 1;
        for (Page page: tempPages) {
            System.out.println(i + ". Просматриваем страницу (" + ColorUtil.disableColor(page.getTitle()) + "): " + ColorUtil.disableColor(page.toString()));
            System.out.println(event.getWhoClicked().getOpenInventory().getTopInventory());
            System.out.println(page.getInventory());
            if (event.getWhoClicked().getOpenInventory().getTopInventory() == page.getInventory()) {
                System.out.println("Нужная страница найдена!");
                event.setCancelled(true);
                ItemStack item = event.getCurrentItem();
                if (item != null) System.out.println("Предмет: " + item.getType());
                if (item != null) {
                    int slot = event.getSlot();
                    System.out.println("Нажатый слот: " + slot);
                    Item itemData = page.getDisplay().getItems()[slot];
                    if (itemData instanceof Button) {
                        System.out.println("Нашли нужную кнопку! - " + itemData);
                        Button button = (Button) itemData;
                        System.out.println("Вызываем метод onClick у кнопки button.onClick()");
                        button.onClick(event);
                        System.out.println("После метода onClick у кнопки button.onClick()");
                        break;
                    }
                    break;
                }
                break;
            }
        }
        System.out.println("Закончился слушатель onClick");
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        List<Page> tempPages = new ArrayList<>(pages);
        for (Page page: tempPages) {
            if (event.getWhoClicked().getOpenInventory().getTopInventory() == page.getInventory()) {
                event.setCancelled(true);
                break;
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        List<Page> tempPages = new ArrayList<>(pages);
        for (Page page : tempPages) {
            if (event.getPlayer().getOpenInventory().getTopInventory() == page.getInventory()) {
                event.setCancelled(true);
                break;
            }
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        List<Page> tempPages = new ArrayList<>(pages);
        for (Page page : tempPages) {
            Player player = (Player) event.getPlayer();
            if (event.getInventory() == page.getInventory()) {
                if (page.getViewers().get(player) != null && page.getViewers().get(player).getData() != null && page.getViewers().get(player).getData().containsKey("task")) {
                    if (page.getViewers().get(player).getData().get("task") instanceof BukkitTask) {
                        BukkitTask task = (BukkitTask) page.getViewers().get(player).getData().get("task");
                        if (task != null)
                            task.cancel();
                    }
                }
                if (page.getEndFunctions().get(player) != null) {
                    page.getEndFunctions().get(player).accept(page.getViewers().get(player));
                    page.getEndFunctions().remove(player);
                }
                System.out.println("Удаляем при закрытии инвентаря ");
                page.getViewers().remove(player);
                if (page.getViewers().isEmpty()) {
                    System.out.println("На этой странице нет вьеверов! Удаляем страницу...");
                    MenuListener.pages.remove(page);
                }
                break;
            }
        }
    }


}
