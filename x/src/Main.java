import javax.swing.*;


public class Main {

    private GameController gameController;

    public Main() {
        gameController = new GameController();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }


}
