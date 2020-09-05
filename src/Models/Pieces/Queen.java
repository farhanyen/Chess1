package Models.Pieces;

import Models.ChessBoard;
import Models.PColor;
import Models.Square;

public class Queen extends Piece {
    public Queen(Square square, PColor color, ChessBoard board, Boolean hasMoved){
        super(square, color, board, hasMoved);
        type = PieceType.Q;
    }

    public Queen(Square square, PColor color, ChessBoard board){
        this(square, color, board, false);
    }

    @Override
    public Boolean isValidMove(Square dest) {
        if(pos.same(dest)) return false;

        int dr = dest.row - pos.row;
        int dc = dest.col - pos.col;

        if(dr == 0 || dc == 0){
            return true;
        } else if(Math.abs(dr) == Math.abs(dc)){
            return true;
        } else{
            return false;
        }

    }
}
