package Models.Pieces;

import Models.ChessBoard;
import Models.PColor;
import Models.Square;

public class Rook extends Piece {
    public Rook(Square square, PColor color, ChessBoard board, Boolean hasMoved){
        super(square, color, board, hasMoved);
        type = PieceType.R;
    }

    public Rook(Square square, PColor color, ChessBoard board){
       this(square, color, board, false);
    }


    @Override
    public Boolean isValidMove(Square dest) {
        if(pos.same(dest)) return false;

        int dr = dest.row - pos.row;
        int dc = dest.col - pos.col;

        return dr == 0 || dc == 0;
    }
}
