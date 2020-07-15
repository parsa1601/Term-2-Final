package HomeMenu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Random;


public class TicTacToe {
    private static boolean[] access = new boolean[9];
    private static boolean isPlayerStarter ;
    private static int computerFirstChoice;
    private static final char BOT = 'O';
    private static final char USER = 'X';
    private static Board gameBoard;
    private static Text[] xSigns = new Text[9];
    private static Text[] oSigns = new Text[9];
    private static Rectangle[] table = new Rectangle[9];
    private static String gameInfo = "";
    private static final MyDate today = new MyDate();

    public static void display(String  username){
        FileInputStream botImageFile = null;
        try {
            botImageFile = new FileInputStream("src/HomeMenu/discordLogo.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageBot = new Image(botImageFile);
        FileInputStream userImageFile = null;
        try {
            userImageFile = new FileInputStream("src/HomeMenu/userLogo.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image imageUser = new Image(userImageFile);
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setWidth(300);
        newWindow.setHeight(200);


        table[0] = cell(10,10);
        table[1] = cell(60,10);
        table[2] = cell(110,10);
        table[3] = cell(10,60);
        table[4] = cell(60,60);
        table[5] = cell(110,60);
        table[6] = cell(10,110);
        table[7] = cell(60,110);
        table[8] = cell(110,110);

        oSigns[0] = cellTextXO(23,23,'O');
        oSigns[0].setVisible(false);
        xSigns[0] = cellTextXO(23,23,'X');
        xSigns[0].setVisible(false);
        oSigns[1] = cellTextXO(73,23,'O');
        oSigns[1].setVisible(false);
        xSigns[1] = cellTextXO(73,23,'X');
        xSigns[1].setVisible(false);
        oSigns[2] = cellTextXO(123,23,'O');
        oSigns[2].setVisible(false);
        xSigns[2] = cellTextXO(123,23,'X');
        xSigns[2].setVisible(false);
        oSigns[3] = cellTextXO(23,73,'O');
        oSigns[3].setVisible(false);
        xSigns[3] = cellTextXO(23,73,'X');
        xSigns[3].setVisible(false);
        oSigns[4] = cellTextXO(73,73,'O');
        oSigns[4].setVisible(false);
        xSigns[4] = cellTextXO(73,73,'X');
        xSigns[4].setVisible(false);
        oSigns[5] = cellTextXO(123,73,'O');
        oSigns[5].setVisible(false);
        xSigns[5] = cellTextXO(123,73,'X');
        xSigns[5].setVisible(false);
        oSigns[6] = cellTextXO(23,123,'O');
        oSigns[6].setVisible(false);
        xSigns[6] = cellTextXO(23,123,'X');
        xSigns[6].setVisible(false);
        oSigns[7] = cellTextXO(73,123,'O');
        oSigns[7].setVisible(false);
        xSigns[7] = cellTextXO(73,123,'X');
        xSigns[7].setVisible(false);
        oSigns[8] = cellTextXO(123,123,'O');
        oSigns[8].setVisible(false);
        xSigns[8] = cellTextXO(123,123,'X');
        xSigns[8].setVisible(false);

        Rectangle starter1 = starters(170,40, Color.BLUE);
        starter1.setFill(new ImagePattern(imageUser));
        starter1.setOnMouseClicked(mouseEvent -> {
            isPlayerStarter = true;
        });
        Rectangle starter2 = starters(235,40, Color.RED);
        starter2.setFill(new ImagePattern(imageBot));
        starter2.setOnMouseClicked(mouseEvent -> {
            isPlayerStarter = false;
            Random random = new Random();
            computerFirstChoice = random.nextInt(8);
        });
        Text describe = new Text(173,20,"Choose" +  " Starter");
        describe.setFill(Color.DARKSEAGREEN);
        describe.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12));
        Rectangle startGame = new Rectangle(90,40,Color.FORESTGREEN);
        startGame.setX(180);
        startGame.setY(105);
        Text start = new Text(200,130,"Start!");
        start.setFill(Color.AZURE);
        start.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 15));
        start.setOnMouseClicked(mouseEvent -> {
            gameInfo += "Game Started At" + today.day + " / " + today.month + " / " + today.year + "\n";
            char[] gameBoardBody = {'0','1','2','3','4','5','6','7','8'};
            gameBoard = new Board(gameBoardBody);
            for (int i = 0 ; i < 9 ; i ++){
                access[i] = true;
            }
            if(!isPlayerStarter) {
                access[computerFirstChoice] = false;
                gameBoard.getBoard()[computerFirstChoice] = BOT;
                oSigns[computerFirstChoice].setVisible(true);
            }
        });


        table[0].setOnMouseClicked(mouseEvent -> {
            if(access[0]) {
                gameBoard.getBoard()[0] = USER;
                xSigns[0].setVisible(true);
                gameInfo += "Player : 1 \n";
                int result = botMove(gameBoard);
                if (result == -3) System.out.println("TTTTTIIIIIEEEEE");
                else if (result == -2) System.out.println("AIIIIIIIIIIIIII");
                else if (result == -1) System.out.println("PLAYER111111111");
                else {
                    gameBoard.getBoard()[result]= BOT;
                    access[result] = false;
                    oSigns[result].setVisible(true);
                    int resultPlusOne = result + 1;
                    gameInfo += "AI : " + resultPlusOne + "\n";
                }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[1].setOnMouseClicked(mouseEvent -> {
            if(access[1]) {
                gameInfo += "Player : 2 \n";
                gameBoard.getBoard()[1] = USER;
                xSigns[1].setVisible(true);
                int result = botMove(gameBoard);
                if (result == -3) System.out.println("TTTTTIIIIIEEEEE");
                else if (result == -2) System.out.println("AIIIIIIIIIIIIII");
                else if (result == -1) System.out.println("PLAYER111111111");
                else {
                    gameBoard.getBoard()[result]= BOT;
                    access[result] = false;
                    oSigns[result].setVisible(true);
                    int resultPlusOne = result + 1;
                    gameInfo += "AI : " + resultPlusOne + "\n";
                }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[2].setOnMouseClicked(mouseEvent -> {
            if(access[2]){
                gameInfo += "Player : 3 \n";
                gameBoard.getBoard()[2] = USER;
                xSigns[2].setVisible(true);
            int result = botMove(gameBoard);
            if(result == -3) System.out.println("TTTTTIIIIIEEEEE");
            else if(result == -2) System.out.println("AIIIIIIIIIIIIII");
            else if(result == -1) System.out.println("PLAYER111111111");
            else{
                gameBoard.getBoard()[result]= BOT;
                access[result] = false;
                oSigns[result].setVisible(true);
                int resultPlusOne = result + 1;
                gameInfo += "AI : " + resultPlusOne + "\n";
            }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[3].setOnMouseClicked(mouseEvent -> {
            if(access[3]){
                gameInfo += "Player : 4 \n";
                gameBoard.getBoard()[3] = USER;
                xSigns[3].setVisible(true);
            int result = botMove(gameBoard);
            if(result == -3) System.out.println("TTTTTIIIIIEEEEE");
            else if(result == -2) System.out.println("AIIIIIIIIIIIIII");
            else if(result == -1) System.out.println("PLAYER111111111");
            else{
                gameBoard.getBoard()[result]= BOT;
                access[result] = false;
                oSigns[result].setVisible(true);
                int resultPlusOne = result + 1;
                gameInfo += "AI : " + resultPlusOne + "\n";
            }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[4].setOnMouseClicked(mouseEvent -> {
            if(access[4]){
                gameInfo += "Player : 5 \n";
                gameBoard.getBoard()[4] = USER;
                xSigns[4].setVisible(true);
            int result = botMove(gameBoard);
            if(result == -3) System.out.println("TTTTTIIIIIEEEEE");
            else if(result == -2) System.out.println("AIIIIIIIIIIIIII");
            else if(result == -1) System.out.println("PLAYER111111111");
            else{
                gameBoard.getBoard()[result]= BOT;
                access[result] = false;
                oSigns[result].setVisible(true);
                int resultPlusOne = result + 1;
                gameInfo += "AI : " + resultPlusOne + "\n";
            }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[5].setOnMouseClicked(mouseEvent -> {
            if(access[5]) {
                gameInfo += "Player : 6 \n";
                gameBoard.getBoard()[5] = USER;
                xSigns[5].setVisible(true);
                int result = botMove(gameBoard);
                if (result == -3) System.out.println("TTTTTIIIIIEEEEE");
                else if (result == -2) System.out.println("AIIIIIIIIIIIIII");
                else if (result == -1) System.out.println("PLAYER111111111");
                else {
                    gameBoard.getBoard()[result]= BOT;
                    access[result] = false;
                    oSigns[result].setVisible(true);
                    int resultPlusOne = result + 1;
                    gameInfo += "AI : " + resultPlusOne + "\n";
                }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[6].setOnMouseClicked(mouseEvent -> {
            if (access[6]) {
                gameInfo += "Player : 7 \n";
                gameBoard.getBoard()[6] = USER;
                xSigns[6].setVisible(true);
                int result = botMove(gameBoard);
                if (result == -3) System.out.println("TTTTTIIIIIEEEEE");
                else if (result == -2) System.out.println("AIIIIIIIIIIIIII");
                else if (result == -1) System.out.println("PLAYER111111111");
                else {
                    gameBoard.getBoard()[result]= BOT;
                    access[result] = false;
                    oSigns[result].setVisible(true);
                    int resultPlusOne = result + 1;
                    gameInfo += "AI : " + resultPlusOne + "\n";
                }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[7].setOnMouseClicked(mouseEvent -> {
            if(access[7]){
                gameInfo += "Player : 8 \n";
                gameBoard.getBoard()[7] = USER;
                xSigns[7].setVisible(true);
            int result = botMove(gameBoard);
            if(result == -3) System.out.println("TTTTTIIIIIEEEEE");
            else if(result == -2) System.out.println("AIIIIIIIIIIIIII");
            else if(result == -1) System.out.println("PLAYER111111111");
            else{
                gameBoard.getBoard()[result]= BOT;
                access[result] = false;
                oSigns[result].setVisible(true);
                int resultPlusOne = result + 1;
                gameInfo += "AI : " + resultPlusOne + "\n";
            }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });

        table[8].setOnMouseClicked(mouseEvent -> {
            if(access[8]){
                gameInfo += "Player : 9 \n";
                gameBoard.getBoard()[8] = USER;
                xSigns[8].setVisible(true);
            int result = botMove(gameBoard);
            if(result == -3) System.out.println("TTTTTIIIIIEEEEE");
            else if(result == -2) System.out.println("AIIIIIIIIIIIIII");
            else if(result == -1) System.out.println("PLAYER111111111");
            else{
                gameBoard.getBoard()[result]= BOT;
                access[result] = false;
                oSigns[result].setVisible(true);
                int resultPlusOne = result + 1;
                gameInfo += "AI : " + resultPlusOne + "\n";
            }
                if (isWin(gameBoard, 'X')) {
                    System.out.println("Congratulations !!! User Win.");
                    gameFInish(gameInfo , -1 , username);
                } else if (isWin(gameBoard, 'O')) {
                    System.out.println("Congratulations !!! AI Win.");
                    gameFInish(gameInfo , 1 , username);
                }
                if (!gameBoard.checkMovesLeft()) {
                    System.out.println("TIE !");
                    gameFInish( gameInfo ,  0 , username);
                }
            }
        });




        Pane layout = new Pane();
        layout.setPadding(new Insets(10, 10, 0, 10));
        layout.getChildren().addAll(table[0], table[1], table[2], table[3], table[4], table[5], table[6], table[7], table[8],
            oSigns[0],xSigns[0],oSigns[1],xSigns[1],oSigns[2],xSigns[2],oSigns[3],xSigns[3],oSigns[4],xSigns[4],
            oSigns[5],xSigns[5],oSigns[6],xSigns[6],oSigns[7],xSigns[7],oSigns[8],xSigns[8],
                describe,starter1,starter2,startGame,start
                );
//        layout.setBackground(background);
        Scene scene = new Scene(layout, 300, 400);
        newWindow.setScene(scene);
        newWindow.show();
    }

    private static Rectangle cell(int x, int y){
        Rectangle rectangle = new Rectangle(40,40);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(Color.LIGHTGRAY);
        return rectangle;
    }
    private static Text cellTextXO(int x, int y, char XO){
        Text text = new Text();
        text.setX(x);
        text.setY(y+11);
        text.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 19));
        if (XO == 'X') {
            text.setText("X");
            text.setFill(Color.BLUE);
        }
        if (XO == 'O') {
            text.setText("O");
            text.setFill(Color.RED);
        }
        return text;
    }
    private static Rectangle starters(int x, int y, Paint paint){
        Rectangle rectangle = new Rectangle(45,45);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setFill(paint);
        return rectangle;
    }

    static void clearScreen(){
        for(int i = 0 ; i < 25; i++)
            System.out.println();
    }

    static void generateBoard(char[] board){
        for(int i = 1 ; i <= board.length ; i++){
            System.out.print("|"+board[i-1]+"| ");
            if(i % 3 == 0)
                System.out.println();
        }
    }

    static boolean validatePosition(Board board, int position){
        if(board.getBoard()[position] == 'X' || board.getBoard()[position] == 'O')
            return false;
        else
            return true;
    }

    static boolean isWin(Board board, char input){
        if((board.getBoard()[0] == input && board.getBoard()[1] == input && board.getBoard()[2] == input) ||
                (board.getBoard()[3] == input && board.getBoard()[4] == input && board.getBoard()[5] == input) ||
                (board.getBoard()[6] == input && board.getBoard()[7] == input && board.getBoard()[8] == input) ||
                (board.getBoard()[0] == input && board.getBoard()[3] == input && board.getBoard()[6] == input) ||
                (board.getBoard()[1] == input && board.getBoard()[4] == input && board.getBoard()[7] == input) ||
                (board.getBoard()[2] == input && board.getBoard()[5] == input && board.getBoard()[8] == input) ||
                (board.getBoard()[0] == input && board.getBoard()[4] == input && board.getBoard()[8] == input) ||
                (board.getBoard()[2] == input && board.getBoard()[4] == input && board.getBoard()[6] == input)){
            return true;
        }else {
            return false;
        }
    }

    static int isWin(Board board, int depth){
        if((board.getBoard()[0] == BOT && board.getBoard()[1] == BOT && board.getBoard()[2] == BOT) ||
                (board.getBoard()[3] == BOT && board.getBoard()[4] == BOT && board.getBoard()[5] == BOT) ||
                (board.getBoard()[6] == BOT && board.getBoard()[7] == BOT && board.getBoard()[8] == BOT) ||
                (board.getBoard()[0] == BOT && board.getBoard()[3] == BOT && board.getBoard()[6] == BOT) ||
                (board.getBoard()[1] == BOT && board.getBoard()[4] == BOT && board.getBoard()[7] == BOT) ||
                (board.getBoard()[2] == BOT && board.getBoard()[5] == BOT && board.getBoard()[8] == BOT) ||
                (board.getBoard()[0] == BOT && board.getBoard()[4] == BOT && board.getBoard()[8] == BOT) ||
                (board.getBoard()[2] == BOT && board.getBoard()[4] == BOT && board.getBoard()[6] == BOT)){
            return -(10);
        }else if((board.getBoard()[0] == USER && board.getBoard()[1] == USER && board.getBoard()[2] == USER) ||
                (board.getBoard()[3] == USER && board.getBoard()[4] == USER && board.getBoard()[5] == USER) ||
                (board.getBoard()[6] == USER && board.getBoard()[7] == USER && board.getBoard()[8] == USER) ||
                (board.getBoard()[0] == USER && board.getBoard()[3] == USER && board.getBoard()[6] == USER) ||
                (board.getBoard()[1] == USER && board.getBoard()[4] == USER && board.getBoard()[7] == USER) ||
                (board.getBoard()[2] == USER && board.getBoard()[5] == USER && board.getBoard()[8] == USER) ||
                (board.getBoard()[0] == USER && board.getBoard()[4] == USER && board.getBoard()[8] == USER) ||
                (board.getBoard()[2] == USER && board.getBoard()[4] == USER && board.getBoard()[6] == USER)){
            return 10;
        }{
            return 0;
        }
    }



    static int minimax(Board board, int depth, char input){
        int score = isWin(board, depth);

        if(isWin(board,depth) == 10) return score-depth;
        else if(isWin(board,depth) == -10) return score+depth;

        if(!board.checkMovesLeft()) return 0;

        if(input == BOT){
            int bestValue = 99999999;
            for(int i = 0 ; i < board.getBoard().length; i++){
                if(board.getBoard()[i] != 'X' && board.getBoard()[i] != 'O'){
                    char before = board.getBoard()[i];
                    board.getBoard()[i] = BOT;

                    int value = minimax(board,depth++,USER);
                    bestValue = Math.min(bestValue, value);

                    board.getBoard()[i] = before;
                }
            }
            return bestValue;
        }else{
            int bestValue = -99999999;
            for(int i = 0 ; i < board.getBoard().length; i++){
                if(board.getBoard()[i] != 'X' && board.getBoard()[i] != 'O'){
                    char before = board.getBoard()[i];
                    board.getBoard()[i] = USER;

                    int value = minimax(board,depth++,BOT);
                    bestValue = Math.max(bestValue, value);

                    board.getBoard()[i] = before;
                }
            }
            return bestValue;
        }

    }

    static int findBestMoves(Board board){
        int bestMoveValues =  999999999;
        int bestMove = -1;

        for(int i = 0 ; i < board.getBoard().length; i++){
            if(board.getBoard()[i] != 'X' && board.getBoard()[i] != 'O'){
                char before = board.getBoard()[i];
                board.getBoard()[i] = BOT;

                int bestValue = minimax(board,0,USER);

                board.getBoard()[i] = before;

                if(bestValue < bestMoveValues){
                    bestMoveValues = bestValue;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    private static int botMove(Board board){///-1 --> player winning / -2 --> AI winning / -3 --> tie!
        if(isWin(board, 'X')){
            System.out.println("Congratulations !!! User Win.");
            return -1;
        }else if(isWin(board, 'O')){
            System.out.println("Congratulations !!! AI Win.");
            return -2;
        }

        if(!board.checkMovesLeft()){
            System.out.println("TIE !");
            return -3;
        }

//        generateBoard(board.initializeBoard(boards));


        if(!board.checkMovesLeft()){
            System.out.println("TIE !");
            return -3;
        }

        System.out.println("Thinking...");

        int bestMove = findBestMoves(board);
        board.setBoardPosition(bestMove, 'O');
        System.out.println(bestMove );

        return bestMove ;
    }
    private static void gameFInish(String gameInfo , int status, String username){
        if (status == 0) gameInfo+= "Game Finished Tie!";
        if (status == 1) gameInfo+= "AI won the Game";
        if (status ==-1) gameInfo+= "Amazing, Player won the game!!!!";
        File file = new File("src/HomeMenu/Users/Info/"+ username+ " /" + "TicTacToe.txt");
        try {
            File userFile = new File("src/HomeMenu/Users/Info/"+ username+ " /" + "TicTacToe.txt");
            String backup = "";
            if (userFile.exists()) {
                FileReader fr = new FileReader(userFile);
                BufferedReader br = new BufferedReader(fr);
                String line;

                while ((line = br.readLine()) != null) {
                    backup += line + "\n";
                }
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(backup + "\n" + gameInfo);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}

