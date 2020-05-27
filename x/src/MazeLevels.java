import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

public class MazeLevels extends LevelSet {

    private LevelConfigObj2[][] mazeLevels = new LevelConfigObj2[7][7];
    //private LevelConfigurationObject testMazeLevel;
    private GenericLevel currentLevel;
    private SpawnPoint currentSpawnPoint;
    private SpawnPoint prevSpawnPoint;
    private int prevMazeIndex_i = 0;
    private int prevMazeIndex_j = 0;
    private int prevPigX = 600;
    private int prevPigY = 0;

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

        //double newPigX;
        //double newPigY;
        if (prevMazeIndex_i == mazeIndex_i && prevMazeIndex_j == mazeIndex_j && currentLevel.pigMouseWaiting) {
            lvl.pigMouseX_0 = prevPigX;
            lvl.pigMouseY_0 = prevPigY;
            dist = 0;
        } else {
            lvl.pigMouseX_0 = currentSpawnPoint.x;
            lvl.pigMouseY_0 = currentSpawnPoint.y;
            if (currentLevel.pigMouseWaiting) {
                dist = 900;
            } else {
                double xDist = catX - currentLevel.pigMouse.x;
                double yDist = catY - currentLevel.pigMouse.y;
                dist = Math.sqrt(xDist * xDist + yDist * yDist);
            }
        }
        if (currentLevel.pigMouseWaiting) {
            prevPigX = prevSpawnPoint.x;
            prevPigY = prevSpawnPoint.y;
        } else {
            prevPigX = (int)currentLevel.pigMouse.x;
            prevPigY = (int)currentLevel.pigMouse.y;
        }
        prevMazeIndex_i = i;
        prevMazeIndex_j = j;
        prevSpawnPoint = currentSpawnPoint;
        currentLevel = lvl;
        lvl.pigMouseWaiting = true;
        lvl.pigMouseWaitTicks = (int)dist;
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
            currentLevel = lvl;
            currentLevel.displayList.cat.x = currentSpawnPoint.x;
            currentLevel.displayList.cat.y = currentSpawnPoint.y;
            currentLevel.pigMouseWaiting = true;
            currentLevel.pigMouseWaitTicks = 500;
            currentLevel.pigMouseX_0 = currentSpawnPoint.x;
            currentLevel.pigMouseY_0 = currentSpawnPoint.y;
            return lvl;
        }
    }

    @Override
    public Level getFirstLevel() {
        GenericLevel lvl = new GenericLevel();
        mazeIndex_i = 0;
        mazeIndex_j = 3;
        prevMazeIndex_i = 0;
        prevMazeIndex_j = 3;
        //insert pigmouse and cat at standard first level spot
        lvl.ConfigureLevel(mazeLevels[0][3]);
        currentLevel = lvl;
        currentSpawnPoint = lvl.topSpawn;
        prevSpawnPoint = lvl.topSpawn;
        currentLevel.displayList.cat.x = currentSpawnPoint.x;
        currentLevel.displayList.cat.y = currentSpawnPoint.y;
        currentLevel.pigMouseWaiting = true;
        currentLevel.pigMouseWaitTicks = 600;
        currentLevel.pigMouseX_0 = currentSpawnPoint.x;
        currentLevel.pigMouseY_0 = currentSpawnPoint.y;
        return lvl;
    }


    public void load49LevelConfigObjects() throws IOException, ClassNotFoundException {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                URL levelName = MazeLevels.class.getResource(String.format("newLevels/%d_%d.level", i, j));
                FileInputStream fStream = new FileInputStream(levelName.getPath());
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
