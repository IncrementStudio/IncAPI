package ru.incrementstudio.incapi.internection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public abstract class InternectionServer extends Thread {
    private final ServerSocket server;
    public InternectionServer(int port) throws IOException {
        server = new ServerSocket(port);
        start();
    }

    @Override
    public void run() {
        while (!server.isClosed()) {
            try {
                Socket connection = server.accept();
                if (connection.isConnected()) {
                    new ConnectionHandler(connection) {
                        @Override
                        public void dataHandler(byte[] data) {
                            InternectionServer.this.dataHandler(this, data);
                        }
                    };
                }
            } catch (SocketException ignore) {
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void dataHandler(ConnectionHandler handler, byte[] data) { }
    public void close() throws IOException {
        server.close();
    }

    public static class ConnectionHandler extends Thread {
        private final Socket clientSocket;
        private boolean closed = false;
        private final Deque<byte[]> sendQueue = new ArrayDeque<>();
        private final Deque<OneTimeHandler> handlers = new ArrayDeque<>();
        private static final byte[] closeCode = new byte[] { -128, -128, -128, -128 };
        public ConnectionHandler(Socket connection) {
            clientSocket = connection;
            start();
        }
        public void addOneTimeHandler(OneTimeHandler handler) {
            handlers.add(handler);
        }
        @Override
        public void run() {
            DataInputStream in;
            DataOutputStream out;
            try {
                in = new DataInputStream(clientSocket.getInputStream());
                out = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            while (clientSocket.isConnected() && !closed) {
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

                    if (!sendQueue.isEmpty()) {
                        byte[] data = sendQueue.remove();
                        try {
                            if (!Arrays.equals(data, closeCode)) {
                                out.writeInt(data.length);
                                out.write(data);
                                out.flush();
                            } else {
                                close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        out.writeInt(0);
                        out.flush();
                    }
                } catch (EOFException e) {
                    send(closeCode);
                    close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public void dataHandler(byte[] data) { }
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
