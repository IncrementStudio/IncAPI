package ru.incrementstudio.incapi;

public class Messages {

    public static String noPermission = Main.config.get().getString("messages.no-permission");
    public static String onlyPlayers = Main.config.get().getString("messages.only-players");
    public static String playerNotFound = Main.config.get().getString("messages.player-not-found");
    public static String reloaded = Main.config.get().getString("messages.reloaded");
}
