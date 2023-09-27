package ru.incrementstudio.incapi.menu;

public class Display {
    private final Item[] items;

    public Display(int size) {
        items = new Item[size];
    }

    public Display setSlot(Item item, int slot) {
        if (slot < items.length) items[slot] = item;
        return this;
    }

    public Display setSlot(Item item, int... slots) {
        for (int slot: slots) {
            if (slot < items.length) items[slot] = item;
        }
        return this;
    }

    public Item[] getItems() {
        return items;
    }
}
