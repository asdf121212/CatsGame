import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class ViewController {

    private javax.swing.JFrame frame;
    private Level currentLevel;

    private Timer repaintTimer;

    private DisplayList displayList;

    public ViewController() {
        InitializeFrame();
    }

    private void InitializeFrame() {
        frame = new JFrame();
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDimensions.width / 2 - 600, screenDimensions.height / 2 - 350);
        frame.setSize(1200, 700);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    ActionListener repaintListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentLevel.repaint();
        }
    };

    public void revalidateFrame() {
        frame.revalidate();
    }


    public void changeLevel(Level newLevel) {
        if (currentLevel != null) {
            frame.remove(currentLevel);
        }
        currentLevel = newLevel;
        displayList = newLevel.displayList;
        frame.add(newLevel);
        frame.pack();
    }
//    public void StartRepaintTimer() {
//        if (repaintTimer != null) {
//            repaintTimer.stop();
//        }
//        repaintTimer = new Timer(5, repaintListener);
//        repaintTimer.start();
//    }


}
