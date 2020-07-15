package HomeMenu;

import HomeMenu.SingInError;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class NewPass {

    public static void   display(String username)  {
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/LightOrange.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageForBackground = new Image(fileInputStreamForBackground);
        BackgroundImage backgroundImage = new BackgroundImage(imageForBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
//        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundImage);
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/BLANCHEDALMOND.png");
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
        newWindow.setTitle("changing password...");
        newWindow.setWidth(250);
        Label myLabel = new Label();
        myLabel.setText("What is your favorite color?!");
        Text passWordText = new Text("New Password");
        Rectangle rectangle = new Rectangle(130,70);
        rectangle.setX(60);
        rectangle.setY(270);
        Text enterText = new Text("Done!");
        enterText.setX(70);
        enterText.setY(275);
        enterText.setFill(Color.BROWN);
        Text text = new Text("incorrect information");
        text.setFill(Color.RED);
        text.setX(70);
        text.setY(2850);
        text.setVisible(false);

        TextField ans = new TextField();
        ans.setBackground(background2);
        PasswordField passWordTextField = new PasswordField();
        passWordTextField.setBackground(background2);

        enterText.setOnMouseClicked(mouseEvent -> {
            try {
                File userFile = new File("src/HomeMenu/Users/Info/" + username + " /" + username + " .txt");
                FileReader fr = new FileReader(userFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String ansinfile = null;
                String backup = "";

                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Password: ")) {
                        backup += "Password: " + passWordTextField.getText() + "\n";
                    }else {
                        backup += line + "\n";
                    }
                }
                File userFile2 = new File("src/HomeMenu/Users/Info/" + username + " /" + username + " .txt");
                FileReader fr2 = new FileReader(userFile2);
                BufferedReader br2 = new BufferedReader(fr2);
                while ((line = br2.readLine()) != null) {
                    if (line.startsWith("answer: ")) {
                        ansinfile = line.substring(8);
                    }
                }
                System.out.println(ansinfile);
                if (ans.getText().equals(ansinfile)) {
                    FileWriter fw = new FileWriter(userFile);
                    BufferedWriter bf = new BufferedWriter(fw);
                    PrintWriter pw = new PrintWriter(bf);
                    pw.println(backup);
                   newWindow.close();
                   pw.close();
                } else text.setVisible(true);
            }catch (IOException e) {
                text.setVisible(true);
//                e.printStackTrace();
            }
        });

        VBox layout = new VBox(11);
        layout.setBackground(background);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(myLabel,ans,passWordText, passWordTextField , enterText, text);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 300);
        newWindow.setScene(scene);
        newWindow.show();
}
}

