package core;

import java.util.ArrayList;
/*
UI Row is 1 - 8
UI Col is A - H

Board Row is 0 - 7
Board Col is 0 - 7
*/

public class CheckersLogic{
    // 2 players
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

    public boolean isValidRow(int row){
        return 1 <= row && row <= 8;
    }

    public boolean isValidCol(String col){
        // Check if col is A, B, C, D, E, F, G, or H (case insensitive)
        return col.equalsIgnoreCase("A") || col.equalsIgnoreCase("B") || col.equalsIgnoreCase("C") || col.equalsIgnoreCase("D") || col.equalsIgnoreCase("E") || col.equalsIgnoreCase("F") || col.equalsIgnoreCase("G") || col.equalsIgnoreCase("H");
    }

    public int translateRow(int row){
        return row - 1;
    }

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

    public String getToken(int row, int col){
        Pawn pawn = this.board.getPawn(row, col);
        if(pawn == null){
            return null;
        }

        return pawn.getToken();
    }

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

    public boolean playerOneMove(int row, String col, int newRow, String newCol)throws IllegalArgumentException{
        return this.playerMove(Direction.UP, row, col, newRow, newCol);
    }

    public boolean playerTwoMove(int row, String col, int newRow, String newCol)throws IllegalArgumentException{
        return this.playerMove(Direction.DOWN, row, col, newRow, newCol);
    }

    public boolean playerOneVictory(){
        return !this.board.canPlayerMove(this.player2);
    }

    public boolean playerTwoVictory(){
        return !this.board.canPlayerMove(this.player1);
    }

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