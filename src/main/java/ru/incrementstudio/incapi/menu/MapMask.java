package ru.incrementstudio.incapi.menu;

import java.util.Arrays;

public class MapMask implements Mask {
    private final boolean[] map;

    public MapMask(int size) {
        map = new boolean[size];
        Arrays.fill(map, true);
    }

    public MapMask(boolean[] mask) {
        map = mask.clone();
    }

    public MapMask(MapMask base) {
        map = base.map.clone();
    }

    public int getSize() {
        return map.length;
    }

    public boolean[] getMap() {
        return map;
    }

    public MapMask set(int slot, boolean value) {
        map[slot] = value;
        return this;
    }

    public MapMask toggle(int slot) {
        map[slot] = !map[slot];
        return this;
    }

    @Override
    public boolean check(int slot) {
        return map[slot];
    }
}