package HomeMenu;
import com.sun.javafx.text.TextRun;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SignUP {


    static DataOutputStream dataOutputStream;
    static DataInputStream dataInputStream;
    private static Socket socket;
    public static JSONObject  user = null;
    public static JSONArray userList = null;
    private static String output = "";

    public static void   display(String srcPath) {
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Signing Up...");
        newWindow.setWidth(250);
        Label myLabel = new Label();
        myLabel.setText("Please Enter Following Information");


        Text firstNameText = new Text("First Name");
        Text lastNameText = new Text("Last Name");
        Text ageText = new Text("Age");
        Text userNameText = new Text("User Name");
        Text passWordText = new Text("Password");


        TextField firstNameTextField = new TextField();
        firstNameTextField.setPromptText("Enter Your First Name");
        TextField lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Enter Your Last Name");
        TextField ageTextField = new TextField();
        ageTextField.setPromptText("Enter Your Age");
        TextField userNameTextField = new TextField();
        userNameTextField.setPromptText("Enter Your Preferred User Name");
        PasswordField passWordTextField = new PasswordField();
        passWordTextField.setPromptText("Enter Your Preferred Password");





        Button confirm = new Button("CONFIRM");
        confirm.setOnMouseClicked(mouseEvent ->  {
            System.out.println("ll --->" + lastNameTextField.getText());

            output += "fname: " + firstNameTextField.getText();
            output += " lname: " + lastNameTextField.getText();
            output += " uname: " + userNameTextField.getText();
            output += " pass: " + passWordTextField.getText();

                try {
                    run();
                } catch (IOException e) {
                    e.getMessage();
                }
                ///PrintWriter outFileInfo = null;
                /*try (
                        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("/src/HomeMenu/Users/Info/" + userNameText.getText() + ".dat"));
                ) {
                    /*FileWriter fw = new FileWriter(srcPath + "HomeMenu/Users/Info/" + userNameText.getText()+".dat");
                    BufferedWriter bw = new BufferedWriter(fw);
                    outFileInfo = new PrintWriter(bw);
                    String informationOfEachUserString = "Name: " + firstNameText.getText() + " " + lastNameText.getText() + "\n" +
                            "Age: " + ageText.getText() + "\n" +
                            "Username: " + userNameText.getText() + "\n" +
                            "Password: " + passWordText.getText();
                    outputStream.writeUTF(informationOfEachUserString);


                    //outFileInfo.print(informationOfEachUserString);

                } ///if (outFileInfo != null) outFileInfo.close();
                catch (IOException e) {
                    e.getMessage();
                }
*/
                ConfirmSignUp.display(userNameText.getText());
                newWindow.close();
        });


        VBox layout = new VBox(11);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(myLabel, firstNameText, firstNameTextField, lastNameText, lastNameTextField, ageText, ageTextField,
                userNameText, userNameTextField, passWordText, passWordTextField, confirm);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 400);
        newWindow.setScene(scene);
        newWindow.show();

    }

        public static void  run() throws IOException {
            socket = new Socket("127.0.0.1", 8889);
            handleConnection();
            System.out.println("Successfully connected to server!") ;
        }

        private static void handleConnection() {
        try {
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
//            System.out.println(userList.toJSONString());
//            ObjectOutputStream oos = new ObjectOutputStream(dataOutputStream);
            ////dataOutputStream.writeUTF();
            dataOutputStream.writeUTF(output);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
