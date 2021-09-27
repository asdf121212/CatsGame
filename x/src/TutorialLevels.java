public class TutorialLevels extends LevelSet {

    public int levelIndex = 0;
    //private Class[] levelClasses = new Class[] { Level1.class, Level2.class, Level3.class, Level4.class, Level5.class };
    private Class[] levelClasses = new Class[] { TutorialLevel1.class, Level1.class, Level2.class, Level4.class };

    public TutorialLevels() {

    }

    @Override
    public Level goToSelectCat() {
        return null;
    }

    @Override
    public Level returnFromSelectCat() {
        return null;
    }

    public void ReloadLevels() {

    }

    @Override
    public LevelSet getNextLevelSet() {
        return new MainMenus();
    }

    @Override
    public boolean lastLevel() {
        return levelIndex >= levelClasses.length - 1;
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
        nextLevel.setNumLives(3);
        return nextLevel;
    }

    @Override
    public Level getFirstLevel() {
        //levelIndex = 2;//////////////////testing
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
