package HomeMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class chatThread extends  Thread {
//    boolean isServer;
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    String massage;
    String username;
    String history = "";
    String contact;

    public chatThread(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, String username, String contact) {
        this.socket = socket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.username = username;
        this.contact = contact;
    }
    public chatThread( String username, String contact) {
        this.username = username;
        this.contact = contact;
    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStreamForBackground = null;
                fileInputStreamForBackground = new FileInputStream("src/HomeMenu/pattern25.jpg");
            Image imageForBackground = new Image(fileInputStreamForBackground);
            BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );
            BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),   CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(backgroundImage);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            massage = dataInputStream.readUTF();
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.setTitle("chat");
            newWindow.setWidth(300);
            newWindow.setHeight(500);
            VBox layout = new VBox(20);
            layout.setPadding(new Insets(20,20,20,20));
            layout.setBackground(background);
            TextField textField = new TextField();
            textField.setPromptText("Type Here....");
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            if (!history.equals("")) textArea.appendText(history);
            Button send = new Button("Send");
            Button refresh = new Button("Refresh");
////            Button hide = new Button("Hide");
//            hide.setOnMouseClicked(mouseEvent -> {
//                history += textArea.getText();
//                newWindow.close();
//            });
//            updateThread ut = new updateThread( socket, dataOutputStream,  dataInputStream,  username);
//            ut.run();
//            textArea.appendText(ut.getAns());
            send.setOnMouseClicked(mouseEvent -> {
                System.out.println("hi");
                massage = "Send: " + "To: "+ this.contact + " From: " + username + ": ";
                System.out.println(massage);
                massage += textField.getText();
                try {
                    socket = new Socket("127.0.0.1", 8889);
                    dataOutputStream = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
                    dataOutputStream.writeUTF(massage);
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    FileReader fileReader = new FileReader("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + username + " /ChatsInfo/" + contact + " .txt");
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String massage = "";
                    String lastLine = bufferedReader.readLine();
                    while (lastLine != null){
                        massage += lastLine;
                        lastLine = bufferedReader.readLine();
                    }
                    textArea.appendText(dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                textField.clear();
            });
            refresh.setOnMouseClicked(mouseEvent -> {
                massage = "Sendd: " + "To: "+ contact ;
                try {
                    socket = new Socket("127.0.0.1", 8889);
                    dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    dataOutputStream.writeUTF(massage);
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    textArea.appendText (dataInputStream.readUTF());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            layout.getChildren().addAll(textArea,textField,send,refresh);
            layout.setAlignment(Pos.CENTER);
            Scene scene = new Scene(layout,100,200);
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

