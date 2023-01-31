package ui;
import java.util.Scanner;
import java.lang.Integer;
import core.CheckersLogic;

class CheckersTextConsole{

    public static void printBoard(CheckersLogic game){
        for(Integer i = 8; i > 0; i--){
            System.out.print(i.toString() + "|");
            int row = i-1;
            for(Integer j = 0; j < 8; j++){
                String token = game.getToken(row, j);
                if(token == null){
                    System.out.print("_");
                }
                else{
                    System.out.print(token);
                }
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h ");
    }

    public static void main(String[] args){
        boolean playerOneTurn = true;
        boolean gameIsOver = false;
        boolean secondPlayerIsComputer = false;
        Scanner input = new Scanner(System.in);

        while(true){
            System.out.println("Begin Game. Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.");
            String playerType = input.nextLine();
            if(playerType.equalsIgnoreCase("P")){
                secondPlayerIsComputer = false;
                break;
            }
            else if(playerType.equalsIgnoreCase("C")){
                secondPlayerIsComputer = true;
                break;
            }
            else{
                System.out.println("Invalid input. Please try again.");
            }
        }

        CheckersLogic game = new CheckersLogic(secondPlayerIsComputer);

        if(secondPlayerIsComputer){
            System.out.println("Start game against computer. You are Player X and Computer is Player O.");
        }

        while(!gameIsOver){
            String banner = playerOneTurn? "Player One's Turn" : "Player Two's Turn";
            String playerMessage = "Enter your move: ";
            System.out.println(banner);
            System.out.println(playerMessage);
            System.out.println();
            printBoard(game);
            
            if(!playerOneTurn && secondPlayerIsComputer){
                try{
                    String computerMove = game.produceComputerMove();
                    System.out.println(computerMove);
                    playerOneTurn = true;
                    if(game.playerTwoVictory()){
                        System.out.println("Player Two Wins!");
                        gameIsOver = true;
                    }
                }
                catch(Exception e){
                    System.out.println(e);
                    System.out.println("Sorry something went wrong with computer move.");
                    return;
                }
            }
            else{
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
                    System.out.println(e);
                    if(playerOneTurn){
                        System.out.println("Player One's move was invalid. Please try again.");
                    }
                    else{
                        System.out.println("Player Two's move was invalid. Please try again.");
                    }
                    continue;
                }
            }
        }
    }
}