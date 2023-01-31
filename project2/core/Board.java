package core;

import java.util.ArrayList;

public class Board{
    public ArrayList<Pawn> board;

    public Board(){
        this.board = new ArrayList<Pawn>(Pawn.totalPawnCount);
    }

    public void addPawns(ArrayList<Pawn> pawns){
        this.board.addAll(pawns);
    }

    public Pawn getPawn(int row, int col){
        for(int i = 0; i < this.board.size(); i++){
            if(this.board.get(i).getRow() == row && this.board.get(i).getCol() == col){
                return this.board.get(i);
            }
        }
        return null;
    }

    public boolean isPositionValid(int row, int col){
        if(0 <= row && row <= 7 && 0 <= col && col <= 7){
            return true;
        }
        return false;
    }

    public boolean canPlayerMove(Player player){
        for(int i = 0; i < player.getPawns().size(); i++){
            Pawn pawn = player.getPawns().get(i);
            if(pawn.getCaptured()){
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

        boolean isLeftValid = this.isPositionValid(pawnRow, pawnLeftCol) && this.isPositionValid(emptyRow, emptyLeftCol) && this.getPawn(pawnRow, pawnLeftCol) != null && this.getPawn(emptyRow, emptyLeftCol) == null;
        boolean isRightValid = this.isPositionValid(pawnRow, pawnRightCol) && this.isPositionValid(emptyRow, emptyRightCol) && this.getPawn(pawnRow, pawnRightCol) != null && this.getPawn(emptyRow, emptyRightCol) == null;
        return isLeftValid || isRightValid;
    }

    // true means capture was made AND another capture is possible
    public boolean movePawn(Direction direction, int row, int col, int newRow, int newCol)throws IllegalArgumentException{
        Pawn pawn = this.getPawn(row, col);
        if(pawn == null){
            throw new IllegalArgumentException("There is no pawn at the specified row and col");
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
        Pawn isEmpty = this.getPawn(newRow, newCol);
        if(isEmpty != null){
            throw new IllegalArgumentException("Pawn cannot be captured because there is already a pawn at the specified row and col");
        }
        pawn.move(newRow, newCol);
        pawnToCapture.setCaptured(true);

        return this.isCapturePossible(direction, pawn);
    }

}