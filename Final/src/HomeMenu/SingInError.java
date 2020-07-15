package HomeMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SingInError {
    public static void display(String username)  {
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/signInError.png");
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
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground2),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background2 = new Background(backgroundFill);
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Error!");
        newWindow.setWidth(300);
        newWindow.setHeight(300);
//        Text error1 = new Text("Username");
//        Text error2 = new Text(" Or ");
//        Text error3 = new Text("Password");
//        Text error4 = new Text("Incorrect");
//        error1.setFill(Color.RED);
//        error2.setFill(Color.RED);
//        error3.setFill(Color.RED);
//        error4.setFill(Color.RED);
//        FileInputStream fileInputStreamForBackground = null;
//        try {
//            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/F.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Image imageForBackground = new Image(fileInputStreamForBackground);
//        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT
//        );
//        Background background = new Background(backgroundImage);
        Text forgetPassword = new Text("Change Password :(");
        forgetPassword.setX(170);
        forgetPassword.setY(235);
        forgetPassword.setFill(Color.DARKRED);
        forgetPassword.setOnMouseClicked(mouseEvent -> {
            NewPass.display(username);
        });

        Text ok = new Text("OK!");
        ok.setX(250);
        ok.setY(250);
        ok.setFill(Color.DARKRED);
        ok.setOnMouseClicked(mouseEvent -> {
            newWindow.close();
        });

        Pane layout = new Pane();
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(ok,forgetPassword);
        layout.setBackground(background2);
        Scene scene = new Scene(layout,100,200);
        newWindow.setScene(scene);
        newWindow.showAndWait();
    }
}
