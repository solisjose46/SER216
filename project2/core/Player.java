package core;
import java.util.ArrayList;

public class Player{
    Direction direction;
    String token;
    ArrayList<Pawn> pawns = new ArrayList<Pawn>(Pawn.playerPawnCount);

    Player(String token, Direction direction){
        this.token = token;
        this.direction = direction;
    }

    public void addPawn(int row, int col){
        this.pawns.add(new Pawn(row, col, this.token));
    }

    public ArrayList<Pawn> getPawns(){
        return this.pawns;
    }
}