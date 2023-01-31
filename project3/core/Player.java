package core;
import java.util.ArrayList;


/***
Player class is used to represent the player.

@author Jose Solis
@version 1.0
*/

public class Player{
    Direction direction;
    String token;
    ArrayList<Pawn> pawns = new ArrayList<Pawn>(Pawn.playerPawnCount);

    Player(String token, Direction direction){
        this.token = token;
        this.direction = direction;
    }

    /***
    This method is used to give the player a pawn.

    @param row The row of the pawn.
    @param col The column of the pawn.
    */
    public void addPawn(int row, int col){
        this.pawns.add(new Pawn(row, col, this.token));
    }

    /***
    This method is used to get the players pawns.
    Useful for iterating through the pawns to check for valid moves.

    @return ArrayList<Pawn> The pawns of the player.
    */
    public ArrayList<Pawn> getPawns(){
        return this.pawns;
    }
}