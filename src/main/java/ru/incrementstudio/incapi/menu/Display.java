package ru.incrementstudio.incapi.menu;

import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.menu.elements.Item;

public class Display {
    private final Item[] items;
    public Item[] getItems() {
        return items;
    }

    public Display(int size) {
        items = new Item[size];
    }

    public Display setSlot(ItemStack itemStack, int slot) {
        setSlot(new Item(itemStack), slot);
        return this;
    }
    public Display setSlot(Item item, int slot) {
        if (slot < 0 || slot >= items.length)
            throw new IllegalArgumentException("Incorrect slot");
        items[slot] = item;
        return this;
    }
    public Display setSlots(ItemStack itemStack, int... slots) {
        setSlots(new Item(itemStack), slots);
        return this;
    }
    public Display setSlots(Item item, int... slots) {
        for (int slot : slots)
            setSlot(item, slot);
        return this;
    }

    public int getSize() {
        return items.length;
    }
}
