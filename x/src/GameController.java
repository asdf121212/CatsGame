import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.*;

public class GameController {

    private Level currentLevel;
    private int extraLives = 3;
    private int levelIndex = 0;
    private Class[] levelClasses = new Class[] { Level1.class, Level2.class };

    private KeyAdapter keyAdapter;
    //private Timer leftTimer;
    //private Timer rightTimer;
    //private Timer jumpTimer;

    private Timer updateTimer;

    private float cat_Vy = 0;
    private double cat_Vx = 0;
    private double Gravity = 0.20;

    private DisplayList displayList;
    private boolean spaceReleased = true;

    private ViewController viewController;

    public GameController() {
        currentLevel = new Level1();
        //currentLevel = new Level2();/////////////////////for development purposes
        viewController = new ViewController();
        SwingUtilities.invokeLater(() -> InitializeLevel(currentLevel));
    }

    ActionListener updateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            ///////////////////////////////////////////////////////////////////////////////////
            double checkX;
            if ((displayList.cat.state | 110) == 111) {
                checkX = displayList.cat.GetX() + 60 + cat_Vx;
            } else {
                checkX = displayList.cat.GetX() + 25 + cat_Vx;
            }
            double floorY;
            if (cat_Vy >= 0) {
                 floorY = (currentLevel).nearestFloorY(checkX, displayList.cat.GetY() + 50 + cat_Vy);
            } else {
                floorY = -1;
            }//////////should change how this works.
            if (floorY == -1) {
                displayList.cat.state = (byte)(displayList.cat.state | 100);
                cat_Vy += Gravity;
            } else {
                if (cat_Vy > 0) {
                    displayList.cat.SetY((int)Math.round(floorY) - 50);
                }
                cat_Vy = 0;
                displayList.cat.state = (byte)(displayList.cat.state & 011);
                if (Math.abs(cat_Vx) > 0) {
                    displayList.cat.state = (byte)(displayList.cat.state | 010);
                }
            }
            ///////////////////////////////////////////////////////////////////////////////////
            if (!displayList.cat.Dying) {
                displayList.cat.tryIncrementXY(cat_Vx, cat_Vy);
            }


            currentLevel.update();

            //if (displayList.cat.Dying) {
//                if (rightTimer != null && rightTimer.isRunning()) {
//                    rightTimer.stop();
//                }
//                if (leftTimer != null && leftTimer.isRunning()) {
//                    leftTimer.stop();
//                }
//                if (jumpTimer != null && jumpTimer.isRunning()) {
//                    jumpTimer.stop();
//                }
            //}
            if (displayList.cat.Dead) {
                cat_die();
            } else if (currentLevel.hasReachedNextLevel()) {
                advance_levels();
            }
        }
    };

    private void InitializeLevel(Level level) {
        level.setNumLives(extraLives);
        cat_Vx = 0;
        cat_Vy = 0;
        viewController.changeLevel(level);
        currentLevel = level;
        //currentLevel.addMouseListener(mouseAdapter);/////////////////////////for development only
        displayList = currentLevel.displayList;
        updateTimer = new Timer(5, updateListener);
        updateTimer.setInitialDelay(1);
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (cat_Vy == 0) {
                        cat_Vy = -6;
                        displayList.cat.state = (byte)(displayList.cat.state | 100);
                    }
                    //if (jumpTimer == null || !jumpTimer.isRunning()) {
                        //jumpTimer = new Timer(10, fallAction);
                        //jumpTimer.setInitialDelay(0);
                        //jumpTimer.start();
                    //}
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    //if (leftTimer == null || !leftTimer.isRunning()) {
                        //leftTimer = new Timer(10, moveLeft);
                        cat_Vx = -2;//////
                        //leftTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)(displayList.cat.state | 011);
                        //leftTimer.start();
                    //}
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    //if (rightTimer == null || !rightTimer.isRunning()) {
                        //rightTimer = new Timer(10, moveRight);
                        cat_Vx = 2;
                        //rightTimer.setInitialDelay(0);
                        displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
                        //rightTimer.start();
                    //}
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
                    spaceReleased = false;
                    displayList.AddFluffball(displayList.cat.generateFluffball());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (cat_Vx < 0) {
                        cat_Vx = 0;
                        displayList.cat.state = (byte)(displayList.cat.state & 101);
                    }
                    //if (leftTimer != null) {
                        //leftTimer.stop();
                        //displayList.cat.state = (byte)(displayList.cat.state & 101);
                    //}
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (cat_Vx > 0) {
                        cat_Vx = 0;
                        displayList.cat.state = (byte)(displayList.cat.state & 101);
                    }
                    //if (rightTimer != null) {
                        //rightTimer.stop();
                        //displayList.cat.state = (byte)(displayList.cat.state & 101);
                    //}
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
                int health = displayList.cat.getHealth();
                currentLevel.removeKeyListener(keyAdapter);
                updateTimer.stop();
                Level nextLevel = (Level)levelClasses[levelIndex].newInstance();
                nextLevel.displayList.cat.setHealth(health);
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
//        if (rightTimer != null && rightTimer.isRunning()) {
//            rightTimer.stop();
//        }
//        if (leftTimer != null && leftTimer.isRunning()) {
//            leftTimer.stop();
//        }
//        if (jumpTimer != null && jumpTimer.isRunning()) {
//            jumpTimer.stop();
//        }
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
                //extraLives = 3;
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

    //development method to see where to place stuff
    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(String.format("x:  %d    y:  %d", e.getX(), e.getY()));
        }
    };

//    private void startFalling(double initialYVelocity) {
//
//    }

//    ActionListener moveRight = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayList.cat.tryIncrementXY(3, 0);
//            if (displayList.cat.state >> 2 == 0) {
//                displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
//            }
//        }
//    };
//    ActionListener moveLeft = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayList.cat.tryIncrementXY(-3, 0);
//            if (displayList.cat.state >> 2 == 0) {
//                displayList.cat.state = (byte)(displayList.cat.state | 011);
//            }
//        }
//    };
//    ActionListener fallAction = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            displayList.cat.tryIncrementXY(0, Math.round(cat_Vy));
//            cat_Vy += currentLevel.getGRAV();
//            if (displayList.cat.GetY() >= currentLevel.getGroundLevel(displayList.cat.GetX(), displayList.cat.GetY())) {
//                displayList.cat.SetY(currentLevel.getGroundLevel(displayList.cat.GetX(), displayList.cat.GetY()));
//                displayList.cat.state = (byte)(displayList.cat.state & 011);
//                jumpTimer.stop();
//            }
//        }
//    };


}
