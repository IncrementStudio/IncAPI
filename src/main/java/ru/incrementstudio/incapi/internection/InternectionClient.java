package ru.incrementstudio.incapi.internection;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class InternectionClient extends Thread {
    private final int port;
    private final Deque<byte[]> sendQueue = new ArrayDeque<>();
    private final Deque<OneTimeHandler> handlers = new ArrayDeque<>();
    private static final byte[] closeCode = new byte[] { -128, -128, -128, -128 };
    private Socket socket;
    private boolean closed = false;
    public InternectionClient(int port) {
        this.port = port;
    }
    public void addOneTimeHandler(OneTimeHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void run() {
        DataInputStream in;
        DataOutputStream out;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (!socket.isClosed()) {
            if (!sendQueue.isEmpty()) {
                byte[] data = sendQueue.remove();
                try {
                    if (!Arrays.equals(data, closeCode)) {
                        out.writeInt(data.length);
                        out.write(data);
                        out.flush();

                        int length = in.readInt();
                        if (length > 0) {
                            byte[] readData = new byte[length];
                            in.read(readData, 0, length);
                            if (handlers.isEmpty())
                                dataHandler(readData);
                            else {
                                OneTimeHandler handler = handlers.remove();
                                handler.dataHandler(readData);
                            }
                        }
                    } else {
                        break;
                    }
                } catch (SocketException ignored) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(byte[] data) {
        sendQueue.add(data);
    }
    public void bind() throws IOException {
        socket = new Socket("localhost", port);
        start();
    }
    public void dataHandler(byte[] data) { }
    public void close() throws IOException {
        socket.close();
    }
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }
}
