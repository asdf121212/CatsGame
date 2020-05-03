import java.awt.*;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DisplayList {

    public Cat cat;
    private ArrayList<Fluffball> fluffballs;
    private ArrayList<Enemy> enemies;
    //private ArrayList<Shape> backgroundShapesList;
    //private ArrayList<Shape> foregroundShapesList;

    public DisplayList() {
        fluffballs = new ArrayList<>();
        enemies = new ArrayList<>();
        //backgroundShapesList = new ArrayList<>();
        //foregroundShapesList = new ArrayList<>();
        cat = new Cat();
    }
//    public void clearDynamics() {
//        fluffballs.clear();
//    }
//    public void AddBackgroundShape(Shape shape) {
//        backgroundShapesList.add(shape);
//    }
//    public void AddForegroundShape(Shape shape) {
//        foregroundShapesList.add(shape);
//    }
//    public void RemoveForegroundShape(Shape shape) { foregroundShapesList.remove(shape); }
//    public void AddEntity(Entity entity) {
//        entities.add(entity);
//    }
    public void AddFluffball(Fluffball fluffball) {
        fluffballs.add(fluffball);
    }
    public void AddEnemy(Enemy enemy) {
        enemies.add(enemy);
    }


    //public ArrayList<Shape> getBackgroundShapes() {
        //return backgroundShapesList;
    //}
    //public ArrayList<Shape> getForegroundShapes() {
        //return foregroundShapesList;
    //}

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
