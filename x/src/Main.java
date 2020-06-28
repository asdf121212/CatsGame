import javax.swing.*;


public class Main extends java.applet.Applet {

    private GameController gameController;
    //private MazeController mazeController;

    public Main() {
        gameController = new GameController();
        //mazeController = new MazeController();
        SwingUtilities.invokeLater(Main::new);
    }

    public static void main(String[] args) {
        //SwingUtilities.invokeLater(Main::new);
    }


}
