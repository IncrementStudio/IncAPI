package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final List<Page> pages = new ArrayList<>();
    public List<Page> getPages() {
        return pages;
    }
    public Page getPage(int index) {
        return pages.get(index);
    }
    private final Map<Player, Data> viewers = new HashMap<>();
    public Map<Player, Data> getViewers() {
        return viewers;
    }

    public void show(Player player) {
        show(player, 0);
    }
    public void show(Player player, Data data) {
        show(player, 0, data);
    }
    public void show(Player player, int page) {
        show(player, page, new Data());
    }
    public void show(Player player, int page, Data data) {
        viewers.put(player, data);
        getPage(page).show(player);
    }

    public Menu addPage(Page page) {
        pages.add(page);
        return this;
    }
    public Menu insertPage(Page page, int index) {
        pages.add(index, page);
        return this;
    }
    public Menu setPage(int index, Page page) {
        pages.set(index, page);
        return this;
    }
    public Menu removePage(Page page) {
        pages.remove(page);
        return this;
    }
    public Menu removePage(int index) {
        pages.remove(index);
        return this;
    }
    public Menu clearPages() {
        pages.clear();
        return this;
    }
}
