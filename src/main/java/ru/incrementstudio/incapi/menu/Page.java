package ru.incrementstudio.incapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.incrementstudio.incapi.menu.content.Item;
import ru.incrementstudio.incapi.menu.menus.Menu;

public class Page {
    private String title;
    private Display display;
    private PageHolder holder;
    private Inventory inventory;
    private final Data data = new Data();
    private final ViewersMap viewers = new ViewersMap();
    private Menu menu;
    private boolean clickable = true, draggable = true, droppable = true;
    private Mask clickMask, dragMask;

    public Page() {
        this(54);
    }

    public Page(int size) {
        this("", size);
    }

    public Page(String title, int size) {
        this(title, size, new PageHolder());
    }

    public Page(String title, Display display) {
        this(title, display, new PageHolder());
    }

    public Page(String title, int size, PageHolder holder) {
        this(title, new Display(size), holder);
    }

    public Page(String title, Display display, PageHolder holder) {
        this.title = title;
        this.display = display;
        holder.setPage(this);
        this.holder = holder;
        apply();
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return display.getSize();
    }

    public PageHolder getHolder() {
        return holder;
    }

    public Display getDisplay() {
        return display;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Data getData() {
        return data;
    }

    public ViewersMap getViewers() {
        return new ViewersMap(viewers);
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean isClickable() {
        return clickable;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public boolean isDroppable() {
        return droppable;
    }

    public Mask getClickMask() {
        return clickMask;
    }

    public Mask getDragMask() {
        return dragMask;
    }

    public Page open(@NotNull Player player) {
        viewers.put(player, new Data());
        player.openInventory(inventory);
        return this;
    }

    public Page close(@NotNull Player player) {
        if (viewers.containsKey(player)) {
            viewers.remove(player);
            player.closeInventory();
        }
        return this;
    }

    public Page reopen(Player player) {
        if (viewers.containsKey(player)) {
            player.openInventory(inventory);
        }
        return this;
    }

    public Page reopen() {
        for (Player player : viewers.keySet())
            player.openInventory(inventory);
        return this;
    }

    public Page setTitle(@NotNull String title) {
        this.title = title;
        apply();
        return this;
    }

    public Page setSize(int size) {
        display.setSize(size);
        apply();
        return this;
    }

    public Page setHolder(@NotNull PageHolder holder) {
        this.holder.setPage(null);
        holder.setPage(this);
        this.holder = holder;
        apply();
        return this;
    }

    public Page setSlot(@Nullable Item item, int slot) {
        this.display.setSlot(item, slot);
        update();
        return this;
    }

    public Page setSlots(@Nullable Item item, int... slots) {
        this.display.setSlots(item, slots);
        update();
        return this;
    }

    public Page setSlots(@NotNull Display display) {
        this.display = display.clone();
        update();
        return this;
    }

    public Page setClickable(boolean clickable) {
        this.clickable = clickable;
        return this;
    }

    public Page setDraggable(boolean draggable) {
        this.draggable = draggable;
        return this;
    }

    public Page setDroppable(boolean droppable) {
        this.droppable = droppable;
        return this;
    }

    public Page setClickMask(Mask mask) {
        clickMask = mask;
        return this;
    }

    public Page setDragMask(Mask mask) {
        dragMask = mask;
        return this;
    }

    private void update() {
        for (int i = 0; i < display.getSize(); i++) {
            Item item = display.getSlot(i);
            if (item == null)
                inventory.setItem(i, null);
            else {
                Item newItem = item.clone();
                if (newItem.onPlace(this, i)) {
                    inventory.setItem(i, newItem.get());
                }
            }
        }
    }

    private void apply() {
        inventory = Bukkit.createInventory(holder, getSize(), getTitle());
        update();
    }

    public final void onClose(Player player, InventoryCloseEvent event) {
        viewers.remove(player);
        onPageClose(player, event);
    }

    public void onPageClose(Player player, InventoryCloseEvent event) {
    }

    public final boolean onAdd(Menu menu) {
        if (this.menu != null)
            return this.menu == menu;
        this.menu = menu;
        return true;
    }
}