package HomeMenu;

import ChatServerTrial.Connection;

import java.io.Serializable;
import java.util.function.Consumer;

public class Client extends Connection {
    private String ip;
    private int port;

    public Client(Consumer<Serializable> receiver, String ip, int port) {
        super(receiver);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIp() {
        return ip;
    }

    @Override
    protected int getPort() {
        return port;
    }
}

