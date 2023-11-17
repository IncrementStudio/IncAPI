package ru.incrementstudio.incapi.quantum;

import java.util.*;
import java.util.function.Consumer;

public class Quantum {
    private final Map<Integer, Consumer<Object>> listeners = new HashMap<>();
    public Map<Integer, Consumer<Object>> getListeners() { return listeners; }

    public void setListener(int id, Consumer<Object> listener) {
        listeners.put(id, listener);
    }

    public void send(int id, Object data) {
        Consumer<Object> listener = listeners.get(id);
        listener.accept(data);
    }
}
