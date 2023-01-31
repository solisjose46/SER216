package core;

import java.util.ArrayList;
/**
CheckersComputerPlayer class is a subclass of Player class. 
It is used to represent the computer player in the game.

@author Jose Solis
@version 1.0
 */
public class CheckersComputerPlayer extends Player{
    ComputerMove computerMove;
    Board board;

    public CheckersComputerPlayer(String token, Direction direction, Board board){
        super(token, direction);
        this.board = board;
        this.computerMove = null;
    }
    /**
    This method is used to get the computer's current row position.
    The row is returned in user input format ie 3c-4d.

    @return int
     */
    public int getCurrentRow(){
        return computerMove.getCurrentRow();
    }
    
    /**
    This method is used to get the computer's current column position.
    The column is returned in user input format ie 3c-4d.

    @return String
     */
    public String getCurrentCol(){
        return computerMove.getCurrentCol();
    }

    /**
    This method is used to get the computer's next row position.
    The row is returned in user input format ie 3c-4d.

    @return int
     */
    public int getNextRow(){
        return computerMove.getNextRow();
    }

    /**
    This method is used to get the computer's next column position.
    The column is returned in user input format ie 3c-4d.

    @return String
     */
    public String getNextCol(){
        return computerMove.getNextCol();
    }

    /**
    This method is used to get the computer's move (start-end).
    The row is returned in user input format ie 3c-4d.
    Useful to display to the user the computer's move.
    This should be called after the imitatePlayerMove() method to get most recent move.

    @return String
     */
    public String getFormattedMove(){
        return this.computerMove.toString();
    }
    /**
    This method is used by the game logic to perform the computer's move.
    This algorithm prioritzes capturing moves over moving forward.
    The algorithm also prioritzes left moves over right moves.
    It is a naive algorithm that selects the first pawn that can capture or move forward.

     */
    public void imitatePlayerMove(){
        Pawn pawn = null;
        boolean isCaptureMove = false;
        // get the first pawn that can capture or move forward
        for(int i = 0; i < this.getPawns().size(); i++){
            pawn = this.getPawns().get(i);
            if(pawn.isCaptured()){
                continue;
            }
            // priority is to capture
            if(board.isCapturePossible(this.direction, pawn)){
                isCaptureMove = true;
                break;
            }
            if(board.canPawnMoveForward(this.direction, pawn)){
                break;
            }
        }
        int currentRow = pawn.getRow();
        int currentCol = pawn.getCol();
        int nextRow = -1; int nextCol = -1; int leftCol = -1; int rightCol = -1;
        // prioritzing left move
        boolean isLeftMove = false;
        if(isCaptureMove){
            if(this.direction == Direction.UP){
                nextRow = pawn.getRow() + 2;
            } else {
                nextRow = pawn.getRow() - 2;
            }

            leftCol = pawn.getCol() - 2;
            rightCol = pawn.getCol() + 2;
        } else {
            if(this.direction == Direction.UP){
                nextRow = pawn.getRow() + 1;
            } else {
                nextRow = pawn.getRow() - 1;
            }

            leftCol = pawn.getCol() - 1;
            rightCol = pawn.getCol() + 1;
        }

        if(board.isPositionValid(nextRow, leftCol)){
            isLeftMove = board.getPawn(nextRow, leftCol) == null;
        }

        if(isLeftMove){
            nextCol = leftCol;
        }
        else{
            nextCol = rightCol;
        }

        if(this.computerMove == null){
            this.computerMove = new ComputerMove(currentRow, currentCol, nextRow, nextCol);
        } else {
            this.computerMove.setMove(currentRow, currentCol, nextRow, nextCol);
        }
    }

}