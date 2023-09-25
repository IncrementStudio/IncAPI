package ru.incrementstudio.incapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class Menu implements Listener {
    public BukkitTask DYNAMIC_UPDATE(Player viewer, Plugin plugin, long time) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                reopen(viewer);
            }
        }.runTaskTimer(plugin, time, time);
    }
    public BukkitTask ANIMATED_TITLE(Player viewer, List<String> titles, Plugin plugin, long time) {
        return new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i == titles.size())
                    i = 0;
                builder.setTitle(titles.get(i));
                apply();
                reopen(viewer);
                i++;
            }
        }.runTaskTimer(plugin, time, time);
    }

    private final MenuBuilder builder;
    private Inventory inventory = Bukkit.createInventory(null, 54, "Menu");
    private Map<Player, List<Object>> viewers = new HashMap<>();
    private boolean reopen = false;
    private Map<String, String> persistentData = new HashMap<>();
    public Map<String, String> getPersistentData() { return persistentData; }

    public Menu(@NotNull MenuBuilder builder) {
        this.builder = builder;
    }

    public Menu(int size) {
        this.builder = new MenuBuilder(size);
    }

    public MenuBuilder builder() { return builder; }
    public Map<Player, List<Object>> getViewers() { return viewers; }
    public Consumer<List<Object>> endFunction;

    public Menu apply() {
        inventory = builder.build();
        return this;
    }

    public Menu show(Player player) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player) instanceof BukkitTask) {
                BukkitTask task = (BukkitTask) viewers.get(player);
                if (task != null)
                    task.cancel();
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        viewers.put(player, new ArrayList<>());
        player.openInventory(inventory);
        return this;
    }
    public Menu show(Player player, Object object) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player) instanceof BukkitTask) {
                BukkitTask task = (BukkitTask) viewers.get(player);
                if (task != null)
                    task.cancel();
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        viewers.put(player, new ArrayList<>(){{add(object);}});
        player.openInventory(inventory);
        return this;
    }

    public Menu show(Player player, List<Object> object) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player) instanceof BukkitTask) {
                BukkitTask task = (BukkitTask) viewers.get(player);
                if (task != null)
                    task.cancel();
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        viewers.put(player, object);
        player.openInventory(inventory);
        return this;
    }

    public Menu show(Player player, List<Object> object, Consumer<List<Object>> start, Consumer<List<Object>> end) {
        if (viewers.containsKey(player)) {
            if (viewers.get(player) instanceof BukkitTask) {
                BukkitTask task = (BukkitTask) viewers.get(player);
                if (task != null)
                    task.cancel();
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(player));
                endFunction = null;
            }
            viewers.remove(player);
        }
        start.accept(object);
        endFunction = end;
        viewers.put(player, object);
        player.openInventory(inventory);
        return this;
    }


    public Menu reopen(Player player) {
        reopen = true;
        player.openInventory(inventory);
        return this;
    }

    public Menu register(Plugin plugin) {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        return this;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTopInventory() == inventory) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item != null) {
                int slot = event.getSlot();
                Item itemData = builder.getMap()[slot];
                if (itemData instanceof Button) {
                    Button button = (Button) itemData;
                    button.onClick(event);
                }
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getWhoClicked().getOpenInventory().getTopInventory() == inventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getOpenInventory().getTopInventory() == inventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getInventory() == inventory) {
            if (reopen) {
                reopen = false;
            } else {
                if (viewers.get(event.getPlayer()) instanceof BukkitTask) {
                    BukkitTask task = (BukkitTask) viewers.get(event.getPlayer());
                    if (task != null)
                        task.cancel();
                }
                if (endFunction != null) {
                    endFunction.accept(viewers.get(event.getPlayer()));
                    endFunction = null;
                }
                viewers.remove(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        if (viewers.containsKey(event.getPlayer())) {
            if (viewers.get(event.getPlayer()) instanceof BukkitTask) {
                BukkitTask task = (BukkitTask) viewers.get(event.getPlayer());
                if (task != null)
                    task.cancel();
            }
            if (endFunction != null) {
                endFunction.accept(viewers.get(event.getPlayer()));
                endFunction = null;
            }
            viewers.remove(event.getPlayer());
        }
    }
}
