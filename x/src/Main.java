import javafx.application.Application;
import jdk.nashorn.internal.runtime.ECMAException;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;


public class Main {

    private GameController gameController;

    //private javax.swing.JFrame frame;
//    private Level currentLevel;
//    private int extraLives = 3;

//    private KeyAdapter keyAdapter;
//    private Timer leftTimer;
//    private Timer rightTimer;
//    private Timer jumpTimer;

//    private Timer repaintTimer;

//    private Timer hitCheckTimer;

//    private int tickCount = 180;
//    private float vel_y = 0;
//    private DisplayList displayList;
//    private boolean spaceReleased = true;

    public Main() {
        //Initialize();
        gameController = new GameController();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }


    //private void Initialize() {
//        frame = new JFrame();
//        Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setLocation(screenDimensions.width / 2 - 600, screenDimensions.height / 2 - 350);
//        frame.setSize(800, 500);
//        frame.setBackground(Color.BLACK);
//        frame.setVisible(true);
//        frame.setResizable(false);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //currentLevel = new Level1();
        //InitializeLevel(currentLevel);
    //}

//    ActionListener hitCheckListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            ////make the squirrel shoot balls
//            if (displayList.getEnemies().size() == 1 && !displayList.getEnemies().get(0).Dying) {
//                Squirrel squirrel = (Squirrel) displayList.getEnemies().get(0);/////////not good structure- should refactor
//                if (tickCount == 220) {
//                    SwingUtilities.invokeLater(() -> displayList.AddDanger(squirrel.generateBall(-5)));
//                    tickCount = 0;
//                } else {
//                    tickCount++;
//                }
//            }
//
//            ////check if fluffballs hit enemies, remove dead enemies--- kill cat if cat touches enemy
//            for (Enemy enemy : displayList.getEnemies()) {
//                if (enemy.Dead) {
//                    SwingUtilities.invokeLater(() -> displayList.removeEnemy(enemy));
//                    continue;
//                } else if (enemy.Dying) {
//                    continue;
//                }
//                for (Fluffball fluffball : displayList.getFluffballs()) {
//                    if (fluffball.getHitBox().intersects(enemy.getHitBox())) {
//                        fluffball.stop();
//                        SwingUtilities.invokeLater(() -> displayList.removeFluffball(fluffball));
//                        SwingUtilities.invokeLater(enemy::entityHit);
//                    }
//                }
//                if (enemy.getHitBox().intersects(displayList.cat.getHitBox())) {
//                    if (displayList.cat != null && !(displayList.cat.Dying || displayList.cat.Dead)) {
//                        SwingUtilities.invokeLater(() -> displayList.cat.catHit(200));///threw error
//                    }
//                }
//            }
//
//            ////check if projectiles hit cat, remove dead dangers
//            for (Danger danger : displayList.getDangers()) {
//                if (danger.Dead) {
//                    SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
//                } else if (danger.Dying) {
//                    continue;
//                } else {
//                    if (danger.getHitBox().intersects(displayList.cat.getHitBox())) {
//                        SwingUtilities.invokeLater(() -> displayList.cat.catHit(danger.getDamage()));//add
//                        danger.hitTarget();
//                    }
//                }
//            }
//            if (displayList.cat.Dying) {
//                if (rightTimer != null && rightTimer.isRunning()) {
//                    rightTimer.stop();
//                }
//                if (leftTimer != null && leftTimer.isRunning()) {
//                    leftTimer.stop();
//                }
//                if (jumpTimer != null && jumpTimer.isRunning()) {
//                    jumpTimer.stop();
//                }
//            }
//            if (displayList.cat.Dead) {
//                cat_die();
//            }
//        }
//    };

//    private void cat_die() {
//        currentLevel.removeKeyListener(keyAdapter);
//        if (rightTimer != null && rightTimer.isRunning()) {
//            rightTimer.stop();
//        }
//        if (leftTimer != null && leftTimer.isRunning()) {
//            leftTimer.stop();
//        }
//        if (jumpTimer != null && jumpTimer.isRunning()) {
//            jumpTimer.stop();
//        }
//        hitCheckTimer.stop();
//        displayList.cat = null;
//        for (Danger danger : displayList.getDangers()) {
//            if (danger.Dead || danger.Dying) {
//                SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
//            }
//        }
//
//        if (extraLives <= 0) {
//            GameOver();
//        } else {
//            extraLives--;
//            try {
//                //Level1 level = new Level1();
//                Level level = currentLevel.getClass().newInstance();
//                SwingUtilities.invokeLater(() -> InitializeLevel(level));
//            }
//            catch (Exception ex) {
//                System.out.println("could not instantiate new level");
//            }
//        }
//    }
//
//    private void GameOver() {
//        AutoResetSound s = new AutoResetSound("SoundFiles/gameOver.wav");
//        if (currentLevel != null) {
//            frame.remove(currentLevel);
//            currentLevel.removeKeyListener(keyAdapter);
//        }
//        GameOverPanel gameOverPanel = new GameOverPanel();
//        frame.add(gameOverPanel);
//        frame.pack();
//        s.Start();
//
//        gameOverPanel.addMouseListener(new MouseInputAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                if (gameOverPanel.clickedQuit(e.getX(), e.getY())) {
//                    System.exit(0);
//                }else if (gameOverPanel.clickedRetry(e.getX(), e.getY())) {
//                    extraLives = 3;
//                    Level level = new Level1();
//                    frame.remove(gameOverPanel);
//                    InitializeLevel(level);
//                }
//            }
//        });
//        gameOverPanel.addMouseMotionListener(new MouseInputAdapter() {
//            @Override
//            public void mouseMoved(MouseEvent e) {
//                gameOverPanel.mouseMove(e.getX(), e.getY());
//            }
//        });
//    }
//
//    MouseAdapter mouseAdapter = new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            currentLevel.requestFocus();
//            currentLevel.mouseClick(e.getX(), e.getY());
//        }
//    };

//    private void InitializeLevel(Level level) {
//        level.setNumLives(extraLives);
//        if (currentLevel != null) {
//            frame.remove(currentLevel);
//            currentLevel.removeKeyListener(keyAdapter);
//        }
//        frame.add(level);
//        currentLevel = level;
//        currentLevel.addMouseListener(mouseAdapter);
//        displayList = currentLevel.displayList;
//        hitCheckTimer = new Timer(5, hitCheckListener);
//        hitCheckTimer.setInitialDelay(1);
//        repaintTimer = new Timer(5, repaintListener);
//        repaintTimer.start();
//        hitCheckTimer.start();
//        keyAdapter = new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//
//                if (e.getKeyCode() == KeyEvent.VK_UP) {
//                    if (jumpTimer == null || !jumpTimer.isRunning()) {
//                        jumpTimer = new Timer(10, jumpAction);
//                        jumpTimer.setInitialDelay(0);
//                        vel_y = -10;
//                        displayList.cat.state = (byte)(displayList.cat.state | 100);
//                        jumpTimer.start();
//                    }
//                }
//                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                    if (leftTimer == null || !leftTimer.isRunning()) {
//                        leftTimer = new Timer(10, moveLeft);
//                        leftTimer.setInitialDelay(0);
//                        displayList.cat.state = (byte)(displayList.cat.state | 011);
//                        leftTimer.start();
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                    if (rightTimer == null || !rightTimer.isRunning()) {
//                        rightTimer = new Timer(10, moveRight);
//                        rightTimer.setInitialDelay(0);
//                        displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
//                        rightTimer.start();
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
//                    spaceReleased = false;
//                    displayList.AddFluffball(displayList.cat.generateFluffball());
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
//                    if (leftTimer != null) {
//                        leftTimer.stop();
//                        displayList.cat.state = (byte)(displayList.cat.state & 101);
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
//                    if (rightTimer != null) {
//                        rightTimer.stop();
//                        displayList.cat.state = (byte)(displayList.cat.state & 101);
//                    }
//                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                    spaceReleased = true;
//                }
//            }
//        };
//        Fluffball f = displayList.cat.generateFluffball();
//        f = null;
//        currentLevel.addKeyListener(keyAdapter);
//        currentLevel.requestFocus();
//        frame.pack();
//        frame.revalidate();
//    }




//    ActionListener repaintListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            currentLevel.repaint();
//        }
//    };


//    ActionListener moveRight = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayList.cat.IncrementXY(3, 0);
//            if (displayList.cat.state >> 2 == 0) {
//                displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
//            }
//        }
//    };
//    ActionListener moveLeft = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayList.cat.IncrementXY(-3, 0);
//            if (displayList.cat.state >> 2 == 0) {
//                displayList.cat.state = (byte)(displayList.cat.state | 011);
//            }
//        }
//    };
//    ActionListener jumpAction = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayList.cat.IncrementXY(0, Math.round(vel_y));
//            vel_y += currentLevel.getGRAV();
//            if (displayList.cat.GetY() >= currentLevel.getGroundLevel(displayList.cat.GetX(), displayList.cat.GetY())) {
//                displayList.cat.SetY(350);
//                displayList.cat.state = (byte)(displayList.cat.state & 011);
//                jumpTimer.stop();
//            }
//        }
//    };


}
