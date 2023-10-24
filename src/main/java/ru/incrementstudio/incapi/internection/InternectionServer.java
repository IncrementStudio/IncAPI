package ru.incrementstudio.incapi.internection;

import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class InternectionServer {
    private ServerSocket server;
    public InternectionServer(int port) throws IOException {
        server = new ServerSocket(port);
        new BukkitRunnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket connection = server.accept();
                        handleConnection(connection);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.run();
    }
    public abstract void handleConnection(Socket socket);
    public static void send(Socket socket, byte[] data) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(data);
        out.flush();
    }
}
