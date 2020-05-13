import java.awt.*;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayList {

    public Cat cat;
    private ArrayList<Fluffball> fluffballs;
    private ArrayList<Enemy> enemies;

    public DisplayList() {
        fluffballs = new ArrayList<>();
        enemies = new ArrayList<>();
        cat = new Cat();
    }

    public void AddFluffball(Fluffball fluffball) {
        fluffballs.add(fluffball);
    }
    public void AddEnemy(Enemy enemy) {
        enemies.add(enemy);
    }


    public ArrayList<Fluffball> getFluffballs() {
        return fluffballs;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }


    public void removeFluffball(Fluffball fluffball) {
        fluffballs.remove(fluffball);
    }
    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }

}
