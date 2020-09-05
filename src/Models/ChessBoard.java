package Models;

import Models.Pieces.*;

public class ChessBoard {

    public static final int BOARDLENGTH = 600;

    private Piece[][] board;
    private PColor playingColor;
    //private GameState state;

    public ChessBoard(){
        playingColor = PColor.W;
        initBoard();
        //state = new GameState(this);
    }

    public void initBoard(){
        board = new Piece[8][8];
        this.board[0][0] = new Rook(new Square(0, 0), PColor.B, this);
        this.board[0][1] = new Knight(new Square(0, 1), PColor.B, this);
        this.board[0][2] = new Bishop(new Square(0, 2), PColor.B, this);
        this.board[0][3] = new Queen(new Square(0, 3), PColor.B, this);
        this.board[0][4] = new King(new Square(0, 4), PColor.B, this);
        this.board[0][5] = new Bishop(new Square(0, 5), PColor.B, this);
        this.board[0][6] = new Knight(new Square(0, 6), PColor.B, this);
        this.board[0][7] = new Rook(new Square(0, 7), PColor.B, this);

        for(int col = 0; col < 8; col++){
            this.board[1][col] = new Pawn(new Square(1, col), PColor.B, this);
        }

        for(int col = 0; col < 8; col++){
            this.board[6][col] = new Pawn(new Square(6, col), PColor.W, this);
        }

        this.board[7][0] = new Rook(new Square(7, 0), PColor.W, this);
        this.board[7][1] = new Knight(new Square(7, 1), PColor.W, this);
        this.board[7][2] = new Bishop(new Square(7, 2), PColor.W, this);
        this.board[7][3] = new Queen(new Square(7, 3), PColor.W, this);
        this.board[7][4] = new King(new Square(7, 4), PColor.W, this);
        this.board[7][5] = new Bishop(new Square(7, 5), PColor.W, this);
        this.board[7][6] = new Knight(new Square(7, 6), PColor.W, this);
        this.board[7][7] = new Rook(new Square(7, 7), PColor.W, this);
    }

    public Piece get(int r, int c){
        return board[r][c];
    }

    private Piece[][] getBoardCopy(){
        Piece[][] boardCopy = new Piece[8][8];
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                boardCopy[r][c] = board[r][c];
            }
        }
        return boardCopy;
    }

    private Piece[][] getBoardCopy(Piece[][] board, ChessBoard newBoard){
        Piece[][] boardCopy = new Piece[8][8];
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece piece = board[r][c];
                if(piece != null)
                    boardCopy[r][c] = piece.newChessBoard(newBoard);
            }
        }
        return boardCopy;
    }

    public ChessBoard(Piece[][] board, PColor playingColor) {
        this.board = getBoardCopy(board, this);
        this.playingColor = playingColor;
    }

    public ChessBoard testMove(Piece piece, Square dest){
        ChessBoard testBoard = new ChessBoard(board, playingColor);
        testBoard.makeMove(piece, dest);
        return testBoard;
    }

    //public GameState state(){ return state; }



    public Boolean isEmpty(Square dest){
        return board[dest.row][dest.col] == null;
    }

    public Boolean isFriendly(Square dest){
        if(isEmpty((dest))) return false;

        Piece other = board[dest.row][dest.col];
        return other.getColor() == playingColor;
    }

    public Boolean isEnemy(Square dest){
        if(isEmpty((dest))) return false;

        Piece other = board[dest.row][dest.col];
        if(other.getColor() != playingColor)
            return true;
        else return false;
    }

    public Boolean isAttacked(Square dest){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece piece = board[r][c];
                if(piece == null) continue;
                if(!isPlayingColor(piece) && piece.attacks(dest)){
                    System.out.println(String.format("(%s, %s) attacked by (%s, %s)", dest.row, dest.col, r, c));
                    return true;
                }
            }
        }
        return false;
    }



    public Boolean isUnderCheck(){
        Piece king = getPlayingKing();
        return isAttacked(king.getSquare());
    }

    public Piece getPlayingKing(){
        System.out.println("playing color: " + playingColor.name());
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece piece = board[r][c];
                if(piece == null) continue;
                //System.out.println(String.format("Row: %d Col: %d PieceType: %s Color: %s" ,r ,c ,piece.getType().name() ,piece.getColor().name()));
                if(piece.getType() == PieceType.K && isPlayingColor(piece)){
                    System.out.println("Found King!");
                    System.out.println(String.format("PieceType: %s Color: %s",piece.getType().name() , piece.getColor().name()));
                    return piece;
                }
            }
        }
        throw new IllegalArgumentException("No king found");
    }

    public Boolean isPlayingColor(Piece piece){
        return playingColor == piece.getColor();
    }



    public void playMove(Piece piece, Square dest){
        if(!isMoveLegal(piece, dest)) return;

        makeMove(piece, dest);

        updateTurn();
        //state = state.update(piece, dest);
    }

    public Boolean isMoveLegal(Piece piece, Square dest){
        return piece.isLegalMove(dest);
    }

    public void makeMove(Piece piece, Square dest){
        movePiece(piece, dest);
        moveCastlingRook(piece, dest);
        //enPassantCapture(piece, dest);
    }

    private void movePiece(Piece piece, Square dest){
        Piece[][] boardCopy = getBoardCopy();
        boardCopy[piece.getSquare().row][piece.getSquare().col] = null;
        boardCopy[dest.row][dest.col] = piece.move(dest);
        board = boardCopy;
    }

    private void moveCastlingRook(Piece king, Square dest){
        Models.Square pos = king.getSquare();
        int dc = dest.col - pos.col;

        if(!(king.getType() == PieceType.K && Math.abs(dc) == 2)) return;

        Piece rook = (dc < 0) ? board[pos.row][0] : board[pos.row][7];
        Models.Square target = pos.getSquaresBetween(dest).get(0);
        movePiece(rook, target);
    }

    private void enPassantCapture(Piece pawn, Square dest){
        Square pos = pawn.getSquare();
        int dc = dest.col - pos.col; int dr = dest.row - pos.row;

        if(pawn.getType() != PieceType.P) return;
        if(!(Math.abs(dc) == 1 && Math.abs(dr) == 1)) return;

        board = getBoardCopy();
        board[pos.row][dest.col] = null;
    }

    public void updateTurn(){
        playingColor = playingColor.next();
    }


}
