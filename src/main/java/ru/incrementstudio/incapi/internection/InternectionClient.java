package ru.incrementstudio.incapi.internection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class InternectionClient {
    private final int port;
    private Socket socket;
    public InternectionClient(int port) {
        this.port = port;
    }
    public void send(byte[] data) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }
    public void bind() throws IOException {
        socket = new Socket("localhost", port);
        new Thread(() -> {
            while (!socket.isClosed()) {
                try {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    while (socket.isConnected()) {
                        int length = in.readInt();
                        if (length > 0) {
                            byte[] data = new byte[length];
                            in.read(data, 0, length);
                            dataHandler(data);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public void dataHandler(byte[] data) { }
    public void close() throws IOException {
        socket.close();
    }
}
