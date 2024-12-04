package ru.incrementstudio.incapi.menu.content;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.menu.Page;

public class Item implements Cloneable {
    private ItemStack itemStack;

    public Item(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Item(Material material) {
        this(new ItemStack(material));
    }

    public ItemStack get() {
        return itemStack;
    }

    public boolean onPlace(Page page, int slot) {
        return true;
    }

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            clone.itemStack = itemStack.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}