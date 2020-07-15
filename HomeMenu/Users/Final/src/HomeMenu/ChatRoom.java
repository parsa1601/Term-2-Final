package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.TabExpander;
import java.io.*;
import java.net.CookieHandler;
import java.net.Socket;

public class ChatRoom {
    private static int counterOfContacts = 0;
    private static int counterOfGroups = 0;
    static Text contacts0 = new Text();
    static Text contacts1 = new Text();
    static Text contacts2 = new Text();
    static Text contacts3 = new Text();
    static Text contacts4 = new Text();
    static Text contacts5 = new Text();
    static Text contacts6 = new Text();
    static Text contacts7 = new Text();
    static Text contacts8 = new Text();
    static Text contacts9 = new Text();
    static Text groups0 = new Text();
    static Text groups1 = new Text();
    static Text groups2 = new Text();
    static Text groups3 = new Text();
    static Text groups4 = new Text();
    static Text groups5 = new Text();
    static Text groups6 = new Text();
    static Text groups7 = new Text();
    static Text groups8 = new Text();
    static Text groups9 = new Text();
    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    public static void display(String username){
        File file = new File("src/Homemenu/Info/" + username + " /ChatsInfo");
        file.mkdir();
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/redBlue.png");
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
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setTitle("Chat Room");
        newWindow.setWidth(600);
        newWindow.setHeight(600);


        Text contacts = new Text("Contacts");
        setContacts();
        setGroups();
        Text groups = new Text("Groups");
        contacts.setX(50);
        contacts.setY(30);
        contacts.setFill(Color.BLUE);
        contacts.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));
        groups.setX(444);
        groups.setY(30);
        groups.setFill(Color.BLUE);
        groups.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 25));


        Line line = new Line(300,35,300,580);
        line.setFill(Color.DARKTURQUOISE);


        Circle addGroup = new Circle(535,515,40,Color.MAROON);
        Circle addContact = new Circle(235,515,40,Color.DARKRED);
        addContact.setOnMouseClicked(mouseEvent -> {
            FileInputStream fileInputStreamForBackground2 = null;
            try {
                fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/lightBlue.jpeg");
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
            Stage newWindow2 = new Stage();
            newWindow2.initModality(Modality.APPLICATION_MODAL);
            newWindow2.setTitle("adding...");
            newWindow2.setWidth(380);
            newWindow2.setHeight(100);
            Label label = new Label();
            label.setTextFill(Color.NAVY);
            label.setText("Contact username:");
            TextField usernameTf = new TextField();
            Button add = new Button("Add");
            add.setTextFill(Color.NAVY);
            add.setOnMouseClicked(mouseEvent1 -> {
                File file2 = new File("src/HomeMenu/Users/Info/" + usernameTf.getText() + " ");
                if (file2.exists()){
                    File file4 = new File(("src/HomeMenu/Users/Info/" + username + " /ChatsInfo"));
                    System.out.println(file4.exists());
                    if (!file4.exists()) {
                        System.out.println(file4.mkdir());
                    }
                    try {
                        FileWriter fileWriter = new FileWriter("src/HomeMenu/Users/Info/" + username + " /ChatsInfo/" + usernameTf.getText() + " .txt");
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        PrintWriter printWriter= new PrintWriter(bufferedWriter);
//                        printWriter.println(usernameTf.getText() + " Just add to " + username + " contacts");
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setContactsEach(counterOfContacts, usernameTf.getText());
                    counterOfContacts++;
                    newWindow2.close();
                }
                else {
                    Stage newWindow3 = new Stage();
                    newWindow3.initModality(Modality.APPLICATION_MODAL);
                    newWindow3.setTitle("Error");
                    newWindow3.setWidth(111);
                    newWindow3.setHeight(120);
                    Label newLabel = new Label("\tInvalid \n Information");
                    newLabel.setTextFill(Color.DARKRED);
                    Button ok = new Button("Ok!");
                    ok.setTextFill(Color.MAROON);
                    ok.setOnMouseClicked(mouseEvent2 -> {
                        newWindow3.close();
                    });
                    Pane layout = new VBox(8);
                    layout.setPadding(new Insets(10, 10, 0, 10));
                    layout.getChildren().addAll(newLabel,ok);
                    layout.setBackground(background2);
                    Scene scene = new Scene(layout, 200, 100);
                    newWindow3.setScene(scene);
                    newWindow3.show();
                    newWindow3.setResizable(false);
                }
            });
            Pane layout = new HBox(8);
            layout.setPadding(new Insets(10, 10, 0, 10));
            layout.getChildren().addAll(label,usernameTf, add);
            layout.setBackground(background2);
            Scene scene = new Scene(layout, 200, 100);
            newWindow2.setScene(scene);
            newWindow2.show();
            newWindow2.setResizable(false);
        });
        addGroup.setOnMouseClicked(mouseEvent -> {
            FileInputStream fileInputStreamForBackground2 = null;
            try {
                fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/lightBlue.jpeg");
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
            Stage newWindow2 = new Stage();
            newWindow2.initModality(Modality.APPLICATION_MODAL);
            newWindow2.setTitle("creating...");
            newWindow2.setWidth(380);
            newWindow2.setHeight(100);
            Label label = new Label();
            label.setTextFill(Color.NAVY);
            label.setText("Group name:");
            TextField nameTf = new TextField();
            Button add = new Button("Creating");
            add.setTextFill(Color.NAVY);
            add.setOnMouseClicked(mouseEvent1 -> {
                File file2 = new File("src/HomeMenu/Users/Info/" + nameTf.getText() + " ");
                if (!file2.exists()){
                    file2.mkdir();
                    File file4 = new File(("src/HomeMenu/Users/Info/" + username + " /GroupsInfo"));
                    System.out.println(file4.exists());
                    if (!file4.exists()) {
                        System.out.println(file4.mkdir());
                    }
                    try {
                        FileWriter fileWriter = new FileWriter("src/HomeMenu/Users/Info/" + username + " /GroupsInfo/" + nameTf.getText() + " .txt");
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        PrintWriter printWriter= new PrintWriter(bufferedWriter);
                        printWriter.println(nameTf.getText() + " Just add to " + username + " groups");
                        printWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setGroupsEach(counterOfGroups, nameTf.getText());
                    counterOfGroups++;
                    newWindow2.close();
                }
                else {
                    Stage newWindow3 = new Stage();
                    newWindow3.initModality(Modality.APPLICATION_MODAL);
                    newWindow3.setTitle("Error");
                    newWindow3.setWidth(111);
                    newWindow3.setHeight(120);
                    Label newLabel = new Label("\tInvalid \n Information");
                    newLabel.setTextFill(Color.DARKRED);
                    Button ok = new Button("Ok!");
                    ok.setTextFill(Color.MAROON);
                    ok.setOnMouseClicked(mouseEvent2 -> {
                        newWindow3.close();
                    });
                    Pane layout = new VBox(8);
                    layout.setPadding(new Insets(10, 10, 0, 10));
                    layout.getChildren().addAll(newLabel,ok);
                    layout.setBackground(background2);
                    Scene scene = new Scene(layout, 200, 100);
                    newWindow3.setScene(scene);
                    newWindow3.show();
                    newWindow3.setResizable(false);
                }
            });
            Pane layout = new HBox(8);
            layout.setPadding(new Insets(10, 10, 0, 10));
            layout.getChildren().addAll(label,nameTf, add);
            layout.setBackground(background2);
            Scene scene = new Scene(layout, 200, 100);
            newWindow2.setScene(scene);
            newWindow2.show();
            newWindow2.setResizable(false);
        });

        contacts0.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts0.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts1.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts1.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts2.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts2.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts3.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts3.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts4.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts4.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts5.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts5.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts6.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts6.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts7.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts7.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts8.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts8.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        contacts9.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, contacts9.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        groups0.setOnMouseClicked(mouseEvent -> {
            System.out.println("clicked");
            try {
                massanger(username, groups0.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups1.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups1.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups2.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups2.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups3.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups3.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups4.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups4.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups5.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups5.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups6.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups6.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups7.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups7.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups8.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups8.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        groups9.setOnMouseClicked(mouseEvent -> {
            try {
                massanger(username, groups9.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        Pane layout = new Pane();
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(contacts, groups, line, addContact,addGroup,contacts0,contacts1,contacts2,contacts3,contacts4,contacts5,contacts6,contacts7,contacts8,contacts9,
                groups0, groups1,groups2,groups3,groups4,groups5,groups6,groups7,groups8,groups9);
        layout.setBackground(background);
        Scene scene = new Scene(layout, 300, 400);
        newWindow.setScene(scene);
        newWindow.show();
        newWindow.setResizable(false);
    }

    private static void setContacts(){
        contacts0.setX(50);
        contacts0.setY(100);
        contacts0.setVisible(false);
        contacts1.setX(50);
        contacts1.setY(135);
        contacts1.setVisible(false);
        contacts2.setX(50);
        contacts2.setY(170);
        contacts2.setVisible(false);
        contacts3.setX(50);
        contacts3.setY(205);
        contacts3.setVisible(false);
        contacts4.setX(50);
        contacts4.setY(240);
        contacts4.setVisible(false);
        contacts5.setX(50);
        contacts5.setY(275);
        contacts5.setVisible(false);
        contacts6.setX(50);
        contacts6.setY(310);
        contacts6.setVisible(false);
        contacts7.setX(50);
        contacts7.setY(345);
        contacts7.setVisible(false);
        contacts8.setX(50);
        contacts8.setY(380);
        contacts8.setVisible(false);
        contacts9.setX(50);
        contacts9.setY(415);
        contacts9.setVisible(false);
    }

    private static void setGroups() {
        groups0.setX(350);
        groups0.setY(100);
        groups0.setVisible(false);
        groups1.setX(350);
        groups1.setY(135);
        groups1.setVisible(false);
        groups2.setX(350);
        groups2.setY(170);
        groups2.setVisible(false);
        groups3.setX(350);
        groups3.setY(205);
        groups3.setVisible(false);
        groups4.setX(350);
        groups4.setY(240);
        groups4.setVisible(false);
        groups5.setX(350);
        groups5.setY(275);
        groups5.setVisible(false);
        groups6.setX(350);
        groups6.setY(310);
        groups6.setVisible(false);
        groups7.setX(350);
        groups7.setY(345);
        groups7.setVisible(false);
        groups8.setX(350);
        groups8.setY(380);
        groups8.setVisible(false);
        groups9.setX(350);
        groups9.setY(415);
        groups9.setVisible(false);
    }

    private static void setContactsEach(int number , String con){
        if (number == 0) {
            contacts0.setVisible(true);
            contacts0.setText(con);
            contacts0.setFill(Color.CYAN);
        }
        if (number == 1) {
            contacts1.setVisible(true);
            contacts1.setText(con);
            contacts1.setFill(Color.CYAN);
        }
        if (number == 2) {
            contacts2.setVisible(true);
            contacts2.setText(con);
            contacts2.setFill(Color.CYAN);
        }
        if (number == 3) {
            contacts3.setVisible(true);
            contacts3.setText(con);
            contacts3.setFill(Color.CYAN);
        }if (number == 4) {
            contacts4.setVisible(true);
            contacts4.setText(con);
            contacts4.setFill(Color.CYAN);
        }if (number == 5) {
            contacts5.setVisible(true);
            contacts5.setText(con);
            contacts5.setFill(Color.CYAN);
        }
        if (number == 5) {
            contacts5.setVisible(true);
            contacts5.setText(con);
            contacts5.setFill(Color.CYAN);
        }
        if (number == 6) {
            contacts6.setVisible(true);
            contacts6.setText(con);
            contacts6.setFill(Color.CYAN);
        }if (number == 7) {
            contacts7.setVisible(true);
            contacts7.setText(con);
            contacts7.setFill(Color.CYAN);
        }if (number == 8) {
            contacts8.setVisible(true);
            contacts8.setText(con);
            contacts8.setFill(Color.CYAN);
        }if (number == 9) {
            contacts9.setVisible(true);
            contacts9.setText(con);
            contacts9.setFill(Color.CYAN);
        }
    }

    private static void setGroupsEach(int number, String con) {
        if (number == 0) {
            groups0.setVisible(true);
            groups0.setText(con);
            groups0.setFill(Color.CYAN);
        }
        if (number == 1) {
            groups1.setVisible(true);
            groups1.setText(con);
            groups1.setFill(Color.CYAN);
        }
        if (number == 2) {
            groups2.setVisible(true);
            groups2.setText(con);
            groups2.setFill(Color.CYAN);
        }
        if (number == 3) {
            groups3.setVisible(true);
            groups3.setText(con);
            groups3.setFill(Color.CYAN);
        }if (number == 4) {
            groups4.setVisible(true);
            groups4.setText(con);
            groups4.setFill(Color.CYAN);
        }if (number == 5) {
            groups5.setVisible(true);
            groups5.setText(con);
            groups5.setFill(Color.CYAN);
        }
        if (number == 5) {
            groups5.setVisible(true);
            groups5.setText(con);
            groups5.setFill(Color.CYAN);
        }
        if (number == 6) {
            groups6.setVisible(true);
            groups6.setText(con);
            groups6.setFill(Color.CYAN);
        }if (number == 7) {
            groups7.setVisible(true);
            groups7.setText(con);
            groups7.setFill(Color.CYAN);
        }if (number == 8) {
            groups8.setVisible(true);
            groups8.setText(con);
            groups8.setFill(Color.CYAN);
        }if (number == 9) {
            groups9.setVisible(true);
            groups9.setText(con);
            groups9.setFill(Color.CYAN);
        }
    }
    private static void massanger(String username ,String contact) throws IOException {
        socket = new Socket("127.0.0.1", 8889);
        handleConnection(username ,contact);
        System.out.println("Successfully connected to server!") ;
    }
    private static void handleConnection(String username ,String contact) {
        try {
            String output = "chat: " + username;
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println(output);
            dataOutputStream.writeUTF(output);
            dataOutputStream.flush();
            Thread thread = new chatThread(socket , dataOutputStream , dataInputStream , username, contact);
            thread.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
