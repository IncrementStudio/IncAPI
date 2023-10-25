package ru.incrementstudio.incapi.internection;

import java.io.IOException;

public abstract class InternectionDouble extends InternectionServer {
    private final InternectionClient client;
    public InternectionDouble(int inPort, int outPort) throws IOException {
        super(inPort);
        client = new InternectionClient(outPort);
    }
    public InternectionDouble(int inPort, InternectionClient client) throws IOException {
        super(inPort);
        this.client = client;
    }
    public InternectionClient getClient() {
        return client;
    }
    @Override
    public void close() throws IOException {
        client.close();
        super.close();
    }
}
