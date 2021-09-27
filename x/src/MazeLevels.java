import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class MazeLevels extends LevelSet {

    private LevelConfigObj2[][] mazeLevels = new LevelConfigObj2[7][7];
    //private StartLevel startLevel;
    private LevelConfigObj2 startConfigObj;
    //private LevelConfigurationObject testMazeLevel;
    private GenericMazeLevel currentLevel;
    private SpawnPoint currentSpawnPoint;
    private SpawnPoint preTrapSpawnPoint;

    private int pig_i;
    private int pig_j;
    private int pigX;
    private int pigY;
    private boolean pigIsInPreviousLevel;
    //private SpawnPoint pigRespawnPoint;
    //private SpawnPoint nextPigSpawnPoint;
    private int pigRespawnX;
    private int pigRespawnY;
    private int nextPigSpawnX;
    private int nextPigSpawnY;


    double dist = 0;

    private byte catState = 000;

    private int mazeIndex_i = 0;
    private int mazeIndex_j = 0;


    private CatChoosingMenu catSelectionMenu;
    private GenericMazeLevel pausedLevel;
    private boolean selectingCat = false;

    public MazeLevels() {
        mazeIndex_i = 0;
        mazeIndex_j = 3;
        try {
            //load1TestLevelConfigObj();
            load49LevelConfigObjects();
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Level goToSelectCat() {
        pausedLevel = currentLevel;
        catSelectionMenu = new CatChoosingMenu();
        return catSelectionMenu;
    }
    @Override
    public Level returnFromSelectCat() {
        if (pausedLevel != null) {
            try {
                Cat cat = DisplayList.catClass.newInstance();
                cat.x = currentLevel.displayList.cat.x;
                cat.y = currentLevel.displayList.cat.y;
                cat.Vy = currentLevel.displayList.cat.Vy;
                cat.Vx = currentLevel.displayList.cat.Vx;
                cat.Dying = currentLevel.displayList.cat.Dying;
                cat.state = currentLevel.displayList.cat.state;
                cat.AddLevelInfo(currentLevel.displayList.cat.getLevelInfo());
                currentLevel.displayList.cat = cat;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            currentLevel = pausedLevel;
            pausedLevel = null;
            return currentLevel;
        } else {
            return null;
        }
    }

    @Override
    public boolean lastLevel() {
        return false;
    }

    @Override
    public Level getNextLevel() {
        //need to find a way to not transition as soon as level starts
        catState = (byte) (currentLevel.displayList.cat.state & 001);
        double catX = currentLevel.displayList.cat.x;
        double catY = currentLevel.displayList.cat.y;
        int i = mazeIndex_i;
        int j = mazeIndex_j;
        LevelTransitions direction = null;
        if (catX > 1165 && mazeIndex_j < 6) {
            mazeIndex_j++;
            direction = LevelTransitions.RIGHT;
        } else if (catX < -35 && mazeIndex_j > 0) {
            mazeIndex_j--;
            direction = LevelTransitions.LEFT;
        } else if (catY > 670 && (mazeIndex_i < 6 || mazeIndex_j == 3)) {
            mazeIndex_i++;
            direction = LevelTransitions.DOWN;
        } else if (catY < -20 && mazeIndex_i > 0) {
            mazeIndex_i--;
            direction = LevelTransitions.UP;
        }
        if (mazeIndex_i == 7 && mazeIndex_j == 3) {
            return new WinLevel();
        }
        GenericMazeLevel lvl = new GenericMazeLevel();
        lvl.ConfigureLevel(mazeLevels[mazeIndex_i][mazeIndex_j]);
        //byte catState = currentLevel.displayList.cat.state;
        //double cat_yVel = currentLevel.displayList.cat.Vy;

        SpawnPoint tempSpawnPoint = currentSpawnPoint;

        if (direction == LevelTransitions.DOWN) {
            currentSpawnPoint = lvl.topSpawn;
        } else if (direction == LevelTransitions.UP) {
            currentSpawnPoint = lvl.bottomSpawn;
        } else if (direction == LevelTransitions.RIGHT) {
            currentSpawnPoint = lvl.leftSpawn;
        } else {
            currentSpawnPoint = lvl.rightSpawn;
        }
        lvl.displayList.cat.x = currentSpawnPoint.x;
        lvl.displayList.cat.y = currentSpawnPoint.y;
        lvl.displayList.cat.state = (byte) (lvl.displayList.cat.state | catState);

        pigIsInPreviousLevel = currentLevel.pigMouseWaiting;
        if (currentSpawnPoint.isFallTrap) {
            if (!tempSpawnPoint.isFallTrap) {
                preTrapSpawnPoint = tempSpawnPoint;
            }
            //pigIsInPreviousLevel = true;
            lvl.pigMouseWaitTicks = 50000;
            lvl.pigMouseWaiting = true;
        } else {
            if (pigIsInPreviousLevel) {
                if (pig_i == mazeIndex_i && pig_j == mazeIndex_j) {
                    dist = 0;
                    pigIsInPreviousLevel = false;
                    nextPigSpawnX = pigRespawnX;///not sure about this one
                    nextPigSpawnY = pigRespawnY;
                } else {
                    //nextPigSpawnPoint = currentSpawnPoint;
                    pigRespawnX = nextPigSpawnX;
                    pigRespawnY = nextPigSpawnY;
                    nextPigSpawnX = currentSpawnPoint.x;
                    nextPigSpawnY = currentSpawnPoint.y;
                    //pigRespawnPoint = tempSpawnPoint;
//                pigRespawnX = currentSpawnPoint.x;
//                pigRespawnY = currentSpawnPoint.y;
                    pigX = tempSpawnPoint.x;
                    pigY = tempSpawnPoint.y;
                    pig_i = i;
                    pig_j = j;
                    dist = 600;
                }
            } else {
                pig_i = i;
                pig_j = j;
                //nextPigSpawnPoint = currentSpawnPoint;
                pigRespawnX = nextPigSpawnX;
                pigRespawnY = nextPigSpawnY;
                nextPigSpawnX = currentSpawnPoint.x;
                nextPigSpawnY = currentSpawnPoint.y;

                pigX = (int) currentLevel.pigMouse.x;
                pigY = (int) currentLevel.pigMouse.y;
                double xDist = catX - currentLevel.pigMouse.x;
                double yDist = catY - currentLevel.pigMouse.y;
                dist = Math.sqrt(xDist * xDist + yDist * yDist);
                pigIsInPreviousLevel = true;
            }
            lvl.pigMouseWaitTicks = (int) dist;
            if (!pigIsInPreviousLevel) {
                lvl.pigMouseWaiting = false;
                lvl.pigMouse = new PigMouse(pigX, pigY);
                lvl.pigMouse.addLevelInfo(new LevelInfo((IndexedNodeFloor[]) lvl.floors, lvl.nodeList, lvl.displayList.cat));
                lvl.displayList.AddEnemy(lvl.pigMouse);
                lvl.pigRespawnX = pigRespawnX;
                lvl.pigRespawnY = pigRespawnY;
            } else {
                //lvl.pigMouseX_0 = pigX;
                //lvl.pigMouseY_0 = pigY;
                lvl.pigMouseWaiting = true;
                //lvl.nextPigSpawn = nextPigSpawnPoint;
                lvl.nextPigSpawnX = nextPigSpawnX;
                lvl.nextPigSpawnY = nextPigSpawnY;
                //lvl.respawnPoint = pigRespawnPoint;
                lvl.pigRespawnX = pigRespawnX;
                lvl.pigRespawnY = pigRespawnY;
            }
        }

        currentLevel = lvl;
        return lvl;
    }


    @Override
    public Level getSameLevel() {
        if (currentSpawnPoint.isFallTrap) {
            while (mazeLevels[mazeIndex_i][mazeIndex_j].topSpawnPoint.isFallTrap) {
                mazeIndex_i--;
            }
            currentSpawnPoint = new SpawnPoint();
            currentSpawnPoint.x = preTrapSpawnPoint.x;
            currentSpawnPoint.y = preTrapSpawnPoint.y;
            //currentLevel = (GenericLevel) getFirstLevel();
            //return currentLevel;
        }
        if (currentLevel instanceof StartLevel) {
            currentLevel = (StartLevel)getFirstLevel();
            return currentLevel;
        }
        else {
            GenericMazeLevel lvl = new GenericMazeLevel();
            lvl.ConfigureLevel(mazeLevels[mazeIndex_i][mazeIndex_j]);
            currentLevel = lvl;
            currentLevel.displayList.cat.x = currentSpawnPoint.x;
            currentLevel.displayList.cat.y = currentSpawnPoint.y;
            currentLevel.pigMouseWaiting = true;
            currentLevel.pigMouseWaitTicks = 400;

            //might need to set these to the respawn point?? or set
            lvl.nextPigSpawnX = nextPigSpawnX;
            lvl.nextPigSpawnY = nextPigSpawnY;

            lvl.pigRespawnX = pigRespawnX;
            lvl.pigRespawnY = pigRespawnY;
            return lvl;
        }
    }

    @Override
    public Level getFirstLevel() {
        //GenericLevel lvl = new GenericLevel();
        GenericMazeLevel lvl = new StartLevel();
        //Level.numLives = 3;
        lvl.ConfigureLevel(startConfigObj);
        mazeIndex_i = -1;
        mazeIndex_j = 3;
        //prevMazeIndex_i = 0;
        //prevMazeIndex_j = 3;

        //lvl.ConfigureLevel(mazeLevels[0][3]);
        //lvl.ConfigureLevel(mazeLevels[6][2]);/////////////////////////development

        currentLevel = lvl;

        //currentSpawnPoint = lvl.topSpawn;
        //currentSpawnPoint = lvl.leftSpawn;//////////////////////development
        currentSpawnPoint = lvl.rightSpawn;

        currentLevel.displayList.cat.x = lvl.leftSpawn.x;
        currentLevel.displayList.cat.y = lvl.rightSpawn.y;
        //currentLevel.pigMouseWaiting = true;
        currentLevel.pigMouseWaiting = false;
        currentLevel.pigMouseWaitTicks = 0;
        pig_i = -1;
        pig_j = 3;
        pigX = currentSpawnPoint.x;
        pigY = currentSpawnPoint.y;

        lvl.pigMouse = new PigMouse(pigX, pigY);
        lvl.pigMouse.addLevelInfo(new LevelInfo((IndexedNodeFloor[]) lvl.floors, lvl.nodeList, lvl.displayList.cat));
        lvl.displayList.AddEnemy(lvl.pigMouse);

        nextPigSpawnX = currentSpawnPoint.x;
        nextPigSpawnY = currentSpawnPoint.y;
        pigRespawnX = currentSpawnPoint.x;
        pigRespawnY = currentSpawnPoint.y;
        lvl.nextPigSpawnX = nextPigSpawnX;
        lvl.nextPigSpawnY = nextPigSpawnY;
        lvl.pigRespawnX = pigRespawnX;
        lvl.pigRespawnY = pigRespawnY;
        return lvl;
    }

    public void ReloadLevels() {
        mazeLevels = new LevelConfigObj2[7][7];
        try {
            load49LevelConfigObjects();
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public LevelSet getNextLevelSet() {
        return null;
    }

    public void load49LevelConfigObjects() throws IOException, ClassNotFoundException {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                InputStream fStream = MazeLevels.class.getResourceAsStream(String.format("newLevels/%d_%d.level", i, j));
                ObjectInputStream oStream = new ObjectInputStream(fStream);
                LevelConfigObj2 configObj = (LevelConfigObj2) oStream.readObject();
                mazeLevels[i][j] = configObj;
                oStream.close();
                fStream.close();
            }
        }
        InputStream fStream = MazeLevels.class.getResourceAsStream(String.format("otherLevels/startLevel.level"));
        ObjectInputStream oStream = new ObjectInputStream(fStream);
        LevelConfigObj2 configObj = (LevelConfigObj2) oStream.readObject();
        //startLevel = new StartLevel();
        //startLevel.ConfigureLevel(configObj);
        startConfigObj = configObj;
        oStream.close();
        fStream.close();
    }

}
