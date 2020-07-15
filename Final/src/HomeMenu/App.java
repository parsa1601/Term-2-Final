package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class App {
    private static Socket socket = null;
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static final String FIRSTTEXTOFREFRSH = "Online users\n" + "\"press refresh\"" + "\n";
    public static void display(String username){
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setWidth(600);
        newWindow.setHeight(500);
        Rectangle chat = massageIcon();
        chat.setOnMouseClicked(mouseEvent -> {
            ChatRoom.display(username);
        });
        Text information = new Text("Games History");
        information.setX(400);
        information.setY(40);
        information.setFill(Color.AZURE);
        information.setOnMouseClicked(mouseEvent -> {
            Info.display(username);
        });
        Circle exitIcon = new Circle(550, 420, 30);
        exitIcon.setFill(Color.AZURE);
        Text textForExit = new Text(535, 425, "EXIT");
        textForExit.setFill(Color.DARKRED);
        textForExit.setOnMouseClicked(mouseEvent -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8889);
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dataOutputStream.writeUTF("leave");
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            newWindow.close();
        });
        TextArea onlineUsers = textAreaOfOnlineUsers();
        Rectangle refresh = refresh();
        refresh.setOnMouseClicked(mouseEvent -> {
            SearchForGroupChat.display();
        });
        Text refreshTxt = refreshText();
        refreshTxt.setOnMouseClicked(mouseEvent -> {
            String str = checkOnline();
            onlineUsers.clear();
            onlineUsers.appendText(FIRSTTEXTOFREFRSH);
            onlineUsers.appendText(str);
        });
        Rectangle ticTacToe = tictactoe();
        Text ticTacToeText = new Text("Tic-Tac-Toe");
        ticTacToeText.setFill(Color.AZURE);
        ticTacToeText.setY(300);
        ticTacToeText.setX(168);
        ticTacToe.setOnMouseClicked(mouseEvent -> {
            FileInputStream fileInputStreamForBackground2 = null;
            try {
                fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/george.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image imageForBackground2 = new Image(fileInputStreamForBackground2);
            BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground2),   CornerRadii.EMPTY, Insets.EMPTY);
            Background background2 = new Background(backgroundFill);
            Stage newWindow2 = new Stage();
            newWindow2.initModality(Modality.APPLICATION_MODAL);
            newWindow2.setWidth(230);
            newWindow2.setHeight(230);
            Rectangle computer = new Rectangle(100,22,Color.BROWN);
            computer.setY(10);
            computer.setX(10);
            Text vsComputer = new Text("vs Computer");
            vsComputer.setX(20);
            vsComputer.setY(22);
            vsComputer.setFill(Color.YELLOW);
            vsComputer.setOnMouseClicked(mouseEvent1 -> {
                TicTacToe.display(username);
                newWindow2.close();
            });

            Rectangle player = new Rectangle(100,22,Color.BROWN);
            player.setX(120);
            player.setY(10);
            Text vsPlayer = new Text("vs Player");
            vsPlayer.setX(137);
            vsPlayer.setY(22);
            vsPlayer.setFill(Color.YELLOW);
            vsPlayer.setOnMouseClicked(mouseEvent1 -> {
                serachPlayerForSnakeNLadder.display(username , "TTT");
            });
            Pane layout = new Pane();
            layout.setPadding(new Insets(10, 10, 0, 10));
            layout.getChildren().addAll(computer,player, vsComputer, vsPlayer);
            layout.setBackground(background2);
            Scene scene = new Scene(layout, 200, 100);
            newWindow2.setScene(scene);
            newWindow2.show();
            newWindow2.setResizable(false);
        });
        Rectangle snakeAndLadder = snakeNLadder();
        snakeAndLadder.setOnMouseClicked(mouseEvent -> {
            FileInputStream fileInputStreamForBackground2 = null;
            try {
                fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/george.jpg");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image imageForBackground2 = new Image(fileInputStreamForBackground2);
            BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground2),   CornerRadii.EMPTY, Insets.EMPTY);
            Background background2 = new Background(backgroundFill);
            Stage newWindow2 = new Stage();
            newWindow2.initModality(Modality.APPLICATION_MODAL);
            newWindow2.setWidth(230);
            newWindow2.setHeight(230);
            Rectangle computer = new Rectangle(100,22,Color.BROWN);
            computer.setY(10);
            computer.setX(10);
            Text vsComputer = new Text("vs Computer");
            vsComputer.setX(20);
            vsComputer.setY(22);
            vsComputer.setFill(Color.YELLOW);
            vsComputer.setOnMouseClicked(mouseEvent1 -> {
                SnakeNLeader.display();
                newWindow2.close();
            });

            Rectangle player = new Rectangle(100,22,Color.BROWN);
            player.setX(120);
            player.setY(10);
            Text vsPlayer = new Text("vs Player");
            vsPlayer.setX(137);
            vsPlayer.setY(22);
            vsPlayer.setFill(Color.YELLOW);
            vsPlayer.setOnMouseClicked(mouseEvent1 -> {
                serachPlayerForSnakeNLadder.display(username , "SNL");
                newWindow2.close();
            });
            Pane layout = new Pane();
            layout.setPadding(new Insets(10, 10, 0, 10));
            layout.getChildren().addAll(computer,player, vsComputer, vsPlayer);
            layout.setBackground(background2);
            Scene scene = new Scene(layout, 200, 100);
            newWindow2.setScene(scene);
            newWindow2.show();
            newWindow2.setResizable(false);
        });

        Pane layout = new Pane();
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(userIcon() , userName(username) , chat , refresh , onlineUsers, ticTacToe, ticTacToeText, refreshTxt , snakeAndLadder, information, exitIcon , textForExit);
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/333.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageForBackground = new Image(fileInputStreamForBackground);
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);
        Scene scene = new Scene(layout, 300, 400);
        newWindow.setScene(scene);
        newWindow.show();

    }
    private static Rectangle userIcon(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/HomeMenu/iconPink.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(fileInputStream);
        Rectangle rectangleOfUser = new Rectangle(50,50,new ImagePattern(image));
        rectangleOfUser.setY(10);
        rectangleOfUser.setX(128);
        return rectangleOfUser;
    }
    private static Text userName(String username){
        Text text = new Text(username);
        text.setX(180);
        text.setY(42);
        text.setFill(Color.MINTCREAM);
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 16));
        return text;
    }
    private static Rectangle massageIcon(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/HomeMenu/mail.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(fileInputStream);
        Rectangle rectangleOfMassanger = new Rectangle(70,45,new ImagePattern(image));
        rectangleOfMassanger.setX(520);
        rectangleOfMassanger.setY(10);
        return rectangleOfMassanger;
    }
    private static Rectangle tictactoe(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/HomeMenu/TicTactoe.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(fileInputStream);
        Rectangle rectangle = new Rectangle(150,150, new ImagePattern(image));
        rectangle.setX(128);
        rectangle.setY(80);
        return rectangle;
    }
    private static Rectangle snakeNLadder(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("src/HomeMenu/SNLP.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(fileInputStream);
        Rectangle rectangle = new Rectangle(150,150, new ImagePattern(image));
        rectangle.setX(128);
        rectangle.setY(250);
        return rectangle;
    }
    private static void massanger(String username) throws IOException {
        socket = new Socket("127.0.0.1", 8889);
        handleConnection(username);
        System.out.println("Successfully connected to server!") ;
    }
    private static void handleConnection(String username) {
        try {
            String output = "chat: " + username;
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF(output);
            dataOutputStream.flush();
            Thread thread = new chatThread(socket , dataOutputStream , dataInputStream , username, "temporary");///TODO: hi bitch change bitch bitch bitch
            thread.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //    private static class threadForServerClient extends Thread {
//
//        @Override
//        public void run() {
//            try {
//                dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(dataInputStream);
//        }
//    }
    private static Rectangle refresh(){
        Rectangle rectangleOfRefresh = new Rectangle(60,25);
        rectangleOfRefresh.setX(22);
        rectangleOfRefresh.setY(410);
        rectangleOfRefresh.setFill(Color.MINTCREAM);
        return rectangleOfRefresh;
    }
    private static Text refreshText(){
        Text refresh  = new Text("Refresh");
        refresh.setX(28);
        refresh.setY(425);
        refresh.setFill(Color.FIREBRICK);
        return refresh;
    }
    private static String checkOnline(){
        String result = "";
        try {
            Socket socket = new Socket("127.0.0.1", 8889);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream.writeUTF("OnlineUsers: ");
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static TextArea textAreaOfOnlineUsers(){
        TextArea textArea = new TextArea();
        textArea.appendText(FIRSTTEXTOFREFRSH);
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/BLANCHEDALMOND.jpeg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageForBackground = new Image(fileInputStreamForBackground);
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        textArea.setBackground(background);
        textArea.setPrefHeight(400);
        textArea.setPrefSize(119,400);
        textArea.setEditable(false);
        return textArea;
    }
    private static void checking(String username){
        try {
            Socket newSocket = new Socket("127.0.0.1", 8889);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(newSocket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(newSocket.getInputStream()));
            new CheckThread(newSocket , dataInputStream , dataOutputStream, username).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static class CheckThread implements Runnable{
        Socket socket;
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        String username;

        public CheckThread(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, String username) {
            this.socket = socket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.username = username;
        }

        @Override
        public void run() {
            try {
                dataOutputStream.writeUTF("game: " + username );
                dataOutputStream.flush();
                System.out.println(dataInputStream.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}