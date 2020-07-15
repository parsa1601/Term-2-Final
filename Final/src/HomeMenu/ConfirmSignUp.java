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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ConfirmSignUp {
    public static void display (String username) {
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
        newWindow.setTitle("Done!");
        newWindow.setWidth(400);
        Label myLabel = new Label();
        myLabel.setText(username + " Created Successfully!" + "\nYou Can Now Enter By Click On Sign In");

        Button confirm = new Button("Ok");

        confirm.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                newWindow.close();
            }
        } ) ;

        VBox layout = new VBox(25);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(myLabel,confirm);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(background2);
        Scene scene = new Scene(layout,300,300);
        newWindow.setScene(scene);
        newWindow.showAndWait();

    }
}
