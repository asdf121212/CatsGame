import com.sun.javafx.scene.control.behavior.KeyBinding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

public class Level1 extends JPanel {

    private DisplayList displayList;

    private Timer moveTimer;


    public Level1() {

        setPreferredSize(new Dimension(800, 500));

        displayList = new DisplayList();
        setBackground(Color.BLACK);

        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    displayList.cat.IncrementXY(0, 5);
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    displayList.cat.IncrementXY(0, -5);
                    repaint();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                    displayList.cat.IncrementXY(-5, 0);
//                    repaint();
                    if (moveTimer == null || !moveTimer.isRunning()) {
                        moveTimer = new Timer(50, moveLeft);
                        moveTimer.setInitialDelay(0);/////////////////////
                        moveTimer.start();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    //displayList.cat.IncrementXY(5, 0);
                    //repaint();
                    if (moveTimer == null || !moveTimer.isRunning()) {
                        moveTimer = new Timer(50, moveRight);
                        moveTimer.setInitialDelay(0);/////////////////////
                        moveTimer.start();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (moveTimer != null) {
                    moveTimer.stop();
                }
            }
        });

    }

    ActionListener moveRight = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(10, 0);
            repaint();
        }
    };
    ActionListener moveLeft = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(-10, 0);
            repaint();
        }
    };


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        displayList.cat.paintComponent(g2);

    }


//    public void CatMove(int dx, int dy) {
//        displayList.cat.IncrementXY(dx, dy);
//    }


}
