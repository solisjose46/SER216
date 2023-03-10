package core;

import java.util.ArrayList;
/***
Board class is responsible for keeping track of the pawns on the board.

@author Jose Solis
@version 1.0

*/
public class Board{
    public ArrayList<Pawn> board;

    public Board(){
        this.board = new ArrayList<Pawn>(Pawn.totalPawnCount);
    }

    /***
    This method adds a collection of pawns to the board.
    Used in conjunction when creating a player's pawns.
    @param pawns ArrayList<Pawn> collection of pawns to add to the board.
     */
    public void addPawns(ArrayList<Pawn> pawns){
        this.board.addAll(pawns);
    }

    /**
    This method returns the pawn at the given row and col.
    It returns null if the row and col is "empty".
    The idea is each space on the "8x8" can only have one pawn.
    A pawn can only move to a new space if it is "empty".
    Empty means there is no pawn at that space or if there is a pawn it must be captured to treat the space as empty.

    @param row int row of the pawn to return.
    @param col int col of the pawn to return.
    @return Pawn pawn at the given row and col, null if there is no pawn at the given row and col.
    */
    public Pawn getPawn(int row, int col){
        for(int i = 0; i < this.board.size(); i++){
            if(this.board.get(i).getRow() == row && this.board.get(i).getCol() == col && !this.board.get(i).isCaptured()){
                return this.board.get(i);
            }
        }
        return null;
    }

    /**
    This method checks if the given row and col is a valid position on the board.
    Useful for checking if a pawn can move to a new position.
    @param row int row to check.
    @param col int col to check.
    @return boolean true if the row and col is a valid position on the board, false otherwise. Does not check if the position is empty.
     */
    public boolean isPositionValid(int row, int col){
        if(0 <= row && row <= 7 && 0 <= col && col <= 7){
            return true;
        }
        return false;
    }

    /***
    This method checks if the given player can move any of their pawns.
    Useful for checking if the game is over.

    @param player Player player to check if they can move any of their pawns.
    @return boolean true if the player can move any of their pawns, false otherwise.
    */
    public boolean canPlayerMove(Player player){
        for(int i = 0; i < player.getPawns().size(); i++){
            Pawn pawn = player.getPawns().get(i);
            if(pawn.isCaptured()){
                continue;
            }
            if(this.canPawnMoveForward(player.direction, pawn)){
                return true;
            }
            if(this.isCapturePossible(player.direction, pawn)){
                return true;
            }
        }
        return false;
    }

    /**
    This method checks if the given pawn can move forward.
    A pawn can move forward if there is an empty space in front of it.
    Useful for checking if the game is over or if the computer can perform a move with its pawn.

    @param direction Direction direction of the pawn.
    @param pawn Pawn pawn to check if it can move forward.
    @return boolean true if the pawn can move forward, false otherwise.
    **/
    public boolean canPawnMoveForward(Direction direction, Pawn pawn){
        int row; int colLeft; int colRight;
        if(direction == Direction.UP){
            row = pawn.getRow() + 1;
            colLeft = pawn.getCol() - 1;
            colRight = pawn.getCol() + 1;
        } else {
            row = pawn.getRow() - 1;
            colLeft = pawn.getCol() - 1;
            colRight = pawn.getCol() + 1;
        }
        
        boolean isLeftValid = this.isPositionValid(row, colLeft) && this.getPawn(row, colLeft) == null;
        boolean isRightValid = this.isPositionValid(row, colRight) && this.getPawn(row, colRight) == null;
        return isLeftValid || isRightValid;
    }

    /**
    This method checks if the given pawn can capture another pawn.
    A pawn can capture another pawn if there is an empty space two spaces in front of it and there is an enemy pawn one space in front of it.
    Useful for checking if the game is over or if the computer can perform a capture with its pawn.
    @param direction Direction direction of the pawn.
    @param pawn Pawn pawn to check if it can capture another pawn.
    @return boolean true if the pawn can capture another pawn, false otherwise.
    **/
    public boolean isCapturePossible(Direction direction, Pawn pawn){
        int pawnRow; int emptyRow; int pawnLeftCol; int pawnRightCol; int emptyLeftCol; int emptyRightCol;
        int row = pawn.getRow(); int col = pawn.getCol();

        if(direction == Direction.UP){
            pawnRow = row + 1;
            emptyRow = row + 2;
            pawnLeftCol = col - 1;
            pawnRightCol = col + 1;
            emptyLeftCol = col - 2;
            emptyRightCol = col + 2;
        } else {
            pawnRow = row - 1;
            emptyRow = row - 2;
            pawnLeftCol = col - 1;
            pawnRightCol = col + 1;
            emptyLeftCol = col - 2;
            emptyRightCol = col + 2;
        }
        
        boolean isLeftValid = this.isPositionValid(pawnRow, pawnLeftCol);
        isLeftValid = isLeftValid && this.isPositionValid(emptyRow, emptyLeftCol);
        isLeftValid = isLeftValid && this.getPawn(pawnRow, pawnLeftCol)!= null;
        isLeftValid = isLeftValid && this.getPawn(emptyRow, emptyLeftCol) == null;
        isLeftValid = isLeftValid && !this.getPawn(pawnRow, pawnLeftCol).getToken().equals(pawn.getToken());

        boolean isRightValid = this.isPositionValid(pawnRow, pawnRightCol);
        isRightValid = isRightValid && this.isPositionValid(emptyRow, emptyRightCol);
        isRightValid = isRightValid && this.getPawn(pawnRow, pawnRightCol) != null;
        isRightValid = isRightValid && this.getPawn(emptyRow, emptyRightCol) == null;
        isRightValid = isRightValid && !this.getPawn(pawnRow, pawnRightCol).getToken().equals(pawn.getToken());

        return isLeftValid || isRightValid;
    }

