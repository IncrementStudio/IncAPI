package ru.incrementstudio.incapi.commands.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Argument implements CharSequence {
    private final CharSequence value;
    public CharSequence getValue() {
        if (value instanceof Argument)
            return ((Argument) value).getValue();
        return value;
    }
    private Argument parent;
    public Argument getParent() {
        return parent;
    }
    private void setParent(Argument parent) {
        this.parent = parent;
    }
    public List<Argument> getChilds(int target) {
        return getChilds(target, 0, Collections.singletonList(this));
    }
    private List<Argument> getChilds(int target, int iteration, List<Argument> arguments) {
        if (iteration == target) {
            return arguments;
        }
        List<Argument> result = new ArrayList<>();
        for (Argument argument : arguments)
            result.addAll(getChilds(target, iteration + 1, argument.getChilds()));
        return result;
    }
    private final List<Argument> childs;
    public List<Argument> getChilds() {
        return childs;
    }
    public Argument(CharSequence value, CharSequence... childs) {
        this.value = value;
        this.childs = Arrays.stream(childs)
                .map(x -> {
                    if (x instanceof Argument)
                        return (Argument) x;
                    return new Argument(x);
                })
                .collect(Collectors.toList());
        this.childs.stream()
                .map(Argument::getValue)
                .forEach(x -> {
                    if (Collections.frequency(this.childs.stream()
                            .map(Argument::getValue)
                            .collect(Collectors.toList()), x) > 1)
                        throw new IllegalArgumentException("DUPLICATE ARGUMENTS: " + x + "!");
                });
        this.childs.forEach(x -> x.setParent(this));
    }
    public Argument(CharSequence value, List<CharSequence> childs) {
        this.value = value;
        this.childs = childs.stream()
                .map(x -> {
                    if (x instanceof Argument)
                        return (Argument) x;
                    return new Argument(x);
                })
                .collect(Collectors.toList());
        this.childs.stream()
                .map(Argument::getValue)
                .forEach(x -> {
                    if (Collections.frequency(this.childs.stream()
                            .map(Argument::getValue)
                            .collect(Collectors.toList()), x) > 1)
                        throw new IllegalArgumentException("DUPLICATE ARGUMENTS: " + x + "!");
                });
        this.childs.forEach(x -> x.setParent(this));
    }
    public Argument(CharSequence value) {
        this.value = value;
        this.childs = new ArrayList<>();
    }

    @Override
    public int length() {
        return value.length();
    }

    @Override
    public char charAt(int index) {
        return value.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return value.subSequence(start, end);
    }
}

