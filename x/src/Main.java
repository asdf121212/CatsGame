import javax.swing.*;


public class Main {

    private GameController gameController;
    //private MazeController mazeController;

    public Main() {
        gameController = new GameController();
        //mazeController = new MazeController();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }


}
