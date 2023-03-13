package com.nighthawk.team_backend.mvc.lightboard;

public class CheckerBoard extends LightBoard {

    public CheckerBoard(int numRows){
        // call superclass constructor to initalize lights
        super(numRows, numRows);
        int index = 0;
        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numRows; col++){
                // alternating colors
                if(index % 2 == 0){
                    lights[row][col] = new Light((short)0, (short)0, (short)0);  // black
                }
                else{
                    lights[row][col] = new Light((short)255, (short)255, (short)255);  // white
                }
                index++;
            }
            // alternating starting color of row
            if(row % 2 != 0){
                index = 0;
            }
            else{
                index = 1;
            }
        }
    }
    static public void main(String[] args) {
        // create and display CheckerBoard
        CheckerBoard checkerBoard = new CheckerBoard(8);
        System.out.println(checkerBoard);  // use toString() method
        System.out.println(checkerBoard.toTerminal());
        System.out.println(checkerBoard.toColorPalette());
        System.out.println(checkerBoard.toEffectColorPalette());
    }
}
