package ui;
import java.util.Scanner;
import java.lang.Integer;
import core.CheckersLogic;

class CheckersTextConsole{
    public static final int uiRowSize = 9;
    public static final int uiColSize = 18;

    public static void printBoard(CheckersLogic game){
        int boardRow = 0; int boardCol = 0;
        System.out.println("  a b c d e f g h ");
        for(Integer i = 1; i < uiRowSize; i++){
            System.out.print(i.toString() + "|");
            for(Integer j = 2; j < uiColSize; j++){
                String token = game.getToken(boardRow, boardCol);
                if(token == null){
                    System.out.print("_");
                }
                else{
                    System.out.print(token);
                }
                boardCol++;
                System.out.print("|");
            }
            System.out.println();
            boardRow++;
        }
    }

    public static void main(String[] args){
        boolean playerOneTurn = true;
        boolean gameIsOver = false;
        CheckersLogic game = new CheckersLogic();
        while(!gameIsOver){
            String banner = playerOneTurn? "Player One's Turn" : "Player Two's Turn";
            String playerMessage = "Enter your move: ";
            System.out.println(banner);
            System.out.println(playerMessage);
            System.out.println();
            printBoard(game);
            Scanner input = new Scanner(System.in);
            String move = input.nextLine();

            // If move is more than 5 characters, it is invalid
            if(move.length() > 5 || !move.contains("-")){
                System.out.println("Invalid move format. Please try again. eg 1a-2b");
                continue;
            }
            // Split move by the character "-"
            String[] moveSplit = move.split("-");
            String from = moveSplit[0];
            String to = moveSplit[1];
            try{
                int row = Integer.parseInt(from.substring(0,1));
                String col = from.substring(1,2);
                int newRow = Integer.parseInt(to.substring(0,1));
                String newCol = to.substring(1,2);
                if(playerOneTurn){
                    if(game.playerOneMove(row, col, newRow, newCol)){
                        System.out.println("Player One can move again!");
                    }
                    else{
                        playerOneTurn = false;
                    }
                    
                    if(game.playerOneVictory()){
                        System.out.println("Player One Wins!");
                        gameIsOver = true;
                    }
                }
                else{
                    if(game.playerTwoMove(row, col, newRow, newCol)){
                        System.out.println("Player Two can move again!");
                    }
                    else{
                        playerOneTurn = true;
                    }
                    if(game.playerTwoVictory()){
                        System.out.println("Player Two Wins!");
                        gameIsOver = true;
                    }
                }
            }
            catch(Exception e){
                System.out.println("Invalid move format. Please try again. eg a1-b2");
                continue;
            }
        }
    }
}