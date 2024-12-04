package ru.incrementstudio.incapi.menu.content;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.menu.Page;

public class StaticItem extends Item {
    private Page page;
    private int slot;

    public StaticItem(ItemStack itemStack) {
        super(itemStack);
    }

    public StaticItem(Material material) {
        super(material);
    }

    public Page getPage() {
        return page;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public final boolean onPlace(Page page, int slot) {
        if (this.page != null)
            return this.page == page && this.slot == slot;
        this.page = page;
        this.slot = slot;
        return true;
    }

    @Override
    public StaticItem clone() {
        return (StaticItem) super.clone();
    }
}
