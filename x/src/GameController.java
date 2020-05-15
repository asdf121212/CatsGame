import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

public class GameController {

    private Level currentLevel;
    //private int extraLives = 3;
    private int levelIndex = 0;
    private Class[] levelClasses = new Class[] { Level1.class, Level2.class, Level3.class, Level4.class };

    private Song[] songs = new Song[] {
        new Song("SoundFiles/Songs/Ganymede.aif", -4),
        new Song("SoundFiles/Songs/trinoculars2.aif", -5),
        new Song("SoundFiles/Songs/ancalagon.aif", -5),
        new Song("SoundFiles/Songs/Sand edit.aif", -5)
    };
    private boolean play = false;
    private int songIndex = 0;

    private KeyAdapter keyAdapter;

    private Timer updateTimer;


    private DisplayList displayList;
    private Cat cat;
    private boolean spaceReleased = true;

    private ViewController viewController;

    public GameController() {
        Level startLevel = new Level1();
        //Level startLevel = new Level2();/////////////////////for development purposes
        //Level startLevel = new Level3();/////////////////////for development purposes
        //Level startLevel = new Level4();/////////////////////for development purposes
        viewController = new ViewController();
        SwingUtilities.invokeLater(() -> InitializeLevel(startLevel));


        //Song song = new Song("SoundFiles/Songs/Ganymede.aif");
        //Thread songThread = new Thread(songs[songIndex]::Start);
        Thread songThread = new Thread(GameController.this::playSongs);
        //Song.initializeLine();
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
            ///////////////////////////////////////////////////////////////////////////////////
            double floorCheckX1 = displayList.cat.GetX() + 20;
            double floorCheckX2 = displayList.cat.GetX() + 55;
            double wallCheckX1 = displayList.cat.GetX();
            double wallCheckX2 = displayList.cat.GetX() + 75;
            RoundRectangle2D floor_Rect = null;
            RoundRectangle2D wall_Rect = null;
            for (RoundRectangle2D floor : currentLevel.getFloors()) {
                if (floor.contains(floorCheckX1, displayList.cat.GetY() + 51 + cat.Vy)
                || floor.contains(floorCheckX2, displayList.cat.GetY() + 51 + cat.Vy)) {
                    floor_Rect = floor;
                }
            }
            for (RoundRectangle2D wall : currentLevel.getWalls()) {
                if (wall.contains(wallCheckX1, displayList.cat.GetY() + 25) && cat.Vx < 0) {
                    wall_Rect = wall;
                } else if (wall.contains(wallCheckX2, displayList.cat.GetY() + 25) && cat.Vx > 0) {
                    wall_Rect = wall;
                }
            }
            if (floor_Rect == null || cat.Vy < 0) {
                displayList.cat.state = (byte)(displayList.cat.state | 100);
                cat.Vy += cat.Gravity;
            } else {
                if (cat.Vy > 0) {
                    //make sure it's level with floor
                    displayList.cat.SetY((int)Math.round(floor_Rect.getY()) - 50);
                }
                cat.Vy = 0;
                displayList.cat.state = (byte)(displayList.cat.state & 011);
            }
            if (wall_Rect != null) {
                cat.Vx = 0;
            }
            ///////////////////////////////////////////////////////////////////////////////////

            if (!displayList.cat.Dying) {
                displayList.cat.tryIncrementXY(cat.Vx, cat.Vy);
            }

            currentLevel.update();

            if (cat.Dead) {
                cat_die();
            } else if (currentLevel.hasReachedNextLevel()) {
                advance_levels();
            }
        }
    };

    private void InitializeLevel(Level level) {
        //level.setNumLives(extraLives);
        //cat.Vx = 0;
        //cat.Vy = 0;
        if (currentLevel != null) {
            currentLevel.Dispose();
        }
        viewController.changeLevel(level);
        currentLevel = level;
        //currentLevel.addMouseListener(mouseAdapter);/////////////////////////for development only
        displayList = currentLevel.displayList;
        cat = displayList.cat;
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
                int health = cat.getHealth();
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
        cat = null;
//        for (Danger danger : displayList.getDangers()) {
//            if (danger.Dead || danger.Dying) {
//                SwingUtilities.invokeLater(() -> displayList.removeDanger(danger));
//            }
//        }

        if (Level.numLives <= 0) {
            GameOver();
        } else {
            Level.numLives--;
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
                    Level.numLives = 3;
                    levelIndex = 0;
                    Level level = new Level1();
                    InitializeLevel(level);//, true);
                    //Thread songThread = new Thread(soundTrack::Start);
                    //songThread.start();
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

    //private Point2D prevPoint = null;
    //private int indexxx = 3;
    //development method to see where to place stuff
    //MouseAdapter mouseAdapter = new MouseAdapter() {
        //@Override
        //public void mouseClicked(MouseEvent e) {
//            if (prevPoint == null) {
//                prevPoint = e.getPoint();
//            } else {
//                System.out.println(String.format("private RoundRectangle2D jumpPad%d = new RoundRectangle2D.Double" +
//                        "(%f, %f, %f, %f, 10, 10);", indexxx, prevPoint.getX(), prevPoint.getY(),
//                        e.getX() - prevPoint.getX(), e.getY() - prevPoint.getY()));
//                prevPoint = null;
//                indexxx++;
//            }
            //System.out.println(String.format("x:  %d    y:  %d", e.getX(), e.getY()));
        //}
    //};


}
