package HomeMenu;

import java.io.*;
import java.net.Socket;

public class CheckUpdateThread extends Thread {
    Socket socket;
    DataOutputStream dataOutputStream;
    long size;
    String path;

    public CheckUpdateThread(Socket socket, DataOutputStream dataOutputStream, long size, String path) {
        this.socket = socket;
        this.dataOutputStream = dataOutputStream;
        this.size = size;
        this.path = path;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public double getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void run() {
        File file = new File(path);
        String massage = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            if (file.length() > size) {
                String lastLine = br.readLine();
                while (lastLine != null) {
                    massage = lastLine + "\n";
                    lastLine = br.readLine();
                }
            }
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF(massage);
            dataOutputStream.flush();
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}
