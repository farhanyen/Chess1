package Models.Pieces;

import Models.ChessBoard;
import Models.PColor;
import Models.Square;

import java.util.ArrayList;

public abstract class Piece {
    public static final int PLENGTH = (int) (Square.LENGTH * 0.75);
    protected ChessBoard board;

    protected final Square pos;
    protected final PColor color;
    protected final Boolean hasMoved;
    protected PieceType type;



    public Piece(Square square, PColor color, ChessBoard board, Boolean hasMoved) {
        pos = square;
        this.color = color;
        this.board = board;
        this.hasMoved = hasMoved;
    }


    public Square getSquare() {
        return pos;
    }
    public PColor getColor() {
        return color;
    }
    public PieceType getType(){
        return type;
    }
    public Boolean hasMoved() {
        return hasMoved;
    }

    public Piece newChessBoard(ChessBoard newBoard){
        switch (type){
            case R: return new Rook(pos, color, newBoard, hasMoved);
            case N: return new Knight(pos, color, newBoard, hasMoved);
            case B: return new Bishop(pos, color, newBoard, hasMoved);
            case Q: return new Queen(pos, color, newBoard, hasMoved);
            case K: return new King(pos, color, newBoard, hasMoved);
            case P: return new Pawn(pos, color, newBoard, hasMoved);
            default: throw new IllegalArgumentException("invalid argument");
        }
    }

    public Piece move(Square dest){
        return movedPieceFactory(dest);
    }
    private Piece movedPieceFactory(Square dest){
        switch (type){
            case R: return new Rook(dest, color, board, true);
            case N: return new Knight(dest, color, board, true);
            case B: return new Bishop(dest, color, board, true);
            case Q: return new Queen(dest, color, board, true);
            case K: return new King(dest, color, board, true);
            case P:
                if(color == PColor.W && dest.row == 0){
                    return new Queen(dest, color, board, true);
                } else if(color == PColor.B && dest.row == 7){
                    return new Queen(dest, color, board, true);
                } else {
                    return new Pawn(dest, color, board, true);
                }
            default: throw new IllegalArgumentException("invalid argument");
        }
    }

    public Boolean isLegalMove(Square dest){
        if(!(isValidMove(dest) || validSituationalMove(dest))) return false;
        if(!noCollision(dest)) return false;

        if(board.testMove(this, dest).isUnderCheck()) return false;

        return true;
    }

    public abstract Boolean isValidMove(Square dest); // standard piece moves e.g. bishop moves in diagonals
    public Boolean validSituationalMove(Square dest){
        return false;
    } //enPassant & castling

    public Boolean noCollision(Square dest){
        return destSquareAvailable(dest) && squaresBetweenAreEmpty(dest);
    }
    public Boolean destSquareAvailable(Square dest){
        if(type == PieceType.P && pos.col == dest.col){//pawn moving straight
            return board.isEmpty(dest);
        }
        else{
            return !board.isFriendly(dest);
        }
    }
    public Boolean squaresBetweenAreEmpty(Square dest){
        if(type == PieceType.N) return true;


        ArrayList<Square> SquaresBetween = pos.getSquaresBetween(dest); //return squares between pos-dest
        for(Square s: SquaresBetween){
            if(!board.isEmpty(s)){
                System.out.println("NE: sRow: " + s.row + " sCol: " + s.col);
                return false;
            }
        }

        return true;
    }

    public Boolean isDiagonalPawnMove(Square dest){
        if(type != PieceType.P) return false;

        int dr = dest.row - pos.row;
        int dc = dest.col - pos.col;

        if(getColor() == PColor.W){
            return (Math.abs(dc) == 1 && dr == -1);
        } else {
            return (Math.abs(dc) == 1 && dr == 1);
        }
    }

    public Boolean attacks(Square dest){
        if(type == PieceType.P) return isDiagonalPawnMove(dest);
        else return (isValidMove(dest) && squaresBetweenAreEmpty(dest));
    }

    public ArrayList<Square> getSquaresAttacking(){
        ArrayList<Square> squares = new ArrayList<>();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Square s = new Square(r, c);
                if(attacks(s)) squares.add(s);
            }
        }
        return squares;
    }
    public ArrayList<Square> getLegalMoves(){
        ArrayList<Square> squares = new ArrayList<>();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Square s = new Square(r, c);
                if(isLegalMove(s)) squares.add(s);
            }
        }
        return squares;
    }
}
