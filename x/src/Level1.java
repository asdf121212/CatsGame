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

    private Timer leftTimer;
    private Timer rightTimer;
    private Timer jumpTimer;

    //private int dx = 0;
    private float vel_y = 0;
    private final float GRAV = 0.5f;
    private int GroundLevel = 350;

    public Level1() {

        setPreferredSize(new Dimension(1200, 700));

        displayList = new DisplayList();
        setBackground(Color.BLACK);


        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (jumpTimer == null || !jumpTimer.isRunning()) {
                        jumpTimer = new Timer(10, jumpAction);
                        jumpTimer.setInitialDelay(0);
                        vel_y = -10;
                        displayList.cat.state = (byte)(displayList.cat.state | 100);//aaaaaaaaaaaaa
                        jumpTimer.start();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (leftTimer == null || !leftTimer.isRunning()) {
                        leftTimer = new Timer(10, moveLeft);
                        leftTimer.setInitialDelay(0);/////////////////////
                        displayList.cat.state = (byte)(displayList.cat.state | 011);//aaaaaaaaaaaaa
                        leftTimer.start();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    if (rightTimer == null || !rightTimer.isRunning()) {
                        rightTimer = new Timer(10, moveRight);
                        rightTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);//aaaaaaaaaaaaa
                        rightTimer.start();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //check that it's in the right direction
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (leftTimer != null) {
                        leftTimer.stop();
                        displayList.cat.state = (byte)(displayList.cat.state & 101);//aaaaaaaaaaaaa
                        repaint();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (rightTimer != null) {
                        rightTimer.stop();
                        displayList.cat.state = (byte)(displayList.cat.state & 101);//aaaaaaaaaaaaa
                        repaint();
                    }
                }
//                if (moveTimer != null) {
//                    if (moveTimer.getActionListeners()[0].equals(moveRight) && e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                        moveTimer.stop();
//                    } else if (moveTimer.getActionListeners()[0].equals(moveLeft) && e.getKeyCode() == KeyEvent.VK_LEFT) {
//                        moveTimer.stop();
//                    }
//                }
            }
        });

    }

    ActionListener moveRight = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(3, 0);
            if (displayList.cat.state >> 2 == 0) {
                displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);//aaaaaaaaaaaaa
            }
            repaint();
        }
    };
    ActionListener moveLeft = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(-3, 0);
            if (displayList.cat.state >> 2 == 0) {
                displayList.cat.state = (byte)(displayList.cat.state | 011);//aaaaaaaaaaaaa
            }
            repaint();
        }
    };
    ActionListener jumpAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(0, Math.round(vel_y));
            vel_y += GRAV;
            repaint();
            if (displayList.cat.GetY() >= GroundLevel) {
                displayList.cat.SetY(350);
                displayList.cat.state = (byte)(displayList.cat.state & 011);//aaaaaaaaaaaaa
                jumpTimer.stop();
            }
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
