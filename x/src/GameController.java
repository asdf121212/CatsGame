import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;

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
    protected MouseMotionListener mouseMotionListener;
    protected MouseListener mouseListener;
    private boolean pressedInsidePauseButton = false;
    private Level pausedLevel;
    private boolean changingCatFromLevel = false;

    protected Timer updateTimer;

    protected DisplayList displayList;
    protected Cat cat;
    protected boolean spaceReleased = true;

    protected ViewController viewController;

    private int dieWaitTicks = 0;

    private Class catClass;

    public GameController() {
        ////////////////////////////////////////////////////////
        DisplayList.catClass = Zinzan.class;
        //DisplayList.catClass = Pheobe.class;
        //DisplayList.catClass = Daisy.class;
        //GenericLevel.floorColor = Color.gray;
        //Level.backGroundColor = Color.GRAY;
        Level.backGroundColor = Color.BLACK;
        ////////////////////////////////////////////////////////
        //currentLevelSet = new FirstLevels();
        //currentLevelSet = new MazeLevels();
        currentLevelSet = new MainMenus();
        //currentLevelSet = new PauseLevelSet();
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
    ActionListener menuUpdateListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentLevel.repaint();
            if (currentLevel instanceof PauseMenu) {
                PauseMenu pauseMenu = (PauseMenu)currentLevel;
                if (pauseMenu.transition()) {
                    if (pauseMenu.clickedResume()) {
                        InitializeLevel(pausedLevel);
                    } else if (pauseMenu.clickedChangeCat()) {
                        changingCatFromLevel = true;
                        InitializeLevel(currentLevelSet.goToSelectCat());
                    } else if (pauseMenu.clickedRestart()) {
                        currentLevelSet.ReloadLevels();
                        Level.numLives = 3;
                        InitializeLevel(currentLevelSet.getFirstLevel());
                    } else if (pauseMenu.clickedMainMenu()) {
                        currentLevelSet = new MainMenus();
                        Level.numLives = 3;
                        InitializeLevel(currentLevelSet.getFirstLevel());
                    }
                }
            }
            else {
                if (((Menu) currentLevel).transition()) {
                    advance_levels();
                }
            }
        }
    };

    protected void InitializeLevel(Level level) {
        if (updateTimer != null) {
            updateTimer.stop();
        }
        if (currentLevel != null) {
            currentLevel.Dispose();
            for (MouseListener mListener : currentLevel.getMouseListeners()) {
                currentLevel.removeMouseListener(mListener);
            }
            for (MouseMotionListener mListener : currentLevel.getMouseMotionListeners()) {
                currentLevel.removeMouseMotionListener(mListener);
            }
            for (KeyListener kListener : currentLevel.getKeyListeners()) {
                currentLevel.removeKeyListener(kListener);
            }
        }

        viewController.changeLevel(level);
        currentLevel = level;

        if (!(currentLevel instanceof Menu)) {
            displayList = currentLevel.displayList;
            cat = displayList.cat;
            cat.AddLevelInfo(new LevelInfo(currentLevel.floors, currentLevel.walls, null));
            catProjectile f = cat.generateProjectile();
            f = null;
            Ball b = new Ball(0, 0, 50, 0, 0);
            b = null;

            if (currentLevel instanceof GenericMazeLevel) {
                GenericMazeLevel lvl = (GenericMazeLevel)currentLevel;
                mouseListener = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (lvl.pauseButtonContains(e.getX(), e.getY())) {
                            lvl.pauseImagePressed = true;
                            pressedInsidePauseButton = true;
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (pressedInsidePauseButton && lvl.pauseButtonContains(e.getX(), e.getY())) {
                            pauseGame();
                            pressedInsidePauseButton = false;
                            lvl.pauseImagePressed = false;
                        } else {
                            pressedInsidePauseButton = false;
                            lvl.pauseImagePressed = false;
                        }
                    }
                };
                lvl.addMouseListener(mouseListener);
            } else if (currentLevel instanceof TutorialLevel) {
                TutorialLevel lvl = (TutorialLevel)currentLevel;
                mouseListener = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (lvl.pauseButtonContains(e.getX(), e.getY())) {
                            lvl.pauseImagePressed = true;
                            pressedInsidePauseButton = true;
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (pressedInsidePauseButton && lvl.pauseButtonContains(e.getX(), e.getY())) {
                            pauseGame();
                            pressedInsidePauseButton = false;
                            lvl.pauseImagePressed = false;
                        } else {
                            pressedInsidePauseButton = false;
                            lvl.pauseImagePressed = false;
                        }
                    }
                };
                lvl.addMouseListener(mouseListener);
            }
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
                        displayList.AddCatProjectile(cat.generateProjectile());
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


            updateTimer = new Timer(5, updateListener);
            updateTimer.setInitialDelay(1);
            currentLevel.addKeyListener(keyAdapter);
            updateTimer.start();
        } else {
            mouseMotionListener = new MouseAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    ((Menu)currentLevel).mouseMove(e.getX(), e.getY());
                }
            };
            mouseListener = new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ((Menu)currentLevel).mouseClick(e.getX(), e.getY());
                }
            };
            currentLevel.addMouseMotionListener(mouseMotionListener);
            currentLevel.addMouseListener(mouseListener);
            updateTimer = new Timer(5, menuUpdateListener);
            updateTimer.start();
        }
        currentLevel.requestFocus();
        //viewController.StartRepaintTimer();
        viewController.revalidateFrame();
    }


    private void pauseGame() {
        updateTimer.stop();
        pausedLevel = currentLevel;
        boolean showChangeCat = !(currentLevelSet instanceof TutorialLevels);
        PauseMenu pauseMenu = new PauseMenu(showChangeCat);
        InitializeLevel(pauseMenu);
    }
