package HomeMenu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyPermission;


import Trip.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.naming.ldap.SortKey;


public class MainServer {

    public static void main(String[] args) throws IOException {
        new ServerImpl().run();
    }
    
    static class User {
        private String username;
        private String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    static class ClientHandler extends Thread {
        private Socket clientSocket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private String massage;
        private ServerImpl server;
        private User user;
        private String textOfChatting;
        private String with = null;
        private ArrayList< ArrayList<String> > arrayListOfUsersChats = new ArrayList<>();
        private HashMap<String , Socket> socketHashMap;
        private HashMap<String , Socket> socketHashMapGame;
        private String  tTcx = "";
        private String  tTCO = "";



        public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerImpl server, HashMap<String, Socket> hashMap, HashMap<String, Socket> hashMapG) throws IOException {
            this.clientSocket = clientSocket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            this.massage = dataInputStream.readUTF();
            this.server = server;
            this.socketHashMap = hashMap;
            this.socketHashMapGame = hashMapG;
        }


        private void handleClient() throws IOException {
            String input;
//            serverChannelThread serverChannelThread = new serverChannelThread();
//            serverChannelThread.run();
            while (true) {
                System.out.println("hi");
                input = massage;
                System.out.println("Client sent : " + input);
                if (input.startsWith("fname: ")) {

                    String uname = input.substring(input.indexOf("uname: ") + 7, input.indexOf("pass: "));

                    ArrayList<String> strings = new ArrayList<>();
                    strings.add(uname);
                    arrayListOfUsersChats.add(strings);
                }
                else if (input.startsWith("SignIn: ")) {
                    String username;
                    String password;
                    username = input.substring(18);
//                    password = input.substring(input.indexOf("Password: " + 10));
                    System.out.println(username);
                    System.out.println(socketHashMap.size());
                    socketHashMap.put(username , clientSocket);
                    System.out.println(socketHashMap.size());
                    for(Map.Entry<String , Socket> e : socketHashMap.entrySet()){
                        System.out.println(e.toString());
                    }
                }
                else if (input.startsWith("chat: ")){
                    System.out.println("new chat user with username: " + input.substring(6));
                    File file = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + input.substring(6) + " ");
                    System.out.println(file.mkdir());
                    String pathname = "/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + input.substring(6) + " /" + input.substring(6) + " .txt";
                    File thisfile = new File(pathname);
                    if (!thisfile.exists())
                        thisfile.createNewFile();
                    PrintWriter pw;
                    FileWriter fw = new FileWriter(pathname);
                    BufferedWriter bw = new BufferedWriter(fw);
                    pw = new PrintWriter(bw);
                    if (pw!=null) pw.close();
                    pw.println("Server: " + user + " is now Online!");
                    dataOutputStream.writeUTF("start your chat");
                    dataOutputStream.flush();
//                    System.out.println("iii " + input.substring(input.indexOf("with") + 5));
//                    System.out.println("input.indexOf(with)" + input.indexOf("with"));
                    with = input.substring(input.indexOf("with") + 5);
                }
                else if (input.startsWith("Send: ")){
//                    System.out.println(input.substring(6));
                    int fromU = input.indexOf("From: ") + 6;
                    int toU = 0;
                    for (int  i = fromU ; i < input.length() ; i++){
                        if (input.charAt(i)  == ' '){
                            toU = i;
                            break;
                        }
                    }
                    int fromC = input.indexOf("To: ") + 4;
                    int toC = 0;
                    for (int  i = fromC ; i < input.length() ; i++){
                        if (input.charAt(i)  == ' '){
                            toC = i;
                            break;
                        }
                    }
//                    dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                    String user = input.substring(fromU,toU - 1);
                    String contact = input.substring(fromC,toC);
                    //                    for (int i = 0 ; i < arrayListOfUsersChats.size() ; i++){
//                        if (arrayListOfUsersChats.get(i).get(0).equals(user))
//                            arrayListOfUsersChats.get(i).add(userMassage);
//                    }
                    String contactMassage = null;

                    PrintWriter chatsOfUser = null;
                    String userMassage = input.substring(toC + 6)+ "\n";
                    FileWriter fw = new FileWriter("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + user + " /" + user + " .txt");
                    BufferedWriter bw = new BufferedWriter(fw);
                    chatsOfUser = new PrintWriter(bw);
                    chatsOfUser.print(input.substring(toC +6));
                    if(chatsOfUser!=null) chatsOfUser.close();
                    File contactFile = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + contact + " /" + contact + " .txt");
                    BufferedReader br = new BufferedReader(new FileReader(contactFile));
                    String lastLine = br.readLine();
                    while (lastLine != null){
                        contactMassage = lastLine + "\n";
                        lastLine = br.readLine();
                    }
                    chatsOfUser = new PrintWriter(new BufferedWriter(new FileWriter(contactFile)));
                    chatsOfUser.print("#$%");
//                    System.out.println(user);
//                    System.out.println(userMassage);
//                    System.out.println(contact);
//                    System.out.println(contactMassage);
//                    System.out.println(sendingMassages);

                    String sendingMassages = "";
                    if (contactMassage!=null && !contactMassage.endsWith("#$%"))
                        sendingMassages = contactMassage ;
                    sendingMassages+=userMassage;

                    dataOutputStream.writeUTF(sendingMassages);
                    dataOutputStream.flush();

                }
                else if (input.startsWith("Sendd: ")) {
//                    int fromC = input.indexOf();
                    String contact = input.substring(11);
                    File contactFile = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + contact + " /" + contact + " .txt");
                    BufferedReader br = new BufferedReader(new FileReader(contactFile));
                    String lastLine = br.readLine();
                    String  contactMassage = null;
                    while (lastLine != null){
                        contactMassage = lastLine + "\n";
                        lastLine = br.readLine();
                    }
                    System.out.println(contact);
                    System.out.println(contactMassage);
                    PrintWriter chatsOfUser = new PrintWriter(new BufferedWriter(new FileWriter(contactFile)));
                    chatsOfUser.print("#$%");
                    String sendingMassages = "";
                    if (contactMassage!=null && !contactMassage.endsWith("#$%")) {
                        sendingMassages += contactMassage;
                    }
                    dataOutputStream.writeUTF(sendingMassages);
                    dataOutputStream.flush();
                }
                else if(input.startsWith("OnlineUsers: ")){
                    String result = "";
                    for(Map.Entry<String , Socket> e : socketHashMap.entrySet()){
                        result += e.getKey() + "\n";
                        }
                    dataOutputStream.writeUTF(result);
                    dataOutputStream.flush();
                }
                else if (input.startsWith("game: ")){
                    String username = input.substring(6);
                    socketHashMapGame.put(username , clientSocket);
                    System.out.println(socketHashMapGame.size());
                    for(Map.Entry<String , Socket> e : socketHashMapGame.entrySet()) {
                        if (!e.getKey().equals(username)) {
                            DataOutputStream newOutPutStream = new DataOutputStream(new BufferedOutputStream(e.getValue().getOutputStream()));
                            System.out.println(e.getKey());
                            newOutPutStream.writeUTF(input);
                            newOutPutStream.flush();
                        }
                    }
                }
                else if (input.startsWith("SNL :")){
                    System.out.println(input);
                    System.out.println(input.charAt(6));
                    String username = input.substring(input.indexOf("User: ")+6 , input.indexOf("Cont: "));
                    System.out.println(username);
                    String contact = input.substring(input.indexOf("Cont: ")+6 , input.indexOf("#"));
                    System.out.println(contact);
                    File userFolder = new File ("src/HomeMenu/Users/Info/" + username + " /Game/SNL/" + contact + " .txt");
                    BufferedReader bufferedReader1 = new BufferedReader(new FileReader(userFolder));
                    String userBackup = "";
                    String lastLineUser = bufferedReader1.readLine();
                    while (lastLineUser != null){
                        userBackup += lastLineUser + "\n";
                        lastLineUser = bufferedReader1.readLine();
                    }
                    File contactFolder = new File ("src/HomeMenu/Users/Info/" + contact + " /Game/SNL/" + username + " .txt");
                    BufferedReader bufferedReader3 = new BufferedReader(new FileReader(contactFolder));
                    String contactBackup = "";
                    String lastLineContact = bufferedReader3.readLine();
                    while (lastLineContact != null){
                        contactBackup += lastLineContact + "\n";
                        lastLineContact = bufferedReader3.readLine();
                    }
//                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(contactFolder));
                    if (input.endsWith("yes")){
                                    System.out.println("line 280");
                                    BufferedReader bufferedReader = new BufferedReader(new FileReader(userFolder));
                                    String startingLine = bufferedReader.readLine();
                                    if (userFolder.length() == 0) {
                                        dataOutputStream.writeUTF("can");
                                        PrintWriter printWriter = new PrintWriter (new BufferedWriter(new FileWriter(userFolder)));
                                        printWriter.println(userBackup + contact + ": " + "doesn't start!");
                                        printWriter.close();
                                        PrintWriter printWriter1 = new PrintWriter (new BufferedWriter(new FileWriter(contactFolder)));
                                        printWriter1.println(contactBackup + contact + ": " + "doesn't start!");
                                        printWriter1.close();
                                    }else {
                                        if (startingLine.startsWith(contact)) {
                                            dataOutputStream.writeUTF("can");
                                            System.out.println("line 289");
                                        }
                                        if (startingLine.startsWith(username)) {
                                            dataOutputStream.writeUTF("cant");
                                            System.out.println("line 296");
                                    }
                                }
                        } else if (input.endsWith("no")) {
                            System.out.println("line 303");
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(userFolder));
                            String startingLine = bufferedReader.readLine();
                            if (userFolder.length() == 0) {
                                dataOutputStream.writeUTF("can");
                                PrintWriter printWriter = new PrintWriter (new BufferedWriter(new FileWriter(userFolder)));
                                printWriter.println(userBackup + username + ": " + "doesn't start!");
                                printWriter.close();
                                PrintWriter printWriter1 = new PrintWriter (new BufferedWriter(new FileWriter(contactFolder)));
                                printWriter1.println(contactBackup + username + ": " + "doesn't start!");
                                printWriter1.close();
                            }else {
                                if (startingLine.startsWith(contact)) {
                                    dataOutputStream.writeUTF("cant");
                                    System.out.println("line 313");
                                }
                                if (startingLine.startsWith(username)) {
                                    dataOutputStream.writeUTF("can");
                                    System.out.println("line 318");
                                }
                            }
                        } else if (input.endsWith("turn")){
                            BufferedReader bufferedReader = new BufferedReader(new FileReader(userFolder));
                            String startingLine = bufferedReader.readLine();
                            String theLastLine = "";
                            while (startingLine != null){
                                theLastLine = startingLine + "\n";
                                startingLine = bufferedReader.readLine();
                            }
                            if (theLastLine.startsWith(contact)) {
                                dataOutputStream.writeUTF("can");
                                System.out.println("line 317");
                            }
                            if (theLastLine.startsWith(username)){
                                dataOutputStream.writeUTF("cant");
                                System.out.println("line 321");
                            }
                        }else if (input.endsWith("change")){
                            PrintWriter printWriter = new PrintWriter (new BufferedWriter(new FileWriter(userFolder)));
                            printWriter.println(userBackup + username + ": " + "just played!");
                            printWriter.close();
                            PrintWriter printWriter1 = new PrintWriter (new BufferedWriter(new FileWriter(contactFolder)));
                            printWriter1.println(contactBackup + username + ": " + "just played!");
                            printWriter1.close();
                            dataOutputStream.writeUTF("ok");
                         } else{
                            char numberOfDice = input.charAt(input.length() - 1);
                            PrintWriter printWriter = new PrintWriter (new BufferedWriter(new FileWriter(userFolder)));
                            System.out.println("line 326");
                            printWriter.println(userBackup + username + ": " + numberOfDice);
                            printWriter.close();
                            PrintWriter printWriter2 = new PrintWriter (new BufferedWriter(new FileWriter(contactFolder)));
                            System.out.println("line 326");
                            printWriter2.println(contactBackup + username + ": " + numberOfDice);
                            printWriter2.close();
                            dataOutputStream.writeUTF("ok");
                        }
                        dataOutputStream.flush();
                }
                else if (input.startsWith("TTT :")) {
                    System.out.println(input);
                    System.out.println(input.charAt(6));
                    String username = input.substring(input.indexOf("User: ") + 6, input.indexOf("Cont: "));
                    System.out.println(username);
                    String contact = input.substring(input.indexOf("Cont: ") + 6, input.indexOf("#"));
                    System.out.println(contact);
                    File userFolder = new File("src/HomeMenu/Users/Info/" + username + " /Game/TTT/" + contact + " .txt");
                    BufferedReader bufferedReader1 = new BufferedReader(new FileReader(userFolder));
                    String userBackup = "";
                    String lastLineUser = bufferedReader1.readLine();
                    while (lastLineUser != null) {
                        userBackup += lastLineUser + "\n";
                        lastLineUser = bufferedReader1.readLine();
                    }
                    File contactFolder = new File("src/HomeMenu/Users/Info/" + contact + " /Game/TTT/" + username + " .txt");
                    BufferedReader bufferedReader3 = new BufferedReader(new FileReader(contactFolder));
                    String contactBackup = "";
                    String lastLineContact = bufferedReader3.readLine();
                    while (lastLineContact != null) {
                        contactBackup += lastLineContact + "\n";
                        lastLineContact = bufferedReader3.readLine();
                    }
                    if (input.endsWith("yes")) {
                        System.out.println("line 280");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(userFolder));
                        String startingLine = bufferedReader.readLine();
                        if (userFolder.length() == 0) {
                            dataOutputStream.writeUTF("can");
                            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(userFolder)));
                            printWriter.println(userBackup + contact + ": " + "doesn't start!");
                            printWriter.close();
                            PrintWriter printWriter1 = new PrintWriter(new BufferedWriter(new FileWriter(contactFolder)));
                            printWriter1.println(contactBackup + contact + ": " + "doesn't start!");
                            printWriter1.close();
                        } else {
                            if (startingLine.startsWith(contact)) {
                                dataOutputStream.writeUTF("can");
                                System.out.println("line 289");
                            }
                            if (startingLine.startsWith(username)) {
                                dataOutputStream.writeUTF("cant");
                                System.out.println("line 296");
                            }
                        }
                    }
                    else if (input.endsWith("no")) {
                        System.out.println("line 303");
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(userFolder));
                        String startingLine = bufferedReader.readLine();
                        if (userFolder.length() == 0) {
                            dataOutputStream.writeUTF("can");
                            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(userFolder)));
                            printWriter.println(userBackup + username + ": " + "doesn't start!");
                            printWriter.close();
                            PrintWriter printWriter1 = new PrintWriter(new BufferedWriter(new FileWriter(contactFolder)));
                            printWriter1.println(contactBackup + username + ": " + "doesn't start!");
                            printWriter1.close();
                        } else {
                            if (startingLine.startsWith(contact)) {
                                dataOutputStream.writeUTF("cant");
                                System.out.println("line 313");
                            }
                            if (startingLine.startsWith(username)) {
                                dataOutputStream.writeUTF("can");
                                System.out.println("line 318");
                            }
                        }
                    }
                    else if (input.endsWith("turn")) {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(userFolder));
                        String startingLine = bufferedReader.readLine();
                        String theLastLine = "";
                        while (startingLine != null) {
                            theLastLine = startingLine + "\n";
                            startingLine = bufferedReader.readLine();
                        }
                        if (theLastLine.startsWith(contact)) {
                            dataOutputStream.writeUTF("can");
                            System.out.println("line 317");
                        }
                        if (theLastLine.startsWith(username)) {
                            dataOutputStream.writeUTF("cant");
                            System.out.println("line 321");
                        }
                    }
                    else if(input.endsWith("X")||input.endsWith("O")){
                        int home = Integer.parseInt(String.valueOf(input.charAt(input.indexOf("#")+1)));
                        char sign = input.charAt(input.indexOf("#") + 3);
                        PrintWriter printWriter = new PrintWriter (new BufferedWriter(new FileWriter(userFolder)));
                        System.out.println("line 326");
                        printWriter.println(userBackup + username + ": " + home+ "/" + sign);
                        printWriter.close();
                        PrintWriter printWriter2 = new PrintWriter (new BufferedWriter(new FileWriter(contactFolder)));
                        System.out.println("line 326");
                        printWriter2.println(contactBackup + username + ": " + home + "/" + sign);
                        printWriter2.close();
                        dataOutputStream.writeUTF("ok");
                    }
                }
                //////////////////////////////////////////////////////////TODO: this part is the continue of the user handling clients for other works i should add to this part asap;
                break;
            }

        }

        @Override
        public void run() {
            try {
                handleClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ServerImpl {
        private HashMap<String , Socket> socketHashMap = new HashMap<>();
        private HashMap<String , Socket> socketHashMapGame = new HashMap<>();
//        private String tTCX = "";
//        private String tTCO = "";



        public void run() throws IOException {
            ServerSocket serverSocket = new ServerSocket(8889);
            while (true) {
                Socket clientSocket;
                try {
                    System.out.println("Waiting for Client...");
                    System.out.println("A client Connected!");
                    clientSocket = serverSocket.accept();
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));


                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                    new ClientHandler(clientSocket, dataOutputStream, dataInputStream, this,socketHashMap, socketHashMapGame ).start();
                    System.out.println(clientSocket.getLocalPort());
                    System.out.println(clientSocket.getOutputStream().toString());
                    System.out.println(clientSocket.getOutputStream());
                    clientSocket.getChannel();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error in accepting client!");
                    break;
                }
            }
        }
    }
}
