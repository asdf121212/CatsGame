import java.awt.*;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayList {

    public Cat cat;
    private ArrayList<Fluffball> fluffballs;
    private ArrayList<Enemy> enemies;
    private ArrayList<ZinzanLife> extraLives;

    public DisplayList() {
        fluffballs = new ArrayList<>();
        enemies = new ArrayList<>();
        extraLives = new ArrayList<>();
        cat = new Cat();
    }

    public void AddFluffball(Fluffball fluffball) {
        fluffballs.add(fluffball);
    }
    public void AddEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void AddExtraLive(ZinzanLife life) { extraLives.add(life); }


    public ArrayList<Fluffball> getFluffballs() {
        return fluffballs;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<ZinzanLife> getExtraLives() { return extraLives; }

    public void removeFluffball(Fluffball fluffball) {
        fluffballs.remove(fluffball);
    }
    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }
    public void removeExtraLife(ZinzanLife life) { extraLives.remove(life); }

}
