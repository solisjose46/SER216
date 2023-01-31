package core;

import java.util.ArrayList;


/***
ComputerMove class is used to store the computer move and provide useful methods for translating the move to ui friendly format.

@author Jose Solis
@version 1.0

*/
public class ComputerMove{
    Integer currentRow;
    String currentCol;
    Integer nextRow;
    String nextCol;

    public ComputerMove(Integer currentRow, Integer currentCol, Integer nextRow, Integer nextCol){
        this.setMove(currentRow, currentCol, nextRow, nextCol);
    }

    /***
    This method is used to get the current row of the move.

    @return The current row of the move.
    */
    public Integer getCurrentRow(){
        return this.currentRow;
    }

    /***
    This method is used to get the current column of the move.

    @return The current column of the move.
    */
    public String getCurrentCol(){
        return this.currentCol;
    }

    /***
    This method is used to get the next row of the move.

    @return The next row of the move.
    */
    public Integer getNextRow(){
        return this.nextRow;
    }

    /***
    This method is used to get the next column of the move.

    @return The next column of the move.
    */
    public String getNextCol(){
        return this.nextCol;
    }

    /***
    This method is used to translate the row to a ui friendly format.

    @param row The row to translate.
    @return The translated row.
    */
    public Integer translateRow(Integer row){
        return row + 1;
    }

    /***
    This method is used to translate the column to a ui friendly format.

    @param col The column to translate.
    @return The translated column.
    */
    public String translateCol(Integer col){
        if(col == 0){
            return "A";
        }
        else if(col == 1){
            return "B";
        }
        else if(col == 2){
            return "C";
        }
        else if(col == 3){
            return "D";
        }
        else if(col == 4){
            return "E";
        }
        else if(col == 5){
            return "F";
        }
        else if(col == 6){
            return "G";
        }
        return "H";
    }

    /***
    This method is used to set the move.
    Useful for keeping track of the computers most recent move.

    @param currentRow The current row of the move.
    @param currentCol The current column of the move.
    @param nextRow The next row of the move.
    @param nextCol The next column of the move.
    */
    public void setMove(Integer currentRow, Integer currentCol, Integer nextRow, Integer nextCol){
        this.currentRow = this.translateRow(currentRow);
        this.currentCol = this.translateCol(currentCol);
        this.nextRow = this.translateRow(nextRow);
        this.nextCol = this.translateCol(nextCol);
    }

    /***
    This method is used to get the move in a ui friendly format.

    @return String The move in a ui friendly format.
    */
    @Override
    public String toString(){
        return this.currentRow.toString() + this.currentCol + "-" + this.nextRow.toString() + this.nextCol;
    }
}