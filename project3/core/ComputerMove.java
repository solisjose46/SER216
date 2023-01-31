package core;

import java.util.ArrayList;

public class ComputerMove{
    Integer currentRow;
    String currentCol;
    Integer nextRow;
    String nextCol;

    public ComputerMove(Integer currentRow, Integer currentCol, Integer nextRow, Integer nextCol){
        this.setMove(currentRow, currentCol, nextRow, nextCol);
    }

    public Integer getCurrentRow(){
        return this.currentRow;
    }

    public String getCurrentCol(){
        return this.currentCol;
    }

    public Integer getNextRow(){
        return this.nextRow;
    }

    public String getNextCol(){
        return this.nextCol;
    }

    public Integer translateRow(Integer row){
        return row + 1;
    }

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
    public void setMove(Integer currentRow, Integer currentCol, Integer nextRow, Integer nextCol){
        this.currentRow = this.translateRow(currentRow);
        this.currentCol = this.translateCol(currentCol);
        this.nextRow = this.translateRow(nextRow);
        this.nextCol = this.translateCol(nextCol);
    }

    @Override
    public String toString(){
        return this.currentRow.toString() + this.currentCol + "-" + this.nextRow.toString() + this.nextCol;
    }
}