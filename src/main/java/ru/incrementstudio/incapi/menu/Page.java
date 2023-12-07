package ru.incrementstudio.incapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Page {
    private Display display;
    private String title;
    private Inventory inventory;
    private Menu menu;
    private final Data data = new Data();

    public Page(Menu menu) {
        this.menu = menu;
        title = "";
        setDisplay(new Display(54));
    }
    public Page(Menu menu, int size) {
        this.menu = menu;
        title = "";
        setDisplay(new Display(size));
    }
    public Page(Menu menu, String title, int size) {
        this.menu = menu;
        this.title = title;
        setDisplay(new Display(size));
    }
    public Page(Menu menu, String title, Display display) {
        this.menu = menu;
        this.title = title;
        setDisplay(display);
    }

    public Page setTitle(String title) {
        this.title = title;
        return this;
    }
    public Page setDisplay(Display display) {
        if (display == null)
            throw new NullPointerException("Display is null");
        this.display = display;
        return this;
    }
    public Page overlayDisplay(Display display) {
        for (int i = 0; i < Math.min(getSize(), display.getSize()); i++) {
            if (display.getItems()[i] == null
                    || display.getItems()[i].getItemStack().getType() == Material.AIR)
                continue;
            this.display.setSlot(display.getItems()[i], i);
        }
        return this;
    }
    public Page underlayDisplay(Display display) {
        for (int i = 0; i < Math.min(getSize(), display.getSize()); i++) {
            if ((this.display.getItems()[i] != null && this.display.getItems()[i].getItemStack().getType() != Material.AIR)
                    || (display.getItems()[i] == null || display.getItems()[i].getItemStack().getType() == Material.AIR))
                continue;
            this.display.setSlot(display.getItems()[i], i);
        }
        return this;
    }
    public Page apply() {
        inventory = Bukkit.createInventory(new PageInventoryHolder(this), getSize(), title);
        for (int i = 0; i < getSize(); i++) {
            Item item = display.getItems()[i];
            if (item == null) continue;
            inventory.setItem(i, item.getItemStack());
        }
        return this;
    }
    public Page setData(String key, Object value) {
        data.setData(key, value);
        return this;
    }
    public Page setSlot(Item item, int slot) {
        display.setSlot(item, slot);
        return this;
    }
    public Page setSlot(ItemStack itemStack, int slot) {
        display.setSlot(itemStack, slot);
        return this;
    }
    public Page setSlots(Item item, int... slots) {
        display.setSlots(item, slots);
        return this;
    }
    public Page setSlots(ItemStack itemStack, int... slots) {
        display.setSlots(itemStack, slots);
        return this;
    }

    public void show(Player player) {
        player.openInventory(inventory);
    }

    public Display getDisplay() {
        return display;
    }
    public Menu getMenu() {
        return menu;
    }
    public int getSize() {
        return display.getSize();
    }
    public String getTitle() {
        return title;
    }
    public Data getData() {
        return data;
    }
    public Inventory getInventory() {
        return inventory;
    }
}
