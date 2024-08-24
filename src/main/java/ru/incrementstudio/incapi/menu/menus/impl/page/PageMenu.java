package ru.incrementstudio.incapi.menu.menus.impl.page;

import org.bukkit.entity.Player;
import ru.incrementstudio.incapi.IncPlugin;
import ru.incrementstudio.incapi.menu.Data;
import ru.incrementstudio.incapi.menu.menus.Menu;

import java.util.ArrayList;
import java.util.List;

public class PageMenu extends Menu {
    private final List<Page> pages = new ArrayList<>();

    public PageMenu(IncPlugin plugin) {
        super(plugin);
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
        getViewers().put(player, data);
        getPage(page).show(player);
    }

    public final void reopenAll() {
        for (Page page : pages) {
            page.reopenAll();
        }
    }

    public final Page getPage(int index) {
        return pages.get(index);
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

    public List<Page> getPages() {
        return pages;
    }
}
