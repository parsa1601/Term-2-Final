package HomeMenu;

import ChatServerTrial.Connection;

import java.io.Serializable;
import java.util.function.Consumer;

public class Server extends Connection {
    private int port;

    public Server(Consumer<Serializable> receiver, int port) {
        super(receiver);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true;
    }

    @Override
    protected String getIp() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
