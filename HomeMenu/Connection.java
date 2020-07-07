package HomeMenu;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.ECField;
import java.util.function.Consumer;

public abstract class Connection {

    private Consumer<Serializable> receiver;
    private ConnectionThread connectionThread = new ConnectionThread();

    public Connection(Consumer<Serializable> receiver) {
        this.receiver = receiver;
        connectionThread.setDaemon(true);
    }

    public void startTheConnection() throws Exception{
        connectionThread.start();
    }

    public void sending(Serializable data) throws Exception{
        connectionThread.outputStream.writeObject(data);
    }

    public void endTheConnection() throws Exception{
        connectionThread.socket.close();
    }

    protected abstract boolean isServer();
    protected abstract String getIp();
    protected abstract int getPort();

    private class ConnectionThread extends Thread{
        private Socket socket;
        private ObjectOutputStream outputStream;
        @Override
        public void run() {
            try(
                    ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                    Socket socket = isServer() ? server.accept() : new Socket(getIp() , getPort());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ){
                this.socket = socket;
                this.outputStream = outputStream;
                socket.setTcpNoDelay(true);
                while (true){
                    Serializable data = (Serializable) inputStream.readObject();
                }
            }
            catch (Exception e){
                e.getMessage();
            }
        }
    }
}
