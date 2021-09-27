//import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayList {

    public Cat cat;
    private ArrayList<catProjectile> catProjectiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<ZinzanLife> extraLives;

    public static Class<? extends Cat> catClass;

    public DisplayList() {
        catProjectiles = new ArrayList<>();
        enemies = new ArrayList<>();
        extraLives = new ArrayList<>();
        try {
            cat = catClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    public void addCat(Cat cat) {
//        this.cat = cat;
//    }

    public void AddCatProjectile(catProjectile catProjectile) {
        catProjectiles.add(catProjectile);
    }
    public void AddEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void AddExtraLive(ZinzanLife life) { extraLives.add(life); }


    public ArrayList<catProjectile> getCatProjectiles() {
        return catProjectiles;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<ZinzanLife> getExtraLives() { return extraLives; }

    public void removeCatProjectile(catProjectile catProjectile) {
        catProjectiles.remove(catProjectile);
    }
    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }
    public void removeExtraLife(ZinzanLife life) { extraLives.remove(life); }

}
