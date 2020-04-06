import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;


public class Main {

    private javax.swing.JFrame frame;
    private JPanel currentPanel;

    public Main() {
        Initialize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());

    }


    private void Initialize() {

        frame = new JFrame();
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDimensions.width / 2 - 350, screenDimensions.height / 2 - 250);
        frame.setSize(800, 500);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
        frame.setResizable(false);


        ///////////////////////////////////////
        Level1 level = new Level1();
        frame.add(level, BorderLayout.CENTER);
        level.setVisible(true);
        frame.pack();
        frame.repaint();
        frame.revalidate();

        ///////////////////////////////////////

    }



}
