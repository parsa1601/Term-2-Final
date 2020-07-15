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
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream(srcPath + "/HomeMenu/black1.jpg");
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
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream(srcPath + "/HomeMenu/LightOrange.jpg");
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
        newWindow.setTitle("Signing Up...");
        newWindow.setWidth(250);
        newWindow.setHeight(555);
        Label myLabel = new Label();
        myLabel.setText("Please Enter Following Information");
        myLabel.setTextFill(Color.ORANGE);

        Text firstNameText = new Text("First Name");
        firstNameText.setFill(Color.ORANGE);
        Text lastNameText = new Text("Last Name");
        lastNameText.setFill(Color.ORANGE);
        Text ageText = new Text("E-mail");
        ageText.setFill(Color.ORANGE);
        Text userNameText = new Text("User Name");
        userNameText.setFill(Color.ORANGE);
        Text passWordText = new Text("Password");
        passWordText.setFill(Color.ORANGE);
        Text confirmText = new Text("Confirm");
        confirmText.setFill(Color.DARKORANGE);
        Text whatIsYourFavoriteColor = new Text("what is your \n favorite color?!");
        whatIsYourFavoriteColor.setFill(Color.DARKORANGE);


        TextField firstNameTextField = new TextField();
        firstNameTextField.setBackground(background2);
        TextField lastNameTextField = new TextField();
        lastNameTextField.setBackground(background2);
        TextField ageTextField = new TextField();
        ageTextField.setBackground(background2);
        TextField userNameTextField = new TextField();
        userNameTextField.setBackground(background2);
        PasswordField passWordTextField = new PasswordField();
        passWordTextField.setBackground(background2);
        TextField whatIsYourFavoriteColorTextField = new TextField();
        whatIsYourFavoriteColorTextField.setBackground(background2);

        
        String username = userNameTextField.getText();
        File file = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + username );
        System.out.println(file.exists());


        Button confirm = new Button("CONFIRM");
        confirmText.setOnMouseClicked(mouseEvent -> {
            System.out.println(ageTextField.getText());
            if (!checkMailForm(ageTextField.getText())){
                System.out.println(checkMailForm(ageTextField.getText()));
                passWordTextField.clear();
                ageTextField.clear();
                ageText.setText("\t\t    Email\n should be in an appropriate type!");
            }if (checkMail(ageTextField.getText()) == -1){
                    System.out.println(checkMail(ageTextField.getText()));
                    passWordTextField.clear();
                    ageTextField.clear();
                    ageText.setText("   Email\n should be new!");
                }if (passWordTextField.getText().length() <= 5){
                        passWordTextField.clear();
                        passWordText.setText("\t\t    Password" + "\n Should be at least 5 characters!");
                } else{
                writeMail(ageTextField.getText());
                if( new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + userNameTextField.getText() +" " ).exists()) {
                            SignUpError.display();
                        }else {
                                output += "fname: " + firstNameTextField.getText();
                                output += " lname: " + lastNameTextField.getText();
                                output += " uname: " + userNameTextField.getText();
                                output += " pass: " + passWordTextField.getText();
                                output += " email: " + ageTextField.getText();
                                output += " answer: " + whatIsYourFavoriteColorTextField.getText();

                                try {
                                    run();
                                } catch (IOException e) {
                                    e.getMessage();
                                }
                            String fname = firstNameTextField.getText();
                            String lname = lastNameTextField.getText();
                            String uname = userNameTextField.getText() + " ";
                            String pass = passWordTextField.getText();
                            String mail = ageTextField.getText();
                            String answer = whatIsYourFavoriteColorTextField.getText();
                            File thisDir = new File("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + uname);
                            System.out.println(thisDir.mkdir());
                            PrintWriter userInformationTXTFile = null;
                            try {
                                FileWriter fw = new FileWriter("/home/parsa/IdeaProjects/MyProject Uses JavaFx/src/HomeMenu/Users/Info/" + uname + "/" + uname + ".txt");
                                BufferedWriter bw = new BufferedWriter(fw);
                                userInformationTXTFile = new PrintWriter(bw);
                                MyDate today = new MyDate();
                                userInformationTXTFile.println("This in information of a new user who joined into server in date of: " + today.day + " / " + today.month + " / " + today.year);
                                userInformationTXTFile.println("name of the user: " + fname + " " + lname);
                                userInformationTXTFile.println("UserName: " + uname);
                                userInformationTXTFile.println("Password: " + pass);
                                userInformationTXTFile.println("E-mail: " + mail);
                                userInformationTXTFile.println("answer: " + answer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                if (userInformationTXTFile != null) userInformationTXTFile.close();
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
                            }
                        }
        });


        VBox layout = new VBox(11);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(myLabel, firstNameText, firstNameTextField, lastNameText, lastNameTextField, ageText, ageTextField,whatIsYourFavoriteColor, whatIsYourFavoriteColorTextField,userNameText, userNameTextField, passWordText, passWordTextField, confirmText);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(background);
        Scene scene = new Scene(layout, 300, 400);
        newWindow.setScene(scene);
        newWindow.show();
        newWindow.setResizable(false);
    }

    private static boolean checkMailForm(String text) {
        boolean b = false;
        for (int i = 0 ; i < text.length() ; i++){
            System.out.println(text.charAt(i));
            if (text.charAt(i) == '@') {
                System.out.println("hello");
                b = true;
            }
        }
        return (b);
    }

    private static int checkMail(String text) {
        File file = new File("src/HomeMenu/Users/Info/emails.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String backup = null;
            String line = bufferedReader.readLine();
            while (line != null) {
                backup = line;
                if (backup.equals(text)) return -1;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

        private static void writeMail(String text) {
        File file = new File ("src/HomeMenu/Users/Info/emails.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String backup = "";
            String line = bufferedReader.readLine();
            while (line != null){
                backup += line + "\n";
                line = bufferedReader.readLine();
            }
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printWriter.println(backup + text);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
