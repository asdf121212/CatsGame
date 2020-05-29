import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameController {

    protected Level currentLevel;
    protected LevelSet currentLevelSet;


    private Song[] songs = new Song[] {
        new Song("SoundFiles/Songs/Ganymede.aif", -4),
        new Song("SoundFiles/Songs/trinoculars2.aif", -5),
        new Song("SoundFiles/Songs/ancalagon.aif", -5),
        new Song("SoundFiles/Songs/Sand edit.aif", -5)
    };
    protected boolean play;
    private int songIndex = 0;

    protected KeyAdapter keyAdapter;

    protected Timer updateTimer;

    protected DisplayList displayList;
    protected Cat cat;
    protected boolean spaceReleased = true;

    protected ViewController viewController;

    private int dieWaitTicks = 0;

    public GameController() {
        //currentLevelSet = new FirstLevels();
        currentLevelSet = new MazeLevels();
        Level startLevel = currentLevelSet.getFirstLevel();
        viewController = new ViewController();
        SwingUtilities.invokeLater(() -> InitializeLevel(startLevel));

        Thread songThread = new Thread(GameController.this::playSongs);
        play = true;
        songThread.start();
    }

    public void playSongs() {
        while (play) {
            songs[songIndex].Start();
            songIndex++;
            if (songIndex >= songs.length) {
                songIndex = 0;
            }
        }
    }

    ActionListener updateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentLevel.update();
            currentLevel.repaint();
            if (cat.Dead) {
                if (dieWaitTicks < 60) {
                    dieWaitTicks++;
                } else {
                    dieWaitTicks = 0;
                    cat_die();
                }
            } else if (currentLevel.hasReachedNextLevel()) {
                advance_levels();
            }
        }
    };

    protected void InitializeLevel(Level level) {
        if (currentLevel != null) {
            currentLevel.Dispose();
        }
        viewController.changeLevel(level);
        currentLevel = level;
        //currentLevel.addMouseListener(mouseAdapter);/////////////////////////for development only
        displayList = currentLevel.displayList;
        cat = displayList.cat;

        cat.AddLevelInfo(new LevelInfo(currentLevel.floors, currentLevel.walls, null));
        //cat.enable();

        updateTimer = new Timer(5, updateListener);
        updateTimer.setInitialDelay(1);
        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (cat.Vy == 0) {
                        cat.Vy = -6;
                        cat.state = (byte)(cat.state | 100);
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        cat.Vx = -2;//////
                        cat.state = (byte)(cat.state | 011);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        cat.Vx = 2;
                        cat.state = (byte)((cat.state & 110) | 010);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
                    spaceReleased = false;
                    displayList.AddFluffball(cat.generateFluffball());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (cat.Vx < 0) {
                        cat.Vx = 0;
                        cat.state = (byte)(cat.state & 101);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (cat.Vx > 0) {
                        cat.Vx = 0;
                        cat.state = (byte)(cat.state & 101);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spaceReleased = true;
                }
            }
        };
        Fluffball f = cat.generateFluffball();
        f = null;
        Ball b = new Ball(0, 0, 50, 0, 0);
        b = null;
        currentLevel.addKeyListener(keyAdapter);
        currentLevel.requestFocus();
        //viewController.StartRepaintTimer();
        updateTimer.start();
        viewController.revalidateFrame();
    }

    protected void advance_levels() {
        if (!currentLevelSet.lastLevel()) {
            try {
                int health = cat.getHealth();
                currentLevel.removeKeyListener(keyAdapter);
                updateTimer.stop();
                Level nextLevel = currentLevelSet.getNextLevel();
                nextLevel.displayList.cat.setHealth(health);
                InitializeLevel(nextLevel);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage() + " GameController: advance levels error");
            }
        } else {
            return;
        }
    }



    protected void cat_die() {
        currentLevel.removeKeyListener(keyAdapter);
        updateTimer.stop();
        cat = null;

        if (Level.numLives <= 0) {
            currentLevel.Dispose();
            GameOver();
        } else {
            Level.numLives--;
            try {
                updateTimer.stop();
                Level level = currentLevelSet.getSameLevel();
                SwingUtilities.invokeLater(() -> InitializeLevel(level));
            }
            catch (Exception ex) {
                System.out.println("could not instantiate new level");
            }
        }
    }

    protected void GameOver() {

        play = false;
        songs[songIndex].Stop();

        GameOverPanel gameOverPanel = new GameOverPanel();
        AutoResetSound s = new AutoResetSound("SoundFiles/gameOver.wav");
        viewController.changeLevel(gameOverPanel);
        currentLevel.removeKeyListener(keyAdapter);
        //currentLevel.repaint();
        s.Start();

        gameOverPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameOverPanel.clickedQuit(e.getX(), e.getY())) {
                    System.exit(0);
                }else if (gameOverPanel.clickedRetry(e.getX(), e.getY())) {
                    updateTimer.stop();
                    currentLevelSet.ReloadLevels();
                    Level.numLives = 3;
                    Level level = currentLevelSet.getFirstLevel();
                    InitializeLevel(level);
                    Thread songThread = new Thread(GameController.this::playSongs);
                    play = true;
                    songThread.start();
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


}
