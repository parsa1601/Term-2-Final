package HomeMenu;

import ChatServerTrial.Client;
import ChatServerTrial.Connection;
import ChatServerTrial.Server;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChatBox {

    private static boolean isServer = false;
    private static TextArea massages = new TextArea();
    private static Connection connection = isServer ? server() : client();

    private static Connection client() {
        return new Client(data ->{
            Platform.runLater(() ->{
                massages.appendText(data.toString() + "\n");
            });
        },"127.0.0.1" , 55555);
    }

    private static Connection server() {
        return new Server(data ->{
            Platform.runLater(() ->{
                massages.appendText(data.toString() + "\n");
            });
        },55555);
    }


    private static Parent createContent(){
        massages.setPrefHeight(500);
        TextField field = new TextField();

        field.setOnAction(event -> {
            String massage = isServer ? "Server: " : "Client: ";
            massage += field.getText();
            field.clear();

            massages.appendText(massage + "\n");

            try {
                connection.sending(massage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox root = new VBox(20, massages , field);
        root.setPrefSize(550,300);
        return root;
    }

    public static void display(boolean infoAboutBeingServer){
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Chat Room");
        newWindow.setScene(new Scene(createContent()));
        newWindow.show();
        isServer = infoAboutBeingServer;
    }
}
