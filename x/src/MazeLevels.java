import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class MazeLevels extends LevelSet {

    private LevelConfigObj2[][] mazeLevels = new LevelConfigObj2[7][7];
    //private LevelConfigurationObject testMazeLevel;
    private GenericLevel currentLevel;
    private SpawnPoint currentSpawnPoint;

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

    //int newCatX = 100;
    //int newCatY = 100;
    double dist = 0;
    //int newPigX = 600;
    //int newPigY = 0;

    private int mazeIndex_i = 0;
    private int mazeIndex_j = 0;


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
    public boolean lastLevel() {
        return false;
    }

    @Override
    public Level getNextLevel() {
        //need to find a way to not transition as soon as level starts
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
        GenericLevel lvl = new GenericLevel();
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

        pigIsInPreviousLevel = currentLevel.pigMouseWaiting;
        if (pigIsInPreviousLevel) {
            if (pig_i == mazeIndex_i && pig_j == mazeIndex_j) {
                dist = 0;
                pigIsInPreviousLevel = false;
                //nextPigSpawnPoint = pigRespawnPoint;///not sure about this one
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
                dist = 900;
            }
        } else {
            pig_i = i;
            pig_j = j;
            //nextPigSpawnPoint = currentSpawnPoint;
            pigRespawnX = nextPigSpawnX;
            pigRespawnY = nextPigSpawnY;
            nextPigSpawnX = currentSpawnPoint.x;
            nextPigSpawnY = currentSpawnPoint.y;

            pigX = (int)currentLevel.pigMouse.x;
            pigY = (int)currentLevel.pigMouse.y;
            double xDist = catX - currentLevel.pigMouse.x;
            double yDist = catY - currentLevel.pigMouse.y;
            dist = Math.sqrt(xDist * xDist + yDist * yDist);
            pigIsInPreviousLevel = true;
        }
        lvl.pigMouseWaitTicks = (int)dist;
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

        currentLevel = lvl;
        return lvl;
    }

    ///return getFirstLevel --- the maze starts over buddy
    @Override
    public Level getSameLevel() {
        if (currentSpawnPoint.isFallTrap) {
            currentLevel = (GenericLevel) getFirstLevel();
            return currentLevel;
        } else {
            GenericLevel lvl = new GenericLevel();
            lvl.ConfigureLevel(mazeLevels[mazeIndex_i][mazeIndex_j]);
            //lvl.pigMouseX_0 = currentLevel.respawnPigMouseX_0;
            //lvl.pigMouseY_0 = currentLevel.respawnPigMouseY_0;
            //lvl.respawnPigMouseX_0 = currentLevel.respawnPigMouseX_0;
            //lvl.respawnPigMouseY_0 = currentLevel.respawnPigMouseY_0;
            currentLevel = lvl;
            currentLevel.displayList.cat.x = currentSpawnPoint.x;
            currentLevel.displayList.cat.y = currentSpawnPoint.y;
            currentLevel.pigMouseWaiting = true;
            currentLevel.pigMouseWaitTicks = 500;
            //lvl.pigMouseX_0 = pigX;
            //lvl.pigMouseY_0 = pigY;
            //lvl.pigMouseWaiting = true;
            //lvl.nextPigSpawn = nextPigSpawnPoint;
            lvl.nextPigSpawnX = nextPigSpawnX;
            lvl.nextPigSpawnY = nextPigSpawnY;
            //lvl.respawnPoint = pigRespawnPoint;
            lvl.pigRespawnX = pigRespawnX;
            lvl.pigRespawnY = pigRespawnY;
            return lvl;
        }
    }

    @Override
    public Level getFirstLevel() {
        GenericLevel lvl = new GenericLevel();
        mazeIndex_i = 0;
        mazeIndex_j = 3;
        //prevMazeIndex_i = 0;
        //prevMazeIndex_j = 3;

        lvl.ConfigureLevel(mazeLevels[0][3]);
        //lvl.ConfigureLevel(mazeLevels[6][2]);/////////////////////////development

        currentLevel = lvl;

        currentSpawnPoint = lvl.topSpawn;
        //currentSpawnPoint = lvl.leftSpawn;//////////////////////development

        currentLevel.displayList.cat.x = currentSpawnPoint.x;
        currentLevel.displayList.cat.y = currentSpawnPoint.y;
        currentLevel.pigMouseWaiting = true;
        currentLevel.pigMouseWaitTicks = 500;
        //currentLevel.pigMouseX_0 = currentSpawnPoint.x;
        //currentLevel.pigMouseY_0 = currentSpawnPoint.y;
        pig_i = 0;
        pig_j = 3;
        pigX = currentSpawnPoint.x;
        pigY = currentSpawnPoint.y;
        //nextPigSpawnPoint = currentSpawnPoint;
        nextPigSpawnX = currentSpawnPoint.x;
        nextPigSpawnY = currentSpawnPoint.y;
        //pigRespawnPoint = currentSpawnPoint;
        pigRespawnX = currentSpawnPoint.x;
        pigRespawnY = currentSpawnPoint.y;
        lvl.nextPigSpawnX = nextPigSpawnX;
        lvl.nextPigSpawnY = nextPigSpawnY;
        //lvl.respawnPoint = pigRespawnPoint;
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

    public void load49LevelConfigObjects() throws IOException, ClassNotFoundException {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                //URL levelName = MazeLevels.class.getResource(String.format("newLevels/%d_%d.level", i, j));
                //FileInputStream fStream = new FileInputStream(levelName.getPath());
                InputStream fStream = MazeLevels.class.getResourceAsStream(String.format("newLevels/%d_%d.level", i, j));
                //FileInputStream fStream = new FileInputStream(String.format("/Users/thomas/Desktop/CatRepo/CatsGame/x/src/newLevels/%d_%d.level", i, j));
                ObjectInputStream oStream = new ObjectInputStream(fStream);
                LevelConfigObj2 configObj = (LevelConfigObj2) oStream.readObject();
                mazeLevels[i][j] = configObj;
                oStream.close();
                fStream.close();
            }
        }
    }
//    public void load1TestLevelConfigObj() throws IOException, ClassNotFoundException {
//        URL levelName = MazeLevels.class.getResource(String.format("Levels/%d_%d.lvl", 0, 0));
//        FileInputStream fStream = new FileInputStream(levelName.getPath());
//        ObjectInputStream oStream = new ObjectInputStream(fStream);
//        testMazeLevel = (LevelConfigurationObject) oStream.readObject();
//    }


}
