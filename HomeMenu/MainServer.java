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


        public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerImpl server , HashMap<String  , Socket> hashMap) throws IOException {
            this.clientSocket = clientSocket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            this.massage = dataInputStream.readUTF();
            this.server = server;
            this.socketHashMap = hashMap;
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
                    String fname = input.substring(input.indexOf("fname: ") + 7, input.indexOf("lname: "));
                    String lname = input.substring(input.indexOf("lname: ") + 7, input.indexOf("uname: "));
                    String uname = input.substring(input.indexOf("uname: ") + 7, input.indexOf("pass: "));
                    String pass = input.substring(input.indexOf("pass: ") + 6);
                    System.out.println(fname + "----" + lname + "----" + uname + "----" + pass + "----");
                    File thisDir = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + uname);
                    System.out.println(thisDir.mkdir());
                    PrintWriter userInformationTXTFile = null;
                    try {
                        FileWriter fw = new FileWriter("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + uname + "/" + uname + ".txt");
                        BufferedWriter bw = new BufferedWriter(fw);
                        userInformationTXTFile = new PrintWriter(bw);
                        MyDate today = new MyDate();
                        userInformationTXTFile.println("This in information of a new user who joined into server in date of: " + today.day + " / " + today.month + " / " + today.year);
                        userInformationTXTFile.println("name of the user: " + fname + " " + lname);
                        userInformationTXTFile.println("UserName: " + uname);
                        userInformationTXTFile.println("Password: " + pass);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (userInformationTXTFile != null) userInformationTXTFile.close();
                    }
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
                    File file = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + input.substring(6));
                    System.out.println(file.mkdir());
                    String pathname = "/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Chat/" + input.substring(6) + "/" + input.substring(6) + ".txt";
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
//                    for (int i = 0 ; i < arrayListOfUsersChats.size() ; i++){
//                        System.out.println(arrayListOfUsersChats.get(i).get(0));
//                        if (arrayListOfUsersChats.get(i).get(0).contains(contact)){
//                            System.out.println("find!");
//                            contactMassage = arrayListOfUsersChats.get(i).get(arrayListOfUsersChats.get(i).size() -1 );
//                        }
//                    }
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
//                    for(Map.Entry<String , Socket> e : socketHashMap.entrySet()){
//                        if (socketHashMap.containsKey(contact)){
//                            DataOutputStream newOutPutStream = new DataOutputStream(new BufferedOutputStream(e.getValue().getOutputStream()));
//                            newOutPutStream.writeUTF(contactMassage);
//                            newOutPutStream.flush();
//                        }
//                    }
                    dataOutputStream.writeUTF(sendingMassages);
                    dataOutputStream.flush();
//                    Stage newWindow = new Stage();
//                    newWindow.initModality(Modality.APPLICATION_MODAL);
//                    newWindow.setTitle("Server Channel!");
//                    newWindow.setWidth(100);
//                    Pane layout = new Pane();
//                    layout.setPadding(new Insets(20,20,20,20));
//                    TextArea textArea = new TextArea();
//                    serverChannelThread.setMassage(input.substring(6));
//                    layout.getChildren().add(textArea);
//                    Scene scene = new Scene(layout,100,200);
//                    newWindow.setScene(scene);
//                    newWindow.show();
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
                }else if(input.startsWith("OnlineUsers: ")){
                    String result = "";
                    for(Map.Entry<String , Socket> e : socketHashMap.entrySet()){
                        result += e.getKey() + "\n";
                        }
                    dataOutputStream.writeUTF(result);
                    dataOutputStream.flush();
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
        private ArrayList<User> users = new ArrayList<>();
        private HashMap<String , Socket> socketHashMap = new HashMap<>();


        private User handleSignIn(String username, String password, DataOutputStream dataOutputStream) throws IOException {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    if (!user.getPassword().equals(password)) {
                        dataOutputStream.writeUTF("Failure");
                    } else {
                        dataOutputStream.writeUTF("Success");
                        System.out.println("Logged in user with username : " + username + " and password : " + password);
                    }
                    dataOutputStream.flush();
                    return user;
                }
            }
            User newUser = new User(username, password);
            users.add(newUser);
            dataOutputStream.writeUTF("Success");
            dataOutputStream.flush();
            System.out.println("Created and Logged in user with username : " + newUser.getUsername() + " and password : " + newUser.getPassword());
            return newUser;
        }

        public void run() throws IOException {
            ServerSocket serverSocket = new ServerSocket(8889);
            while (true) {
                Socket clientSocket;
                try {
                    System.out.println("Waiting for Client...");
                    System.out.println("A client Connected!");
                    clientSocket = serverSocket.accept();
                    DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
//                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                    String content = br.readLine();
//                    System.out.println(content);

//
//                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
//                    JSONParser jsonParser = new JSONParser();
//                    Object obj = jsonParser.parse(dataInputStream.readUTF());
//                    JSONArray userList = (JSONArray) obj;
//                    System.out.println(userList.toJSONString());

                    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                    new ClientHandler(clientSocket, dataOutputStream, dataInputStream, this,socketHashMap).start();
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
