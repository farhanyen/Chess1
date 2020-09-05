package Models.Pieces;


import Models.ChessBoard;
import Models.PColor;
import Models.Square;

public class Bishop extends Piece {
    public Bishop(Square square, PColor color, ChessBoard board, Boolean hasMoved){
        super(square, color, board, hasMoved);
        type = PieceType.B;
    }

    public Bishop(Square square, PColor color, ChessBoard board){
        this(square, color, board, false);
    }

    @Override
    public Boolean isValidMove(Square dest) {
        if(pos.same(dest)) return false;

        int dr = dest.row - pos.row;
        int dc = dest.col - pos.col;

        return Math.abs(dr) == Math.abs(dc);
    }
}
