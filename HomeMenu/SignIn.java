package HomeMenu;

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
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class SignIn {

    public static void   display(String srcPath)  {
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream(srcPath + "/HomeMenu/888.jpeg");
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
            fileInputStreamForBackground2 = new FileInputStream(srcPath + "/HomeMenu/BLANCHEDALMOND.png");
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
        newWindow.setTitle("Signing In...");
        newWindow.setWidth(250);
        Label myLabel = new Label();
        myLabel.setText("Enter Username and Password");
        myLabel.setTextFill(Color.LIGHTPINK);
        Text userNameText = new Text("User Name");
        userNameText.setFill(Color.LIGHTPINK);
        Text passWordText = new Text("Password");
        passWordText.setFill(Color.LIGHTPINK);
        Rectangle rectangle = new Rectangle(130,70);
        rectangle.setX(60);
        rectangle.setY(270);
        rectangle.setFill(Color.LIGHTPINK);
        Text enterText = new Text("ENTER");
        enterText.setX(70);
        enterText.setY(275);
        enterText.setFill(Color.LIGHTPINK);


        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter Your User Name");
        userNameTextField.setBackground(background2);
        PasswordField passWordTextField = new PasswordField();
        passWordTextField.setPromptText("Enter Your Password");
        passWordTextField.setBackground(background2);

        Button enter= new Button("ENTER");
        enterText.setOnMouseClicked(mouseEvent -> {
            try {
                File userFile = new File(srcPath + "/HomeMenu/Users/Info/" + userNameTextField.getText() + " /" + userNameTextField.getText() + " .txt");
                FileReader fr = new FileReader(userFile);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String usernameFoundedInFile = null;
                String pwdFoundedInFile = null;
                while ((line = br.readLine()) != null) {
                    if (line.charAt(0) == 'U') {
                        usernameFoundedInFile = line.substring(10);
                    }
                    if (line.charAt(0) == 'P') {
                        pwdFoundedInFile = line.substring(10);
                    }
                }
                System.out.println(usernameFoundedInFile);
                System.out.println(pwdFoundedInFile);
                System.out.println("sign in");
                Socket socket;
                DataOutputStream dataOutputStream;
                String output = "SignIn: " + "Username: " + userNameTextField.getText();
                if (usernameFoundedInFile.equals(userNameTextField.getText() + " ") && pwdFoundedInFile.equals(passWordTextField.getText())) {
                    System.out.println("congratulation Windows");
                    socket = new Socket("127.0.0.1", 8889);
                    dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                    App.display(userNameTextField.getText());
                    dataOutputStream.writeUTF(output);
                    dataOutputStream.flush();
                    newWindow.close();

                } else SingInError.display();
            }catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox layout = new VBox(11);
        layout.setBackground(background);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(myLabel,userNameText, userNameTextField,passWordText, passWordTextField , enterText);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 250, 300);
        newWindow.setScene(scene);
        newWindow.show();
}
}
