public class ChangeCatInGameLevels extends LevelSet {

    private Level currentLevel;
    private LevelSet pausedLevelSet;
    private Level pausedLevel;

    //public PauseLevelSet()

    @Override
    public boolean lastLevel() {
        return false;
    }

    @Override
    public Level getNextLevel() {
        return null;
    }

    @Override
    public Level getSameLevel() {
        return null;
    }

    @Override
    public Level getFirstLevel() {
        currentLevel = new PauseMenu(true);
        return currentLevel;
    }

    @Override
    public void ReloadLevels() {

    }

    @Override
    public LevelSet getNextLevelSet() {
        return null;
    }

    @Override
    public Level goToSelectCat() {
        return null;
    }

    @Override
    public Level returnFromSelectCat() {
        return null;
    }
}
