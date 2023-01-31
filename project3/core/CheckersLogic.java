package core;

import java.util.ArrayList;
/***
CheckersLogic class provides the logic to the ui.CheckersUI class.
It uses the core.Board class to represent the board and the core.Player class to represent the players.
This class validates the user input and determines if the move is valid by throwing an IllegalArgumentException.
The ui should catch the exception and display the error message to the user and prompt the user again.

It is easier to do matrix operation with 0-7 for both rows and cols but the user input is 1-8 for rows and A-H for cols.
User input needs to be translated to valid row and col numbers and vice versa for displaying pawns.

UI Row is 1 - 8
UI Col is A - H

Board Row is 0 - 7
Board Col is 0 - 7

1a = 0, 0

@author Jose Solis
@version 1.0

 */

/*

*/

public class CheckersLogic{
    Player player1;
    Player player2;
    Board board;
    boolean player2IsComputer;

    public CheckersLogic(boolean player2IsComputer){
        this.board = new Board();
        this.player2IsComputer = player2IsComputer;
        this.initializePlayerOne();
        this.initializePlayerTwo();
    }

    /***
    This method is used to validate the user input of the row.
    
    @param row The row number to be validated.
    @return true if the row is valid, false otherwise.
    */
    public boolean isValidRow(int row){
        return 1 <= row && row <= 8;
    }

    /***
    This method is used to validate the user input of the column.

    @param col The column to be validated.
    @return true if the column is valid, false otherwise.
    */
    public boolean isValidCol(String col){
        // Check if col is A, B, C, D, E, F, G, or H (case insensitive)
        return col.equalsIgnoreCase("A") || col.equalsIgnoreCase("B") || col.equalsIgnoreCase("C") || col.equalsIgnoreCase("D") || col.equalsIgnoreCase("E") || col.equalsIgnoreCase("F") || col.equalsIgnoreCase("G") || col.equalsIgnoreCase("H");
    }

    /***
    This method is used to translate ui row to board row.
    The ui row is 1 - 8 and the board row is 0 - 7.

    @param row The ui row to be translated.
    @return The board row.
    */
    public int translateRow(int row){
        return row - 1;
    }

    /***
    This method is used to translate ui column to board column.
    The ui column is A - H and the board column is 0 - 7.

    @param col The ui column to be translated.
    @return The board column.
    */
    public int translateCol(String col){
        int A = 0; int B = 1; int C = 2; int D = 3; int E = 4; int F = 5; int G = 6; int H = 7;
        if(col.equalsIgnoreCase("A")){
            return A;
        } else if(col.equalsIgnoreCase("B")){
            return B;
        } else if(col.equalsIgnoreCase("C")){
            return C;
        } else if(col.equalsIgnoreCase("D")){
            return D;
        } else if(col.equalsIgnoreCase("E")){
            return E;
        } else if(col.equalsIgnoreCase("F")){
            return F;
        } else if(col.equalsIgnoreCase("G")){
            return G;
        }
        return H;
    }

    /***
    This method is to place the initial pawns for player 1.
    */
    public void initializePlayerOne(){
        // Initialize player 1
        this.player1 = new Player(Pawn.playerOneToken, Direction.UP);
        this.player1.addPawn(0, 0);
        this.player1.addPawn(0, 2);
        this.player1.addPawn(0, 4);
        this.player1.addPawn(0, 6);
        this.player1.addPawn(1, 1);
        this.player1.addPawn(1, 3);
        this.player1.addPawn(1, 5);
        this.player1.addPawn(1, 7);
        this.player1.addPawn(2, 0);
        this.player1.addPawn(2, 2);
        this.player1.addPawn(2, 4);
        this.player1.addPawn(2, 6);

        this.board.addPawns(this.player1.getPawns());     
    }

    /***
    This method is to place the initial pawns for player 2.
    If player 2 is a computer, then the computer player is initialized.
    */
    public void initializePlayerTwo(){
        if(this.player2IsComputer){
            this.player2 = new CheckersComputerPlayer(Pawn.playerTwoToken, Direction.DOWN, this.board);
        } else{
            this.player2 = new Player(Pawn.playerTwoToken, Direction.DOWN);
        }
        this.player2.addPawn(5, 1);
        this.player2.addPawn(5, 3);
        this.player2.addPawn(5, 5);
        this.player2.addPawn(5, 7);
        this.player2.addPawn(6, 0);
        this.player2.addPawn(6, 2);
        this.player2.addPawn(6, 4);
        this.player2.addPawn(6, 6);
        this.player2.addPawn(7, 1);
        this.player2.addPawn(7, 3);
        this.player2.addPawn(7, 5);
        this.player2.addPawn(7, 7);

        this.board.addPawns(this.player2.getPawns());
    }

