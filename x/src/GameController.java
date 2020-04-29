import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class GameController {

    private Level currentLevel;
    private int extraLives = 3;
    private int levelIndex = 0;
    private Class[] levelClasses = new Class[] { Level1.class, Level2.class };

    private KeyAdapter keyAdapter;

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
            double floorCheckX1 = displayList.cat.GetX() + 20;
            double floorCheckX2 = displayList.cat.GetX() + 55;
            double wallCheckX1 = displayList.cat.GetX();
            double wallCheckX2 = displayList.cat.GetX() + 75;
            RoundRectangle2D floor_Rect = null;
            RoundRectangle2D wall_Rect = null;
            for (RoundRectangle2D floor : currentLevel.getFloors()) {
                if (floor.contains(floorCheckX1, displayList.cat.GetY() + 51 + cat_Vy)
                || floor.contains(floorCheckX2, displayList.cat.GetY() + 51 + cat_Vy)) {
                    floor_Rect = floor;
                }
            }
            for (RoundRectangle2D wall : currentLevel.getWalls()) {
                if (wall.contains(wallCheckX1, displayList.cat.GetY() + 25) && cat_Vx < 0) {
                    wall_Rect = wall;
                } else if (wall.contains(wallCheckX2, displayList.cat.GetY() + 25) && cat_Vx > 0) {
                    wall_Rect = wall;
                }
            }
            if (floor_Rect == null || cat_Vy < 0) {
                displayList.cat.state = (byte)(displayList.cat.state | 100);
                cat_Vy += Gravity;
            } else {
                if (cat_Vy > 0) {
                    //make sure it's level with floor
                    displayList.cat.SetY((int)Math.round(floor_Rect.getY()) - 50);
                }
                cat_Vy = 0;
                displayList.cat.state = (byte)(displayList.cat.state & 011);
            }
            if (wall_Rect != null) {
                cat_Vx = 0;
            }
            ///////////////////////////////////////////////////////////////////////////////////

            if (!displayList.cat.Dying) {
                displayList.cat.tryIncrementXY(cat_Vx, cat_Vy);
            }

            currentLevel.update();

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
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        cat_Vx = -2;//////
                        displayList.cat.state = (byte)(displayList.cat.state | 011);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        cat_Vx = 2;
                        displayList.cat.state = (byte)((displayList.cat.state & 110) | 010);
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
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (cat_Vx > 0) {
                        cat_Vx = 0;
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
                updateTimer.stop();
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
        GameOverPanel gameOverPanel = new GameOverPanel();
        viewController.changeLevel(gameOverPanel);
        currentLevel.removeKeyListener(keyAdapter);
        s.Start();

        gameOverPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameOverPanel.clickedQuit(e.getX(), e.getY())) {
                    System.exit(0);
                }else if (gameOverPanel.clickedRetry(e.getX(), e.getY())) {
                    updateTimer.stop();
                    extraLives = 3;
                    Level level = new Level1();
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


}
