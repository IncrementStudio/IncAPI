package ru.incrementstudio.incapi.internection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class InternectionClient {
    private Socket socket;
    public InternectionClient(int port) throws IOException {
        socket = new Socket("localhost", port);
    }
    public void send(byte[] data) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write(data);
        out.flush();
    }
}
