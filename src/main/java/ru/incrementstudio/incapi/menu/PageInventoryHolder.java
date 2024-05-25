package ru.incrementstudio.incapi.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PageInventoryHolder implements InventoryHolder {
    private Page page;
    public Page getPage() {
        return page;
    }

    public PageInventoryHolder(Page page) {
        this.page = page;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
