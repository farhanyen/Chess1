import Models.*;
import Models.Pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardPanel extends JPanel {

    private ChessBoard board;
    private Piece currentPiece = null;
    private int curx, cury;

    private ImagesLoader imsLoader;
    private BoardRenderer renderer;

    private Image dbImage;
    private Graphics dbg;

    public BoardPanel(){
        board = new ChessBoard();
        imsLoader = new ImagesLoader();
        renderer = new BoardRenderer(imsLoader);


        setPreferredSize(new Dimension(ChessBoard.BOARDLENGTH, ChessBoard.BOARDLENGTH));
        setFocusable(true);
        requestFocus();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressPiece(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dropPiece(e);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dragPiece(e);
            }
        });

    }

    private void pressPiece(MouseEvent e){
        Square s = Square.getSquare(e.getX(), e.getY());
        Piece piece = board.get(s.row, s.col);

        if(piece == null || !board.isPlayingColor(piece)) return;
        else currentPiece = piece;

        curx = e.getX(); cury = e.getY();
        redraw();
    }

    private void dragPiece(MouseEvent e){
        if(currentPiece == null) return;

        curx = e.getX(); cury = e.getY();
        redraw();
    }

    private void dropPiece(MouseEvent e){
        if(currentPiece == null) return;

        Square s = Square.getSquare(e.getX(), e.getY());
        if(board.isMoveLegal(currentPiece, s)){
            board.playMove(currentPiece, s);
        }

        currentPiece = null;
        curx = 0; cury = 0;
        redraw();
    }

    public void redraw() {
        boardRender();
        paintBoard();
    }

    private void paintBoard() {
        try{
            Graphics g = this.getGraphics();
            if(g != null && dbImage != null){
                g.drawImage(dbImage, 0, 0, null);
                g.dispose();
            }
        } catch(Exception e){
            System.out.println("Graphics context error: " + e);
        }

    }

    private void boardRender() {
        dbImage = createImage(ChessBoard.BOARDLENGTH, ChessBoard.BOARDLENGTH);
        if(dbImage != null){
            dbg = dbImage.getGraphics();
        }
        else{
            System.out.println("Error creating Panel Image");
        }

        drawBoard(dbg);
        drawPieces(dbg);
    }

    private void drawBoard(Graphics g){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Square s = new Square(r, c);
                renderer.drawSquare(s, g);
            }
        }
    }

    private void drawPieces(Graphics g){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Piece piece = board.get(r, c);
                if(piece != null && piece != currentPiece){
                    renderer.drawPiece(piece, g);
                }
            }
        }
        if(currentPiece != null){
            renderer.drawPieceAtCursor(currentPiece, curx, cury, g);
        }
    }
}
