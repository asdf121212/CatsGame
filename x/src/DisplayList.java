import java.awt.*;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayList {

    public Cat cat;
    //private ArrayList<Entity> entities;
    private ArrayList<Fluffball> fluffballs;
    private ArrayList<Enemy> enemies;
    private ArrayList<Entity> dangers;
    private ArrayList<Shape> backgroundShapesList;
    private ArrayList<Shape> foregroundShapesList;

    public DisplayList() {
        //entities = new ArrayList<>();
        fluffballs = new ArrayList<>();
        enemies = new ArrayList<>();
        dangers = new ArrayList<>();
        backgroundShapesList = new ArrayList<>();
        foregroundShapesList = new ArrayList<>();
        cat = new Cat();
    }

    public void AddBackgroundShape(Shape shape) {
        backgroundShapesList.add(shape);
    }
    public void AddForegroundShape(Shape shape) {
        foregroundShapesList.add(shape);
    }
//    public void AddEntity(Entity entity) {
//        entities.add(entity);
//    }
    public void AddFluffball(Fluffball fluffball) {
        fluffballs.add(fluffball);
    }
    public void AddEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public void AddDanger(Entity danger) {
        dangers.add(danger);
    }

    public ArrayList<Shape> getBackgroundShapes() {
        return backgroundShapesList;
    }
    public ArrayList<Shape> getForegroundShapes() {
        return foregroundShapesList;
    }
//    public ArrayList<Entity> getEntities() {
//        return entities;
//    }
    public ArrayList<Fluffball> getFluffballs() {
        return fluffballs;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public ArrayList<Entity> getDangers() {
        return dangers;
    }

//    public void removeEntity(Entity entity) {
//        entities.remove(entity);
//    }
    public void removeFluffball(Fluffball fluffball) {
        fluffballs.remove(fluffball);
    }
    public void removeEnemy(Entity enemy) {
        enemies.remove(enemy);
    }
    public void removeDanger(Entity danger) {
        dangers.remove(danger);
    }

}
