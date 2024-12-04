package ru.incrementstudio.incapi.menu;

import ru.incrementstudio.incapi.menu.content.Item;

import java.util.Arrays;

public class Display implements Cloneable {
    private Item[] items;

    public Display(int size) {
        items = new Item[size];
    }

    public Display(Display base) {
        items = base.clone().getSlots();
    }

    public int getSize() {
        return items.length;
    }

    public Item[] getSlots() {
        return items;
    }

    public Item getSlot(int slot) {
        return items[slot];
    }

    public Display setSize(int size) {
        Item[] old = items;
        items = new Item[size];
        for (int i = 0; i < Math.min(old.length, size); i++) {
            items[i] = old[i];
        }
        return this;
    }

    public Display setSlot(Item item, int slot) {
        if (slot < 0 || slot >= items.length)
            throw new IllegalArgumentException("Incorrect slot");
        if (item == null || item.get().getType().isAir())
            items[slot] = null;
        else
            items[slot] = item.clone();
        return this;
    }

    public Display setSlots(Item item, int... slots) {
        for (int slot : slots)
            setSlot(item, slot);
        return this;
    }

    public Display setSlots(Display display) {
        for (int i = 0; i < Math.min(getSize(), display.getSize()); i++) {
            Item item = display.getSlot(i);
            setSlot(item, i);
        }
        return this;
    }

    public Display overlay(Display display) {
        for (int i = 0; i < Math.min(getSize(), display.getSize()); i++) {
            Item item = display.getSlot(i);
            if (item == null) continue;
            setSlot(item, i);
        }
        return this;
    }

    public Display underlay(Display display) {
        for (int i = 0; i < Math.min(getSize(), display.getSize()); i++) {
            Item item = display.getSlot(i);
            if (item == null) continue;
            Item old = getSlot(i);
            if (old != null) continue;
            setSlot(item, i);
        }
        return this;
    }

    @Override
    public Display clone() {
        try {
            Display clone = (Display) super.clone();
            clone.items = Arrays.stream(items)
                    .map(Item::clone)
                    .toArray(Item[]::new);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}