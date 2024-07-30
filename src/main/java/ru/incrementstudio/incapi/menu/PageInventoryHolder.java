package ru.incrementstudio.incapi.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PageInventoryHolder implements InventoryHolder {
    private final Page page;
    private final boolean canClick;
    private final boolean canDrag;
    private final boolean canDrop;

    public PageInventoryHolder(Page page, boolean canClick, boolean canDrag, boolean canDrop) {
        this.page = page;
        this.canClick = canClick;
        this.canDrag = canDrag;
        this.canDrop = canDrop;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    public boolean isCanDrop() {
        return canDrop;
    }

    public Page getPage() {
        return page;
    }
}
