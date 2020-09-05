import Models.Pieces.Piece;
import Models.Square;

import java.awt.*;

public class BoardRenderer {

    private ImagesLoader imsLoader;

    public BoardRenderer(ImagesLoader imsLoader){
        this.imsLoader = imsLoader;
    }

    public void drawSquare(Square s, Graphics g){
        String imageName;
        if((s.row + s.col) % 2 == 0) imageName = "whiteSquare";
        else imageName = "blackSquare";

        Image squareImage = imsLoader.getImage(imageName);
        g.drawImage(squareImage, s.locx, s.locy, null);
    }

    private Image getPieceImage(Piece piece){
        String imageName = piece.getColor().toString() + "_" + piece.getType().toString();
        return imsLoader.getImage(imageName);
    }

    public void drawPiece(Piece piece, Graphics g){
        Image pieceImage = getPieceImage(piece);
        Square s = piece.getSquare();

        int locx = s.locx + (Square.LENGTH - pieceImage.getWidth(null))/2;
        int locy = s.locy + (Square.LENGTH - pieceImage.getHeight(null))/2;
        g.drawImage(pieceImage, locx, locy, null);
    }

    public void drawPieceAtCursor(Piece piece, int cursorx, int cursory, Graphics g){
        Image pieceImage = getPieceImage(piece);

        int locx = cursorx - pieceImage.getWidth(null)/2;
        int locy = cursory - pieceImage.getHeight(null)/2;
        g.drawImage(pieceImage, locx, locy, null);
    }

}
