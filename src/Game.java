import javax.swing.*;

public class Game extends JFrame {
    private BoardPanel bp;

    private Game(){
        makeGui();
        pack();
        setResizable(false);
        setVisible(true);

        startGame();
    }

    private void makeGui(){
        bp = new BoardPanel();
        this.add(bp);
    }

    private void startGame(){
        bp.redraw();
    }

    public static void main(String args[]){
        new Game();
    }
}
