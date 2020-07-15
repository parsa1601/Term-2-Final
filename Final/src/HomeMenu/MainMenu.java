package HomeMenu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class MainMenu extends Application {
    private String srcPath = "/home/parsa/IdeaProjects/MyProject Uses JavaFx/src";
    Stage window;
    Scene scene;
    private Rectangle signInOption,signUpOption;
    private Circle mainIcon,exitIcon;
    private Text textForSignUp,textForSignIn,textForExit ;


    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Main Menu!");
        signInOption = new Rectangle(20,240,200,30);
        signInOption.setFill(Color.THISTLE);
        signUpOption = new Rectangle(20,290,200,30);
        signUpOption.setFill(Color.THISTLE);
        mainIcon = new Circle(120,110,100);
        FileInputStream fileInputStreamForIcon = new FileInputStream(srcPath + "/HomeMenu/koala.jpg");
        Image imageForIcon = new Image(fileInputStreamForIcon);
        mainIcon.setFill(new ImagePattern(imageForIcon));
        textForSignIn = new Text(95,260,"Sign In");
        textForSignIn.setFill(Color.BLUEVIOLET);
        textForSignUp = new Text(95,310,"Sign Up");
        textForSignUp.setFill(Color.BLUEVIOLET);
        exitIcon = new Circle(200,360,30);
        exitIcon.setFill(Color.SLATEBLUE);
        textForExit = new Text(185,365,"EXIT");

        textForSignUp.setOnMouseClicked(mouseEvent -> {
            SignUP.display(srcPath);
        });
        textForSignIn.setOnMouseClicked(mouseEvent -> {
            SignIn.display(srcPath , window);
        });



        exitIcon.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });
        textForExit.setOnMouseClicked(mouseEvent -> {
            System.exit(0);
        });


        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20,20,20,20));
        FileInputStream fileInputStreamForBackground = new FileInputStream(srcPath + "/HomeMenu/111.jpg");
        Image imageForBackground = new Image(fileInputStreamForBackground);
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
                );
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);
        layout.getChildren().addAll(signInOption,signUpOption,textForSignIn,textForSignUp,exitIcon,textForExit);///mainIcon
        scene = new Scene(layout,250,400);
        window.setScene(scene);
        window.setResizable(false);
        window.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
//    class signInThread extends Thread{
//        public signInThread() { }
//        @Override
//        public void run() {
//            SignIn.display(srcPath);
//        }
//    }
}

