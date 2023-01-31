package core;

/***
Pawn class is used to represent the pawn on the board.
Each pawn has a row, column, token, and capture status.
The token is either X or O.
The capture status is true if the pawn has been captured, false otherwise.
The capture status is used to determine if the pawn can be moved again.
If it is capture then it is soft deleted from the board and cannot be moved again.

@author Jose Solis
@version 1.0
*/
class Pawn{
    public static final int playerPawnCount = 12;
    public static final int totalPawnCount = 24;
    public static final String playerOneToken = "X";
    public static final String playerTwoToken = "O";
    int row;
    int col;
    String token;
    boolean captureStatus;


    public Pawn(int row, int col, String token){
        this.row = row;
        this.col = col;
        this.token = token;
        this.captureStatus = false;
    }

    /***
    This method is used to move the pawn to a new location.
    
    @param row The new row of the pawn.
    @param col The new column of the pawn.
    */
    public void move(int row, int col){
        this.row = row;
        this.col = col;
    }

    /***
    This method is used to get the token of the pawn.

    @return String The token of the pawn.
    */
    public String getToken(){
        return this.token;
    }

    /***
    This method is used to get the capture status of the pawn.
    To be captured means it is soft deleted from the board and cannot be moved again.

    @return boolean True if the pawn is captured, false otherwise.
    */
    public boolean isCaptured(){
         return this.captureStatus;
    }

    /***
    This method is used to set the capture status of the pawn.
    To be captured means it is soft deleted from the board and cannot be moved again.

    @param captureStatus True if the pawn is captured, false otherwise.
    */
    public void setCaptureStatus(boolean captureStatus){
        this.captureStatus = captureStatus;
    }

    /***
    This method is used to get the row of the pawn.

    @return int The row of the pawn.
    */
    public int getRow(){
        return this.row;
    }

    /***
    This method is used to get the column of the pawn.

    @return int The column of the pawn.
    */
    public int getCol(){
        return this.col;
    }
}