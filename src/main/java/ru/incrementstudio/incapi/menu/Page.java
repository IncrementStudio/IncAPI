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
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.scheduler.BukkitTask;
import ru.incrementstudio.incapi.utils.ColorUtil;

import java.util.*;
import java.util.function.Consumer;

public class Page {
    private Inventory inventory;
    private Display display;
    private int size;
    private String title;

    private final List<Data> data = new ArrayList<>();
    private final Map<Player, Data> viewers = new HashMap<>();
    private final Map<Player, Consumer<Data>> endFunctions = new HashMap<>();

    public boolean reopen = false;

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
        System.out.println(ColorUtil.toColor("&aВызвался метод show(Player player)"));
        if (viewers.containsKey(player)) {
            System.out.println("Игрок уже есть во вьюверах. Добавляем ему reopen - true");
            viewers.get(player).addData("reopen", true);
            System.out.println("После этого Data игрока: " + viewers.get(player).getData());
        } else {
            System.out.println("Игрока нет во вьюверах. Добавляем...");
            viewers.put(player, new Data());
        }
        if (!MenuListener.pages.contains(this)) {
            System.out.println("Menu нет в листе. Добавляем...");
            MenuListener.pages.add(this);
        }
        System.out.println("После добавления в MenuListener.pages: " + ColorUtil.disableColor(MenuListener.pages.toString()));
        System.out.println("Вьюверы этой страницы: " + viewers);
        player.openInventory(inventory);
        System.out.println("Открыли инвентарь игроку баккитовским способом");
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
            if (endFunctions.get(player) != null) {
                endFunctions.get(player).accept(viewers.get(player));
                endFunctions.remove(player);
            }
            viewers.remove(player);
        }
        viewers.put(player, data);
        if (!MenuListener.pages.contains(this)) MenuListener.pages.add(this);
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
            if (endFunctions.get(player) != null) {
                endFunctions.get(player).accept(viewers.get(player));
                endFunctions.remove(player);
            }
            viewers.remove(player);
        }
        start.accept(data);
        endFunctions.put(player, end);
        viewers.put(player, data);
        if (!MenuListener.pages.contains(this)) MenuListener.pages.add(this);
        player.openInventory(inventory);
    }

    public Page apply() {
        if (title == null) title = "";
        if (size % 9 != 0 || size < 9 || size > 54) size = 54;
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

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Player, Consumer<Data>> getEndFunctions() {
        return endFunctions;
    }
    @Override
    public String toString() {
        return "Page{" +
                "inventory=" + inventory +
                ", display=" + display +
                ", size=" + size +
                ", title='" + title + '\'' +
                ", data=" + data +
                ", viewers=" + viewers +
                ", endFunctions=" + endFunctions +
                '}';
    }

}
