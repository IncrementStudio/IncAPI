package ru.incrementstudio.incapi.commands.builder;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Command implements TabCompleter {
    private final CharSequence root;

    public CharSequence getRoot() {
        return root;
    }

    public Command(String root) {
        this.root = root;
    }

    public Command(String root, CharSequence... childs) {
        this.root = new Argument(root, childs);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (root instanceof Argument) {
            Argument argument = (Argument) root;
            return argument.getChilds(strings.length).stream()
                    .filter(x -> checkPreArgs(strings, strings.length - 1, x))
                    .map(x -> (String) x.getValue())
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private boolean checkPreArgs(String[] args, int iteration, Argument argument) {
        if (iteration == 0)
            return true;
        if (!argument.getParent().getValue().equals(args[iteration - 1]))
            return false;
        return checkPreArgs(args, iteration - 1, argument.getParent());
    }
}