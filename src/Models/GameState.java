package Models;

import Models.Pieces.Piece;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

    private final ChessBoard board;
    private final ArrayList<Pair<Piece, Square>> movesList;
    private final HashMap<Piece, ArrayList<Square>> possibleMoves;

    public GameState (ChessBoard board){
        this.board = board;
        this.movesList = new ArrayList<>();
        this.possibleMoves = findPossibleMoves();
    }

    public GameState(ChessBoard board, ArrayList<Pair<Piece, Square>> movesList){
        this.board = board;
        this.movesList = movesList;
        this.possibleMoves = findPossibleMoves();
    }

    private HashMap<Piece, ArrayList<Square>> findPossibleMoves(){
        HashMap<Piece, ArrayList<Square>> possibleMoves = new HashMap<>();

        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece piece = board.get(r, c);
                if(piece == null || !board.isPlayingColor(piece)) continue;
                ArrayList<Square> moves = piece.getLegalMoves();
                if(!moves.isEmpty()) possibleMoves.put(piece, moves);
            }
        }
        return possibleMoves;
    }

    public ArrayList<Pair<Piece, Square>> getMovesList() {
        return movesList;
    }

    public Pair<Piece, Square> getLastMove(){
        if(movesList.size() == 0){
            System.out.println("No last move");
            return null;
        }
        return movesList.get(movesList.size() - 1);
    }

    public HashMap<Piece, ArrayList<Square>> getPossibleMoves() {

        return possibleMoves;
    }

    public Boolean isCheckMate(){
        return (board.isUnderCheck() && possibleMoves.isEmpty());
    }

    public Boolean isStalemate(){
        return(!board.isUnderCheck() && possibleMoves.isEmpty());
    }

    public GameState update(Piece piece, Square square){
        Pair<Piece, Square> move = new Pair<>(piece, square);
        ArrayList<Pair<Piece, Square>> newList = new ArrayList<>(movesList);
        newList.add(move);

        return new GameState(board, newList);
    }

}
