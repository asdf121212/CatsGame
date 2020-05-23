import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

public class MazeLevels extends LevelSet {

    private LevelConfigurationObject[][] mazeLevels = new LevelConfigurationObject[7][7];
    //private LevelConfigurationObject testMazeLevel;
    private GenericLevel currentLevel;

    private int prevMazeIndex_i = 0;
    private int prevMazeIndex_j = 0;
    private int prevPigX = 600;
    private int prevPigY = 0;

    int newCatX = 100;
    int newCatY = 100;
    double dist = 0;
    int newPigX = 600;
    int newPigY = 0;

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

        newCatX = 0;
        newCatY = 0;
        if (catX > 1170) {
            if (mazeIndex_j < 6) {
                mazeIndex_j++;
                newCatY = (int)catY - 20;
                newCatX = -20;
            }
        } else if (catX < -50) {
            if (mazeIndex_j > 0) {
                mazeIndex_j--;
                newCatY = (int)catY - 20;
                newCatX = 1150;
            }
        } else if (catY > 680) {
            if (mazeIndex_i < 6) {
                mazeIndex_i++;
                newCatY = -15;
                newCatX = (int)catX;
            }
        } else if (catY < -20) {
            if (mazeIndex_i > 0) {
                mazeIndex_i--;
                newCatY = 680;
                newCatX = (int)catX;
            }
        }

        if (mazeIndex_i == 7 && mazeIndex_j == 3) {
            return new WinLevel();
        }

        if (currentLevel.pigMouseWaiting) {
            if (mazeIndex_j == prevMazeIndex_j && mazeIndex_i == prevMazeIndex_i) {
                dist = 0;
                newPigX = prevPigX;
                newPigY = prevPigY;
            } else {
                dist = 1000;
                newPigX = newCatX;
                newPigY = newCatY - 15;
            }
        } else {
            double pigMouseX = currentLevel.pigMouse.x;
            double pigMouseY = currentLevel.pigMouse.y;
            dist = Math.sqrt((catX - pigMouseX) * (catX - pigMouseX) + (catY - pigMouseY) * (catY - pigMouseY));
            newPigX = newCatX;
            newPigY = newCatY - 15;
        }

        prevMazeIndex_i = i;
        prevMazeIndex_j = j;


        if (!currentLevel.pigMouseWaiting) {
            prevPigX = (int)currentLevel.pigMouse.x;
            prevPigY = (int)currentLevel.pigMouse.y;
        } else {
            prevPigX = newCatX;
            prevPigY = newCatY - 15;
        }

        GenericLevel lvl = new GenericLevel();
        lvl.ConfigureLevel(mazeLevels[mazeIndex_i][mazeIndex_j]);
        currentLevel = lvl;
        currentLevel.displayList.cat.x = newCatX;
        currentLevel.displayList.cat.y = newCatY;
        currentLevel.pigMouseWaiting = true;
        currentLevel.pigMouseWaitTicks = (int)dist;
        currentLevel.pigMouseX_0 = newPigX;
        currentLevel.pigMouseY_0 = newPigY;
        return lvl;
    }

    ///return getFirstLevel --- the maze starts over buddy
    @Override
    public Level getSameLevel() {
        currentLevel = (GenericLevel)getFirstLevel();
        return currentLevel;
//        GenericLevel lvl = new GenericLevel();
//        lvl.ConfigureLevel(mazeLevels[mazeIndex_i][mazeIndex_j]);
//        currentLevel = lvl;
//        currentLevel.displayList.cat.x = newCatX;
//        currentLevel.displayList.cat.y = newCatY;
//        currentLevel.pigMouseWaiting = true;
//        currentLevel.pigMouseWaitTicks = (int)dist;
//        currentLevel.pigMouseX_0 = newPigX;
//        currentLevel.pigMouseY_0 = newPigY;
//        return lvl;
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
        currentLevel.displayList.cat.x = 100;
        currentLevel.displayList.cat.y = 100;
        currentLevel.pigMouseWaiting = true;
        currentLevel.pigMouseWaitTicks = 1;
        currentLevel.pigMouseX_0 = 600;
        currentLevel.pigMouseY_0 = 0;
        return lvl;
    }


    public void load49LevelConfigObjects() throws IOException, ClassNotFoundException {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                URL levelName = MazeLevels.class.getResource(String.format("Levels/%d_%d.lvl", i, j));
                FileInputStream fStream = new FileInputStream(levelName.getPath());
                ObjectInputStream oStream = new ObjectInputStream(fStream);
                LevelConfigurationObject configObj = (LevelConfigurationObject) oStream.readObject();
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
