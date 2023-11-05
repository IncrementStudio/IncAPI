package ru.incrementstudio.incapi.internection;

import ru.incrementstudio.incapi.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public abstract class InternectionServer extends Thread {
    private final ServerSocket server;
    private final List<ConnectionHandler> handlers = new ArrayList<>();
    public InternectionServer(int port) throws IOException {
        super("Inc_Server_" + port);
        server = new ServerSocket(port);
        start();
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!server.isClosed()) {
                    try {
                        Socket connection = server.accept();
                        if (connection.isConnected()) {
                            ConnectionHandler clientHandler = new ConnectionHandler(connection) {
                                @Override
                                public void dataHandler(byte[] data) {
                                    InternectionServer.this.dataHandler(this, data);
                                }
                            };
                            handlers.add(clientHandler);
                        }
                    } catch (SocketException ignore) {
                        cancel();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 0L, 10L);
    }
    public void dataHandler(ConnectionHandler handler, byte[] data) { }
    public void close() throws IOException {
        server.close();
    }

    public static class ConnectionHandler {
        private final Thread receiveThread;
        private final Thread sendThread;
        private final Socket clientSocket;
        private boolean closed = false;
        private final Deque<byte[]> sendQueue = new ArrayDeque<>();
        private final Deque<OneTimeHandler> handlers = new ArrayDeque<>();
        private static final byte[] closeCode = new byte[] { -128, -128, -128, -128 };
        public ConnectionHandler(Socket connection) {
            clientSocket = connection;
            receiveThread = new Thread(this::receive, "Inc_HandlerRecv_" + connection.getPort());
            sendThread = new Thread(this::send, "Inc_HandlerSend_" + connection.getPort());
            receiveThread.start();
            sendThread.start();
        }
        public void addOneTimeHandler(OneTimeHandler handler) {
            handlers.add(handler);
        }
        private void receive() {
            DataInputStream in;
            try {
                in = new DataInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (clientSocket.isConnected() && !closed) {
                        try {
                            int length = in.readInt();
                            if (length > 0) {
                                byte[] data = new byte[length];
                                in.read(data, 0, length);
                                if (handlers.isEmpty())
                                    dataHandler(data);
                                else {
                                    OneTimeHandler handler = handlers.remove();
                                    handler.dataHandler(data);
                                }
                            }
                        } catch (EOFException e) {
                            send(closeCode);
                            cancel();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 0L, 10L);
        }
        public void dataHandler(byte[] data) { }
        private void send() {
            DataOutputStream out;
            try {
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!clientSocket.isClosed() && !closed) {
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
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }, 0L, 10L);
        }
        public void send(byte[] data) {
            sendQueue.add(data);
        }
        public void close() {
            closed = true;
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
