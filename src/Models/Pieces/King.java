package Models.Pieces;


import Models.ChessBoard;
import Models.PColor;
import Models.Square;

public class King extends Piece {
    public King(Square square, PColor color, ChessBoard board, Boolean hasMoved){
        super(square, color, board, hasMoved);
        type = PieceType.K;
    }

    public King(Square square, PColor color, ChessBoard board){
        this(square, color, board, false);
    }

    @Override
    public Boolean isValidMove(Square dest) {
        if(pos.same(dest)) return false;

        int dr = dest.row - pos.row;
        int dc = dest.col - pos.col;

        return Math.abs(dr) <= 1 && Math.abs(dc) <= 1;
    }

    @Override
    public Boolean validSituationalMove(Square dest){
        return isValidCastling(dest);
    }

    public Boolean isValidCastling(Square dest){
        if(hasMoved) return false;

        //locate castling rook - kingside or queenside
        Piece rook = (dest.col == 2) ? board.get(pos.row, 0) : board.get(pos.row, 7);
        if(rook == null || rook.getType() != PieceType.R || rook.hasMoved()) return false;

        //empty squares between king and rook
        if(!squaresBetweenAreEmpty(rook.getSquare())) return false;

        //cannot castle out of, into, or through check
        if(board.isUnderCheck()) return false;
        Square between = pos.getSquaresBetween(dest).get(0);
        if(board.testMove(this, between).isUnderCheck()) return false;
        if(board.testMove(this, dest).isUnderCheck()) return false;
        return true;
    }
}
