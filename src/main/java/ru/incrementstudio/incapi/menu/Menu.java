package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final List<Page> pages = new ArrayList<>();
    public final List<Page> getPages() {
        return pages;
    }
    public final Page getPage(int index) {
        return pages.get(index);
    }
    private final Map<Player, Data> viewers = new HashMap<>();
    public final Map<Player, Data> getViewers() {
        return viewers;
    }

    public final void show(Player player) {
        show(player, 0);
    }
    public final void show(Player player, Data data) {
        show(player, 0, data);
    }
    public final void show(Player player, int page) {
        show(player, page, new Data());
    }
    public final void show(Player player, int page, Data data) {
        viewers.put(player, data);
        getPage(page).show(player);
    }
    public final void reopenAll() {
        for (Page page : pages) {
            page.reopenAll();
        }
    }

    public final Menu addPage(Page page) {
        page.setMenu(this);
        pages.add(page);
        return this;
    }
    public final Menu insertPage(Page page, int index) {
        page.setMenu(this);
        pages.add(index, page);
        return this;
    }
    public final Menu setPage(int index, Page page) {
        page.setMenu(this);
        pages.set(index, page);
        return this;
    }
    public final Menu removePage(Page page) {
        pages.remove(page);
        return this;
    }
    public final Menu removePage(int index) {
        pages.remove(index);
        return this;
    }
    public final Menu clearPages() {
        pages.clear();
        return this;
    }

    public void onPlayerClose(Player player, InventoryCloseEvent event) { }
}
