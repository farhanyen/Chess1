package Models.Pieces;

import Models.ChessBoard;
import Models.PColor;
import Models.Square;
import javafx.util.Pair;

public class Pawn extends Piece {
    public Pawn(Square square, PColor color, ChessBoard board, Boolean hasMoved){
        super(square, color, board, hasMoved);
        type = PieceType.P;
    }

    public Pawn(Square square, PColor color, ChessBoard board){
        this(square, color, board, false);
    }

    @Override
    public Boolean isValidMove(Square dest) {
        if(pos.same(dest)) return false;

        int dr = dest.row - pos.row;
        int dc = dest.col - pos.col;


        if(getColor() == PColor.W){
            if(dc == 0 &&dr == -1)
                return true;
            else if(dc == 0 && dr == -2 && !hasMoved)
                return true;
            else if(Math.abs(dc) == 1 && dr == -1){
                return board.isEnemy(dest);
            }
        } else {
            if(dc == 0 && dr == 1)
                return true;
            else if(dc == 0 && dr == 2 && !hasMoved)
                return true;
            else if(Math.abs(dc) == 1 && dr == 1){
                return board.isEnemy(dest);
            }
        }

        return false;
    }



    /*private Boolean validEnPassant(Square dest){
        Pair<Piece, Square> lastMove = board.state().getLastMove();
        if(lastMove == null) return false;

        Piece lastPiece = lastMove.getKey();
        Square lastSquare = lastMove.getValue();

        if(!(lastPiece.getType() == PieceType.P && !lastPiece.hasMoved)) return false;
        if(Math.abs(lastPiece.getSquare().row - lastSquare.row) != 2) return false;
        if(Math.abs(lastSquare.col - pos.col) != 1) return false;

        return true;
    }*/
}

