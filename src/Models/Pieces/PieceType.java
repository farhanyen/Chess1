package Models.Pieces;

public enum PieceType {
    R, N, B, Q, K, P;
    public String toString(){
        switch(this){
            case R: return "rook";
            case N: return "knight";
            case B: return "bishop";
            case Q: return "queen";
            case K: return "king";
            case P: return "pawn";
            default: throw new IllegalArgumentException();
        }
    }
}


