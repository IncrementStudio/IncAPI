package ru.incrementstudio.incapi.internection;

import ru.incrementstudio.incapi.Main;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class InternectionClient {
    private final int port;
    private final Thread receiveThread;
    private final Thread sendThread;
    private final Deque<byte[]> sendQueue = new ArrayDeque<>();
    private static final byte[] closeCode = new byte[] { -128, -128, -128, -128 };
    private Socket socket;
    private boolean closed = false;
    public InternectionClient(int port) {
        this.port = port;
        sendThread = new Thread(this::send, "Inc_ClientSend_" + port);
        receiveThread = new Thread(this::receive, "Inc_ClientRecv_" + port);
    }
    private void receive() {
        DataInputStream in;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (socket.isConnected()) {
                    try {
                        int length = in.readInt();
                        if (length > 0) {
                            byte[] data = new byte[length];
                            in.read(data, 0, length);
                            dataHandler(data);
                        }
                    } catch (SocketException | EOFException e) {
                        send(closeCode);
                        cancel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0L, 10L);
    }
    private void send() {
        DataOutputStream out;
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!socket.isClosed()) {
                    if (!sendQueue.isEmpty()) {
                        byte[] data = sendQueue.remove();
                        try {
                            if (!Arrays.equals(data, closeCode)) {
                                out.writeInt(data.length);
                                out.write(data);
                                out.flush();
                            } else {
                                cancel();
                            }
                        } catch (SocketException ignored) {
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    cancel();
                }
            }
        }, 0L, 10L);
    }
    public void send(byte[] data) {
        sendQueue.add(data);
    }
    public void bind() throws IOException {
        socket = new Socket("localhost", port);
        receiveThread.start();
        sendThread.start();
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
