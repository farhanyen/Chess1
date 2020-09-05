package Models.Pieces;

import Models.ChessBoard;
import Models.PColor;
import Models.Square;

public class Knight extends Piece {
    public Knight(Square square, PColor color, ChessBoard board, Boolean hasMoved){
        super(square, color, board, hasMoved);
        type = PieceType.N;
    }

    public Knight(Square square, PColor color, ChessBoard board){
        this(square, color, board, false);
    }

    @Override
    public Boolean isValidMove(Square dest) {
        if(pos.same(dest)) return false;

        int dr = Math.abs(dest.row - pos.row);
        int dc = Math.abs(dest.col - pos.col);

        if((dr == 1 && dc == 2) || (dr == 2 && dc ==1))
            return true;
        else
            return false;
    }
}

