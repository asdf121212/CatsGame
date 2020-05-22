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

    private int mazeIndex_i = 0;
    private int mazeIndex_j = 0;


    public MazeLevels() {
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
        double pigMouseX = currentLevel.pigMouse.x;
        double dist = Math.abs(catX - pigMouseX);
        int newCatX = 0;
        int newCatY = 0;
        if (catX > 1170) {
            mazeIndex_j++;
            newCatY = (int)catY;
            newCatX = -20;
        } else if (catX < -50) {
            mazeIndex_j--;
            newCatY = (int)catY;
            newCatX = 1150;
        } else if (catY > 680) {
            mazeIndex_i++;
            newCatY = -15;
            newCatX = (int)catX;
        } else if (catY < -20) {
            mazeIndex_i--;
            newCatY = 685;
            newCatX = (int)catX;
        }
        GenericLevel lvl = new GenericLevel();
        lvl.ConfigureLevel(mazeLevels[mazeIndex_i][mazeIndex_j]);
        currentLevel = lvl;
        currentLevel.displayList.cat.x = newCatX;
        currentLevel.displayList.cat.y = newCatY;
        currentLevel.pigMouseWaiting = true;
        currentLevel.pigMouseWaitTicks = (int)dist;
        currentLevel.pigMouseX_0 = newCatX;
        currentLevel.pigMouseY_0 = newCatY - 10;
        return lvl;
    }

    ///return getFirstLevel --- the maze starts over buddy
    @Override
    public Level getSameLevel() {
        currentLevel = (GenericLevel)getFirstLevel();
        return currentLevel;
    }

    @Override
    public Level getFirstLevel() {
        GenericLevel lvl = new GenericLevel();
        //insert pigmouse and cat at standard first level spot
        lvl.ConfigureLevel(mazeLevels[0][0]);
        currentLevel = lvl;
        return lvl;
    }


    public void load49LevelConfigObjects() throws IOException, ClassNotFoundException {
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                URL levelName = MazeLevels.class.getResource(String.format("Levels/%d_%d.lvl", i, j));
                FileInputStream fStream = new FileInputStream(levelName.toString());
                ObjectInputStream oStream = new ObjectInputStream(fStream);
                LevelConfigurationObject configObj = (LevelConfigurationObject) oStream.readObject();
                mazeLevels[i][j] = configObj;
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
