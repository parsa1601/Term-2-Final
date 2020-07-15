package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class Info {
    public static void display(String username){
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/LightOrange.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageForBackground2 = new Image(fileInputStreamForBackground2);
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground2),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background2 = new Background(backgroundFill);
        Stage newWindow2 = new Stage();
        newWindow2.initModality(Modality.APPLICATION_MODAL);
        newWindow2.setWidth(200);
        newWindow2.setHeight(130);
        Label label = new Label();
        label.setText("Choose one of this games");

        Button ttc = new Button("Tic Tac Toe ");
        ttc.setOnMouseClicked(mouseEvent -> {
            Stage newWindow23 = new Stage();
            newWindow23.initModality(Modality.APPLICATION_MODAL);
            newWindow23.setWidth(230);
            newWindow23.setHeight(230);


            TextArea textArea = new TextArea();

            File file = new File("src/HomeMenu/Users/Info/" + username + " /TicTacToe.txt");
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = bufferedReader.readLine();
                String massage = "";
                while (line!= null){
                    massage += line + "\n";
                    line = bufferedReader.readLine();
                }
                textArea.appendText(massage);
            } catch (IOException e) {
//                e.printStackTrace();
            }

            Pane layout = new VBox();
            layout.setPadding(new Insets(10, 10, 0, 10));
            layout.getChildren().addAll(textArea);
            Scene scene = new Scene(layout, 200, 100);
            newWindow23.setScene(scene);
            newWindow23.show();
            newWindow23.setResizable(false);
        });
        Button snl = new Button("Snake and Ladder");
        snl.setOnMouseClicked(mouseEvent -> {
            Stage newWindow23 = new Stage();
            newWindow23.initModality(Modality.APPLICATION_MODAL);
            newWindow23.setWidth(230);
            newWindow23.setHeight(230);

            TextField textField = new TextField();
            TextArea textArea = new TextArea();
            Button search = new Button("Go");
            search.setOnMouseClicked(mouseEvent1 -> {
                String contact = textField.getText();
                File file = new File("src/HomeMenu/Users/Info/" + username + " /Game/SNL/" + contact + " .txt");
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line = bufferedReader.readLine();
                    String massage = "";
                    while (line!= null){
                        massage += line + "\n";
                        line = bufferedReader.readLine();
                    }
                    textArea.appendText(massage);
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            });

            Pane layout = new VBox();
            layout.setPadding(new Insets(10, 10, 0, 10));
            layout.getChildren().addAll(textField, search, textArea);
            Scene scene = new Scene(layout, 200, 100);
            newWindow23.setScene(scene);
            newWindow23.show();
            newWindow23.setResizable(false);
        });

        Pane layout = new VBox();
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(label,ttc ,snl);
        layout.setBackground(background2);
        Scene scene = new Scene(layout, 200, 100);
        newWindow2.setScene(scene);
        newWindow2.show();
        newWindow2.setResizable(false);
    }
}
