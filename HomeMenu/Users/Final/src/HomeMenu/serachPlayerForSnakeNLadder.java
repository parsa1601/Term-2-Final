package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class serachPlayerForSnakeNLadder {
    public static void display(String username , String gameName){
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/lightBlue.jpeg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageForBackground2 = new Image(fileInputStreamForBackground2);
        BackgroundImage backgroundImage2 = new BackgroundImage(imageForBackground2,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        Background background2 = new Background(backgroundImage2);
        Stage newWindow2 = new Stage();
        newWindow2.initModality(Modality.APPLICATION_MODAL);
        newWindow2.setTitle("???");
        newWindow2.setWidth(380);
        newWindow2.setHeight(100);
        Label label = new Label();
        label.setTextFill(Color.NAVY);
        label.setText("opponent username:");
        TextField nameTf = new TextField();
        Button add = new Button("+");
        Button go = new Button("Go!");
        go.setTextFill(Color.NAVY);
        add.setTextFill(Color.NAVY);
        add.setOnMouseClicked(mouseEvent1 -> {
            File file2 = new File("src/HomeMenu/Users/Info/" + username + " /Game");
            if (!file2.exists()) {
                file2.mkdir();
            }
            File file3 = new File("src/HomeMenu/Users/Info/" + username + " /Game/" + gameName);
            if (!file3.exists()) {
                file3.mkdir();
            }
            File file4 = new File("src/HomeMenu/Users/Info/" + username + " /Game/" + gameName + "/" + nameTf.getText() + " .txt");
                if (!file4.exists()) {
                    try {
                        file4.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    file4.delete();
                    try {
                        file4.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        });
        go.setOnMouseClicked(mouseEvent -> {
                File file2 = new File(("src/HomeMenu/Users/Info/" + nameTf.getText() + " /Game/"+ gameName + "/" + username + " .txt"));
                if (!file2.exists()){
                    Stage newWindow3 = new Stage();
                    newWindow3.initModality(Modality.APPLICATION_MODAL);
                    newWindow3.setTitle("Error");
                    newWindow3.setWidth(111);
                    newWindow3.setHeight(150);
                    Label newLabel = new Label("\tInvalid \n Opponent\n Wait or \nChange Opponent!");
                    newLabel.setTextFill(Color.DARKRED);
                    Button ok = new Button("Ok!");
                    ok.setTextFill(Color.MAROON);
                    ok.setOnMouseClicked(mouseEvent2 -> {
                        newWindow3.close();
                    });
                    Pane layout = new VBox(8);
                    layout.setPadding(new Insets(10, 10, 0, 10));
                    layout.getChildren().addAll(newLabel,ok);
                    layout.setBackground(background2);
                    Scene scene = new Scene(layout, 200, 100);
                    newWindow3.setScene(scene);
                    newWindow3.show();
                    newWindow3.setResizable(false);
                }else {
                    newWindow2.close();
                    if(gameName.equalsIgnoreCase("SNL")) StartSNl.display(username, nameTf.getText());
                    else if (gameName.equalsIgnoreCase("TTT")) TicTacToeMultiPlayer1.display(username,nameTf.getText());
                }
        });
        Pane layout = new HBox(8);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(label,nameTf, add, go);
        layout.setBackground(background2);
        Scene scene = new Scene(layout, 200, 100);
        newWindow2.setScene(scene);
        newWindow2.show();
        newWindow2.setResizable(false);
    }
}
