import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.*;

public class GameController {

    private Level currentLevel;
    private int extraLives = 3;
    private int levelIndex = 0;
    private Class[] levelClasses = new Class[] { Level1.class, Level2.class };

    private KeyAdapter keyAdapter;
    private Timer leftTimer;
    private Timer rightTimer;
    private Timer jumpTimer;

    private Timer updateTimer;

    private float vel_y = 0;
    private DisplayList displayList;
    private boolean spaceReleased = true;

    private ViewController viewController;

    public GameController() {
        currentLevel = new Level1();
        viewController = new ViewController();
        SwingUtilities.invokeLater(() -> InitializeLevel(currentLevel));
    }

    ActionListener updateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            currentLevel.update();
            if (displayList.cat.Dying) {
                if (rightTimer != null && rightTimer.isRunning()) {
                    rightTimer.stop();
                }
                if (leftTimer != null && leftTimer.isRunning()) {
                    leftTimer.stop();
                }
                if (jumpTimer != null && jumpTimer.isRunning()) {
                    jumpTimer.stop();
                }
            } else if (displayList.cat.Dead) {
                cat_die();
            } else if (currentLevel.hasReachedNextLevel()) {
                advance_levels();
            }
        }
    };

    private void InitializeLevel(Level level) {
        level.setNumLives(extraLives);
        viewController.changeLevel(level);
        currentLevel = level;
        //currentLevel.addMouseListener(mouseAdapter);////////////////////////???????
        displayList = currentLevel.displayList;
        updateTimer = new Timer(5, updateListener);
        updateTimer.setInitialDelay(1);
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
        viewController.StartRepaintTimer();
        updateTimer.start();
        viewController.revalidateFrame();
    }

    private void advance_levels() {
        if (levelIndex < levelClasses.length - 1) {
            levelIndex++;
            try {
                Level nextLevel = (Level)levelClasses[levelIndex].newInstance();
                InitializeLevel(nextLevel);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            return;
        }
    }

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
        updateTimer.stop();
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
            try {
                //Level1 level = new Level1();
                Level level = currentLevel.getClass().newInstance();
                SwingUtilities.invokeLater(() -> InitializeLevel(level));
            }
            catch (Exception ex) {
                System.out.println("could not instantiate new level");
            }
        }
    }

    private void GameOver() {
        AutoResetSound s = new AutoResetSound("SoundFiles/gameOver.wav");
        //if (currentLevel != null) {
            //frame.remove(currentLevel);
            //currentLevel.removeKeyListener(keyAdapter);
        //}
        GameOverPanel gameOverPanel = new GameOverPanel();
        viewController.changeLevel(gameOverPanel);
        currentLevel.removeKeyListener(keyAdapter);
        //frame.add(gameOverPanel);
        //frame.pack();
        s.Start();

        gameOverPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameOverPanel.clickedQuit(e.getX(), e.getY())) {
                    System.exit(0);
                }else if (gameOverPanel.clickedRetry(e.getX(), e.getY())) {
                    extraLives = 3;
                    Level level = new Level1();
                    //frame.remove(gameOverPanel);
                    InitializeLevel(level);
                }
            }
        });
        gameOverPanel.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                gameOverPanel.mouseMove(e.getX(), e.getY());
            }
        });
    }

//    MouseAdapter mouseAdapter = new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            currentLevel.requestFocus();
//            currentLevel.mouseClick(e.getX(), e.getY());
//        }
//    };


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
            if (displayList.cat.GetY() >= currentLevel.getGroundLevel(displayList.cat.GetX(), displayList.cat.GetY())) {
                displayList.cat.SetY(350);
                displayList.cat.state = (byte)(displayList.cat.state & 011);
                jumpTimer.stop();
            }
        }
    };


}
