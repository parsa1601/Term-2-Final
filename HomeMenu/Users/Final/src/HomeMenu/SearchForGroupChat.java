package HomeMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SearchForGroupChat {
    public static void display(){
        try {
            FileInputStream fileInputStreamForBackground = null;
            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/george.jpg");
            Image imageForBackground = new Image(fileInputStreamForBackground);
            BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT
            );
            BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(backgroundFill);
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.setTitle("chat");
            newWindow.setWidth(300);
            newWindow.setHeight(300);

            Text text = new Text("Enter your friend's username");
            text.setX(60);
            text.setY(20);
            text.setFill(Color.BROWN);
            TextField textField = new TextField();
            textField.setPromptText("username");
            textField.setLayoutX(60);
            textField.setLayoutY(35);
            BackgroundFill backgroundFill2 = new BackgroundFill(Color.LIGHTYELLOW,CornerRadii.EMPTY, Insets.EMPTY);
            textField.setBackground(new Background(backgroundFill2));
            FileInputStream fileInputStreamForBackgroundSearch = null;
            fileInputStreamForBackgroundSearch = new FileInputStream("src/HomeMenu/searchBrown.png");
            Image imageForBackgroundSearch = new Image(fileInputStreamForBackgroundSearch);
            Rectangle search = new Rectangle(233,35,25,25);
            search.setFill(new ImagePattern(imageForBackgroundSearch));
            Rectangle done = new Rectangle(125,233,50,25);
            done.setFill(Color.YELLOW);
            Text doneText = new Text("DONE");
            doneText.setFill(Color.BROWN);
            doneText.setX(130);
            doneText.setY(250);
            doneText.setVisible(true);
            Pane layout = new Pane();
            layout.setPadding(new Insets(20,20,20,20));
            layout.setBackground(background);
            layout.getChildren().addAll(text,textField,search,done,doneText);
            Scene scene = new Scene(layout,100,200);
            newWindow.setScene(scene);
            newWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void searchFromServer(){

    }
}
