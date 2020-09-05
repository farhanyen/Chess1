package Models;

import java.util.ArrayList;

public class Square {
    public static final int LENGTH = ChessBoard.BOARDLENGTH /8;

    public static Square getSquare(int x, int y){
        return new Square(y/LENGTH, x/LENGTH);
    }

    public final int row, col;
    public final int locx, locy;


    public Square(int row, int col){
        this.row = row; this.col = col;
        locx = col * LENGTH;
        locy = row * LENGTH;
    }


    public Boolean same(Square square){
        return this.row == square.row && this.col == square.col;
    }


    public ArrayList<Square> getSquaresBetween(Square dest){
        ArrayList<Square> Path = new ArrayList<>();

        if(this.same(dest)){
            throw new IllegalArgumentException("No squares between same square");
        }

        int dr = dest.row - row; int dc = dest.col - col; // total diff in row/col

        if(dr != 0 && dc != 0 && Math.abs(dr) != Math.abs(dc)){ //if not in straight line
            throw new IllegalArgumentException("Squares should be in a line");
        }

        if(dr != 0) dr /= Math.abs(dr); //get step change in row/col
        if(dc != 0) dc /= Math.abs(dc);

        Square nextPos = this.add(dr, dc);

        while(!nextPos.same(dest)){
            System.out.println("sRow: " + nextPos.row + "sCol: " + nextPos.col);
            if(nextPos.row > 8 || nextPos.col > 8 || nextPos.row < 0 || nextPos.col < 0){
                throw new IllegalArgumentException("Out of bounds");
            }
            Path.add(nextPos);
            nextPos = nextPos.add(dr, dc);
        }

        return Path;
    }

    public Square add(int dr, int dc){
        return new Square(row + dr, col + dc);
    }

}
