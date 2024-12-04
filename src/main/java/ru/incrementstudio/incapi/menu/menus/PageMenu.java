package ru.incrementstudio.incapi.menu.menus;

import org.bukkit.entity.Player;
import ru.incrementstudio.incapi.menu.Page;
import ru.incrementstudio.incapi.menu.ViewersMap;

public class PageMenu extends Menu {
    private final Page page;

    public PageMenu(Page page) throws IllegalArgumentException {
        if (!page.onAdd(this))
            throw new IllegalArgumentException();
        this.page = page;
    }

    public ViewersMap getViewers() {
        return new ViewersMap(page.getViewers());
    }

    public Page getPage() {
        return page;
    }

    @Override
    public Menu open(Player player) {
        page.open(player);
        return this;
    }
}