    /***
    This method is used to get the token of the pawn at the specified row and column.
    Useful for printing the pawn to the ui.
    If there is no pawn at the specified row and column, then null is returned.
    The ui uses the null case to print an empty space with an underscore.

    @param row The row of the pawn.
    @param col The column of the pawn.
    @return The token of the pawn at the specified row and column, returns null if there is no pawn at the specified row and column.
    */
    public String getToken(int row, int col){
        Pawn pawn = this.board.getPawn(row, col);
        if(pawn == null){
            return null;
        }

        return pawn.getToken();
    }

    /***
    This method is used to validate user input and move the pawn in a specified direction.
    If the pawn made a capture AND the pawn can make another capture, returns true and the ui uses the true case to allow the user to move again.

    @throws IllegalArgumentException If the new position is not a valid position or is not empty.
    @param direction The direction to move the pawn.
    @param row The row of the pawn to move.
    @param col The column of the pawn to move.
    @param newRow The row to move the pawn to.
    @param newCol The column to move the pawn to.
    @return True if the pawn made a capture AND the pawn can make another capture, false otherwise.
    */
    public boolean playerMove(Direction direction, int row, String col, int newRow, String newCol)throws IllegalArgumentException{
        // Check if row and col are valid
        if(!this.isValidRow(row) || !this.isValidCol(col) || !this.isValidRow(newRow) || !this.isValidCol(newCol)){
            throw new IllegalArgumentException("Invalid row or col");
        }

        // Translate row and col to board row and col
        int boardRow = this.translateRow(row);
        int boardCol = this.translateCol(col);
        int boardNewRow = this.translateRow(newRow);
        int boardNewCol = this.translateCol(newCol);

        // Check if the pawn can move to the specified row and col
        return this.board.movePawn(direction, boardRow, boardCol, boardNewRow, boardNewCol);
    }

    /***
    This method is used to move the pawn for player 1.
    see playerMove(Direction direction, int row, String col, int newRow, String newCol)

    @throws IllegalArgumentException If the new position is not a valid position or is not empty.
    @param row The row of the pawn to move.
    @param col The column of the pawn to move.
    @param newRow The row to move the pawn to.
    @param newCol The column to move the pawn to.
    @return True if the pawn made a capture AND the pawn can make another capture, false otherwise.
    */
    public boolean playerOneMove(int row, String col, int newRow, String newCol)throws IllegalArgumentException{
        return this.playerMove(Direction.UP, row, col, newRow, newCol);
    }

    /***
    This method is used to move the pawn for player 2.
    see playerMove(Direction direction, int row, String col, int newRow, String newCol)

    @throws IllegalArgumentException If the new position is not a valid position or is not empty.
    @param row The row of the pawn to move.
    @param col The column of the pawn to move.
    @param newRow The row to move the pawn to.
    @param newCol The column to move the pawn to.
    @return True if the pawn made a capture AND the pawn can make another capture, false otherwise.
    */
    public boolean playerTwoMove(int row, String col, int newRow, String newCol)throws IllegalArgumentException{
        return this.playerMove(Direction.DOWN, row, col, newRow, newCol);
    }

    /***
    This method checks if player one has won by checking if player two has any valid moves left.

    @return True if player one has won, false otherwise.
    */
    public boolean playerOneVictory(){
        return !this.board.canPlayerMove(this.player2);
    }

    /***
    This method checks if player two has won by checking if player one has any valid moves left.

    @return True if player two has won, false otherwise.
    */
    public boolean playerTwoVictory(){
        return !this.board.canPlayerMove(this.player1);
    }

    /***
    This method provides the ui to start the computer to make a move.
    The computer will make a move and return the formatted move to display on the ui.

    @throws IllegalArgumentException If the new position is not a valid position or is not empty.
    @return The formatted move to display on the ui.
    */
    public String produceComputerMove()throws IllegalArgumentException{
        CheckersComputerPlayer computer = (CheckersComputerPlayer)this.player2;
        computer.imitatePlayerMove();
        Integer currentRow = computer.getCurrentRow();
        String currentCol = computer.getCurrentCol();
        Integer nextRow = computer.getNextRow();
        String nextCol = computer.getNextCol();
        this.playerTwoMove(currentRow, currentCol, nextRow, nextCol);
        return computer.getFormattedMove();
    }
}