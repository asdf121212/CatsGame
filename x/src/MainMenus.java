public class MainMenus extends LevelSet {

    private Menu currentLevel;

    public MainMenus() {
        isMenu = true;
    }

    @Override
    public boolean lastLevel() {
        return (currentLevel instanceof CatChoosingMenu && ((CatChoosingMenu)currentLevel).catChosen)
                || (currentLevel instanceof MainMenu && ((MainMenu)currentLevel).playTutorial);
    }

    @Override
    public Menu getNextLevel() {
        if (currentLevel instanceof MainMenu && ((MainMenu)currentLevel).playGame) {
            currentLevel = new CatChoosingMenu();
            return currentLevel;
        }
        else if (currentLevel instanceof CatChoosingMenu && ((CatChoosingMenu) currentLevel).backButtonClicked) {
            currentLevel = new MainMenu();
            return currentLevel;
        }
        else {
            return null;
        }
    }

    @Override
    public Menu getSameLevel() {
        return null;
    }

    @Override
    public Menu getFirstLevel() {
        currentLevel = new MainMenu();
        return currentLevel;
        //currentLevel = new CatChoosingMenu();
        //return currentLevel;
    }

    @Override
    public void ReloadLevels() {

    }

    @Override
    public LevelSet getNextLevelSet() {
        if (currentLevel instanceof MainMenu && ((MainMenu)currentLevel).playTutorial) {
            return new TutorialLevels();
        }
        else if (currentLevel instanceof CatChoosingMenu && ((CatChoosingMenu)currentLevel).catChosen) {
            DisplayList.catClass = ((CatChoosingMenu) currentLevel).selectedCatClass;
            return new MazeLevels();
        }
        else {
            return null;
        }
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
