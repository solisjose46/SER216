package core;

import java.util.ArrayList;

public class CheckersComputerPlayer extends Player{
    ComputerMove computerMove;
    Board board;

    public CheckersComputerPlayer(String token, Direction direction, Board board){
        super(token, direction);
        this.board = board;
        this.computerMove = null;
    }

    public int getCurrentRow(){
        return computerMove.getCurrentRow();
    }

    public String getCurrentCol(){
        return computerMove.getCurrentCol();
    }

    public int getNextRow(){
        return computerMove.getNextRow();
    }

    public String getNextCol(){
        return computerMove.getNextCol();
    }

    public String getFormattedMove(){
        return this.computerMove.toString();
    }

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