    /**
    This method moves the pawn at the given row and col to the new row and col.
    The pawn can only move forward or capture another pawn.
    If the pawn is captured, it is marked as captured (soft delete).

    @throws IllegalArgumentException if the pawn cannot move to the new position.
    @param direction Direction direction of the pawn.
    @param row int row of the pawn.
    @param col int col of the pawn.
    @param newRow int new row of the pawn.
    @param newCol int new col of the pawn.
    @return boolean This method returns true if the pawn made a capture AND another capture is possible, false otherwise.
     **/
    public boolean movePawn(Direction direction, int row, int col, int newRow, int newCol)throws IllegalArgumentException{
        Pawn pawn = this.getPawn(row, col);
        if(pawn == null){
            throw new IllegalArgumentException("There is no pawn at the specified row and col");
        }

        if(pawn.isCaptured()){
            throw new IllegalArgumentException("The pawn is captured");
        }
        
        if(direction == Direction.UP && !pawn.getToken().equals(Pawn.playerOneToken)){
            throw new IllegalArgumentException("Player 1 can only move up");
        }

        if(direction == Direction.DOWN && !pawn.getToken().equals(Pawn.playerTwoToken)){
            throw new IllegalArgumentException("Player 2 can only move down");
        }

        boolean basicUpLeftMove = row + 1 == newRow && col - 1 == newCol;
        boolean basicUpRightMove = row + 1 == newRow && col + 1 == newCol;
        boolean captureUpLeftMove = row + 2 == newRow && col - 2 == newCol;
        boolean captureUpRightMove = row + 2 == newRow && col + 2 == newCol;
        boolean basicDownLeftMove = row - 1 == newRow && col - 1 == newCol;
        boolean basicDownRightMove = row - 1 == newRow && col + 1 == newCol;
        boolean captureDownLeftMove = row - 2 == newRow && col - 2 == newCol;
        boolean captureDownRightMove = row - 2 == newRow && col + 2 == newCol;

        if(direction == Direction.UP && !(basicUpLeftMove || basicUpRightMove || captureUpLeftMove || captureUpRightMove)){
            throw new IllegalArgumentException("Invalid move for player 1");
        }

        if(direction == Direction.DOWN && !(basicDownLeftMove || basicDownRightMove || captureDownLeftMove || captureDownRightMove)){
            throw new IllegalArgumentException("Invalid move for player 2");
        }

        if(basicUpLeftMove || basicUpRightMove || basicDownLeftMove || basicDownRightMove){
            Pawn isEmpty = this.getPawn(newRow, newCol);
            if(isEmpty != null){
                throw new IllegalArgumentException("There is already a pawn at the specified row and col");
            }
            pawn.move(newRow, newCol);
            return false;
        }

        int rowToCapture; int colToCapture;

        if(captureUpLeftMove){
            rowToCapture = row + 1;
            colToCapture = col - 1;
        } else if(captureUpRightMove){
            rowToCapture = row + 1;
            colToCapture = col + 1;
        } else if(captureDownLeftMove){
            rowToCapture = row - 1;
            colToCapture = col - 1;
        } else {
            rowToCapture = row - 1;
            colToCapture = col + 1;
        }

        Pawn pawnToCapture = this.getPawn(rowToCapture, colToCapture);
        if(pawnToCapture == null){
            throw new IllegalArgumentException("There is no pawn to capture at the specified row and col");
        }

        // if pawn to capture token is the same as the pawn token, then it cannot be captured
        if(pawnToCapture.getToken().equals(pawn.getToken())){
            throw new IllegalArgumentException("Self capture is not allowed.");
        }

        Pawn isEmpty = this.getPawn(newRow, newCol);
        if(isEmpty != null ){
            throw new IllegalArgumentException("Pawn cannot be captured because there is already a pawn at the specified row and col");
        }
        pawn.move(newRow, newCol);
        pawnToCapture.setCaptureStatus(true);

        return this.isCapturePossible(direction, pawn);
    }

}