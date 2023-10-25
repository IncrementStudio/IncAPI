package ru.incrementstudio.incapi.internection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class InternectionServer {
    private final ServerSocket server;
    public InternectionServer(int port) throws IOException {
        server = new ServerSocket(port);
        new Thread(() -> {
            while (!server.isClosed()) {
                try {
                    Socket connection = server.accept();
                    handleConnection(connection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void handleConnection(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        while(socket.isConnected()) {
            int length = in.readInt();
            if (length > 0) {
                byte[] data = new byte[length];
                in.read(data, 0, length);
                dataHandler(socket, data);
            }
        }
    }
    public void dataHandler(Socket socket, byte[] data) { }
    public static void send(Socket socket, byte[] data) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }
    public void close() throws IOException {
        server.close();
    }
}
