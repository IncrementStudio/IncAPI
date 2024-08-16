package ru.incrementstudio.incapi.menu;

import java.util.*;

public class Data {
    private final Map<String, List<?>> data = new HashMap<>();
    public List<?> getData(String key) {
        if (!data.containsKey(key))
            data.put(key, new ArrayList<>());
        return data.get(key);
    }
    public void removeData(String key) {
        data.remove(key);
    }
}
