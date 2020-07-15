package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class StartSNl {
    private static String massageHeader;
    public static void display(String username, String contact) {
        FileInputStream fileInputStreamForBackground2 = null;
        try {
            fileInputStreamForBackground2 = new FileInputStream("src/HomeMenu/george.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageForBackground2 = new Image(fileInputStreamForBackground2);
        BackgroundFill backgroundFill = new BackgroundFill(new ImagePattern(imageForBackground2),   CornerRadii.EMPTY, Insets.EMPTY);
        Background background2 = new Background(backgroundFill);
        Stage newWindow2 = new Stage();
        newWindow2.initModality(Modality.APPLICATION_MODAL);
        newWindow2.setWidth(230);
        newWindow2.setHeight(230);
        Rectangle computer = new Rectangle(100,22, Color.BROWN);
        computer.setY(10);
        computer.setX(10);
        Text vsComputer = new Text("Yes");
        vsComputer.setX(20);
        vsComputer.setY(22);
        vsComputer.setFill(Color.YELLOW);
        vsComputer.setOnMouseClicked(mouseEvent1 -> {
            if (yes(username , contact).equals("can")){
                SnakeNLadderMultiPlayer1.display("yes" , username , contact);
                newWindow2.close();
            }
            else if (yes(username , contact).equals("cant")){
                System.out.println("Ridi To Entekhab");
            }
        });

        Rectangle player = new Rectangle(100,22,Color.BROWN);
        player.setX(120);
        player.setY(10);
        Text vsPlayer = new Text("No");
        vsPlayer.setX(137);
        vsPlayer.setY(22);
        vsPlayer.setFill(Color.YELLOW);
        vsPlayer.setOnMouseClicked(mouseEvent1 -> {
            if (no(username , contact).equals("can")){
                SnakeNLadderMultiPlayer2.display("no", username , contact);
                newWindow2.close();
            }
            else if (no(username , contact).equals("cant")){
                System.out.println("Ridi To Entekhab");
            }
        });
        Pane layout = new Pane();
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(computer,player, vsComputer, vsPlayer);
        layout.setBackground(background2);
        Scene scene = new Scene(layout, 200, 100);
        newWindow2.setScene(scene);
        newWindow2.show();
        newWindow2.setResizable(false);
    }
    private static String yes(String username , String contact){
        massageHeader = "SNL :" + "User: " + username + "Cont: " + contact + "# ";
        massageHeader += "yes";
        String result = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8889);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream.writeUTF(massageHeader);
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static String no(String username , String contact){
        massageHeader = "SNL :" + "User: " + username + "Cont: " + contact + "# ";
        massageHeader += "no";
        String result = null;
        try {
            Socket socket = new Socket("127.0.0.1", 8889);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream.writeUTF(massageHeader);
            dataOutputStream.flush();
            result = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
