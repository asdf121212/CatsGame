import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;


public class Main {


    private javax.swing.JFrame frame;
    private Level currentLevel;
    private int extraLives = 4;

    private KeyAdapter keyAdapter;
    private Timer leftTimer;
    private Timer rightTimer;
    private Timer jumpTimer;
    private Timer repaintTimer;
    private Timer hitCheckTimer;

    private int tickCount = 180;
    private float vel_y = 0;
    private DisplayList displayList;
    private boolean spaceReleased = true;

    public Main() {
        Initialize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }


    private void Initialize() {

        frame = new JFrame();
        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDimensions.width / 2 - 600, screenDimensions.height / 2 - 350);
        frame.setSize(800, 500);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        currentLevel = new Level1();
        InitializeLevel(currentLevel);
        //frame.add(currentLevel, BorderLayout.CENTER);
        //currentLevel.setVisible(true);
        //frame.pack();
        //frame.repaint();
        //frame.revalidate();


    }

    ActionListener hitCheckListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (displayList.getEnemies().size() == 1 && !displayList.getEnemies().get(0).Dying) {
                Squirrel squirrel = (Squirrel) displayList.getEnemies().get(0);/////////not good structure- should refactor
                if (tickCount == 220) {
                    SwingUtilities.invokeLater(() -> displayList.AddDanger(squirrel.generateBall(-5)));
                    tickCount = 0;
                } else {
                    tickCount++;
                }
            }
            for (Enemy enemy : displayList.getEnemies()) {
                if (enemy.Dead) {
                    SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
                    continue;
                } else if (enemy.Dying) {
                    continue;
                }
                for (Fluffball fluffball : displayList.getFluffballs()) {
                    if (fluffball.getHitBox().intersects(enemy.getHitBox())) {
                        fluffball.stop();
                        SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
                        SwingUtilities.invokeLater(enemy::entityHit);
                    }
                }
            }
            for (Danger danger : displayList.getDangers()) {
                if (danger.Dead) {
                    SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
                } else if (danger.Dying) {

                } else {
                    if (danger.getHitBox().intersects(displayList.cat.getHitBox())) {
                        SwingUtilities.invokeLater(displayList.cat::catHit);
                        danger.hitTarget();
                    }
                }
            }
            //if (displayList.cat.Dying) {
            ///game over
            //hitCheckTimer.stop();
            //displayList.clearDynamics();

            //} else if (displayList.cat.Dead) {
            if (displayList.cat.Dead) {
                cat_die();
            }
        }
    };

    private void cat_die() {
        currentLevel.removeKeyListener(keyAdapter);
        if (rightTimer != null && rightTimer.isRunning()) {
            rightTimer.stop();
        }
        if (leftTimer != null && leftTimer.isRunning()) {
            leftTimer.stop();
        }
        if (jumpTimer != null && jumpTimer.isRunning()) {
            jumpTimer.stop();
        }
        hitCheckTimer.stop();
        displayList.cat = null;
        for (Danger danger : displayList.getDangers()) {
            if (danger.Dead || danger.Dying) {
                SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
            }
        }

        if (extraLives <= 0) {
            GameOver();
        } else {
            extraLives--;
            //SwingUtilities.invokeLater(() -> InitializeLevel(0));///////////fix
            Level1 level = new Level1();
            //try {get new instance method
            SwingUtilities.invokeLater(() -> InitializeLevel(level));///////////fix
        }
    }

    private void GameOver() {

    }

///////////////////fix constructor
    private void InitializeLevel(Level level) {
        //try {
            ////////Level sameLevel = new Level1();
            //displayList = sameLevel.displayList;
        //}
        //catch (Exception ex) {
            //couldn't load level
        //}
        if (currentLevel != null) {
            frame.remove(currentLevel);
            currentLevel.removeKeyListener(keyAdapter);
        }
        frame.add(level);
        currentLevel = level;

        displayList = currentLevel.displayList;
        hitCheckTimer = new Timer(5, hitCheckListener);
        hitCheckTimer.setInitialDelay(1);
        repaintTimer = new Timer(5, repaintListener);
        repaintTimer.start();
        hitCheckTimer.start();
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (jumpTimer == null || !jumpTimer.isRunning()) {
                        jumpTimer = new Timer(10, jumpAction);
                        jumpTimer.setInitialDelay(0);
                        vel_y = -10;
                        displayList.cat.state = (byte)(displayList.cat.state | 100);
                        jumpTimer.start();
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (leftTimer == null || !leftTimer.isRunning()) {
                        leftTimer = new Timer(10, moveLeft);
                        leftTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)(displayList.cat.state | 011);
                        leftTimer.start();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (rightTimer == null || !rightTimer.isRunning()) {
                        rightTimer = new Timer(10, moveRight);
                        rightTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
                        rightTimer.start();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
                    spaceReleased = false;
                    displayList.AddFluffball(displayList.cat.generateFluffball());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (leftTimer != null) {
                        leftTimer.stop();
                        displayList.cat.state = (byte)(displayList.cat.state & 101);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (rightTimer != null) {
                        rightTimer.stop();
                        displayList.cat.state = (byte)(displayList.cat.state & 101);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spaceReleased = true;
                }
            }
        };
        Fluffball f = displayList.cat.generateFluffball();
        f = null;
        currentLevel.addKeyListener(keyAdapter);
        currentLevel.requestFocus();
        frame.pack();
        frame.revalidate();
    }




    ActionListener repaintListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentLevel.repaint();
        }
    };
    ActionListener moveRight = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(3, 0);
            if (displayList.cat.state >> 2 == 0) {
                displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
            }
        }
    };
    ActionListener moveLeft = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(-3, 0);
            if (displayList.cat.state >> 2 == 0) {
                displayList.cat.state = (byte)(displayList.cat.state | 011);
            }
        }
    };
    ActionListener jumpAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            displayList.cat.IncrementXY(0, Math.round(vel_y));
            vel_y += currentLevel.getGRAV();
            if (displayList.cat.GetY() >= currentLevel.GroundLevel) {
                displayList.cat.SetY(350);
                displayList.cat.state = (byte)(displayList.cat.state & 011);
                jumpTimer.stop();
            }
        }
    };


}
