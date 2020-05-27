public class FirstLevels extends LevelSet {

    public int levelIndex = 0;
    private Class[] levelClasses = new Class[] { Level1.class, Level2.class, Level3.class, Level4.class, Level5.class };

    public FirstLevels() {

    }

    public void ReloadLevels() {

    }

    @Override
    public boolean lastLevel() {
        return levelIndex >= 4;
    }

    @Override
    public Level getNextLevel() {
        levelIndex++;
        if (levelIndex >= levelClasses.length) {
            levelIndex--;
        }
        Level nextLevel = null;
        try {
           nextLevel = (Level)levelClasses[levelIndex].newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return nextLevel;
    }

    @Override
    public Level getFirstLevel() {
        //levelIndex = 4;//////////////////testing
        levelIndex = 0;
        Level nextLevel = null;
        try {
            nextLevel = (Level)levelClasses[levelIndex].newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return nextLevel;
    }

    @Override
    public Level getSameLevel() {
        Level nextLevel = null;
        try {
            nextLevel = (Level)levelClasses[levelIndex].newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return nextLevel;
    }
}
