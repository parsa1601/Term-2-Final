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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SignUpError {
    public static void display()  {
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/LightOrange.jpg");
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
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Error!");
        newWindow.setWidth(100);
        Text error1 = new Text("Username");
        Text error2 = new Text(" Is ");
        Text error3 = new Text("Not New!");
//        Text error4 = new Text("");
        error1.setFill(Color.RED);
        error2.setFill(Color.RED);
        error3.setFill(Color.RED);
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

        Button confirm = new Button("Ok");

        confirm.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                newWindow.close();
            }
        } ) ;

        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(error1,error2,error3,confirm);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(background2);
//        layout.setBackground(background);
        Scene scene = new Scene(layout,100,200);
        newWindow.setScene(scene);
        newWindow.showAndWait();
    }
}

