package ru.incrementstudio.incapi.menu.holders.impl;

import ru.incrementstudio.incapi.menu.menus.impl.page.Page;
import ru.incrementstudio.incapi.menu.holders.IncInventoryHolder;

public class PageInventoryHolder extends IncInventoryHolder {
    private final Page page;

    public PageInventoryHolder(boolean canClick, boolean canDrag, boolean canDrop, Page page) {
        super(canClick, canDrag, canDrop, page.getMenu());
        this.page = page;
    }

    public Page getPage() {
        return page;
    }
}