//    private void unPauseGame() {
//        InitializeLevel(pausedLevel);
//    }

    protected void advance_levels() {
        if (!currentLevelSet.lastLevel()) {
            try {
                if (changingCatFromLevel) {
                    updateTimer.stop();
                    changingCatFromLevel = false;
                    DisplayList.catClass = ((CatChoosingMenu) currentLevel).selectedCatClass;
                    InitializeLevel(currentLevelSet.returnFromSelectCat());
                } else {
                    Level nextLevel = currentLevelSet.getNextLevel();;
                    if (!currentLevelSet.isMenu) {
                        int health = cat.getHealth();
                        currentLevel.removeKeyListener(keyAdapter);
                        nextLevel.displayList.cat.setHealth(health);
                    } else {
                        currentLevel.removeMouseMotionListener(mouseMotionListener);
                        currentLevel.removeMouseListener(mouseListener);
                    }
                    updateTimer.stop();
                    InitializeLevel(nextLevel);
                }
            }
            catch (NullPointerException ex) {
                System.out.println(ex.getMessage() + " GameController: advance levels error");
            }
        } else {
            if (currentLevelSet.getNextLevelSet() != null) {
                currentLevelSet = currentLevelSet.getNextLevelSet();
                InitializeLevel(currentLevelSet.getFirstLevel());
            }
        }
    }



    protected void cat_die() {
        currentLevel.removeKeyListener(keyAdapter);
        updateTimer.stop();
        //cat = null;

        if (Level.numLives <= 0) {
            currentLevel.Dispose();
            GameOver();
        } else {
            if (!(currentLevelSet instanceof TutorialLevels)) {
                Level.numLives--;
            }
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
                    currentLevelSet = new MainMenus();
                    Level startLevel = currentLevelSet.getFirstLevel();
                    SwingUtilities.invokeLater(() -> InitializeLevel(startLevel));
                    Thread songThread = new Thread(GameController.this::playSongs);
                    play = true;
                    songThread.start();
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
