package ru.incrementstudio.incapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Page implements Listener {
    private Inventory inventory;
    private Display display;
    private int size;
    private String title;

    private final List<Data> data = new ArrayList<>();
    private final Map<Player, Data> viewers = new HashMap<>();
    private Consumer<Data> endFunction;


    public Page() {
        this.size = 54;
        display = new Display(size);
    }

    public Page(int size) {
        this.size = size;
        display = new Display(size);
    }

    public Page(int size, String title) {
        this.size = size;
        this.title = title;
        display = new Display(size);
    }

    public Page setSize(int size) {
        this.size = size;
        Display oldDisplay = display;
        display = new Display(size);
        setDisplay(oldDisplay);
        return this;
    }

    public Page setTitle(String title) {
        this.title = title;
        return this;
    }

    public Page setSlot(Item item, int slot) {
        if (slot < display.getItems().length) display.setSlot(item, slot);
        return this;
    }

    public Page setSlot(Item item, int... slots) {
        for (int slot: slots) {
            if (slot < display.getItems().length) display.setSlot(item, slot);
        }
        return this;
    }

    public Page setDisplay(Display display) {
        for (int i = 0; i < Math.min(this.display.getItems().length, display.getItems().length); i++) {
            this.display.getItems()[i] = display.getItems()[i];
        }
        return this;
    }

    public Page clearDisplay() {
        for (int i = 0; i < size; i++) {
            display.setSlot(null, i);
        }
        return this;
    }

    public Page overlayDisplay(Display display) {
        for (int i = 0; i < Math.min(this.display.getItems().length, display.getItems().length); i++) {
            if (display.getItems()[i] == null) continue;
            this.display.getItems()[i] = display.getItems()[i];
        }
        return this;
    }

    public Page underlayDisplay(Display display) {
        for (int i = 0; i < Math.min(this.display.getItems().length, display.getItems().length); i++) {
            if (this.display.getItems()[i] != null) continue;
            this.display.getItems()[i] = display.getItems()[i];
        }
        return this;
    }

    public void show(Player player) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player).getData().containsKey("task")) {
                if (viewers.get(player).getData().get("task") instanceof BukkitTask) {
                    BukkitTask task = (BukkitTask) viewers.get(player).getData().get("task");
                    if (task != null)
                        task.cancel();
                }
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        viewers.put(player, new Data());
        player.openInventory(inventory);
    }

    public void show(Player player, Data data) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player).getData().containsKey("task")) {
                if (viewers.get(player).getData().get("task") instanceof BukkitTask) {
                    BukkitTask task = (BukkitTask) viewers.get(player).getData().get("task");
                    if (task != null)
                        task.cancel();
                }
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        viewers.put(player, data);
        player.openInventory(inventory);
    }

    public void show(Player player, Data data, Consumer<Data> start, Consumer<Data> end) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player).getData().containsKey("task")) {
                if (viewers.get(player).getData().get("task") instanceof BukkitTask) {
                    BukkitTask task = (BukkitTask) viewers.get(player).getData().get("task");
                    if (task != null)
                        task.cancel();
                }
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        start.accept(data);
        endFunction = end;
        viewers.put(player, data);
        player.openInventory(inventory);
    }

    public Page apply() {
        if (title == null) title = "";
        if (size % 9 != 0) size = 54;
        inventory = Bukkit.createInventory(null, size, title);
        for (int i = 0; i < size; i++) {
            Item item = display.getItems()[i];
            if (item == null) continue;
            inventory.setItem(i, item.getItemStack());
        }
        return this;
    }

    public Page addData(Data data) {
        this.data.add(data);
        return this;
    }

    public Page registerListener(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        return this;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTopInventory() == inventory) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item != null) {
                int slot = event.getSlot();
                Item itemData = display.getItems()[slot];
                if (itemData instanceof Button) {
                    Button button = (Button) itemData;
                    button.onClick(event);
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTopInventory() == inventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getOpenInventory().getTopInventory() == inventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (event.getInventory() == inventory) {
            if (viewers.get(player).getData().containsKey("task")) {
                if (viewers.get(player).getData().get("task") instanceof BukkitTask) {
                    BukkitTask task = (BukkitTask) viewers.get(player).getData().get("task");
                    if (task != null)
                        task.cancel();
                }
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (viewers.containsKey(player)) {
            if (viewers.get(player).getData().containsKey("task")) {
                if (viewers.get(player).getData().get("task") instanceof BukkitTask) {
                    BukkitTask task = (BukkitTask) viewers.get(player).getData().get("task");
                    if (task != null)
                        task.cancel();
                }
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
    }

    public Map<Player, Data> getViewers() {
        return viewers;
    }

    public Display getDisplay() {
        return display;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }

    public List<Data> getData() {
        return data;
    }

    public Consumer<Data> getEndFunction() {
        return endFunction;
    }


}
