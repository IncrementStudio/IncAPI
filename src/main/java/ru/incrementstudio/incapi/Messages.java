package ru.incrementstudio.incapi;

public class Messages {

    public static String noPermission;
    public static String onlyPlayers;
    public static String playerNotFound;
    public static String reloaded;

    public static void load() {
        noPermission = Main.config.get().getString("messages.no-permission");
        onlyPlayers = Main.config.get().getString("messages.only-players");
        playerNotFound = Main.config.get().getString("messages.player-not-found");
        reloaded = Main.config.get().getString("messages.reloaded");
    }
}
