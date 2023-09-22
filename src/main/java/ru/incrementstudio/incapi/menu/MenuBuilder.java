package ru.incrementstudio.incapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class MenuBuilder {
    private Item[] map;
    private int size;
    private String title;

    public MenuBuilder(int size) {
        this.size = size;
        this.title = "";
        map = new Item[size];
    }

    public MenuBuilder(int size, String title) {
        this.size = size;
        this.title = title;
        map = new Item[size];
    }

    public Item[] getMap() { return map; }
    public int getSize() { return size; }
    public String getTitle() { return title; }

    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, size, title);
        for (int i = 0; i < size; i++) {
            Item item = map[i];
            if (item == null) continue;
            inventory.setItem(i, item.getItem());
        }
        return inventory;
    }

    public MenuBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MenuBuilder setSlot(int slot, Item item) {
        map[slot] = item;
        return this;
    }

    public MenuBuilder clear() {
        Arrays.fill(map, null);
        return this;
    }

    public MenuBuilder overlay(MenuBuilder builder) {
        for (int i = 0; i < Math.min(map.length, builder.getMap().length); i++) {
            if (builder.getMap()[i] == null) continue;
            map[i] = builder.getMap()[i];
        }
        return this;
    }
}
