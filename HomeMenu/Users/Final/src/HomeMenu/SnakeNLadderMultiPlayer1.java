package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import java.util.Random;

public class SnakeNLadderMultiPlayer1 {
    private static Rectangle[] placesBlue = new Rectangle[63];
    private static Rectangle[] placesRed = new Rectangle[63];
    private static int redPlace = 0;
    private static int bluePlace = 0;

    public static void display(String yesNo , String username , String contact){
        FileInputStream fileInputStreamForBackground = null;
        try {
            fileInputStreamForBackground = new FileInputStream("src/HomeMenu/SNL2.png");
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
        newWindow.setTitle("Snake and Ladder -- " + username);
        newWindow.setWidth(400);
        newWindow.setHeight(600);
        Rectangle rectangle = new Rectangle(400,45, Color.WHITE);
        rectangle.setY(0);
        rectangle.setX(0);

        Rectangle startGame = createStartGameIcon();

        createBluePieces();
        createRedPieces();
        for (Rectangle rect:placesBlue) {
            rect.setVisible(false);
        }
        for (Rectangle rect:placesRed) {
            rect.setVisible(false);
        }
        Rectangle bluePlayer = playerBlueICon();
        Rectangle redPlayer = playerRedICon();

        Text player1 = new Text(33,20,"Player");
        player1.setFill(Color.RED);
        player1.setVisible(false);

        Text player2 = new Text(308,20,"Opponent");
        player2.setFill(Color.BLUE);
        player2.setVisible(false);

        Rectangle dicePlayer = dicePicPlayer();
        Rectangle diceComputer = dicePicComputer();
        Rectangle tapToDice = toToDicePic();

            startGame.setVisible(false);
            bluePlayer.setVisible(true);
            redPlayer.setVisible(true);
            player1.setVisible(true);
            player2.setVisible(true);

            tapToDice.setVisible(true);
            placesBlue[0].setVisible(true);
            placesRed[0].setVisible(true);

        tapToDice.setOnMouseClicked(mouseEvent -> {
            System.out.println((tapToDiceMethod(username , contact)));
            if (tapToDiceMethod(username, contact).equals("can")) {
                int number = dice();
                sendDiceMethod(username, contact , number);
                tapToDice.setVisible(false);
                dicePlayer.setVisible(true);
                System.out.println("d:" + number);
                System.out.println("rp:" + redPlace);
                diceChenger(dicePlayer, number);
                placesRed[redPlace].setVisible(false);
                redPlace += number;
                System.out.println("rp2:" + redPlace);
                placesRed[redPlace].setVisible(true);
                if (checkSnakeOrLadder(redPlace + 1) != redPlace + 1) {
                    delay();
                    delay();
                    placesRed[redPlace].setVisible(false);
                    System.out.println("ck:" + checkSnakeOrLadder(redPlace + 1));
                    redPlace = checkSnakeOrLadder(redPlace + 1);
                    placesRed[redPlace].setVisible(true);
                }
                int enemyNumber = setBlue(username, contact);
                if (!(enemyNumber == -1)){
                    tapToDice.setVisible(false);
                    diceComputer.setVisible(true);
                    diceChenger(diceComputer , enemyNumber);
                    placesBlue[bluePlace].setVisible(false);
                    bluePlace += enemyNumber;
                     System.out.println("bp2:" + bluePlace);
                    placesBlue[bluePlace].setVisible(true);
                    if (checkSnakeOrLadder(bluePlace+1) != bluePlace+1){
                        delay();
                        placesBlue[bluePlace].setVisible(false);
                        System.out.println("ck:" + checkSnakeOrLadder(bluePlace+1));
                       bluePlace = checkSnakeOrLadder(bluePlace+1);
                        placesBlue[bluePlace].setVisible(true);
                    }
                }
            }else System.out.println("Not your turn");
            if ( checkWinners() == 1) {
                showWinners("Red Player Won The Game");
                newWindow.close();
            }
            if ( checkWinners() == -1) {
                showWinners("Blue Player Won The Game");
                newWindow.close();
            }
            tapToDice.setVisible(true);
        });

        Pane layout = new Pane();
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(rectangle,startGame,redPlayer, bluePlayer, player1,player2,dicePlayer, diceComputer,tapToDice);
        layout.getChildren().addAll(placesBlue);
        layout.getChildren().addAll(placesRed);
        layout.setBackground(background);
        Scene scene = new Scene(layout, 300, 400);
        newWindow.setScene(scene);
        newWindow.show();
        newWindow.setResizable(false);
    }

    private static int checkWinners() {
        if (redPlace >= 62) {
            return  1;
        }
        if (bluePlace >= 62){
            return  -1;
        }
        return 0;
    }
    private static void showWinners(String string){
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
        newWindow2.setTitle("???");
        newWindow2.setWidth(380);
        newWindow2.setHeight(100);
        Label label = new Label();
        label.setTextFill(Color.NAVY);
        label.setText(string);
        Pane layout = new HBox(8);
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(label);
        layout.setBackground(background2);
        Scene scene = new Scene(layout, 200, 100);
        newWindow2.setScene(scene);
        newWindow2.show();
        newWindow2.setResizable(false);
    }

    private static void createRedPieces() {
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/redP.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        placesRed[0] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[0].setX(375 - 25);
        placesRed[0].setY(515 - 15);
        placesRed[1] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[1].setX(325 - 25);
        placesRed[1].setY(515 - 15);
        placesRed[2] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[2].setX(275 - 25);
        placesRed[2].setY(515 - 15);
        placesRed[3] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[3].setX(220 - 25);
        placesRed[3].setY(515 - 15);
        placesRed[4] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[4].setX(165 - 40);
        placesRed[4].setY(515 - 15);
        placesRed[5] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[5].setX(100 - 25);
        placesRed[5].setY(515 - 15);
        placesRed[6] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[6].setX(45  - 25);
        placesRed[6].setY(515 - 15);
        placesRed[13] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[13].setX(375 - 25);
        placesRed[13].setY(468 - 15);
        placesRed[12] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[12].setX(325 - 25);
        placesRed[12].setY(468 - 15);
        placesRed[11] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[11].setX(275 - 25);
        placesRed[11].setY(468 - 15);
        placesRed[10] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[10].setX(220 - 25);
        placesRed[10].setY(468 - 15);
        placesRed[9] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[9].setX(165 - 40);
        placesRed[9].setY(468 - 15);
        placesRed[8] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[8].setX(100 - 25);
        placesRed[8].setY(468 - 15);
        placesRed[7] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[7].setX(45  - 25);
        placesRed[7].setY(468 - 15);
        placesRed[14] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[14].setX(375 - 25);
        placesRed[14].setY(418 - 15);
        placesRed[15] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[15].setX(325 - 25);
        placesRed[15].setY(418 - 15);
        placesRed[16] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[16].setX(275 - 25);
        placesRed[16].setY(418 - 15);
        placesRed[17] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[17].setX(220 - 25);
        placesRed[17].setY(418 - 15);
        placesRed[18] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[18].setX(165 - 40);
        placesRed[18].setY(418 - 15);
        placesRed[19] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[19].setX(100 - 25);
        placesRed[19].setY(418 - 15);
        placesRed[20] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[20].setX(45  - 25);
        placesRed[20].setY(418 - 15);
        placesRed[27] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[27].setX(375 - 25);
        placesRed[27].setY(368 - 15);
        placesRed[26] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[26].setX(325 - 25);
        placesRed[26].setY(368 - 15);
        placesRed[25] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[25].setX(275 - 25);
        placesRed[25].setY(368 - 15);
        placesRed[24] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[24].setX(220 - 25);
        placesRed[24].setY(368 - 15);
        placesRed[23] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[23].setX(165 - 40);
        placesRed[23].setY(368 - 15);
        placesRed[22] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[22].setX(100 - 25);
        placesRed[22].setY(368 - 15);
        placesRed[21] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[21].setX(45  - 25);
        placesRed[21].setY(368 - 15);
        placesRed[28] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[28].setX(375 - 25);
        placesRed[28].setY(305 - 15);
        placesRed[29] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[29].setX(325 - 25);
        placesRed[29].setY(305 - 15);
        placesRed[30] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[30].setX(275 - 25);
        placesRed[30].setY(305 - 15);
        placesRed[31] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[31].setX(220 - 25);
        placesRed[31].setY(305 - 15);
        placesRed[32] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[32].setX(165 - 40);
        placesRed[32].setY(305 - 15);
        placesRed[33] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[33].setX(100 - 25);
        placesRed[33].setY(305 - 15);
        placesRed[34] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[34].setX(45  - 25);
        placesRed[34].setY(305 - 15);
        placesRed[41] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[41].setX(375 - 25);
        placesRed[41].setY(245 - 15);
        placesRed[40] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[40].setX(325 - 25);
        placesRed[40].setY(245 - 15);
        placesRed[39] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[39].setX(275 - 25);
        placesRed[39].setY(245 - 15);
        placesRed[38] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[38].setX(220 - 25);
        placesRed[38].setY(245 - 15);
        placesRed[37] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[37].setX(165 - 40);
        placesRed[37].setY(245 - 15);
        placesRed[36] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[36].setX(100 - 25);
        placesRed[36].setY(245 - 15);
        placesRed[35] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[35].setX(45  - 25);
        placesRed[35].setY(245 - 15);
        placesRed[42] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[42].setX(375 - 25);
        placesRed[42].setY(185 - 15);
        placesRed[43] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[43].setX(325 - 25);
        placesRed[43].setY(185 - 15);
        placesRed[44] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[44].setX(275 - 25);
        placesRed[44].setY(185 - 15);
        placesRed[45 ] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[45 ].setX(220 - 25);
        placesRed[45 ].setY(185 - 15);
        placesRed[46] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[46].setX(165 - 40);
        placesRed[46].setY(185 - 15);
        placesRed[47] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[47].setX(100 - 25);
        placesRed[47].setY(185 - 15);
        placesRed[48] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[48].setX(45  - 25);
        placesRed[48].setY(185 - 15);
        placesRed[55] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[55].setX(375 - 25);
        placesRed[55].setY(130 - 15);
        placesRed[54] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[54].setX(325 - 25);
        placesRed[54].setY(130 - 15);
        placesRed[53] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[53].setX(275 - 25);
        placesRed[53].setY(130 - 15);
        placesRed[52] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[52].setX(220 - 25);
        placesRed[52].setY(130 - 15);
        placesRed[51] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[51].setX(165 - 40);
        placesRed[51].setY(130 - 15);
        placesRed[50] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[50].setX(100 - 25);
        placesRed[50].setY(130 - 15);
        placesRed[49] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[49].setX(45  - 25);
        placesRed[49].setY(130 - 15);
        placesRed[56] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[56].setX(375 - 25);
        placesRed[56].setY(75  - 15);
        placesRed[57] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[57].setX(325 - 25);
        placesRed[57].setY(75  - 15);
        placesRed[58] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[58].setX(275 - 25);
        placesRed[58].setY(75  - 15);
        placesRed[59] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[59].setX(220 - 25);
        placesRed[59].setY(75  - 15);
        placesRed[60] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[60].setX(165 - 40);
        placesRed[60].setY(75  - 15);
        placesRed[61] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[61].setX(100 - 25);
        placesRed[61].setY(75  - 15);
        placesRed[62] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[62].setX(45  - 25);
        placesRed[62].setY(75  - 15);          placesRed[0] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[0].setX(375 - 25);
        placesRed[0].setY(515 - 15);
        placesRed[1] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[1].setX(325 - 25);
        placesRed[1].setY(515 - 15);
        placesRed[2] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[2].setX(275 - 25);
        placesRed[2].setY(515 - 15);
        placesRed[3] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[3].setX(220 - 25);
        placesRed[3].setY(515 - 15);
        placesRed[4] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[4].setX(165 - 25);
        placesRed[4].setY(515 - 15);
        placesRed[5] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[5].setX(100 - 25);
        placesRed[5].setY(515 - 15);
        placesRed[6] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[6].setX(45  - 25);
        placesRed[6].setY(515 - 15);
        placesRed[13] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[13].setX(375 - 25);
        placesRed[13].setY(468 - 15);
        placesRed[12] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[12].setX(325 - 25);
        placesRed[12].setY(468 - 15);
        placesRed[11] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[11].setX(275 - 25);
        placesRed[11].setY(468 - 15);
        placesRed[10] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[10].setX(220 - 25);
        placesRed[10].setY(468 - 15);
        placesRed[9] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[9].setX(165 - 25);
        placesRed[9].setY(468 - 15);
        placesRed[8] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[8].setX(100 - 25);
        placesRed[8].setY(468 - 15);
        placesRed[7] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[7].setX(45  - 25);
        placesRed[7].setY(468 - 15);
        placesRed[14] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[14].setX(375 - 25);
        placesRed[14].setY(418 - 15);
        placesRed[15] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[15].setX(325 - 25);
        placesRed[15].setY(418 - 15);
        placesRed[16] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[16].setX(275 - 25);
        placesRed[16].setY(418 - 15);
        placesRed[17] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[17].setX(220 - 25);
        placesRed[17].setY(418 - 15);
        placesRed[18] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[18].setX(165 - 25);
        placesRed[18].setY(418 - 15);
        placesRed[19] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[19].setX(100 - 25);
        placesRed[19].setY(418 - 15);
        placesRed[20] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[20].setX(45  - 25);
        placesRed[20].setY(418 - 15);
        placesRed[27] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[27].setX(375 - 25);
        placesRed[27].setY(368 - 15);
        placesRed[26] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[26].setX(325 - 25);
        placesRed[26].setY(368 - 15);
        placesRed[25] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[25].setX(275 - 25);
        placesRed[25].setY(368 - 15);
        placesRed[24] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[24].setX(220 - 25);
        placesRed[24].setY(368 - 15);
        placesRed[23] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[23].setX(165 - 25);
        placesRed[23].setY(368 - 15);
        placesRed[22] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[22].setX(100 - 25);
        placesRed[22].setY(368 - 15);
        placesRed[21] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[21].setX(45  - 25);
        placesRed[21].setY(368 - 15);
        placesRed[28] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[28].setX(375 - 25);
        placesRed[28].setY(305 - 15);
        placesRed[29] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[29].setX(325 - 25);
        placesRed[29].setY(305 - 15);
        placesRed[30] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[30].setX(275 - 25);
        placesRed[30].setY(305 - 15);
        placesRed[31] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[31].setX(220 - 25);
        placesRed[31].setY(305 - 15);
        placesRed[32] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[32].setX(165 - 25);
        placesRed[32].setY(305 - 15);
        placesRed[33] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[33].setX(100 - 25);
        placesRed[33].setY(305 - 15);
        placesRed[34] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[34].setX(45  - 25);
        placesRed[34].setY(305 - 15);
        placesRed[41] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[41].setX(375 - 25);
        placesRed[41].setY(245 - 15);
        placesRed[40] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[40].setX(325 - 25);
        placesRed[40].setY(245 - 15);
        placesRed[39] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[39].setX(275 - 25);
        placesRed[39].setY(245 - 15);
        placesRed[38] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[38].setX(220 - 25);
        placesRed[38].setY(245 - 15);
        placesRed[37] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[37].setX(165 - 25);
        placesRed[37].setY(245 - 15);
        placesRed[36] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[36].setX(100 - 25);
        placesRed[36].setY(245 - 15);
        placesRed[35] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[35].setX(45  - 25);
        placesRed[35].setY(245 - 15);
        placesRed[42] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[42].setX(375 - 25);
        placesRed[42].setY(185 - 15);
        placesRed[43] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[43].setX(325 - 25);
        placesRed[43].setY(185 - 15);
        placesRed[44] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[44].setX(275 - 25);
        placesRed[44].setY(185 - 15);
        placesRed[45 ] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[45 ].setX(220 - 25);
        placesRed[45 ].setY(185 - 15);
        placesRed[46] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[46].setX(165 - 25);
        placesRed[46].setY(185 - 15);
        placesRed[47] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[47].setX(100 - 25);
        placesRed[47].setY(185 - 15);
        placesRed[48] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[48].setX(45  - 25);
        placesRed[48].setY(185 - 15);
        placesRed[55] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[55].setX(375 - 25);
        placesRed[55].setY(130 - 15);
        placesRed[54] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[54].setX(325 - 25);
        placesRed[54].setY(130 - 15);
        placesRed[53] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[53].setX(275 - 25);
        placesRed[53].setY(130 - 15);
        placesRed[52] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[52].setX(220 - 25);
        placesRed[52].setY(130 - 15);
        placesRed[51] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[51].setX(165 - 25);
        placesRed[51].setY(130 - 15);
        placesRed[50] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[50].setX(100 - 25);
        placesRed[50].setY(130 - 15);
        placesRed[49] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[49].setX(45  - 25);
        placesRed[49].setY(130 - 15);
        placesRed[56] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[56].setX(375 - 25);
        placesRed[56].setY(75  - 15);
        placesRed[57] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[57].setX(325 - 25);
        placesRed[57].setY(75  - 15);
        placesRed[58] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[58].setX(275 - 25);
        placesRed[58].setY(75  - 15);
        placesRed[59] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[59].setX(220 - 25);
        placesRed[59].setY(75  - 15);
        placesRed[60] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[60].setX(165 - 25);
        placesRed[60].setY(75  - 15);
        placesRed[61] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[61].setX(100 - 25);
        placesRed[61].setY(75  - 15);
        placesRed[62] = new Rectangle(15,25,new ImagePattern(image));
        placesRed[62].setX(45  - 25);
        placesRed[62].setY(75  - 15);
    }

    private static void createBluePieces(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/blueP.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        placesBlue[0] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[0].setX(375);
        placesBlue[0].setY(515);
        placesBlue[1] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[1].setX(325);
        placesBlue[1].setY(515);
        placesBlue[2] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[2].setX(275);
        placesBlue[2].setY(515);
        placesBlue[3] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[3].setX(220);
        placesBlue[3].setY(515);
        placesBlue[4] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[4].setX(165);
        placesBlue[4].setY(515);
        placesBlue[5] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[5].setX(100);
        placesBlue[5].setY(515);
        placesBlue[6] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[6].setX(45);
        placesBlue[6].setY(515);
        placesBlue[13] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[13].setX(375);
        placesBlue[13].setY(468);
        placesBlue[12] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[12].setX(325);
        placesBlue[12].setY(468);
        placesBlue[11] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[11].setX(275);
        placesBlue[11].setY(468);
        placesBlue[10] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[10].setX(220);
        placesBlue[10].setY(468);
        placesBlue[9] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[9].setX(165);
        placesBlue[9].setY(468);
        placesBlue[8] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[8].setX(100);
        placesBlue[8].setY(468);
        placesBlue[7] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[7].setX(45);
        placesBlue[7].setY(468);
        placesBlue[14] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[14].setX(375);
        placesBlue[14].setY(418);
        placesBlue[15] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[15].setX(325);
        placesBlue[15].setY(418);
        placesBlue[16] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[16].setX(275);
        placesBlue[16].setY(418);
        placesBlue[17] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[17].setX(220);
        placesBlue[17].setY(418);
        placesBlue[18] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[18].setX(165);
        placesBlue[18].setY(418);
        placesBlue[19] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[19].setX(100);
        placesBlue[19].setY(418);
        placesBlue[20] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[20].setX(45);
        placesBlue[20].setY(418);
        placesBlue[27] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[27].setX(375);
        placesBlue[27].setY(368);
        placesBlue[26] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[26].setX(325);
        placesBlue[26].setY(368);
        placesBlue[25] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[25].setX(275);
        placesBlue[25].setY(368);
        placesBlue[24] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[24].setX(220);
        placesBlue[24].setY(368);
        placesBlue[23] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[23].setX(165);
        placesBlue[23].setY(368);
        placesBlue[22] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[22].setX(100);
        placesBlue[22].setY(368);
        placesBlue[21] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[21].setX(45);
        placesBlue[21].setY(368);
        placesBlue[28] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[28].setX(375);
        placesBlue[28].setY(305);
        placesBlue[29] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[29].setX(325);
        placesBlue[29].setY(305);
        placesBlue[30] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[30].setX(275);
        placesBlue[30].setY(305);
        placesBlue[31] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[31].setX(220);
        placesBlue[31].setY(305);
        placesBlue[32] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[32].setX(165);
        placesBlue[32].setY(305);
        placesBlue[33] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[33].setX(100);
        placesBlue[33].setY(305);
        placesBlue[34] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[34].setX(45);
        placesBlue[34].setY(305);
        placesBlue[41] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[41].setX(375);
        placesBlue[41].setY(245);
        placesBlue[40] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[40].setX(325);
        placesBlue[40].setY(245);
        placesBlue[39] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[39].setX(275);
        placesBlue[39].setY(245);
        placesBlue[38] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[38].setX(220);
        placesBlue[38].setY(245);
        placesBlue[37] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[37].setX(165);
        placesBlue[37].setY(245);
        placesBlue[36] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[36].setX(100);
        placesBlue[36].setY(245);
        placesBlue[35] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[35].setX(45);
        placesBlue[35].setY(245);
        placesBlue[42] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[42].setX(375);
        placesBlue[42].setY(185);
        placesBlue[43] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[43].setX(325);
        placesBlue[43].setY(185);
        placesBlue[44] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[44].setX(275);
        placesBlue[44].setY(185);
        placesBlue[45] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[45].setX(220);
        placesBlue[45].setY(185);
        placesBlue[46] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[46].setX(165);
        placesBlue[46].setY(185);
        placesBlue[47] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[47].setX(100);
        placesBlue[47].setY(185);
        placesBlue[48] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[48].setX(45);
        placesBlue[48].setY(185);
        placesBlue[55] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[55].setX(375);
        placesBlue[55].setY(130);
        placesBlue[54] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[54].setX(325);
        placesBlue[54].setY(130);
        placesBlue[53] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[53].setX(275);
        placesBlue[53].setY(130);
        placesBlue[52] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[52].setX(220);
        placesBlue[52].setY(130);
        placesBlue[51] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[51].setX(165);
        placesBlue[51].setY(130);
        placesBlue[50] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[50].setX(100);
        placesBlue[50].setY(130);
        placesBlue[49] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[49].setX(45);
        placesBlue[49].setY(130);
        placesBlue[56] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[56].setX(375);
        placesBlue[56].setY(75);
        placesBlue[57] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[57].setX(325);
        placesBlue[57].setY(75);
        placesBlue[58] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[58].setX(275);
        placesBlue[58].setY(75);
        placesBlue[59] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[59].setX(220);
        placesBlue[59].setY(75);
        placesBlue[60] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[60].setX(165);
        placesBlue[60].setY(75);
        placesBlue[61] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[61].setX(100);
        placesBlue[61].setY(75);
        placesBlue[62] = new Rectangle(15,25,new ImagePattern(image));
        placesBlue[62].setX(45);
        placesBlue[62].setY(75);
    }

    private static Rectangle createStartGameIcon(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/startRec.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Rectangle rectangle = new Rectangle(120,50,new ImagePattern(image));
        rectangle.setX(140);
        rectangle.setY(0);
        return rectangle;
    }

    private static Rectangle playerRedICon(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/redP.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Rectangle rectangle = new Rectangle(21,35,new ImagePattern(image));
        rectangle.setX(10);
        rectangle.setY(2);
        rectangle.setVisible(false);
        return rectangle;
    }
    private static Rectangle playerBlueICon(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/blueP.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Rectangle rectangle = new Rectangle(21,35,new ImagePattern(image));
        rectangle.setY(2);
        rectangle.setX(375);
        rectangle.setVisible(false);
        return rectangle;
    }
    //    private static Rectangle rightTurn(){
//        Image image = null;
//        try {
//            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/right.png");
//            image = new Image(fileInputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Rectangle rectangle = new Rectangle(30,30 , new ImagePattern(image));
//        rectangle.setX(270);
//        rectangle.setY(10);
//        rectangle.setVisible(false);
//        return rectangle;
//    }
//    private static Rectangle leftTurn(){
//        Image image = null;
//        try {
//            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/left.png");
//            image = new Image(fileInputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Rectangle rectangle = new Rectangle(30,30 , new ImagePattern(image));
//        rectangle.setX(80);
//        rectangle.setY(10);
//        rectangle.setVisible(false);
//        return rectangle;
//    }
    private static Rectangle dicePicPlayer(){
        Rectangle rectangle = new Rectangle(40,40);
        rectangle.setX(120);
        rectangle.setY(2);
        rectangle.setVisible(false);
        return rectangle;
    }
    private static Rectangle dicePicComputer(){
        Rectangle rectangle = new Rectangle(40,40);
        rectangle.setX(245);
        rectangle.setY(2);
        rectangle.setVisible(false);
        return rectangle;
    }
    private static Rectangle toToDicePic(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/ttd.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Rectangle rectangle = new Rectangle(90,35 , new ImagePattern(image));
        rectangle.setX(158);
        rectangle.setY(3);
        rectangle.setVisible(false);
        return rectangle;
    }
    private static int checkSnakeOrLadder (int number){
        if (number == 3 ) return 16;
        if (number == 20 ) return 32;
        if (number == 25 ) return 10;
        if (number == 28 ) return 41;
        if (number == 32 ) return 15;
        if (number == 40 ) return 45;
        if (number == 48 ) return 33;
        if (number == 47 ) return 59;
        if (number == 58 ) return 44;
        return number;
    }
    private static int dice(){
        Random random = new Random();
        return random.nextInt(6)+1;
    }
    private static ImagePattern one(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/1.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImagePattern(image);
    }
    private static ImagePattern two(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/2.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImagePattern(image);
    }
    private static ImagePattern three(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/3.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImagePattern(image);
    }
    private static ImagePattern four(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/4.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImagePattern(image);
    }
    private static ImagePattern five(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/5.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImagePattern(image);
    }
    private static ImagePattern six(){
        Image image = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("src/HomeMenu/6.png");
            image = new Image(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ImagePattern(image);
    }
    private static void diceChenger(Rectangle dice , int number){
        if (number == 1) dice.setFill(one());
        if (number == 2) dice.setFill(two());
        if (number == 3) dice.setFill(three());
        if (number == 4) dice.setFill(four());
        if (number == 5) dice.setFill(five());
        if (number == 6) dice.setFill(six());
    }
    private static void delay() {
        for (int i = 0; i < 1000000000; i++) ;
    }
    private static String tapToDiceMethod(String username , String contact){
        String massageHeader = "SNL :" + "User: " + username + "Cont: " + contact + "# ";
        massageHeader += "turn";
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
    private static void sendDiceMethod(String username , String contact , int diceNumber){
        String massageHeader = "SNL :" + "User: " + username + "Cont: " + contact + "# ";
        massageHeader += diceNumber;
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
    }
    private static int setBlue(String username, String contact) {
        try {
            FileReader file = new FileReader ("src/HomeMenu/Users/Info/" + contact + " /Game/SNL/" + username + " .txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String line = bufferedReader.readLine();
            String lastLine = null;
            int counter = 0;
            while (line != null){
                counter++;
                if( counter%2 == 1 ) {

                    lastLine = line + "\n";
                }
                line = bufferedReader.readLine();
            }
            char ch = lastLine.charAt( lastLine.indexOf(":") + 2 );
            if (!lastLine.startsWith(username) && ch != 'd') return Integer.parseInt(String.valueOf(ch));;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    private static void changeTurn(String username, String contact) {
        String massageHeader = "SNL :" + "User: " + username + "Cont: " + contact + "# ";
        massageHeader += "change";
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
    }
}
