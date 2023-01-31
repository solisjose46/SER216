package core;
// Player 1 has X token and goes up and is first
// Player 2 has O token and goes down and is second
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

    public void move(int row, int col){
        this.row = row;
        this.col = col;
    }
    public String getToken(){
        return this.token;
    }
    
    // true if captured, false if not captured
    public boolean isCaptured(){
         return this.captureStatus;
    }
    
    public void setCaptureStatus(boolean captureStatus){
        this.captureStatus = captureStatus;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }
}