package ru.incrementstudio.incapi.menu;

import java.util.HashMap;
import java.util.Map;

public class Data {
    private final Map<String, Object> data = new HashMap<>();

    public Data addData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Data removeData(String key) {
        data.remove(key);
        return this;
    }

    public Data setData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
