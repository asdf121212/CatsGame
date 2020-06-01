public abstract class LevelSet {

    public boolean isMenu = false;

    public abstract boolean lastLevel();
    public abstract Level getNextLevel();
    public abstract Level getSameLevel();
    public abstract Level getFirstLevel();
    public abstract void ReloadLevels();
    public abstract LevelSet getNextLevelSet();

}
